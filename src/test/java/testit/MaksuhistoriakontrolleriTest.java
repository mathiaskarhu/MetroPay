package testit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Kirjautumiskontrolleri;
import controller.Maksuhistoriakontrolleri;
import controller.Maksukontrolleri;
import model.AktiivinenKayttaja;
import model.Maksutapahtuma;
import model.Tili;
import model.Valuutta;
/**
 * MaksuhistoriakontrolleriTest -luokassa testataan maksuhistoriakontrollerin metodeja.
 * Testeissä hyödynnetään konsolitulosteita.
 * 
 * @author Ryhmä 7
 *
 */

class MaksuhistoriakontrolleriTest {
	Maksuhistoriakontrolleri mHkontrolleri = new Maksuhistoriakontrolleri();
	Kirjautumiskontrolleri kirjkontrolleri = new Kirjautumiskontrolleri();
	Maksukontrolleri mkontrolleri = new Maksukontrolleri();
	ByteArrayOutputStream outContent = null;
	PrintStream originalOut = null;
	
	LocalDate pvm = LocalDate.of(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
	Tili tili1 = null;
	Tili tili2 = null;
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		outContent = new ByteArrayOutputStream();
		originalOut = System.out;
		tili1 = kirjkontrolleri.haeTili("11115555");
		tili2 = kirjkontrolleri.haeTili("12215555");
		
		AktiivinenKayttaja.getInstance().setTili(tili1);
		AktiivinenKayttaja.getInstance().setValuutta(new Valuutta("eur", 1));
		System.setOut(new PrintStream(outContent));
	}

	@Test
	@DisplayName("Testataan aktiivisen käyttäjän maksutapahtumien listausta")
	void tapahtumaListausTest() {
		//TODO: Tarkista tekstikentät maksuhistorianäkymässä.
		Maksutapahtuma mtapahtuma = new Maksutapahtuma(99999, pvm, 3.50, "01010101", "Terve", "Pekka P", tili1, tili2, null, true);
		mkontrolleri.kirjaaMaksutapahtuma(mtapahtuma);
		mHkontrolleri.listaaMaksutapahtumat();
		assertEquals(true, outContent.toString().contains("Terve"), "Maksutapahtumaa ei löytynyt listauksesta.");
	}
	
	@Test
	@DisplayName("Testataan aktiivisen käyttäjän maksutapahtumien suodatusta päivämäärän mukaan")
	void tapahtumaSuodatusTest() {
		//TODO: Tarkista tekstikentät maksuhistorianäkymässä.
		Maksutapahtuma mtapahtuma = new Maksutapahtuma(99999, pvm, 3.50, "01010101", "Terve", "Pekka P", tili1, tili2, null, true);
		mkontrolleri.kirjaaMaksutapahtuma(mtapahtuma);
		mHkontrolleri.suodataMaksutapahtumat();
		assertEquals(true, outContent.toString().contains(pvm.toString()), "Maksutapahtumat suodatettiin väärin.");
		assertEquals(false, outContent.toString().contains("2021-02-13"), "Maksutapahtumat suodatettiin väärin.");
	}
	
	@AfterEach
	void cleanUp() throws Exception {
		AktiivinenKayttaja.getInstance().setTili(null);
		mkontrolleri.poistaMaksutapahtuma(99999);
		System.setOut(originalOut);
	}

}
