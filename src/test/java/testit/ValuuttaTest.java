package testit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Valuutta;

class ValuuttaTest {

	Valuutta valuutta;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		valuutta = new Valuutta("EUR", 0.999);
	}

	@Test
	@DisplayName("Testataan toimikko tyhjä constructor")
	void testValuutta() {
		valuutta = new Valuutta();
		assertNull(valuutta.getTunnus(), "Valuutan tunnus pitäisi olla tyhjä");
		assertEquals(0, valuutta.getKurssi(), "Valuutan kurssi pitäisi olla 0(null)");
	}

	@Test
	@DisplayName("Testataan toimiiko constructor parametreilla")
	void testValuuttaStringDouble() {
		valuutta = new Valuutta("AUD", 0.777);
		assertEquals("AUD", valuutta.getTunnus(), "Valuutan tunnus ei asettunut oikein");
		assertEquals(0.777, valuutta.getKurssi(), "Valuutan kurssi ei asettunut oikein");
	}

	@Test
	@DisplayName("Testataan valuuttatunnuksen haku")
	void testGetTunnus() {
		assertEquals("EUR", valuutta.getTunnus(), "Valuutan tunnuksen hakeminen epäonnistui");

	}

	@Test
	@DisplayName("Testataan valuuttatunnuksen muutos")
	void testSetTunnus() {
		valuutta.setTunnus("GBP");
		assertEquals("GBP", valuutta.getTunnus(), "Valuutan tunnus ei muuttunut");

	}

	@Test
	@DisplayName("Testataan valuuttakurssin haku")
	void testGetKurssi() {
		assertEquals(0.999, valuutta.getKurssi(), "Valuutan kurssin haku epäonnistui");
	}

	@Test
	@DisplayName("Testataan valuuttakurssin muuttaminen")
	void testSetKurssi() {
		valuutta.setKurssi(6.66);
		assertEquals(6.66, valuutta.getKurssi(), "Valuutan kurssi ei muuttunut");
	}

	@Test
	@DisplayName("Testataan toimiiko toString metodi")
	void testToString() {
		assertEquals("EUR", valuutta.getTunnus(), "toString metodi epäonnistui");
	}

}
