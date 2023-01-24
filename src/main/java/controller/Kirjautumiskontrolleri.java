package controller;

import java.io.IOException;
import java.util.ResourceBundle;

import dao.DAO;
import dao.IDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import model.AktiivinenKayttaja;
import model.Tili;
import view.GUI;
import view.IGUI;
import view.ToggleSwitch;

/**
 * Kirjautumiskontrolleri luokassa on kaikki kirjautumis näkymän metodit ja
 * siihen liittyvät toiminnot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */
public class Kirjautumiskontrolleri {

	@FXML
	private Button kirjaudu;

	@FXML
	private ComboBox<Label> languageBox;

	@FXML
	private TextField tunnusluku;

	@FXML
	private PasswordField salasana;
	
	@FXML
	private Pane paneMain;

	private IGUI gui = new GUI();
	private IDAO dao = new DAO();

	ObservableList<Label> options = FXCollections.observableArrayList();
	ResourceBundle rb;

	public Kirjautumiskontrolleri() {
	}

	/**
	 * kirjauduSisaan metodi hakee tilin, tarkistaa tilin salasanan syötetystä
	 * merkkijonosta ja kirjautuu jos se on oikein. Metodi hakee tilin
	 * tietokannasta, jonka jälkeen se tarkistaa vastaako syötetty salasana tilin
	 * salasanaa, tämän jälkeen se asettaa aktiiseenKäyttäjään saadun tilin ja
	 * vaihtaa näkymää.
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void kirjauduSisaan(ActionEvent e) throws IOException {
		try {
			Tili tili = dao.haeTili(tunnusluku.getText().toString());

			if (tunnusluku.getText().toString().equals(tili.getTunnus())
					&& salasana.getText().toString().equals(tili.getSalasana())) {
				// TODO tee järkevimmin
				AktiivinenKayttaja.getInstance().setTili(tili);
				AktiivinenKayttaja.getInstance().alustus();
				// ...
				gui.vaihdaNäkymä("latausnakyma.fxml");
			} else {
				throw new Exception();
			}
		} catch (Exception virheTeksti) {
			Alert a = new Alert(AlertType.WARNING);
			a.setHeaderText(rb.getString("kirjVirheAlert"));
			a.show();
			System.out.println(virheTeksti);
		}

	}

	/**
	 * haeTili metodi hakee tilin dao:sta parametrista saadulla tunnuksella ja
	 * palauttaa tunnusta vastaavan tilin.
	 * 
	 * @param tunnus
	 * @return tili
	 */
	public Tili haeTili(String tunnus) {
		return dao.haeTili(tunnus);
	}

	/**
	 * initialize metodi ajetaan näkymän avautuessa. 
	 * metodissa kutsutaan alustaTeema() ja alustaKielibox() metodia ja kielilaatikolle asetetaan onAction metodi.
	 */
	@FXML
	public void initialize() {
		alustaTeema();
		rb = gui.getBundle();
		alustaKielibox();

		languageBox.setOnAction((Event) -> {
			Label kValittu = languageBox.getSelectionModel().getSelectedItem();
			gui.vaihdaKieli(kValittu.getText(), "kirjautuminen.fxml");
			languageBox.getSelectionModel().select(kValittu);

		});

	}
	
	/**
	 * alustaKieliBox metodi alustaa ja täyttää kielilaatikon
	 */
	private void alustaKielibox() {
		Image ukImg = new Image("/view/resources/img/uk.png"), finImg = new Image("/view/resources/img/finland.png"),
				sweImg = new Image("/view/resources/img/sweden.png"),
				spImg = new Image("/view/resources/img/spain.png");
		ImageView engView = new ImageView(), finView = new ImageView(), spView = new ImageView(),
				sweView = new ImageView();
		engView.setImage(ukImg);
		engView.setFitWidth(32);
		engView.setFitHeight(16);
		finView.setImage(finImg);
		finView.setFitWidth(32);
		finView.setFitHeight(16);
		spView.setImage(spImg);
		spView.setFitWidth(32);
		spView.setFitHeight(16);
		sweView.setImage(sweImg);
		sweView.setFitWidth(32);
		sweView.setFitHeight(16);
		
		Label lblEng = new Label("English"), lblFin = new Label("Suomi"), lblSp = new Label("Español"),
				lblSwe = new Label("Svenska");
		lblEng.setGraphic(engView);
		options.add(lblEng);
		lblFin.setGraphic(finView);
		options.add(lblFin);
		lblSp.setGraphic(spView);
		options.add(lblSp);
		lblSwe.setGraphic(sweView);
		options.add(lblSwe);
		
		languageBox.setItems(options);
		switch (rb.getLocale().getDisplayLanguage()) {
		case "suomi":
			languageBox.setValue(lblFin);
			break;
		case "español":
			languageBox.setValue(lblSp);
			break;
		case "svenska":
			languageBox.setValue(lblSwe);
			break;
		default:
			languageBox.setValue(lblEng);
			break;
		}

	}
	
	/**
	 * alustaTeema metodi luo napin listereineen, josta voi vaihtaa teemmaa.
	 */
	public void alustaTeema() {
		ToggleSwitch button = new ToggleSwitch();
		SimpleBooleanProperty isOn = button.switchOnProperty();
		
		isOn.addListener((observable, dark, color) -> {
			if (color) {
				button.getScene().getRoot().getStylesheets().remove(getClass().getResource("/view/resources/css/style.css").toString());
				button.getScene().getRoot().getStylesheets().add(getClass().getResource("/view/resources/css/color_style.css").toString());
				AktiivinenKayttaja.getInstance().setTeema("COLOR");
				System.out.println("COLOR");
				
			} else if (dark) {
				button.getScene().getRoot().getStylesheets().add(getClass().getResource("/view/resources/css/style.css").toString());
				button.getScene().getRoot().getStylesheets().remove(getClass().getResource("/view/resources/css/color_style.css").toString());
				AktiivinenKayttaja.getInstance().setTeema("DARK");
				System.out.println("DARK");
			}
		});
		paneMain.getChildren().add(button);
	}

}