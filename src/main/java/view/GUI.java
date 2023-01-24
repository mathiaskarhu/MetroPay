package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.AktiivinenKayttaja;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 
 * GUI-luokka käyttöliittymää varten.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class GUI extends Application implements IGUI {

	private static Stage stg;
	private static ResourceBundle bundle;
	private static Locale curLocale;

	/**
	 * 
	 * start metodi käynnistää sovelluksen näkymän.
	 * 
	 * @param primaryStage
	 * @throws Exception
	 * 
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		curLocale = null;
		String appConfigPath = "src/main/resources/MetroPay.properties";
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(appConfigPath));
			String language = properties.getProperty("language");
			String country = properties.getProperty("country");

			curLocale = new Locale(language, country);
			Locale.setDefault(curLocale);
		} catch (Exception e) {
			System.out.println("init meni pieleen!");
			e.printStackTrace();
		}
		System.out.println("TERVEHDYS startista");
		stg = primaryStage;
		primaryStage.setResizable(false);
		bundle = ResourceBundle.getBundle("TextResources", curLocale);
		Parent root = FXMLLoader.load(getClass().getResource("kirjautuminen.fxml"), bundle);
		primaryStage.setTitle("MetroPay");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/img/MetroBank.png")));
		primaryStage.setScene(new Scene(root, 600, 500));
		primaryStage.show();

	}

	/**
	 * 
	 * vaihdaNäkymä metodi, vaihtaa sovelluksen näkymän ja väriteeman.
	 * 
	 * @param fxml
	 * @throws IOException
	 * 
	 */

	public void vaihdaNäkymä(String fxml) throws IOException {
		Parent pane = FXMLLoader.load(getClass().getResource(fxml), bundle);
		stg.getScene().setRoot(pane);
		
		if (AktiivinenKayttaja.getInstance().getTeema() == "COLOR") {
			pane.getScene().getRoot().getStylesheets().remove(getClass().getResource("/view/resources/css/style.css").toString());
			pane.getScene().getRoot().getStylesheets().add(getClass().getResource("/view/resources/css/color_style.css").toString());
			System.out.println("Vaihdettu COLOR");
		} else if (AktiivinenKayttaja.getInstance().getTeema() == "DARK") {
			pane.getScene().getRoot().getStylesheets().add(getClass().getResource("/view/resources/css/style.css").toString());
			pane.getScene().getRoot().getStylesheets().remove(getClass().getResource("/view/resources/css/color_style.css").toString());
			System.out.println("Vaihdettu DARK");
		}
	}
	
	public Scene getCurScene() {
		return stg.getScene();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public ResourceBundle getBundle() {
		return bundle;
	}

	@Override
	public void setBundle(ResourceBundle rb) {
		bundle = rb;

	}
	@Override
	public Locale getCurLocale() {
		return curLocale;
	}
	
	/**
	 * 
	 * vaihdaKieli metodi, vaihtaa sovelluksen kielen valitun kielen mukaan.
	 * 
	 * @param kValittu
	 * @param nakyma
	 * 
	 */

	@Override
	public void vaihdaKieli(String kValittu, String nakyma) {
		System.out.println(kValittu);
		switch (kValittu) {
		case "English":
			curLocale = new Locale("en", "FI");
			break;
		case "Suomi":
			curLocale = new Locale("fi", "FI");
			break;
		case "Español":
			curLocale = new Locale("es", "ES");
			break;
		case "Svenska":
			curLocale = new Locale("sv", "SE");
			break;
		}
		Locale.setDefault(curLocale);
		try {
			setBundle(ResourceBundle.getBundle("TextResources", curLocale));
			vaihdaNäkymä(nakyma);
		} catch (Exception e) {
			System.out.println("Kirjautumisen uudelleen lataaminen ei onnistunut");
			e.printStackTrace();
		}

	}
}