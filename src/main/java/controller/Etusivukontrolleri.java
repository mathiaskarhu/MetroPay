package controller;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import dao.DAO;
import dao.IDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.AktiivinenKayttaja;
import model.Maksutapahtuma;
import model.Tili;
import model.Valuutta;
import view.GUI;
import view.IGUI;
import view.ToggleSwitch;
/**
 * Etusivukontrollerissa on kaikki etusivu näkymään liittyvät metodit sekä toiminnot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */
public class Etusivukontrolleri {

	@FXML
	private Label saldoTxt;

	@FXML
	private Button maksuBtn;

	@FXML
	private Button tilihistoriaBtn;
	
	@FXML
	private Button kryptoBtn;

	@FXML
	private Button tiedotBtn;

	@FXML
	private ImageView ulosBtn;

	@FXML
	private ChoiceBox<Valuutta> valuuttaBox;
	@FXML
	private ComboBox<Label> languageBox;
	private IGUI gui = new GUI();
	private IDAO dao = new DAO();

	private ValuuttaKontrolleri vKontrolleri = new ValuuttaKontrolleri();

	private ArrayList<Valuutta> valuuttaLista;
	
	private ObservableList<Label> options = FXCollections.observableArrayList();
	
	private static int valuuttaBoxIndex = 9;
	private Locale locale;
	private NumberFormat numberFormat;
	public Etusivukontrolleri() {
		// System.out.println("TÄÄLLÄ");
	}

	/**
	 * haeSaldo Metodi palauttaa aktiivisen käyttäjän saldon ja muuntaa sen String
	 * muotoon
	 * 
	 * @return Palauttaa saldon String muodossa
	 */
	public String haeSaldo() {
		String valuutta = AktiivinenKayttaja.getInstance().getValuutta().getTunnus();
		double saldo;
		switch(valuutta) {
			case "BTC":
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoBtc();
				break;
			case "DOGE":
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoDoge();
				break;
			case "ADA":
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoAda();
				break;
			case "ETH":
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoEth();
				break;
			default:
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo();
				saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
				break;
		}
		if(saldo == 0) {
			numberFormat.setMinimumFractionDigits(2);
			return numberFormat.format(saldo);
		} else if (AktiivinenKayttaja.getInstance().getValuutta().getKurssi() < 0.1) {
			numberFormat.setMaximumFractionDigits(6);
			return numberFormat.format(saldo);
		}
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(saldo);
	}

	/**
	 * asetaSaldo asettaa käyttäjän saldon etusivun saldoTxt label kenttään
	 * 
	 * @param saldo
	 */
	public void asetaSaldo(String saldo) {
		String valuutta = AktiivinenKayttaja.getInstance().getValuutta().getTunnus();
		switch (valuutta) {
		case "BTC":
			saldoTxt.setText("₿ " + saldo);
			break;
		case "DOGE":
			saldoTxt.setText("Ð " + saldo);
			break;
		case "ADA":
			saldoTxt.setText("₳ " + saldo);
			break;
		case "ETH":
			saldoTxt.setText("Ξ " + saldo);
			break;
			
		case "AUD":
			saldoTxt.setText("$ " + saldo);
			break;
		case "CHF":
			saldoTxt.setText("fr " + saldo);
			break;
		case "CLP":
			saldoTxt.setText("$ " + saldo);
			break;
		case "CNY":
			saldoTxt.setText("¥ " + saldo);
			break;
		case "DKK":
			saldoTxt.setText("kr " + saldo);
			break;
		case "EUR":
			saldoTxt.setText("€ " + saldo);
			break;
		case "GBP":
			saldoTxt.setText("£ " + saldo);
			break;
		case "JPY":
			saldoTxt.setText("¥ " + saldo);
			break;
		case "MXN":
			saldoTxt.setText("$ " + saldo);
			break;
		case "NOK":
			saldoTxt.setText("kr " + saldo);
			break;
		case "SEK":
			saldoTxt.setText("kr " + saldo);
			break;
		case "THB":
			saldoTxt.setText("฿ " + saldo);
			break;
		case "USD":
			saldoTxt.setText("$ " + saldo);
			break;
		default:
			saldoTxt.setText(saldo);
			break;
		}
	}

	/**
	 * uusiMaksu metodi kutsuu gui:n vaihdanäkymä metodia, joka vaihtaa sovelluksen näkymän.
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void uusiMaksu(ActionEvent e) throws IOException {
		gui.vaihdaNäkymä("maksaminen.fxml");
	}

	/**
	 * tilihistoria metodi kutsuu gui:n vaihdanäkymä metodia, joka vaihtaa sovelluksen näkymän.
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void tilihistoria(ActionEvent e) throws IOException {
		gui.vaihdaNäkymä("tilihistoria.fxml");
	}
	
	public void krypto(ActionEvent e) throws IOException {
		gui.vaihdaNäkymä("crypto.fxml");
	}
	
	public void laina(ActionEvent e) throws IOException {
		gui.vaihdaNäkymä("laina.fxml");
	}
	
	/**
	 * tiedot metodi kutsuu gui:n vaihdanäkymä metodia, joka vaihtaa sovelluksen näkymän.
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void tiedot(ActionEvent e) throws IOException {
		gui.vaihdaNäkymä("tiedot.fxml");
	}

	/**
	 * kirjauduUlos metodi kutsuu gui:n vaihdanäkymä metodia, joka vaihtaa sovelluksen näkymän.
	 * Lisäksi metodi asettaa aktiiivsen käyttäjän tilin null tilaan.
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void kirjauduUlos(MouseEvent e) throws IOException {
		AktiivinenKayttaja.getInstance().setTili(null);
		gui.vaihdaNäkymä("kirjautuminen.fxml");
	}
	
	public void tarkistaMaksut() {
		ArrayList<Maksutapahtuma> maksut = dao.haeOdottavatMaksut(AktiivinenKayttaja.getInstance().getTili().getIban(), LocalDate.now());
		for (Maksutapahtuma mtapahtuma: maksut) {
			suoritaOdottavaMaksu(mtapahtuma);
		}
	}
	
	public void suoritaOdottavaMaksu(Maksutapahtuma m) {
		Tili vastaanottaja = m.getVastaanottajaIban();
		Double maara = m.getMaara();
		
		switch (m.getValuutta().getTunnus()) {
		case "BTC":
			vastaanottaja.setSaldoBtc(vastaanottaja.getSaldoBtc() + maara);
			dao.muutaKryptoSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldoBtc(), "btc");
			break;
		case "DOGE":
			vastaanottaja.setSaldoDoge(vastaanottaja.getSaldoDoge() + maara);
			dao.muutaKryptoSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldoDoge(), "doge");
			break;
		case "ADA":
			vastaanottaja.setSaldoAda(vastaanottaja.getSaldoAda() + maara);
			dao.muutaKryptoSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldoAda(), "ada");
			break;
		case "ETH":
			vastaanottaja.setSaldoEth(vastaanottaja.getSaldoEth() + maara);
			dao.muutaKryptoSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldoEth(), "eth");
			break;
		default:
			vastaanottaja.setSaldo(vastaanottaja.getSaldo() + maara);
			dao.muutaSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldo());
			break;
		}
		m.setTila(true);
		dao.merkkaaMaksetuksi(m.getTapahtuma_ID());
	}
	
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
		switch (locale.getDisplayLanguage()) {
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

	public void setNumberFormat(NumberFormat numberFormat) {
		this.numberFormat = numberFormat;
	}

	/**
	 * initialize metodi ajetaan heti näkymän avautumassa. Metodi kutsuu asetaSaldo() metodia ja
	 * alustaa valuutan.
	 */
	@FXML
	void initialize() {
		System.out.println("initialize");
		locale = gui.getCurLocale();
		System.out.println(locale.getDisplayCountry());
		numberFormat = NumberFormat.getInstance(locale);
		alustaKielibox();
		tarkistaMaksut();
		asetaSaldo(haeSaldo());
		valuuttaLista = vKontrolleri.alustaValuutat();
		
		for (int i = 0; i < valuuttaLista.size(); i++) {
			valuuttaBox.getItems().add(valuuttaLista.get(i));
			if(valuuttaLista.get(i).getTunnus().equals(AktiivinenKayttaja.getInstance().getValuutta().getTunnus())) {
				valuuttaBox.getSelectionModel().select(i);
			}
		}
		/**
		 * Valuutan vaihto metodi
		 */
		valuuttaBox.setOnAction((event) -> {
			Valuutta vValittu = valuuttaBox.getSelectionModel().getSelectedItem();
			valuuttaBoxIndex = valuuttaBox.getSelectionModel().getSelectedIndex();
			AktiivinenKayttaja.getInstance().setValuutta(vValittu);
			asetaSaldo(haeSaldo());
		});
		languageBox.setOnAction((Event) -> {
			Label kValittu = languageBox.getSelectionModel().getSelectedItem();
			gui.vaihdaKieli(kValittu.getText(), "etusivu.fxml");
			languageBox.getSelectionModel().select(kValittu);

		});
	}
	

}
