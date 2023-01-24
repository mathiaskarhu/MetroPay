package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.DAO;
import dao.ValuuttaDAO;
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
 * CryptoMyyntikontrollerissa on kaikki krypton osto näkymään liittyvät metodit sekä toiminnot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */
public class CryptoOstokontrolleri {
	
	@FXML
    private Label takaisinBtn;
	
	@FXML
	private TextField maaraTxt;
	
	@FXML
	private Slider maksaBtn;
	
	@FXML
	private ChoiceBox<Valuutta> valuuttaBox;

	private IGUI gui = new GUI();
	
	private DAO dao = new DAO();
	private ValuuttaDAO vDao = new ValuuttaDAO();	

	private ValuuttaKontrolleri vKontrolleri = new ValuuttaKontrolleri();

	private ArrayList<Valuutta> valuuttaLista;

	private static int valuuttaBoxIndex = 1;
	
	private ResourceBundle rb;
	
	public CryptoOstokontrolleri() {
		System.out.println("CRYPTO OSTO");
	}
	
	/**
	 * osta() metodi hoitaa kryptojen osto ominaisuudet. Metodi laskee lisättävän ja poistettavan summan tilille.
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void osta(MouseEvent e) throws IOException {
		
		try {
				System.out.println("###############################");
				System.out.println(valuuttaBox.getSelectionModel().getSelectedItem().getTunnus());
				System.out.println(AktiivinenKayttaja.getInstance().getTili().getSaldo() - Double.parseDouble(maaraTxt.getText()));
				if(Double.parseDouble(maaraTxt.getText()) < 0){
					System.out.println("Negatiivinen luku");
					throw new Exception();
				}
				double muutettuSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldo() - Double.parseDouble(maaraTxt.getText());
				if(muutettuSaldo < 0) {
					System.out.println("Saldo ei riitä");
					throw new Exception();
				}
				dao.muutaSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuSaldo);
				
				if (valuuttaBox.getSelectionModel().getSelectedItem().getTunnus().equals("BTC")) {
					
					System.out.println("Krypto: " + Double.parseDouble(maaraTxt.getText()) * vDao.haeValuutta("BTC"));
					double muutettuBtcSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldoBtc() + (Double.parseDouble(maaraTxt.getText()) * vDao.haeValuutta("BTC"));
					dao.muutaKryptoSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuBtcSaldo, "btc");
					AktiivinenKayttaja.getInstance().setTili(dao.haeTili(AktiivinenKayttaja.getInstance().getTili().getTunnus()));
					AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(0, null, Double.parseDouble(maaraTxt.getText()), null, null, null, null, null, null, null));
					gui.vaihdaNäkymä("kryptoOstoVahvistaminen.fxml");
				} else if (valuuttaBox.getSelectionModel().getSelectedItem().getTunnus().equals("DOGE")) {
					System.out.println("Krypto: " + Double.parseDouble(maaraTxt.getText()) * vDao.haeValuutta("DOGE"));
					double muutettuDogeSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldoDoge() + (Double.parseDouble(maaraTxt.getText()) * vDao.haeValuutta("DOGE"));
					dao.muutaKryptoSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuDogeSaldo, "doge");
					AktiivinenKayttaja.getInstance().setTili(dao.haeTili(AktiivinenKayttaja.getInstance().getTili().getTunnus()));
					AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(0, null, Double.parseDouble(maaraTxt.getText()), null, null, null, null, null, null, null));
					gui.vaihdaNäkymä("kryptoOstoVahvistaminen.fxml");
					
				} else if (valuuttaBox.getSelectionModel().getSelectedItem().getTunnus().equals("ADA")) {
					System.out.println("Krypto: " + Double.parseDouble(maaraTxt.getText()) * vDao.haeValuutta("ADA"));
					double muutettuAdaSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldoAda() + (Double.parseDouble(maaraTxt.getText()) * vDao.haeValuutta("ADA"));
					dao.muutaKryptoSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuAdaSaldo, "ada");
					AktiivinenKayttaja.getInstance().setTili(dao.haeTili(AktiivinenKayttaja.getInstance().getTili().getTunnus()));
					AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(0, null, Double.parseDouble(maaraTxt.getText()), null, null, null, null, null, null, null));
					gui.vaihdaNäkymä("kryptoOstoVahvistaminen.fxml");

				} else if (valuuttaBox.getSelectionModel().getSelectedItem().getTunnus().equals("ETH")) {
					System.out.println("Krypto: " + Double.parseDouble(maaraTxt.getText()) * vDao.haeValuutta("ETH"));
					double muutettuEthSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldoEth() + (Double.parseDouble(maaraTxt.getText()) * vDao.haeValuutta("ETH"));
					dao.muutaKryptoSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuEthSaldo, "eth");
					AktiivinenKayttaja.getInstance().setTili(dao.haeTili(AktiivinenKayttaja.getInstance().getTili().getTunnus()));
					AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(0, null, Double.parseDouble(maaraTxt.getText()), null, null, null, null, null, null, null));
					gui.vaihdaNäkymä("kryptoOstoVahvistaminen.fxml");
				} else {
					System.out.println("Jotain meni pieleen");
				}
				

		} catch (Exception virheTeksti) {
			Alert a = new Alert(AlertType.WARNING);
			a.setHeaderText(rb.getString("kryOstoAlertHeader"));
			a.setContentText(rb.getString("kryOstoAlertCol"));
			a.show();
			System.out.println(virheTeksti);
		}
	}

	/**
	 * palaaTakaisin() metodi vaihtaa näkymän crypto sivulle.
 	 * @param e
	 * @throws IOException
	 */
    public void palaaTakaisin(MouseEvent e) throws IOException {
        gui.vaihdaNäkymä("crypto.fxml");
    }
    
    /**
     * initialize metodi ajetaan heti näkymän avautumassa. Metodissa täytetään valuutta lista ja asetetaan siihen OnActin metodi
     */
    @FXML
	void initialize() {
		System.out.println("initialize");
		rb = gui.getBundle();
		valuuttaLista = vKontrolleri.alustaValuutat();
		valuuttaLista = vKontrolleri.haeKryptot();
		
		for (int i = 0; i < valuuttaLista.size(); i++) {
			valuuttaBox.getItems().add(valuuttaLista.get(i));
		}
		valuuttaBox.getSelectionModel().select(valuuttaBoxIndex);
		/**
		 * Valuutan vaihto metodi
		 */
		valuuttaBox.setOnAction((event) -> {
			Valuutta vValittu = valuuttaBox.getSelectionModel().getSelectedItem();
			valuuttaBoxIndex = valuuttaBox.getSelectionModel().getSelectedIndex();
		});
	}
}