package testit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.ValuuttaKontrolleri;
import model.Valuutta;
/**
 * ValuuttaKontrolleriTest -luokka on ValuuttaKontrollerin ja sen metodien testaamista varten. 
 * Testissä tulee testatuksi myös ValuuttaDAO:n toiminta.
 * 
 * @author Ryhmä 7
 *
 */
class ValuuttaKontrolleriTest {

	private ArrayList<Valuutta> valuuttaLista;
	private ValuuttaKontrolleri vk = new ValuuttaKontrolleri();
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	@DisplayName("Testaan valuuttojen alustus ja haku ulkoisesta rajapinnasta ja tietokannasta")
	void testAlustaValuutat() {
		valuuttaLista = vk.alustaValuutat();
		assertEquals(15, valuuttaLista.size(), "Kaikkia valuuttoja ei haettu.");
		assertEquals("EUR", valuuttaLista.get(7).getTunnus(), "Valuuttatunnus ei täsmää.");
		assertEquals(1.0, valuuttaLista.get(7).getKurssi(), "Valuuttakurssi ei täsmää.");
	}

}
