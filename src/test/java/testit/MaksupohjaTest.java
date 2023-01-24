package testit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Maksupohja;

class MaksupohjaTest {
	Maksupohja mp;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mp = new Maksupohja("testimaksupohja", "22-02-2022", "5", "104202 042012", "Heipahei", "Joni",
				"FI1122334455667788", "FIYYYYYYYYYYYYYYYY");
	}

	@Test
	@DisplayName("Testataan tapahtuma id: asettamista")
	void testTapahtuma_id() {
		mp.setTapahtuma_id(9999);
		assertEquals(9999, mp.getTapahtuma_id(), "Tapahtuma id ei täsmää syötettyä id:tä.");
		
	}

	@Test
	void testNimi() {
		assertEquals("testimaksupohja", mp.getNimi(), "Konstruktorin asettama nimi ei täsmää.");
		mp.setNimi("hieno nimi");
		assertEquals("hieno nimi", mp.getNimi(), "Nimi ei täsmää.");
	}

	@Test
	void testAjankohta() {
		mp.setAjankohta("14-05-1987");
		assertEquals("14-05-1987", mp.getAjankohta(), "Ajankohta ei täsmää.");
	}

	@Test
	void testMaara() {
		mp.setMaara("10");
		assertEquals("10", mp.getMaara(), "Maksun määrä ei täsmää.");
	}

	@Test
	void testViesti() {
		mp.setViesti("moro moro");
		assertEquals("moro moro", mp.getViesti(), "Viesti ei täsmää.");
	}
	@Test
	void testVastaanottajaNimi() {
		mp.setVastaanottajaNimi("jussi");
		assertEquals("jussi", mp.getVastaanottajaNimi(), "Vastaanottajan nimi ei täsmää.");
	}
	@Test
	void testGetVastaanottajaiban() {
		mp.setVastaanottajaiban("FI1234567812345678");
		assertEquals("FI1234567812345678", mp.getVastaanottajaiban(), "Vastaanottajan iban ei täsmää.");
	}

	@Test
	void testTekija() {
		mp.setTekija("FI8877665544332211");
		assertEquals("FI8877665544332211", mp.getTekija(), "Tekijän iban ei täsmää.");
	}

	@Test
	void testViite() {
		mp.setViite("235235 523532");
		assertEquals("235235 523532", mp.getViite(), "Maksun viite ei täsmää.");
	}

}
