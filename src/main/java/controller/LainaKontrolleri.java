package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.DAO;
import dao.IDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import model.AktiivinenKayttaja;
import model.Maksutapahtuma;
import model.Tili;
import model.Valuutta;
import view.GUI;
import view.IGUI;

/**
 * 
 * LainaKontrollerissa on toteutkset lainalle, sen laskemiselle ja lainasivun päivittämisen oikeilla tiedoilla
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */
public class LainaKontrolleri implements IKontrolleri {

	private IGUI gui = new GUI();
	private IDAO dao = new DAO();

	@FXML
	private Label takaisinBtn;
	@FXML
	private Label prosenttiLabel;
	
	@FXML
	private Label kuukausiEraLabel;
	
	@FXML
	private Label vanhaLainaLabel;
	
	@FXML
	private Label vanhaLainaMaaraLabel;

	@FXML
	private Slider lainaVahvistusBtn;

	@FXML
	private Slider lainaMaaraSlider;

	@FXML
	private TextField maaraTxt;

	@FXML
	private ChoiceBox<Integer> korkoBox;
	
	private Locale locale;
	private NumberFormat numberFormat;
	ResourceBundle rb;

	public LainaKontrolleri() {}
	
	/**
	 * palaaTakaisin() metodi vie näkymän sovelluksen etusivulle.
	 */
	@Override
	public void palaaTakaisin(MouseEvent e) throws IOException {
		gui.vaihdaNäkymä("etusivu.fxml");
	}

	/**
	 * asetaSliderArvo() metodi säätää lainanäkymän slideria. Metodi päivittää sliderin niin että se nousee tai laskee vähintään 50€
	 */
	public void asetaSliderArvo() {
		int arvo = 0;
		try {
			arvo = Integer.valueOf(maaraTxt.getText());
		} catch(Exception e) {
			Alert a = new Alert(AlertType.WARNING);
			a.setHeaderText(rb.getString("laiMaaraVirheHeader"));
			a.setContentText(rb.getString("laiMaaraVirheContent"));
			a.show();
			arvo = 50;
		}
		int modulo = arvo % 50;
		if (modulo == 0) {
			System.out.println("if1");
		} else if (modulo < 25) {
			arvo -= 50 + modulo - 50;
			System.out.println("if2");
		} else if (modulo >= 25) {
			arvo += 50 - modulo;
			System.out.println("if3");
		}
		System.out.println("Asetaarvo2");
		System.out.println(arvo);
		lainaMaaraSlider.setValue(0);
		lainaMaaraSlider.setValue(arvo);
	}
	
	/**
	 * asetaSliderArvo() metodi laskee ja päivittää lainan kuukautisen erän näkymässä.
	 */
	public void paivitaKuukausiEra() {
		double korkoprosentti = 0.15;
		int maksuaika = korkoBox.getSelectionModel().getSelectedItem();
		int lainasumma = 0;
		try {
			lainasumma = Integer.valueOf(maaraTxt.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		double kuukausiera = (lainasumma * maksuaika* korkoprosentti/12 + lainasumma) / maksuaika;
		numberFormat.setMaximumFractionDigits(2);
		kuukausiEraLabel.setText(numberFormat.format(kuukausiera) + " €");
	}
	
	/**
	 * kasitteleLaina() vaihtaa näkymän ja lisää lainan aktiiviselle käyttäjälle. 
	 * @throws IOException
	 */
	public void kasitteleLaina() throws IOException {
		Tili tili = AktiivinenKayttaja.getInstance().getTili();
		double vanhalaina = tili.getLaina();
		double uusilaina = 2500;
		try {
			uusilaina = Double.valueOf(maaraTxt.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		double uusisaldo = tili.getSaldo() + uusilaina;
		if(vanhalaina + uusilaina <= 2000) {
			dao.muutaLainaVelka(tili.getIban(), (vanhalaina + uusilaina));
			dao.muutaSaldo(tili.getIban(), uusisaldo);
			AktiivinenKayttaja.getInstance().setTili(dao.haeTiliIBAN(tili.getIban()));
			AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(null, korkoBox.getSelectionModel().getSelectedItem() , maaraTxt.getText(), kuukausiEraLabel.getText(), null, null, null, null, null));
			gui.vaihdaNäkymä("lainaVahvistaminen.fxml");
		} else {
			Alert a = new Alert(AlertType.WARNING);
			a.setHeaderText(rb.getString("laiMaaraVirheHeader"));
			a.setContentText(rb.getString("laiMaaraVirheContent"));
			a.show();
		}
	}

	/**
	 * initialize metodi ajetaan heti näkymän avautumassa. katsotaan onko käyttäjällä aikaisempia lainoja ja rajoitetaan lainan määrä sen mukaan. 
	 */
	@FXML
	void initialize() {
		locale = gui.getCurLocale();
		rb = gui.getBundle();
		numberFormat = NumberFormat.getInstance(locale);
		numberFormat.setMaximumFractionDigits(2);
		double vanhalaina = AktiivinenKayttaja.getInstance().getTili().getLaina();
		if(vanhalaina > 0) {vanhaLainaMaaraLabel.setText(numberFormat.format(vanhalaina) + " €");}
		else {
			vanhaLainaMaaraLabel.setText("");
			vanhaLainaLabel.setText("");
		}
		lainaMaaraSlider.setMax(2000 - vanhalaina);
		lainaMaaraSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				maaraTxt.setText(String.format("%d", new_val.intValue()));
				paivitaKuukausiEra();
			}
		});
		
		korkoBox.getItems().addAll(3, 6, 12);
		korkoBox.getSelectionModel().select(0);
		paivitaKuukausiEra();
		korkoBox.setOnAction((event) -> {
			paivitaKuukausiEra();
		});
	}
}
