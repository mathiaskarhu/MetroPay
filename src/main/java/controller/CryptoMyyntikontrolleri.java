package controller;

import java.io.IOException;
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
import model.Valuutta;
import view.GUI;
import view.IGUI;

/**
 * CryptoMyyntikontrollerissa on kaikki krypton myynti näkymään liittyvät metodit sekä toiminnot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class CryptoMyyntikontrolleri {
	
	@FXML
    private Label takaisinBtn;
	
	@FXML
	private TextField maaraTxt;
	
	@FXML
	private Slider myyntiBtn;
	
	@FXML
	private ChoiceBox<Valuutta> valuuttaBox;

	private IGUI gui = new GUI();
	
	private DAO dao = new DAO();
	private ValuuttaDAO vDao = new ValuuttaDAO();	

	private ValuuttaKontrolleri vKontrolleri = new ValuuttaKontrolleri();

	private ArrayList<Valuutta> valuuttaLista;

	private static int valuuttaBoxIndex = 1;
	
	private ResourceBundle rb;
	
	public CryptoMyyntikontrolleri() {
		System.out.println("CRYPTO MYY");
	}
	
	/**
	 * 
	 * myynti() metodi hoitaa kryptojen myynti ominaisuudet. Metodi laskee lisättävän ja poistettavan summan tilille.
	 * @param e
	 * @throws IOException
	 * 
	 */
	
	public void myynti(MouseEvent e) throws IOException {
		try {
			if (valuuttaBox.getSelectionModel().getSelectedItem().getTunnus().equals("BTC")) {
				
				double muutettuSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldo() + ( Double.parseDouble(maaraTxt.getText()) / vDao.haeValuutta("BTC") );
				double muutettuKryptoSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldoBtc() - Double.parseDouble(maaraTxt.getText());

				if (Double.parseDouble(maaraTxt.getText()) < 0){
					System.out.println("Negatiivinen luku");
					throw new Exception();
				}
				if (muutettuKryptoSaldo < 0) {
					System.out.println("Ei tarpeeksi varoja");
					throw new Exception();
				}
				
				dao.muutaSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuSaldo);
				dao.muutaKryptoSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuKryptoSaldo, "btc");
				
				AktiivinenKayttaja.getInstance().setTili(dao.haeTili(AktiivinenKayttaja.getInstance().getTili().getTunnus()));
				AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(null, Double.parseDouble(maaraTxt.getText()), "₿ ", null, null, null, null, null, null));
				gui.vaihdaNäkymä("kryptoMyyntivahvistaminen.fxml");
			} else if (valuuttaBox.getSelectionModel().getSelectedItem().getTunnus().equals("DOGE")) {
				
				double muutettuSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldo() + ( Double.parseDouble(maaraTxt.getText()) / vDao.haeValuutta("DOGE") );
				double muutettuDogeSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldoDoge() - Double.parseDouble(maaraTxt.getText());
				if(Double.parseDouble(maaraTxt.getText()) < 0){
					System.out.println("Negatiivinen luku");
					throw new Exception();
				}
				if(muutettuDogeSaldo < 0) {
					System.out.println("Ei tarpeeksi varoja");
					throw new Exception();
				}
				
				dao.muutaSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuSaldo);
				dao.muutaKryptoSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuDogeSaldo, "doge");
				
				AktiivinenKayttaja.getInstance().setTili(dao.haeTili(AktiivinenKayttaja.getInstance().getTili().getTunnus()));
				AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(null, Double.parseDouble(maaraTxt.getText()), "Ð ", null, null, null, null, null, null));
				gui.vaihdaNäkymä("kryptoMyyntivahvistaminen.fxml");
				
			} else if (valuuttaBox.getSelectionModel().getSelectedItem().getTunnus().equals("ADA")) {
				
				double muutettuSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldo() + ( Double.parseDouble(maaraTxt.getText()) / vDao.haeValuutta("ADA") );
				double muutettuAdaSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldoAda() - Double.parseDouble(maaraTxt.getText());
				if(Double.parseDouble(maaraTxt.getText()) < 0){
					System.out.println("Negatiivinen luku");
					throw new Exception();
				}
				if(muutettuAdaSaldo < 0) {
					System.out.println("Ei tarpeeksi varoja");
					throw new Exception();
				}
				dao.muutaSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuSaldo);
				dao.muutaKryptoSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuAdaSaldo, "ada");
				
				AktiivinenKayttaja.getInstance().setTili(dao.haeTili(AktiivinenKayttaja.getInstance().getTili().getTunnus()));
				AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(null, Double.parseDouble(maaraTxt.getText()), "₳ ", null, null, null, null, null, null));
				gui.vaihdaNäkymä("kryptoMyyntivahvistaminen.fxml");

			} else if (valuuttaBox.getSelectionModel().getSelectedItem().getTunnus().equals("ETH")) {
		
				double muutettuSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldo() + ( Double.parseDouble(maaraTxt.getText()) / vDao.haeValuutta("ETH") );
				double muutettuEthSaldo = AktiivinenKayttaja.getInstance().getTili().getSaldoEth() - Double.parseDouble(maaraTxt.getText());
				if(Double.parseDouble(maaraTxt.getText()) < 0){
					System.out.println("Negatiivinen luku");
					throw new Exception();
				}
				if(muutettuEthSaldo < 0) {
					System.out.println("Ei tarpeeksi varoja");
					throw new Exception();
				}
				dao.muutaSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuSaldo);
				dao.muutaKryptoSaldo(AktiivinenKayttaja.getInstance().getTili().getIban(), muutettuEthSaldo, "eth");
				
				AktiivinenKayttaja.getInstance().setTili(dao.haeTili(AktiivinenKayttaja.getInstance().getTili().getTunnus()));
				AktiivinenKayttaja.getInstance().asetaMaksu(new Maksutapahtuma(null, Double.parseDouble(maaraTxt.getText()), "Ξ ", null, null, null, null, null, null));
				gui.vaihdaNäkymä("kryptoMyyntivahvistaminen.fxml");
			} else {
				System.out.println("Jotain meni pieleen");
			}


		} catch (Exception virheTeksti) {
			Alert a = new Alert(AlertType.WARNING);
			a.setHeaderText(rb.getString("kryMyyntiAlertHeader"));
			a.setContentText(rb.getString("kryMyyntiAlertCol"));
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
