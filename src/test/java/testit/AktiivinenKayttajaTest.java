package testit;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Kirjautumiskontrolleri;
import model.AktiivinenKayttaja;
import model.Asiakas;
import model.Maksutapahtuma;
import model.Osoite;
import model.Postitoimipaikka;
import model.Tili;
import model.Valuutta;

class AktiivinenKayttajaTest {
	AktiivinenKayttaja ak;
	Kirjautumiskontrolleri kirjkontrolleri;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ak = null;
		ak = AktiivinenKayttaja.getInstance();
		kirjkontrolleri = new Kirjautumiskontrolleri();
	}
	@Test
	@DisplayName("Testataan asiakastietojen asettamista")
	void testAsiakastiedot() {
		ak.setAsiakas(new Asiakas("Mikko", "Meikäläinen", "Sposti@sposti.com", "408882222", new Osoite()));
		assertEquals("Mikko", ak.getAsiakastiedot().getEtunimi(), "Asiakasta ei asetettu oikein.");
	}

	@Test
	@DisplayName("Testataan osoitteen asettamista")
	void testGetOsoite() {
		ak.setOsoite(new Osoite(9999,"Hervanta", new Postitoimipaikka()));
		assertEquals("Hervanta", ak.getOsoite().getLahiosoite(), "Asiakastietojen osoite väärin!");
	}
	@Test
	@DisplayName("Testataan postitoimipaikan asettamista")
	void testGetPostitoimipaikka() {
		ak.setPostitoimipaikka(new Postitoimipaikka("03456", "TAMPERE"));
		assertEquals("TAMPERE", ak.getPostitoimipaikka().getPaikan_nimi(),"Postitoimipaikka väärin!");
	}

	@Test
	@DisplayName("Testataan tilin asettamista")
	void testSetTili() {
		ak.setTili(kirjkontrolleri.haeTili("11115555"));
		assertEquals("11115555",ak.getTili().getTunnus(),"Tunnus väärin!");
	}

	@Test
	@DisplayName("Testataan käyttäjän tietojen alustusta")
	void testAlustus() {
		ak.setTili(kirjkontrolleri.haeTili("11115555"));
		ak.alustus();
		assertEquals("Kumpula 3", ak.getOsoite().getLahiosoite(), "Alustettu tili väärin!");
		assertEquals("EUR", ak.getValuutta().getTunnus(), "Euroa ei alustettu oletusvaluutaksi!");
	}

	@Test
	@DisplayName("Testataan maksutapahtuman asettamista")
	void testAsetaMaksu() {
		ak.asetaMaksu(new Maksutapahtuma(9999, LocalDate.of(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH), 2.00, "01010101", "Viesti", "Matti M", new Tili(), new Tili(), new Valuutta("EUR", 1.0), true));
		assertEquals("Matti M",ak.getMaksutapahtuma().getNimi(),"Maksun asettaminen ei onnistunut!");
	}
	
	@Test
	@DisplayName("Testataan valuutan asettamista")
	void testGetValuutta() {
		ak.setValuutta(new Valuutta("CPY", 0.0012));
		assertEquals(0.0012, ak.getValuutta().getKurssi(), "Valuuttaa ei asetettu oikein!");
	}

}
