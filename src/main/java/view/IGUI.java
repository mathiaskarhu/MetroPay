package view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.Scene;

public interface IGUI {
	
	/**
	 * 
	 * toteuttaa rajapinnan vaihdaNäkymä metodille, joka vaihtaa sovelluksen näkymän.
	 * @param fxml
	 * @throws IOException
	 * 
	 */
	
	public abstract void vaihdaNäkymä(String fxml) throws IOException;
	public abstract ResourceBundle getBundle();
	public abstract void setBundle(ResourceBundle rb);
	public abstract void vaihdaKieli(String kieli, String nakyma);
	public abstract Locale getCurLocale();
	Scene getCurScene();
}
