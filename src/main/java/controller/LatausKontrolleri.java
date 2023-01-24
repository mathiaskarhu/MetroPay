package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.GUI;
import view.IGUI;

/**
 * 
 * LatausKontrolleri luokka luotu näyttämään latausruutua sen aikaa kun sovellus lataa tietokannasta tiedot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class LatausKontrolleri implements IKontrolleri {
	
	private IGUI gui = new GUI();
	
	@FXML
	private Label ladataanTxt;
	
	/*
	 * 
	 * palaaTakaisin() on tyhjä metodi IKontrolleri-rajapintaa varten.
	 * @param e
	 * @throws IOException
	 * 
	 * */
	
	@Override
	public void palaaTakaisin(MouseEvent e) throws IOException {}
	
	/**
	 * 
	 * lataaEtusivu() metodi kutsuu GUI:N vaihdaNäkymä metodia, joka vaihtaa sovelluksen näkymän.
	 * @param w
	 * 
	 */
	
	@FXML
	public void lataaEtusivu(MouseEvent w) {
		try {
			gui.vaihdaNäkymä("etusivu.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}