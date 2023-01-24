package testit;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Kirjautumiskontrolleri;
import controller.Maksukontrolleri;
import model.AktiivinenKayttaja;
import model.Maksutapahtuma;
import model.Tili;
import model.Valuutta;
/**
 * MaksukontrolleriTest -luokassa testataan maksukontrollerin metodeja.
 * 
 * 
 * @author Ryhmä 7
 *	
 */
class MaksukontrolleriTest {
	AktiivinenKayttaja ak;
	Maksutapahtuma mtapahtuma;
	Maksukontrolleri mkontrolleri;
	Kirjautumiskontrolleri kirjkontrolleri = new Kirjautumiskontrolleri();
	Tili tili1, tili2;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@BeforeEach
	void setUp() throws Exception {
		tili1 = kirjkontrolleri.haeTili("11115555");		
		tili2 = kirjkontrolleri.haeTili("12215555");
		mtapahtuma = new Maksutapahtuma(9999, LocalDate.of(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH), 2.00, "01010101", "Viesti", "Matti M", tili1, tili2, new Valuutta("EUR", 1.0), true);
		mkontrolleri = new Maksukontrolleri();
		ak = AktiivinenKayttaja.getInstance();
		ak.setTili(tili1);
		ak.setValuutta(new Valuutta("EUR", 1));
	}
	
	@AfterEach
	void clearUp() throws Exception {
		mkontrolleri.poistaMaksutapahtuma(9999);
	}

	@Test
	@DisplayName("Testataan maksutapahtuman kirjaamista.")
	void kirjaaMaksutapahtumaTest() {
		mkontrolleri.kirjaaMaksutapahtuma(mtapahtuma);
		Maksutapahtuma mtap = mkontrolleri.haeMaksutapahtuma(9999);
		assertEquals(mtap.getMaara(), 2.00, "Tapahtumaa ei kirjattu oikein.");
	}
	
	@Test
	@DisplayName("Testataan maksun suoritusta eli rahan siirtoa tililtä toiselle.")
	void suoritaMaksuTest() {
		double maksajanSaldo = tili1.getSaldo();
		double vastaanottajanSaldo = tili2.getSaldo();
		// Testataan tämän tiedoston päivitystä.
		mkontrolleri.suoritaMaksu(tili1, tili2, 3.50, new Valuutta("EUR", 1.0), LocalDate.now(), mtapahtuma);
		assertEquals(kirjkontrolleri.haeTili("11115555").getSaldo(),maksajanSaldo - 3.50, "Maksajan saldo ei vähentynyt oikein." );
		assertEquals(kirjkontrolleri.haeTili("12215555").getSaldo(),vastaanottajanSaldo + 3.50, "Vastaanottajan saldo ei noussut oikein.");
		mkontrolleri.suoritaMaksu(tili2, tili1, 3.50, new Valuutta("EUR", 1.0), LocalDate.now(), mtapahtuma);
	}

}
