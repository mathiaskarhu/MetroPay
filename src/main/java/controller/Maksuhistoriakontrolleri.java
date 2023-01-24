package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import dao.DAO;
import dao.IDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.AktiivinenKayttaja;
import model.Maksutapahtuma;
import view.GUI;
import view.IGUI;

/**
 * 
 * Maksuhistoriakontrolleri on tilihistoria -näkymään liitetty kontrolleri.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 * 
 */

public class Maksuhistoriakontrolleri implements IKontrolleri {

	@FXML
	private Label takaisinBtn;

	@FXML
	private Label saldoTxt;

	@FXML
	private Label ibanTxt;

	@FXML
	private TableView<Maksutapahtuma> tilihistoriaTab;

	@FXML
	private TableColumn<Maksutapahtuma, String> nimiCol;

	@FXML
	private TableColumn<Maksutapahtuma, Double> maaraCol;

	@FXML
	private TableColumn<Maksutapahtuma, String> valuuttaCol;
	
	@FXML
	private TableColumn<Maksutapahtuma, LocalDate> ajankohtaCol;

	@FXML
	private DatePicker suodatusPvm;

	@FXML
	private Button suodatusBtn;

	@FXML
	private Button naytaKaikkiBtn;

	private IGUI gui = new GUI();
	private IDAO dao = new DAO();
	
	private ArrayList<Maksutapahtuma> mlista = new ArrayList<Maksutapahtuma>();
	private ArrayList<Maksutapahtuma> suodatettumlista = new ArrayList<Maksutapahtuma>();
	private ObservableList<Maksutapahtuma> lista = FXCollections.observableArrayList();
	
	private Locale locale;
	private NumberFormat numberFormat;
	private ResourceBundle rb;

	/**
	 * 
	 * Asettaa Aktiivisen käyttäjän tilin saldon sivun yläreunassa olevaan saldo
	 * -tekstikenttään.
	 * 
	 * @param saldo aktiivisen käyttäjän pankkitilin saldo
	 * 
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
	 * 
	 * haeSaldo() metodi hakee aktiivisenkäyttäjän kryptovaluuttojen saldot.
	 * 
	 */

	public String haeSaldo() {
		String valuutta = AktiivinenKayttaja.getInstance().getValuutta().getTunnus();
		double saldo;
		switch (valuutta) {
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
		if (saldo == 0) {
			return numberFormat.format(saldo);
		} else if (AktiivinenKayttaja.getInstance().getValuutta().getKurssi() < 0.1) {
			numberFormat.setMaximumFractionDigits(6);
			return numberFormat.format(saldo);
		}
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(saldo);
	}

	/**
	 * 
	 * Asettaa Aktiivisen käyttäjän tilin IBAN -koodin sivun yläreunassa olevaan
	 * iban -tekstikenttään.
	 * 
	 * @param iban aktiivisen käyttäjän pankkitilin iban-koodi
	 * 
	 */
	
	public void asetaIban(String iban) {
		ibanTxt.setText(iban);
	}

	/**
	 * 
	 * Palaa takaisin etusivulle. Metodia kutsutaan klikkaamalla takaisin-nuolta.
	 * 
	 */
	
	public void palaaTakaisin(MouseEvent e) throws IOException {
		gui.vaihdaNäkymä("etusivu.fxml");
	}

	/**
	 * 
	 * Kutsuu asetaMaksuhistoria -metodia ja listaa mlista -maksutapahtumalistan
	 * maksutapahtumat tilihistoriaTabiin. Tyhjentää ensin tilihistoriaTab
	 * -tableviewin ja mlistan.
	 * 
	 */
	
	public void listaaMaksutapahtumat() {
		if (tilihistoriaTab != null)
			tilihistoriaTab.getItems().clear();
		mlista.clear();
		asetaMaksuhistoria();
		for (int i = 0; i < mlista.size(); i++) {
			System.out.println(mlista.get(i).toString());
			if (tilihistoriaTab != null) {
				if(onkoMaksaja(mlista.get(i))){
					tilihistoriaTab.getItems().add(mlista.get(i));
				}
				else {
					if(mlista.get(i).getTila()) {
						tilihistoriaTab.getItems().add(mlista.get(i));
					}
				}
			}
		}
	}

	/**
	 * 
	 * Suodattaa tilihistoriaTabin maksutapahtumat suodatuspvm -datepickeristä
	 * valitun päivämäärän perusteella. Hakee valitun päivämäärän datepickeristä ja
	 * kutsuu asetaMaksuhitoria -metodia. Hakee mlistasta aktiivisen käyttäjän
	 * maksutapahtumat, joidenka päivämääränä on suodatusPvm:stä valittu päviämäärä
	 * ja lisää ne suodatettumlistaan. Lisää suodatettumlistan sisällön
	 * tilihistoriaTabiin.
	 * 
	 */
	
	public void suodataMaksutapahtumat() {
		if (tilihistoriaTab != null)
			tilihistoriaTab.getItems().clear();
		suodatettumlista.clear();
		LocalDate pvm;
		if (suodatusPvm == null) {
			pvm = LocalDate.of(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
		} else {
			pvm = suodatusPvm.getValue();
		}
		asetaMaksuhistoria();

		for (int i = 0; i < mlista.size(); i++) {
			if (mlista.get(i).getAjankohta().toString().contains(pvm.toString())) {
				suodatettumlista.add(mlista.get(i));
			}
		}

		System.out.println("Suodatettu pvm mukaan:");
		for (int i = 0; i < suodatettumlista.size(); i++) {
			System.out.println(suodatettumlista.get(i).toString());
			if (tilihistoriaTab != null)
				tilihistoriaTab.getItems().add(suodatettumlista.get(i));
		}
	}

	/**
	 * 
	 * Lisää aktiivisen käyttäjän maksuhistorian mlistaan, ja muotoilee ne
	 * muotoileTapahtumat -metodilla.
	 * 
	 */
	
	private void asetaMaksuhistoria() {
		mlista = dao.haeMaksuhistoria(AktiivinenKayttaja.getInstance().getTili().getIban());
		muotoileTapahtumat();
		System.out.println(mlista);
	}

	/**
	 * 
	 * muotoileTapahtumat -metodi muuttaa mlistan maksutapahtumien menot näkymään
	 * negatiivisina arvoina ja pyöristää alle 0.01 summat kuuden, ja muut summat
	 * kahden desimaalin tarkkuuteen. Kaikki summat muunnetaan aktiivisen käyttäjän
	 * käytössä olevaan valuuttaan. Menoja ovat tapahtumat, joissa maksajan
	 * IBAN-koodi on aktiivisen käyttäjän IBAN.
	 * 
	 */
	
	private void muotoileTapahtumat() {
		for (int i = 0; i < mlista.size(); i++) {
			System.out.println("mlistasta: " + mlista.get(i).getMaara());
			double maara = mlista.get(i).getMaara();
			if (onkoMaksaja(mlista.get(i))) {
				if (maara < 0.01) {
					mlista.get(i).setMaara(-maara);
				} else {
					mlista.get(i).setMaara(-maara);
				}
			} else {
				String nimi = mlista.get(i).getMaksajaIban().getAsiakasID().getEtunimi();
				mlista.get(i).setNimi(nimi);
			}
		}
	}

	/**
	 * 
	 * Näyttää alertilla käyttäjän tilihistoriaTabista valitseman maksutapahtuman
	 * tarkemmat tiedot. Alertit muotoillaan sen mukaan, onko tapahtuma meno vai
	 * tulo.
	 * 
	 * @param t
	 * 
	 */
	
	private void klikkaaRivia(Maksutapahtuma t) {
		System.out.println(t.getViesti());
		Alert a = new Alert(AlertType.INFORMATION);
		String viite;
		if(t.getViite() == null) viite = "";
		else viite = t.getViite();
		if (onkoMaksaja(t)) {
			a.setHeaderText(rb.getString("TiliAlertMeno"));
			a.setContentText(rb.getString("makSaajaLabel")+ ": " + t.getNimi() + "\n" 
			+ rb.getString("makSaajaIbanLabel")+ ": " +  t.getVastaanottajaIban().getIban() +"\n"
					+ rb.getString("makEraLabel")+ ": " + t.getAjankohta() +"\n"
					+ rb.getString("makViiteLabel")+ ": "+ viite +"\n"
					+ rb.getString("makViestiLabel")+ ": " + t.getViesti() +"\n"
					+ rb.getString("makMaaraLabel")+ ": " + numberFormat.format(t.getMaara()) +" "+ t.getValuutta().getTunnus());
		} else {
			a.setHeaderText(rb.getString("TiliAlertTulo"));
			a.setContentText(rb.getString("TiliMaksajaLabel")+": " + t.getMaksajaIban().getAsiakasID().getEtunimi() +"\n"
					+ rb.getString("TiliMaksajaIbanLabel")+ ": " + t.getMaksajaIban().getIban() +"\n"
					+ rb.getString("makEraLabel")+ ": " + t.getAjankohta() +"\n"
					+ rb.getString("makViiteLabel")+ ": "+ viite +"\n"
					+ rb.getString("makViestiLabel")+ ": " + t.getViesti() +"\n"
					+ rb.getString("makMaaraLabel")+ ": " + numberFormat.format(t.getMaara()) +" "+ t.getValuutta().getTunnus());
		}
		a.showAndWait();
	}

	private boolean onkoMaksaja(Maksutapahtuma t) {
		if (t.getMaksajaIban().getIban().equals(AktiivinenKayttaja.getInstance().getTili().getIban())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Käynnistää tilihistoria -näkymän lisäämällä näyttämällä käyttäjän saldon ja
	 * valitun valuutan tunnuksen tekstikentissä. Saldo esitetään kahden- tai kuuden
	 * desimaalin tarkkuudella riippuen valitun valuutan kurssin kertoimen
	 * suuruudesta. Saldo näytetään asetaSaldo -metodilla. Alustaa myös
	 * tilihistoriaTabin kolumnit maksutapahtuman attribuuteja varten oikeisiin
	 * tietomuotoihin...
	 * 
	 */
	
	@FXML
	void initialize() {
		System.out.println("initialize");
		
		locale = gui.getCurLocale();
		System.out.println(locale.getDisplayCountry());
		numberFormat = NumberFormat.getInstance(locale);
		numberFormat.setMinimumFractionDigits(2);
		rb = gui.getBundle();

		asetaSaldo(haeSaldo());
		asetaIban(AktiivinenKayttaja.getInstance().getTili().getIban());
		nimiCol.setCellValueFactory(new PropertyValueFactory<Maksutapahtuma, String>("nimi"));
		maaraCol.setCellValueFactory(new PropertyValueFactory<Maksutapahtuma, Double>("maara"));
		maaraCol.setCellFactory(tc -> new TableCell<Maksutapahtuma, Double>() {
		    protected void updateItem(Double maara, boolean empty) {
		        super.updateItem(maara, empty);
		        numberFormat.setMaximumFractionDigits(6);
		        if (empty) {
		            setText(null);
		        } else {
		            setText(numberFormat.format(maara));
		        }
		    }
		});
		valuuttaCol.setCellValueFactory(new PropertyValueFactory<Maksutapahtuma, String>("valuutta"));
		ajankohtaCol.setCellValueFactory(new PropertyValueFactory<Maksutapahtuma, LocalDate>("ajankohta"));
		tilihistoriaTab.setItems(lista);
		listaaMaksutapahtumat();

		tilihistoriaTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			klikkaaRivia(newSelection);
		});
	}
}
