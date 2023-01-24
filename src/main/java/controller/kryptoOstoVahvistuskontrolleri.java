package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.AktiivinenKayttaja;
import model.Maksutapahtuma;
import model.Valuutta;
import view.GUI;
import view.IGUI;

/**
 * 
 * kryptoOstoVahvistuskontrolleri luokkassa sijaitsee kaikki kryptovaluutan oston vahvistus näkymän metodit ja toiminnot.
 * luokka implementoi Ikontrolleri rajapinnan, sillä se käyttää palaaTakaisin() metodia.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class kryptoOstoVahvistuskontrolleri implements IKontrolleri {
	
	@FXML
    private Label takaisinBtn;
	
	@FXML
    private Label maksuMaara;
	
    private IGUI gui = new GUI();
    
    private Locale locale;
    
    private NumberFormat numberFormat;
	
    /**
     * 
     * palaaTakaisin metodi kutsuu gui:n vaihdanäkymä metodia, joka vaihtaa sovelluksen näkymän.
     * 
     * @param e
     * @throws IOException
     * 
     */
    
    public void palaaTakaisin(MouseEvent e) throws IOException {
        gui.vaihdaNäkymä("crypto.fxml");
    }
    
    /**
     * 
     * asetaOstoTiedot metodi asettaa näkymän label kenttiin ostotapahtuman tiedot.
     * 
     * @param m
     * 
     */
    
    public void asetaOstoTiedot(Maksutapahtuma m) {
    	Valuutta v = AktiivinenKayttaja.getInstance().getValuutta();
    	maksuMaara.setText("€ -" + numberFormat.format(m.getMaara()));
    }
    
	/**
	 * 
	 * initialize metodi ajetaan heti näkymän avautumassa. Metodi kutsuu asetaOstoTiedot() metodia ja
	 * hakee viimeisimmän käyttäjän tekemän kryptovaluutan ostotapahtuman.
	 * 
	 */
    
	@FXML
	void initialize() {
		System.out.println("initialize");
		locale = gui.getCurLocale();
		numberFormat = NumberFormat.getInstance(locale);
		numberFormat.setMinimumFractionDigits(2);
		asetaOstoTiedot(AktiivinenKayttaja.getInstance().getMaksutapahtuma());
	}
}
