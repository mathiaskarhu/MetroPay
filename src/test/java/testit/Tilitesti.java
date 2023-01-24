package testit;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Asiakas;
import model.Osoite;
import model.Tili;
/**
 * TiliTest luokka sisältää Tili -luokkaa varten tehdyt testit.
 * 
 * 
 * @author Ryhmä 7
 *
 */
class Tilitesti {
	private Tili testiTili;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		testiTili = new Tili("FI12-3456-7890-1234", "34251451", "1234", 7.50, 0, 0, 0, 0, new Asiakas());
	}

	@Test
	@DisplayName("Testataan IBAN -koodin hakemista.")
	void ibanTest() {
		testiTili.setIban("FI12-3456-7890-1234");
		assertEquals("FI12-3456-7890-1234", testiTili.getIban(), "Tili-luokan IBAN väärin!");
	}
	@Test
	@DisplayName("Testataan tunnuksen asettamista.")
	void tunnusTest() {
		testiTili.setTunnus("44444444");
		assertEquals("44444444", testiTili.getTunnus(), "Tunnus väärin!");
	}
	
	@Test
	@DisplayName("Testataan salasanan asettamista.")
	void salasanaTest() {
		testiTili.setSalasana("3333");
		assertEquals("3333", testiTili.getSalasana(), "Salasana väärin!");
	}
	
	@Test
	@DisplayName("Testataan asiakkaan asettamista.")
	void asiakasIDTest() {
		testiTili.setAsiakasID( new Asiakas("Mikko", "Meikäläinen", "Sposti@sposti.com", "408882222", new Osoite()));
		assertEquals("Mikko", testiTili.getAsiakasID().getEtunimi(), "AsiakasID väärin!");
	}
	
	@Test
	@DisplayName("Testataan BTC saldon asettamista.")
	void bitcoinSaldoTest() {
		testiTili.setSaldoBtc(2);
		assertEquals(2, testiTili.getSaldoBtc(), "Bitcoin saldo väärin!");
	}
	
	@Test
	@DisplayName("Testataan ADA saldon asettamista.")
	void carbonaraSaldoTest() {
		testiTili.setSaldoAda(2);
		assertEquals(2, testiTili.getSaldoAda(), "Carbonara saldo väärin!");
	}
	
	@Test
	@DisplayName("Testataan ETH saldon asettamista.")
	void ethereumSaldoTest() {
		testiTili.setSaldoEth(2);
		assertEquals(2, testiTili.getSaldoEth(), "Ethereum saldo väärin!");
	}
	
	@Test
	@DisplayName("Testataan DOGE saldon asettamista.")
	void dogecoinSaldoTest() {
		testiTili.setSaldoDoge(2);
		assertEquals(2, testiTili.getSaldoDoge(), "Dogecoin saldo väärin!");
	}
	
	
	
	
	
}
