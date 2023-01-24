package testit;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import org.hibernate.HibernateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.Kirjautumiskontrolleri;
import dao.DAO;
import model.AktiivinenKayttaja;
import model.Asiakas;
import model.Maksupohja;
import model.Maksutapahtuma;
import model.Osoite;
import model.Postitoimipaikka;
import model.Tili;
import model.Valuutta;

/**
 * DAOTest -luokassa testataan DAO:n toiminnallisuutta hakemalla tietokannasta
 * eri tietoja.
 * 
 * 
 * @author Ryhmä 7
 *
 */
class DAOTest {
	DAO dao = new DAO();
	AktiivinenKayttaja ak;
	Kirjautumiskontrolleri kk;
	Tili testitili;
	Tili testitili2;
	ArrayList<Maksutapahtuma> mlista;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		kk = new Kirjautumiskontrolleri();
		ak = null;
		ak = AktiivinenKayttaja.getInstance();
		testitili = kk.haeTili("11115555");
		testitili2 = kk.haeTili("12215555");
		ak.setTili(testitili);
		ak.setAsiakas(new Asiakas("Mikko", "Meikäläinen", "Sposti@sposti.com", "408882222",
				new Osoite(1, "Hervanta", new Postitoimipaikka("00420", "HELSINKI"))));
		ak.getAsiakastiedot().setId(1);
		ak.alustus();
		mlista = new ArrayList<Maksutapahtuma>();
		dao.luoMaksutapahtuma(new Maksutapahtuma(77777, LocalDate.now(), 2.00, "01010101", "Viesti", "Matti M", testitili, testitili2, new Valuutta("EUR", 1.0), false));
	}
	
	@AfterEach
	void clearUp() throws Exception {
		dao.poistaMaksutapahtuma(77777);
	}

	@Test
	@DisplayName("Testataan tilin hakeminen tietokannasta.")
	void testHaeTili() {
		assertNotNull(dao.haeTili("11115555"), "Tilin hakeminen ei onnistunut");
		assertThrows(Exception.class, () -> dao.haeTili("asfafafas"), "Väärä virheilmoitus");
	}

	@Test
	@DisplayName("Testataan IBAN -tunnuksen hakeminen tietokannasta.")
	void testHaeTiliIBAN() {
		assertNotNull(dao.haeTiliIBAN("1111555511115522"), "Tilin hakeminen IBAN:nin avulla ei onnistunut");
	}

	@Test
	@DisplayName("Testataan asiakkaan hakeminen tietokannasta.")
	void testHaeAsiakas() {
		assertNotNull(dao.haeAsiakas(1), "Asiakkaan hakeminen ei onnistunut");
	}

	@Test
	@DisplayName("Testataan maksutapahtuman hakeminen tietokannasta.")
	void testHaeMaksutapahtuma() {
		assertNotNull(dao.haeMaksutapahtuma(77777), "Maksutapahtuman haku ei onnistunut");
	}

	@Test
	@DisplayName("Testataan maksuhistorian hakeminen tietokannasta.")
	void testHaeMaksuhistoria() {
		assertNotNull(dao.haeMaksuhistoria("1111555511115522"), "Maksuhistorian haku ei onnistunut");
	}

	@Test
	@DisplayName("Testataan maksupohjien hakeminen tietokannasta.")
	void testHaeMaksupohjat() {

		assertNotNull(dao.haeMaksupohjat("1111555511115522"), "Maksupohjien haku ei onnistunut");

	}

	@Test
	@DisplayName("testataan sähköpostin muuttamista")
	void testmuutaSposti() {
		dao.muutaSposti("posti@postidomain.com");
		assertEquals("posti@postidomain.com", dao.haeAsiakas(1).getSposti(), "Sähköpostin muuttaminen ei onnistunut!");

	}

	@Test
	@DisplayName("Testataan puhelinnumeron muuttamista")
	void testmuutaPuh() {
		dao.muutaPuh("0425675466");
		assertEquals("0425675466", dao.haeAsiakas(1).getPuhelin(), "Puhelinnumeron muuttaminen ei onnistunut!");

	}

	@Test
	@DisplayName("Testataan lähiosoitteen muuttamista")
	void testmuutaOsoite() {
		dao.muutaOsoite("Karkkila");
		dao.muutaOsoite("Kumpula 3");
		assertEquals("Kumpula 3", dao.haeAsiakas(1).getOsoiteID().getLahiosoite(),
				"Lähiosoitteen muuttaminen ei onnistunut!");

	}

	@Test
	@DisplayName("Testataan postinumero muuttamista")
	void testMuutaPostinumero() {
		dao.muutaPostiNro("00410");
		assertEquals("00410", dao.haeAsiakas(1).getOsoiteID().getPostinro().getPostinro(),
				"Postinumeron muuttaminen ei onnistunut!");

	}

	@Test
	@DisplayName("Testataan maksupohjan kirjaamista tietokantaan")
	void testluoMaksupohja() {
		dao.luoMaksupohja(new Maksupohja(99999, "Testipohja", null, null, null, null, null, null, "1111555511115522"));
		assertEquals(
				"Testipohja", dao.haeMaksupohjat("1111555511115522")
						.get(dao.haeMaksuhistoria("1111555511115522").size() - 1).getNimi(),
				"Maksupohjan luominen ei onnistunut!");
		int size1 = dao.haeMaksupohjat("1111555511115522").size();
		dao.poistaMaksupohja(99999);
		int size2 = dao.haeMaksupohjat("1111555511115522").size();
		assertEquals(size1 - 1, size2, "Maksupohjan poistaminen ei onnistunut!");
	}

	@Test
	@DisplayName("Testataan kryptosaldon muuttamista")
	void testMuutaKryptosaldo() {
		testitili = kk.haeTili("11115555");
		dao.muutaKryptoSaldo(testitili.getIban(), 50, "btc");
		testitili = kk.haeTili("11115555");
		assertEquals(50, testitili.getSaldoBtc(),
				"Kryptosaldon muuttaminen ei onnistunut!");
	}
	
	@Test
	@DisplayName("Testataan odottavien maksujen hakemista")
	void testHaeOdottavatMaksut() {
		mlista = dao.haeOdottavatMaksut(testitili.getIban(), LocalDate.now());
		assertEquals("Viesti", mlista.get(0).getViesti(), "Odottavien maksujen hakeminen ei onnistunut!");
	}
	
	@Test
	@DisplayName("Testataan maksutapahtuman merkkaamista maksetuksi")
	void testMerkkaaMaksetuksi() {
		Maksutapahtuma mt = dao.haeMaksutapahtuma(77777);
		assertEquals(false, mt.getTila(), "Maksutapahtuman tilan hakeminen ei onnistunut.");
		dao.merkkaaMaksetuksi(77777);
		mt = dao.haeMaksutapahtuma(77777);
		assertEquals(true, mt.getTila(), "Maksutapahtuman merkkaaminen maksetuksi ei onnistunut!");
	}
}
