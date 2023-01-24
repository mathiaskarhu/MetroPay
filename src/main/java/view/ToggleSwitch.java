package view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.AktiivinenKayttaja;

/**
 * 
 * ToggleSwitch luokka luotu "toggle-switch" painiketta varten.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class ToggleSwitch extends StackPane {
	
	private final Rectangle bg = new Rectangle(60, 25, Color.RED);
	private final Button button = new Button();
	
	private String buttonStyleOff = "-fx-effect: dropshadow(two-pass-box, rgba(0, 0, 0, 0.2),15,0.0,0.1,3); -fx-background-color: #40444b;";
    private String buttonStyleOn = "-fx-effect: dropshadow(two-pass-box, rgba(8, 28, 59, 0.5),15,0.0,0.1,3); -fx-background-color: #033977;";
	
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
	public SimpleBooleanProperty switchOnProperty() { return switchedOn; }
	
	/**
	 * 
	 * init() metodi ajetaan heti näkymän avautumassa. Metodi kutsuu setStyle() metodia ja
	 * alustaa "toggle-switch" painikkeen.
	 * 
	 */
	
	private void init() {
		
		getChildren().addAll(bg, button);	
		
		setStyle();
		
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		
		bg.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
	}
	
	/** 
	 * 
	 * setStyle() metodi asettaa "toggle-switch" painikkeen visuaalisen ulkoasun.
	 * 
	 */
	
	private void setStyle() {
		setMinSize(50, 25);
		
		bg.maxWidth(50);
		bg.minWidth(50);
		bg.maxHeight(25);
        bg.minHeight(25);
        
        bg.setArcHeight(bg.getHeight());
        bg.setArcWidth(bg.getHeight());
        bg.setFill(Color.valueOf("#2f3136"));
        
        Double r = 2.0;
        button.setShape(new Circle(r));
        button.setStyle(buttonStyleOff);
        setAlignment(button, Pos.CENTER_LEFT);
        
        button.setMaxSize(30, 30);
        button.setMinSize(30, 30);
        
		if (AktiivinenKayttaja.getInstance().getTeema() == "COLOR") {
			button.setStyle(buttonStyleOn);
			bg.setFill(Color.valueOf("#081c3b"));
			setAlignment(button, Pos.CENTER_RIGHT);
		} else if (AktiivinenKayttaja.getInstance().getTeema() == "DARK") {
			button.setStyle(buttonStyleOff);
			bg.setFill(Color.valueOf("#2f3136"));
			setAlignment(button, Pos.CENTER_LEFT);
		}
	}
	
	public ToggleSwitch() {
		init();
		switchedOn.addListener((a,b,c) -> {
			if (c) {
						button.setStyle(buttonStyleOn);
						bg.setFill(Color.valueOf("#081c3b"));
                		setAlignment(button, Pos.CENTER_RIGHT);
            		} else {
            			button.setStyle(buttonStyleOff);
            			bg.setFill(Color.valueOf("#2f3136"));
                        setAlignment(button, Pos.CENTER_LEFT);
            		}
		});
	}
}