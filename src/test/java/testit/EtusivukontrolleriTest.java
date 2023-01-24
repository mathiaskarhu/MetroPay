package testit;

import static org.junit.jupiter.api.Assertions.*;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.jupiter.api.*;

import controller.Etusivukontrolleri;
import controller.Kirjautumiskontrolleri;
import model.AktiivinenKayttaja;
import model.Valuutta;
/**
 * EtusivukontrolleriTest -luokassa testataan etusivukontrolleriin liittyviä metodeja.
 * 
 * 
 * @author Ryhmä 7
 *
 */
class EtusivukontrolleriTest {

	Etusivukontrolleri etusKontrolleri;
	Kirjautumiskontrolleri kirjKontrolleri = new Kirjautumiskontrolleri();
	Locale locale;
	NumberFormat numberFormat;
	
	@BeforeEach
	void setUp() throws Exception{
		etusKontrolleri = new Etusivukontrolleri();
		AktiivinenKayttaja.getInstance().setTili(kirjKontrolleri.haeTili("11115555"));
		AktiivinenKayttaja.getInstance().setValuutta(new Valuutta("eur", 1));
		locale =  new Locale("en", "FI");
		numberFormat = NumberFormat.getInstance(locale);
		etusKontrolleri.setNumberFormat(numberFormat);
	}
	
	@AfterEach
	void clearUp() throws Exception {
		AktiivinenKayttaja.getInstance().setTili(null);
	}
	
	@Test
	@DisplayName("Testataan aktiivisena olevan käyttäjän saldon hakemista")
	void haeSaldoTest() {
		assertNotNull(etusKontrolleri.haeSaldo(), "Saldoa ei saatu");
		String saldoTxt = etusKontrolleri.haeSaldo();
		
		assertEquals(numberFormat.format(AktiivinenKayttaja.getInstance().getTili().getSaldo()), saldoTxt, "Saldon määrä on väärä");
		
	}

}
