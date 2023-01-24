package controller;

import java.io.IOException;
import java.util.ResourceBundle;

import dao.DAO;
import dao.IDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import model.AktiivinenKayttaja;
import view.GUI;
import view.IGUI;
/**
 * Kayttajatiedotkontrollerissa on kaikki omat tiedot näkymään liittyvät metodit sekä toiminnot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */
public class Kayttajatiedotkontrolleri implements IKontrolleri{
	
	@FXML
    private Label takaisinBtn;
	
    @FXML
    private TextField nimiTxt;
    
    @FXML
    private TextField ibanTxt;
    
    @FXML
    private TextField spostiTxt;
    
    @FXML
    private TextField puhTxt;
    
    @FXML
    private TextField osoiteTxt;
    
    @FXML
    private TextField postinroTxt;
    
	@FXML
    private Button tallennaBtn;
    
    private IGUI gui = new GUI();
	private IDAO dao = new DAO();
	
	private ResourceBundle rb;
	
	/**
	 * 
	 * taytaTiedot metodi hakee aktiivisen käyttäjän käyttäjätiedot ja asettaa ne käyttäliittymä komponentteihin.
	 * 
	 */
	
	public void taytaTiedot() {
		nimiTxt.setText(AktiivinenKayttaja.getInstance().getAsiakastiedot().getEtunimi() + " " + AktiivinenKayttaja.getInstance().getAsiakastiedot().getSukunimi());
		ibanTxt.setText(AktiivinenKayttaja.getInstance().getTili().getIban());
		spostiTxt.setText(AktiivinenKayttaja.getInstance().getAsiakastiedot().getSposti());
		puhTxt.setText(AktiivinenKayttaja.getInstance().getAsiakastiedot().getPuhelin());
		osoiteTxt.setText(AktiivinenKayttaja.getInstance().getOsoite().getLahiosoite());
		postinroTxt.setText(AktiivinenKayttaja.getInstance().getPostitoimipaikka().getPostinro());
	}
	
	/**
	 * 
	 * tallennaTiedot metodi muuttaa "tallenna" nappia painamalla, käyttäjän tekemät käyttäjätietojen muutokset DAOn.
	 * 
	 * @param e
	 * @throws IOException
	 * 
	 */
	
    public void tallennaTiedot(ActionEvent e) throws IOException {
    	//TODO Jokaiselle oma virheteksti
    	try {
    		dao.muutaSposti(spostiTxt.getText());
    		dao.muutaPuh(puhTxt.getText());
    		dao.muutaOsoite(osoiteTxt.getText());
    		dao.muutaPostiNro(postinroTxt.getText());
    		
    	} catch(Exception virheTeksti) {
    		Alert a = new Alert(AlertType.WARNING);
    		a.setHeaderText(rb.getString("omaVirheHeader"));
    		a.setContentText(rb.getString("omaVirheContent"));
    		a.show();
    		System.out.println(virheTeksti);
    	}
    }
    
	/**
	 * 
	 * palaaTakaisin metodi kutsuu GUI:N vaihdaNäkymä metodia, joka vaihtaa sovelluksen näkymän.
	 * @param e
	 * @throws IOException
	 * 
	 */
	
    public void palaaTakaisin(MouseEvent e) throws IOException {
        gui.vaihdaNäkymä("etusivu.fxml");
    }
    
	/**
	 * 
	 * initialize metodi ajetaan näkymän avautuessa. 
	 * initialize kutsuu taytaTiedot() metodia, joka asettaa käyttäjätiedot.
	 * 
	 */
	
	@FXML
	void initialize() {
		System.out.println("initialize");
		rb = gui.getBundle();
		taytaTiedot();
	}

}
