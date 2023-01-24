package testit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Postitoimipaikka;

class PostitoimipaikkaTest {

	Postitoimipaikka pt;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		pt = new Postitoimipaikka("02610", "Espoo");
	}

	@Test
	@DisplayName("Testataan toimikko tyhjä constructor")
	void testPostitoimipaikka() {
		pt = new Postitoimipaikka();
		assertNull(pt.getPostinro(), "postinumero ei ole asetettu oikein");
		assertNull(pt.getPaikan_nimi(), "Paikan nimi ei ole asetettu oikein");
	}

	@Test
	@DisplayName("Testataan toimiiko constructor parametreilla")
	void testPostitoimipaikkaStringString() {
		pt = new Postitoimipaikka("00920", "Helsinki");
		assertEquals("00920", pt.getPostinro(), "postinumero ei ole asetettu oikein");
		assertEquals("Helsinki", pt.getPaikan_nimi(), "Paikan nimi ei ole asetettu oikein");
	}

	@Test
	@DisplayName("Testataan toimiiko postinumeron haku")
	void testGetPostinro() {
		assertEquals("02610", pt.getPostinro(), "postinumero ei ole asetettu oikein");
	}

	@Test
	@DisplayName("Testataan toimiiko postinumeron asettaminen")
	void testSetPostinro() {
		pt.setPostinro("00100");
		assertEquals("00100", pt.getPostinro(), "postinumeron muuttaminen epäonnistui");

	}

	@Test
	@DisplayName("Testataan toimiiko postitoimipaikan nimen haku")
	void testGetPaikan_nimi() {
		assertEquals("Espoo", pt.getPaikan_nimi(), "Paikan nimi ei ole asetettu oikein");
	}

	@Test
	@DisplayName("Testataan toimiiko postitoimipaikan nimen asettaminen")
	void testSetPaikan_nimi() {
		pt.setPaikan_nimi("Vantaa");
		assertEquals("Vantaa", pt.getPaikan_nimi(), "Paikan nimen muuttaminen epäonnistui");

	}

}
