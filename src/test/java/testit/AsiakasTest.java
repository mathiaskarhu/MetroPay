package testit;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dao.DAO;
import model.Asiakas;
import model.Osoite;
import model.Postitoimipaikka;
/**
 * AsiakasTest luokka sisältää Asiakas -luokkaa varten tehdyt testit ja testaa myös Asiakkaasen liittyvän DAO-metodin.
 * 
 * 
 * @author Ryhmä 7
 *
 */

class AsiakasTest {

	private static Asiakas testiAsiakas;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		testiAsiakas = new Asiakas("Mikko", "Meikäläinen", "Sposti@sposti.com", "408882222", new Osoite());
	}

	@Test
	@DisplayName("Testataan asiakkaan ID:n hakeminen")
	void asiakasIDTest() {
		testiAsiakas.setId(999);
		assertEquals(999, testiAsiakas.getId(), "Asiakkaan ID väärin");
	}
	
	@Test
	@DisplayName("Testataan etunimen asettamista ja hakemista")
	void etunimiTest() {
		testiAsiakas.setEtunimi("Jorma");
		assertEquals("Jorma", testiAsiakas.getEtunimi(), "Asiakkaan etunimi väärin");
	}
	
	@Test
	@DisplayName("Testataan sukunimen asettamista ja hakemista")
	void sukunimiTest() {
		testiAsiakas.setSukunimi("Kiireinen");
		assertEquals("Kiireinen", testiAsiakas.getSukunimi(), "Asiakkaan sukunimi väärin");
	}
	
	@Test
	@DisplayName("Testataan sähköpostin asettamista ja hakemista")
	void spostiTest() {
		testiAsiakas.setSposti("testiasiakas@metropay.com");
		assertEquals("testiasiakas@metropay.com", testiAsiakas.getSposti(), "Asiakkaan sähköposti väärin");
	}
	
	@Test
	@DisplayName("Testataan puhelinnumeron asettamista ja hakemista")
	void puhelinTest() {
		testiAsiakas.setPuhelin("0401234567");
		assertEquals("0401234567", testiAsiakas.getPuhelin(), "Asiakkaan puhelinnumero väärin");
	}
	
	@Test
	@DisplayName("Testataan osoitteen asettamista ja hakemista")
	void osoiteIDTest() {
		testiAsiakas.setOsoiteID(new Osoite(999, "Myllypurontie", new Postitoimipaikka()));
		assertEquals(999, testiAsiakas.getOsoiteID().getOsoite_id(), "Asiakkaan osoite väärin");
	}

	@Test
	@DisplayName("Testaaan asiakkaan haku tietokannasta ID:n perusteella.")
	void daoTest() {
		DAO dao = new DAO();
		Asiakas testiDaoAsiakas = dao.haeAsiakas(1);
		assertEquals("Kalle", testiDaoAsiakas.getEtunimi(), "Väärä etunimi");
	}
}
