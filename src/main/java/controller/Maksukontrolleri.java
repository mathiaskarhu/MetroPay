package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import dao.DAO;
import dao.IDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import model.AktiivinenKayttaja;
import model.Maksupohja;
import model.Maksutapahtuma;
import model.Tili;
import model.Valuutta;
import view.GUI;
import view.IGUI;

/**
 * 
 * Maksukontrolleri luokassa on kaikki uusi maksu näkymän metodit ja
 * siihen liittyvät toiminnot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class Maksukontrolleri implements IKontrolleri {

	@FXML
	private Label takaisinBtn;

	@FXML
	private Label saldoTxt;

	@FXML
	private TextField ibanTxt;

	@FXML
	private TextField nimiTxt;

	@FXML
	private TextField viitenroTxt;

	@FXML
	private TextArea viestiTxt;

	@FXML
	private DatePicker pvTxt;

	@FXML
	private TextField maaraTxt;

	@FXML
	private ChoiceBox<Valuutta> valuuttaBox;

	@FXML
	private ChoiceBox<String> maksupohjaBox;

	@FXML
	private Slider maksaBtn;

	@FXML
	private ImageView tallennaBtn;

	@FXML
	private ImageView poistaBtn;

	private IGUI gui = new GUI();
	private IDAO dao = new DAO();

	private ValuuttaKontrolleri vKontrolleri = new ValuuttaKontrolleri();

	private ArrayList<Valuutta> valuuttaLista;

	private static int valuuttaBoxIndex = 7;
	
	private Locale locale;
	
	private NumberFormat numberFormat;
	
	private ObservableList<String> maksupohjaLista = FXCollections.observableArrayList();
	
	private ArrayList<Maksupohja> mplista;

	private ButtonType okButton;
	private ButtonType cancelButton;

	private ResourceBundle rb;

	/**
	 * 
	 * palaaTakaisin metodi kutsuu gui:n vaihdanäkymä metodia, joka vaihtaa
	 * sovelluksen näkymän.
	 * 
	 * @param e
	 * @throws IOException
	 * 
	 */
	
	public void palaaTakaisin(MouseEvent e) throws IOException {
		gui.vaihdaNäkymä("etusivu.fxml");
	}

	/**
	 * 
	 * maksa metodi hakee kentistä tiedot ja kutsuu suoritaMaksu,
	 * kirjaaMaksutapahtuma, AktiivinenKayttaja.asetamaksu ja vaihdanäkymä metodeja.
	 * 
	 * @param e
	 * @throws IOException
	 * 
	 */
	
	public void maksa(MouseEvent e) throws IOException {
		try {
			Tili maksajanTili = AktiivinenKayttaja.getInstance().getTili();
			Tili vastaanottajanTili = haeTili(ibanTxt.getText());
			// double vKurssi = AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			double maksunMaara = Double.parseDouble(maaraTxt.getText());
			Valuutta valittuValuutta = AktiivinenKayttaja.getInstance().getValuutta();
			System.out.println("Maksun määrä: " + maksunMaara);
			LocalDate pvm = pvTxt.getValue();
			Maksutapahtuma mtapahtuma = null;

			if (maksunMaara < 0)
				throw new Exception("Negatiivinen maksun määrä!");
			Boolean tila = false;

			mtapahtuma = new Maksutapahtuma(pvm, maksunMaara, viitenroTxt.getText(), viestiTxt.getText(),
					nimiTxt.getText(), vastaanottajanTili, maksajanTili, valittuValuutta, tila);

			if (suoritaMaksu(maksajanTili, vastaanottajanTili, maksunMaara, valittuValuutta, pvm, mtapahtuma)) {
				kirjaaMaksutapahtuma(mtapahtuma);
			}
			AktiivinenKayttaja.getInstance().asetaMaksu(mtapahtuma);
			gui.vaihdaNäkymä("maksuvahvistaminen.fxml");

		} catch (Exception virheTeksti) {
			Alert a = new Alert(AlertType.WARNING);
			a.setHeaderText(rb.getString("makMaksuVirheHeader"));
			a.setContentText(rb.getString("makMaksuVirheContent"));
			a.show();
			System.out.println(virheTeksti);
		}
	}

	/**
	 * 
	 * kirjaaMaksutapahtuma metodi kutsuu dao luokan luoMaksutapahtuma metodia,
	 * jossa luodaan maksutapahtuma tietokantaan.
	 * 
	 * @param m
	 * 
	 */
	
	public void kirjaaMaksutapahtuma(Maksutapahtuma m) {
		dao.luoMaksutapahtuma(m);
	}

	/**
	 * 
	 * haeTili hakee tilin tekee kutsun ibanin avulla ja palautaa tilin.
	 * 
	 * @param iban
	 * @return
	 * 
	 */
	
	public Tili haeTili(String iban) {
		return dao.haeTiliIBAN(iban);
	}

	/**
	 * 
	 * suoritaMaksu metodi suorittaa 2 dao:n muutaSaldo metodia, jossa maksajalta
	 * vähennetään rahaa saajalle
	 * 
	 * @param maksaja
	 * @param vastaanottaja
	 * @param maara
	 * 
	 */
	
	public boolean suoritaMaksu(Tili maksaja, Tili vastaanottaja, double maara, Valuutta valittuValuutta, LocalDate pvm, Maksutapahtuma mtapahtuma){
		System.out.println("Suoritamaksu START:"); // Testataan päivittämistä
		LocalDate current = LocalDate.now();
		System.out.println("1");
		switch(valittuValuutta.getTunnus()) {
			case "BTC":
				if(maksaja.getSaldoBtc() - maara < 0) {
					return false;
				}
				maksaja.setSaldoBtc(maksaja.getSaldoBtc() - maara);
				dao.muutaKryptoSaldo(maksaja.getIban(), maksaja.getSaldoBtc(), "btc");
				if(pvm.isBefore(current) || pvm.equals(current)) {
					vastaanottaja.setSaldoBtc(vastaanottaja.getSaldoBtc() + maara);
					dao.muutaKryptoSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldoBtc(), "btc");
					mtapahtuma.setTila(true);
				}
				break;
			case "DOGE":
				if(maksaja.getSaldoDoge() - maara < 0) {
					return false;
				}
				maksaja.setSaldoDoge(maksaja.getSaldoDoge() - maara);
				dao.muutaKryptoSaldo(maksaja.getIban(), maksaja.getSaldoDoge(), "doge");
				if(pvm.isBefore(current) || pvm.equals(current)) {
					vastaanottaja.setSaldoDoge(vastaanottaja.getSaldoDoge() + maara);
					dao.muutaKryptoSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldoDoge(), "doge");
					mtapahtuma.setTila(true);
				}
				break;
			case "ADA":
				if(maksaja.getSaldoAda() - maara < 0) {
					return false;
				}
				maksaja.setSaldoAda(maksaja.getSaldoAda() - maara);
				dao.muutaKryptoSaldo(maksaja.getIban(), maksaja.getSaldoAda(), "ada");
				if(pvm.isBefore(current) || pvm.equals(current)) {
					vastaanottaja.setSaldoAda(vastaanottaja.getSaldoAda() + maara);
					dao.muutaKryptoSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldoAda(), "ada");
					mtapahtuma.setTila(true);
				}
				break;
			case "ETH":
				if(maksaja.getSaldoEth() - maara < 0) {
					return false;
				}
				maksaja.setSaldoEth(maksaja.getSaldoEth() - maara);
				dao.muutaKryptoSaldo(maksaja.getIban(), maksaja.getSaldoEth(), "eth");
				if(pvm.isBefore(current) || pvm.equals(current)) {
					vastaanottaja.setSaldoEth(vastaanottaja.getSaldoEth() + maara);
					dao.muutaKryptoSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldoEth(), "eth");
					mtapahtuma.setTila(true);
				}
				break;
			default:
				System.out.println("2");
				if(maksaja.getSaldo() - maara < 0) {
					return false;
				}
				System.out.println("3");
				maara = maara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
				System.out.println("4");
				maksaja.setSaldo(maksaja.getSaldo() - maara);
				System.out.println("5");
				dao.muutaSaldo(maksaja.getIban(), maksaja.getSaldo());
				System.out.println("6");
				if(pvm.isBefore(current) || pvm.equals(current)) {
					System.out.println("7");
					vastaanottaja.setSaldo(vastaanottaja.getSaldo() + maara);
					dao.muutaSaldo(vastaanottaja.getIban(), vastaanottaja.getSaldo());
					mtapahtuma.setTila(true);
				}
				System.out.println("8");
				break;
		}
		System.out.println("Suoritamaksu END:");
		return true;
	}

	/**
	 * 
	 * muutaSaldoa metodi muuttaa käyytöliittymässä näkyvää saldon määrää. Saldon
	 * määrä muuttuu kun maksun määrää syöttää numeroita.
	 * 
	 */
	
	public void muutaSaldoa() {
		try {
			double maksunMaara;
			if (maaraTxt.getText() == "") {
				maksunMaara = 0;
			} else {
				if (Double.parseDouble(maaraTxt.getText()) < 0) {
					throw new Exception();
				}
				maksunMaara = Double.parseDouble(maaraTxt.getText());
			}
			asetaSaldo(maksunMaara);
		} catch (Exception e) {
			System.out.println("Virheellinen saldon määrä");
		}
	}

	/**
	 * 
	 * asetaSaldo metodi asettaa Aktiivisen käyttäjän valuutan, valuutan symbolin sekä saldon
	 * näkyville.
	 * 
	 * @param saldo
	 * 
	 */
	
	public void asetaSaldo(double maksunMaara) {
		String valuutta = AktiivinenKayttaja.getInstance().getValuutta().getTunnus();
		double saldo;
		switch (valuutta) {
		
		case "BTC":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoBtc() - maksunMaara;
			saldoTxt.setText("₿ " + numberFormat.format(saldo));
			break;
			
		case "DOGE":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoDoge() - maksunMaara;
			saldoTxt.setText("Ð " + numberFormat.format(saldo));
			break;
			
		case "ADA":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoAda() - maksunMaara;
			saldoTxt.setText("₳ " + numberFormat.format(saldo));
			break;
			
		case "ETH":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoEth() - maksunMaara;
			saldoTxt.setText("Ξ " + numberFormat.format(saldo));
			break;
			
		case "EUR":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("€ " + numberFormat.format(saldo));
			break;
			
		case "USD":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("$ " + numberFormat.format(saldo));
			break;
			
		case "GBP":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("£ " + numberFormat.format(saldo));
			break;
			
		case "SEK":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("kr " + numberFormat.format(saldo));
			break;
			
		case "JPY":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("¥ " + numberFormat.format(saldo));
			break;
			
		case "THB":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("฿ " + numberFormat.format(saldo));
			break;
			
		case "NOK":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("kr " + numberFormat.format(saldo));
			break;
			
		case "MXN":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("$ " + numberFormat.format(saldo));
			break;
			
		case "DKK":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("kr " + numberFormat.format(saldo));
			break;
			
		case "CNY":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("¥ " + numberFormat.format(saldo));
			break;
			
		case "CLP":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("$ " + numberFormat.format(saldo));
			break;
			
		case "CHF":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("fr " + numberFormat.format(saldo));
			break;
			
		case "AUD":
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
			- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldoTxt.setText("$ " + numberFormat.format(saldo));
			break;
			
		default:
			saldo = AktiivinenKayttaja.getInstance().getTili().getSaldo()
					- maksunMaara / AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			saldo = saldo * AktiivinenKayttaja.getInstance().getValuutta().getKurssi();
			break;
		}
		
		if (saldo == 0) {
			numberFormat.setMinimumFractionDigits(2);
		} else if (AktiivinenKayttaja.getInstance().getValuutta().getKurssi() < 0.1) {
			numberFormat.setMaximumFractionDigits(6);
		} else {
			numberFormat.setMaximumFractionDigits(2);
		}
	}

	/**
	 * 
	 * haeMaksutapahtuma metodi hakee dao:sta maksutapahtuman parametrin tapahtumaID
	 * avulla.
	 * 
	 * @param tapahtumaID
	 * @return
	 * 
	 */
	
	public Maksutapahtuma haeMaksutapahtuma(int tapahtumaID) {
		return dao.haeMaksutapahtuma(tapahtumaID);
	}

	/**
	 * 
	 * poistaMaksutapahtuma metodi tekee kutsun daoon, joka poistaa tietkannasta
	 * maksutapahtuman parametrin tapahtumaID avulla.
	 * 
	 * @param tapahtumaID
	 * 
	 */
	
	public void poistaMaksutapahtuma(int tapahtumaID) {
		dao.poistaMaksutapahtuma(tapahtumaID);

	}
	
	/**
	 * 
	 * haeMaksupohjat() metodi hakee aktiivisenkäyttäjän maksupohjat tietokannasta ja asettaa ne maksupohja valikkoon.
	 * 
	 */

	public void haeMaksupohjat() {
		maksupohjaBox.getItems().clear();
		maksupohjaLista.clear();
		mplista = dao.haeMaksupohjat(AktiivinenKayttaja.getInstance().getTili().getIban());
		maksupohjaLista.add(rb.getString("makMaksupohjaHolder"));
		for (int i = 0; i < mplista.size(); i++) {
			maksupohjaLista.add(mplista.get(i).getNimi());
		}
		System.out.println();
		maksupohjaBox.getItems().addAll(maksupohjaLista);
		maksupohjaBox.getSelectionModel().select(0);

		System.out.println(mplista + "\n" + maksupohjaLista);
	}
	
	/**
	 * 
	 * valitseMaksupohjat() asettaa valitun maksupohjan, 
	 * kutsuu taytaKentat() metodia, joka asettaa maksupohjan tiedot.
	 * 
	 * @param nimi
	 * 
	 */

	public void valitseMaksupohja(String nimi) {
		Maksupohja tyhja = new Maksupohja(null, null, null, null, null, null, null, null);
		if (nimi.equals(rb.getString("makMaksupohjaHolder"))) {
			taytaKentat(tyhja);
		}
		
		for (int i = 0; i < mplista.size(); i++) {
			if (mplista.get(i).getNimi().equals(nimi)) {
				taytaKentat(mplista.get(i));
				break;
			}
		}
	}
	
	/**
	 * 
	 * taytaKentat() metodi täyttää maksukentät maksupohjan tiedoilla.
	 * 
	 * @param mp
	 * 
	 */

	public void taytaKentat(Maksupohja mp) {

		ibanTxt.setText(mp.getVastaanottajaiban());

		nimiTxt.setText(mp.getVastaanottajaNimi());

		viitenroTxt.setText(mp.getViite());

		viestiTxt.setText(mp.getViesti());
		if (mp.getAjankohta() != null) {
			pvTxt.setValue(LocalDate.parse(mp.getAjankohta()));
		} else {
			pvTxt.setValue(null);
		}
		maaraTxt.setText(mp.getMaara());

	}
	
	/**
	 * 
	 * pyydaMaksuPohjaNimi() metodi pyytää käyttäjän syöttämään haluamansa maksupohjan nimen tekstikenttään, kutsuu
	 * tallennaMaksupohja() metodia, joka tallentaa "nimi" syötteen maksupohjan nimeksi.
	 * 
	 * @param ae
	 * 
	 */

	public void pyydaMaksuPohjaNimi(MouseEvent ae) {
		TextInputDialog nimiInput = new TextInputDialog();
		nimiInput.setHeaderText(rb.getString("makMaksupohjaHeader"));
		Optional<String> result = nimiInput.showAndWait();
		if (!result.isPresent()) {
			return;
		}
		try {
			String syote = nimiInput.getEditor().getText();
			if (syote.equals("")) {
				Alert a = new Alert(AlertType.WARNING);
				a.setHeaderText(rb.getString("makMaksupohjaVirheHeader"));
				a.setContentText("makMaksupohjaVirheContent");
				a.show();
			}
			tallennaMaksupohja(syote);
		} catch (Exception e) {
			System.out.println("tallennus peruutettu!");
		}
	}
	
	/**
	 * 
	 * tallennaMaksupohja() metodi tallentaa saaduista tiedoista maksupohjan.
	 * 
	 * @param nimi
	 * 
	 */

	public void tallennaMaksupohja(String nimi) {
		for (int i = 0; i < maksupohjaLista.size(); i++) {
			if (maksupohjaLista.get(i).equals(nimi)) {
				Alert a = new Alert(AlertType.WARNING);
				a.setHeaderText(rb.getString("makMaksupohjaLoytyyVirheHeader"));
				a.setContentText(rb.getString("makMaksupohjaLoytyyVirheContent"));
				a.show();
				return;
			}
		}
		Maksupohja mp;
		if (pvTxt.getValue() == null) {
			mp = new Maksupohja(nimi, null, maaraTxt.getText(), viitenroTxt.getText(), viestiTxt.getText(),
					nimiTxt.getText(), ibanTxt.getText(), AktiivinenKayttaja.getInstance().getTili().getIban());

		} else {
			mp = new Maksupohja(nimi, pvTxt.getValue().toString(), maaraTxt.getText(), viitenroTxt.getText(),
					viestiTxt.getText(), nimiTxt.getText(), ibanTxt.getText(),
					AktiivinenKayttaja.getInstance().getTili().getIban());

		}
		dao.luoMaksupohja(mp);

		Alert a = new Alert(AlertType.INFORMATION);
		a.setHeaderText(rb.getString("makMaksupohjaTallennettuHeader"));
		a.setContentText(rb.getString("makMaksupohjaTallennettuContent"));
		a.show();
		haeMaksupohjat();

	}
	
	/**
	 * 
	 * poista() metodia käytetään "roskakori" painikkeessa. Metodi kutsuu
	 * poistaMaksupohja() metodia poistaakseen käyttäjän valitseman maksupohjan.
	 * 
	 * @param ae
	 * 
	 */

	public void poista(MouseEvent ae) {
		String nimi = maksupohjaBox.getSelectionModel().getSelectedItem();
		for (int i = 0; i < mplista.size(); i++) {
			if(mplista.get(i).getNimi().equals(nimi) && !mplista.get(i).getNimi().equals("Valitse maksupohja")) {
				poistaMaksupohja(mplista.get(i).getTapahtuma_id());
				return;
			}
		}
		Alert a = new Alert(AlertType.WARNING);
		a.setHeaderText(rb.getString("makMaksupohjaPoistoVirheHeader"));
		a.setContentText(rb.getString("makMaksupohjaPoistoVirheContent"));
		a.show();
	}
	
	/**
	 * 
	 * poistaMaksupohja() metodi poistaa maksupohjan sen id:n perusteella.
	 * 
	 * @param id
	 * 
	 */

	public void poistaMaksupohja(int id) {
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.setHeaderText(rb.getString("makMaksupohjaPoistoHeader"));
		a.setContentText(rb.getString("makMaksupohjaPoistoContent"));
		a.getButtonTypes().setAll(okButton, cancelButton);
		a.showAndWait().ifPresent(response -> {
			if (response.getText() == rb.getString("makMaksupohjaPeruutaButton")) {
		         return;
		     } else if (response.getText() == rb.getString("makMaksupohjaKyllaButton")) {
		    	 dao.poistaMaksupohja(id);
		    	 haeMaksupohjat();
		     }
		});
	}

	/**
	 * 
	 * initialize metodi ajetaan heti näkymän avautumassa. Metodi kutsuu
	 * asetaSaldo() metodia ja hakee aktiivisen käyttäjän valuutan.
	 * 
	 */
	
	@FXML
	void initialize() {
		System.out.println("initialize");
		locale = gui.getCurLocale();
		System.out.println(locale.getDisplayCountry());
		numberFormat = NumberFormat.getInstance(locale);
		rb = gui.getBundle();
		okButton = new ButtonType(rb.getString("makMaksupohjaKyllaButton"));
		cancelButton = new ButtonType(rb.getString("makMaksupohjaPeruutaButton"));
		asetaSaldo(0);
		haeMaksupohjat();

		maksupohjaBox.valueProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) valitseMaksupohja(newVal);
		});
		
		ibanTxt.setText(null);
		viestiTxt.setText(null);
		viitenroTxt.setText(null);
		maaraTxt.setText(null);
		nimiTxt.setText(null);
		
		valuuttaLista = vKontrolleri.alustaValuutat();

		for (int i = 0; i < valuuttaLista.size(); i++) {
			valuuttaBox.getItems().add(valuuttaLista.get(i));
			if(valuuttaLista.get(i).getTunnus().equals(AktiivinenKayttaja.getInstance().getValuutta().getTunnus())) {
				valuuttaBox.getSelectionModel().select(i);
			}
		}
		
		/**
		 * 
		 * Valuutan vaihto metodi
		 * 
		 */
		
		valuuttaBox.setOnAction((event) -> {
			Valuutta vValittu = valuuttaBox.getSelectionModel().getSelectedItem();
			valuuttaBoxIndex = valuuttaBox.getSelectionModel().getSelectedIndex();
			AktiivinenKayttaja.getInstance().setValuutta(vValittu);
			asetaSaldo(0);
		});
	}
}