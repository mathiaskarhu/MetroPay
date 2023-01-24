package testit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Osoite;
import model.Postitoimipaikka;

class OsoiteTest {

	Osoite osoite;
	Postitoimipaikka postinumero;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		postinumero = new Postitoimipaikka();
		osoite = new Osoite(999, "Myllypurontie", postinumero);
	}

	@Test
	@DisplayName("Testataan toimiiko constructor parametreilla")
	void testConstructorWithParameters() {
		osoite = new Osoite(1, "Ruoholahdenkatu", null);
		assertEquals(1, osoite.getOsoite_id(), "Osoite_ID ei ole asetettu oikein");
		assertEquals("Ruoholahdenkatu", osoite.getLahiosoite(), "Lähiosoite ei ole asetettu oikein");
		assertNull(osoite.getPostinro(), "postinro ei ole asetettu oikein");
	}

	@Test
	@DisplayName("Testataan toimikko tyhjä constructor")
	void testConstructorEmpty() {
		osoite = new Osoite();
		assertEquals(0, osoite.getOsoite_id(), "Osoite_ID ei ole asetettu oikein");
		assertNull(osoite.getLahiosoite(), "Lähiosoite ei ole asetettu oikein");
		assertNull(osoite.getPostinro(), "postinro ei ole asetettu oikein");
	}

	@Test
	@DisplayName("Testataan toimiiko osoite ID:n haku")
	void testGetOsoite_id() {
		assertEquals(999, osoite.getOsoite_id(), "Osoiteen ID:tä ei saatu oikein");
	}

	@Test
	@DisplayName("Testataan toimiiko osoite ID:n asettaminen")
	void testSetOsoite_id() {
		osoite.setOsoite_id(888);
		assertEquals(888, osoite.getOsoite_id(), "Osoiteen ID ei muuttunut");
	}

	@Test
	@DisplayName("Testataan toimiiko lähiosoitteen haku")
	void testGetLahiosoite() {
		assertEquals("Myllypurontie", osoite.getLahiosoite(), "Lähiosoitetta ei saatu oikein");
	}

	@Test
	@DisplayName("Testataan toimiiko lähiosoitteen asettaminen")
	void testSetLahiosoite() {
		osoite.setLahiosoite("Karaportti");
		assertEquals("Karaportti", osoite.getLahiosoite(), "Lähiosoite ei muuttunut");
	}

	@Test
	@DisplayName("Testataan toimiiko postinumeron haku")
	void testGetPostinro() {
		assertNotNull(osoite.getPostinro(), "Postinro ei ole saatu oikein");
	}

	@Test
	@DisplayName("Testataan toimiiko postinumeron asettaminen")
	void testSetPostinro() {
		osoite.setPostinro(null);
		assertNull(osoite.getPostinro(), "Postinro ei muuttunut");
	}

}
