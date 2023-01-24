package controller;

import java.io.IOException;

import javafx.scene.input.MouseEvent;

public interface IKontrolleri {
	
	/**
	 * 
	 * toteuttaa rajapinnan palaaTakaisin metodille, joka vaihtaa sovelluksen näkymän "etusivu" näkymäksi.
	 * @param e
	 * @throws IOException
	 * 
	 */
	
	public abstract void palaaTakaisin(MouseEvent e) throws IOException;
}
