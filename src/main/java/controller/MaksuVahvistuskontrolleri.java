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
 * MaksuVahvistuskontrolleri luokkassa sijaitsee kaikki maksun vahvistus näkymän meotdit ja toiminnot.
 * luokka implementoi Ikontrolleri rajapinnan, sillä se käyttää palaaTakaisin() metodia.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class MaksuVahvistuskontrolleri implements IKontrolleri {
	
	@FXML
    private Label takaisinBtn;
	
	@FXML
    private Label maksuMaara;
	
	@FXML
    private Label maksuNimi;
	
	@FXML
    private Label maksuPvm;
	
	@FXML
	private Label maksuViesti;
	
	@FXML
	private Label maksuViite;
	
    private IGUI gui = new GUI();
    
    private Locale locale;
    
    private NumberFormat numberFormat;
	
    /**
     * palaaTakaisin metodi kutsuu gui:n vaihdanäkymä metodia, joka vaihtaa sovelluksen näkymän.
     * 
     * @param e
     * @throws IOException
     */
    
    public void palaaTakaisin(MouseEvent e) throws IOException {
        gui.vaihdaNäkymä("etusivu.fxml");
    }
    
    /**
     * asetaMaksuTiedot metodi asettaa näkymän label kenttiin makstapahtuman m tiedot.
     * 
     * @param m
     */
    
    public void asetaMaksuTiedot(Maksutapahtuma m) {
    	Valuutta v = AktiivinenKayttaja.getInstance().getValuutta();
    	maksuMaara.setText("- " + numberFormat.format(m.getMaara()) + " " + v.getTunnus());
    	maksuNimi.setText(m.getNimi());
    	maksuPvm.setText(m.getAjankohta().toString());
    	maksuViite.setText(m.getViite());
    	maksuViesti.setText("\"" + m.getViesti() + "\"");
    }
    
	/**
	 * initialize metodi ajetaan heti näkymän avautumassa. Metodi kutsuu asetaMaksuTiedot() metodia ja
	 * hakee viimeisimmän käyttäjän tekemän maksutapahtuman ja käytetyn valuutan.
	 */
    
	@FXML
	void initialize() {
		System.out.println("initialize");
		locale = gui.getCurLocale();
		numberFormat = NumberFormat.getInstance(locale);
		numberFormat.setMinimumFractionDigits(2);
		asetaMaksuTiedot(AktiivinenKayttaja.getInstance().getMaksutapahtuma());
	}
}
