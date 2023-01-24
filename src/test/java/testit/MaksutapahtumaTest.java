package testit;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Maksutapahtuma;
import model.Tili;
import model.Valuutta;
import controller.Kirjautumiskontrolleri;
/**
 * MaksutapahtumaTest -luokassa testataan Maksutapahtuma -luokan metodeja.
 * 
 * 
 * @author Ryhmä 7
 *
 */
class MaksutapahtumaTest {
	Maksutapahtuma mtapahtuma;
	Kirjautumiskontrolleri kirjkontrolleri =  new Kirjautumiskontrolleri();
	LocalDate pvm;
	Tili tili1;
	Tili tili2;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		pvm = LocalDate.of(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
		mtapahtuma = new Maksutapahtuma(pvm, 7.40, "01010101", "Viesti", "Kalle Kiireinen", new Tili(), new Tili(), null, true);
	}

	@Test
	@DisplayName("Testataan maksutapahtuman maksupäivämäärän asettamista ja hakemista")
	void ajankohtaTest() {
		mtapahtuma.setAjankohta(pvm);
		assertEquals(pvm, mtapahtuma.getAjankohta(), "Maksutapahtuman ajankohta ei ollut oikein!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuman maksun määrän asettamista ja hakemista")
	void maaraTest() {
		mtapahtuma.setMaara(10.5);
		assertEquals(10.5, mtapahtuma.getMaara(), "Maksutapahtuman maksun määrä ei ollut oikein!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuma_id:n asettamista ja hakemista")
	void tapahtumaIDTest() {
		mtapahtuma.setTapahtuma_ID(999);
		assertEquals(999, mtapahtuma.getTapahtuma_ID(), "Maksutapahtuman ID ei ollut oikein!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuman viestin asettamista ja hakemista")
	void viestiTest() {
		mtapahtuma.setViesti("Testataan");
		assertEquals("Testataan", mtapahtuma.getViesti(), "Maksutapahtuman viesti ei ollut oikein!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuman vastaanottajan nimen asettamista ja hakemista")
	void nimiTest() {
		mtapahtuma.setNimi("Kalle");
		assertEquals("Kalle", mtapahtuma.getNimi(), "Maksutapahtuman vastaanottajan nimi ei ollut oikein!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuman vastaanottajan IBAN-tunnuksen asettamista ja hakemista")
	void vastaanottajaIbanTest() {
		tili1 = kirjkontrolleri.haeTili("11115555");		
		mtapahtuma.setVastaanottajaIban(tili1);
		assertEquals(tili1, mtapahtuma.getVastaanottajaIban(), "Maksutapahtuman vastaanottajan IBAN-tunnus ei ollut oikein!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuman maksajan IBAN-tunnuksen asettamista ja hakemista")
	void maksajaIbanTest() {
		tili1 = kirjkontrolleri.haeTili("11115555");		
		mtapahtuma.setMaksajaIban(tili1);
		assertEquals(tili1, mtapahtuma.getMaksajaIban(), "Maksutapahtuman maksajan IBAN-tunnus ei ollut oikein!");
	}

	@Test
	@DisplayName("Testataan maksutapahtuman viitenumeron asettamista ja hakemista")
	void viitenroTest() {
		mtapahtuma.setViite("00000 11111");
		assertEquals("00000 11111", mtapahtuma.getViite(), "Maksutapahtuman viitenumero ei ollut oikein!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuman valuutan asettamista ja hakemista")
	void valuuttaTest() {
		mtapahtuma.setValuutta(new Valuutta("CLP", 0.0012));
		assertEquals("CLP", mtapahtuma.getValuutta().getTunnus(), "Maksutapahtuman valuutta ei ollut oikein!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuman tilan hakemista")
	void tilaTest() {
		assertEquals(true, mtapahtuma.getTila(), "Maksutapahtuman tila ei ollut oikein!");
	}
}
