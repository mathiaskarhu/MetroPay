package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.AktiivinenKayttaja;
import view.GUI;
import view.IGUI;

/**
 * 
 * LainaVahvistusKontrollerissa säädetään kaikki näkymään laitettavat tiedot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */
public class LainaVahvistusKontrolleri implements IKontrolleri{
	
	@FXML
    private Label takaisinBtn;
	
	@FXML
	private Label lainaMaaraLabel;
	
	@FXML
	private Label lainaMaksuaikaLabel;
	
	@FXML
	private Label lainaKuukausiEraLabel;
	
	private IGUI gui = new GUI();
    
	private Locale locale;
    
	private NumberFormat numberFormat;
    
    
	public LainaVahvistusKontrolleri() {
	}
	
	/**
     * palaaTakisin metodi kutsuu gui:n vaihdanäkymä metodia, joka vaihtaa sovelluksen näkymän.
     * 
     * @param e
     * @throws IOException
     */
	public void palaaTakaisin(MouseEvent e) throws IOException {
        gui.vaihdaNäkymä("etusivu.fxml");
    }
	
	/**
	 * metodi asettaa lainan tiedot näkymään
	 */
	public void asetaLainaTiedot() {
		lainaMaaraLabel.setText(AktiivinenKayttaja.getInstance().getMaksutapahtuma().getViite() + " €");
		lainaMaksuaikaLabel.setText(numberFormat.format(AktiivinenKayttaja.getInstance().getMaksutapahtuma().getMaara()));
		lainaKuukausiEraLabel.setText(AktiivinenKayttaja.getInstance().getMaksutapahtuma().getViesti());
	}
    
	
	/**
	 * initialize metodi ajetaan heti näkymän avautumassa. Hakee numeroformaatin ja kutsuu asetaLainaTiedot metodia.
	 */
	@FXML
	void initialize() {
		System.out.println("initialize");
		locale = gui.getCurLocale();
		numberFormat = NumberFormat.getInstance(locale);
		asetaLainaTiedot();
	}

}
