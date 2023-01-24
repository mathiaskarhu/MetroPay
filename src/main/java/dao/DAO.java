package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import model.AktiivinenKayttaja;
import model.Asiakas;
import model.Maksupohja;
import model.Maksutapahtuma;
import model.Tili;
import model.Valuutta;

/**
 * 
 * DAO-luokka tietokantaa varten.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class DAO implements IDAO {

	private SessionFactory istuntotehdas = null;

	public DAO() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Yhteyden muodostus tietokantaan ei onnistunut!" + e.getMessage());
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä hakee ja vie käyttäjätunnuksen tietokannasta haeTili metodilla.
     * 
     * @param tunnus
     * 
     */

	@Override
	public Tili haeTili(String tunnus) {
		Session istunto = null;
		Tili tili = null;
		try {
			istunto = istuntotehdas.openSession();
			istunto.beginTransaction();
			String query = "FROM Tili WHERE tunnus='" + tunnus + "'";
			tili = (Tili) istunto.createQuery(query).getSingleResult();
			istunto.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			istunto.close();
			System.out.println();
		}
		return tili;
	}
	
    /**
     * 
     * luo istuntotehtaan sekä hakee ja vie käyttäjän ibanin tietokannasta haeTiliIBAN metodilla.
     * 
     * @param iban
     * 
     */
	
	@Override
	public Tili haeTiliIBAN(String iban) {
		Session istunto = null;
		Tili tili = null;
		try {
			istunto = istuntotehdas.openSession();
			istunto.beginTransaction();
			String query = "FROM Tili WHERE iban='" + iban + "'";
			tili = (Tili) istunto.createQuery(query).getSingleResult();
			istunto.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			istunto.close();
			System.out.println();
		}
		return tili;
	}
	
    /**
     * 
     * luo istuntotehtaan sekä hakee ja vie asiakkaan tietokannasta haeAsiakas metodilla.
     * 
     * @param asiakasID
     * 
     */

	@Override
	public Asiakas haeAsiakas(int asiakasID) {
		Session istunto = null;
		Asiakas asiakas = null;
		try {
			istunto = istuntotehdas.openSession();
			istunto.beginTransaction();
			String query = "FROM Asiakas WHERE asiakas_id='" + asiakasID + "'";
			asiakas = (Asiakas) istunto.createQuery(query).getSingleResult();
			istunto.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			istunto.close();
			System.out.println();
		}
		return asiakas;
	}
	
    /**
     * 
     * luo istuntotehtaan sekä hakee ja vie maksutapahtuman tietokannasta haeMaksutapahtuma metodilla.
     * 
     * @param tapahtumaID
     * 
     */

	@Override
	public Maksutapahtuma haeMaksutapahtuma(int tapahtumaID) {
		Session istunto = null;
		Maksutapahtuma tapahtuma = null;
		try {
			istunto = istuntotehdas.openSession();
			istunto.beginTransaction();
			String query = "FROM Maksutapahtuma WHERE tapahtuma_id='" + tapahtumaID + "'";
			tapahtuma = (Maksutapahtuma) istunto.createQuery(query).getSingleResult();
			istunto.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			istunto.close();
			System.out.println();
		}
		return tapahtuma;
	}
	
    /**
     * 
     * sulkee istuntotehtaan finalize() metodilla.
     * 
     */


	public void finalize() {
		try {
			if (istuntotehdas != null)
				istuntotehdas.close();
		} catch (Exception e) {
			System.err.println("Istuntotehtaan sulkeminen epäonnistui:\n " + e.getMessage());
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä luo uuden maksutapahtuman tietokantaan luoMaksutapahtuma metodilla.
     * 
     * @param maksutapahtuma
     * 
     */

	@Override
	public void luoMaksutapahtuma(Maksutapahtuma maksutapahtuma) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			istunto.save(maksutapahtuma);
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä muuttaa tietokannassa olevaa saldoa muutaSaldo metodilla.
     * 
     * @param iban
     * @param saldo
     * 
     */

	@Override
	public void muutaSaldo(String iban, double saldo) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			String query = "update Tili set saldo = " + saldo + " where iban = '" + iban + "'";
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Tilin saldon päivittäminen ei onnistunut!");
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä muuttaa tietokannassa olevia kryptovaluuttojen saldoa muutaKryptoSaldo metodilla.
     * 
     * @param iban
     * @param saldo
     * @param tunnus
     * 
     */
	
	@Override
	public void muutaKryptoSaldo(String iban, double saldo, String tunnus) {
		Transaction transaktio = null;
		tunnus = tunnus.toLowerCase();
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			String query = "update Tili set saldo_" + tunnus + " = " + saldo + " where iban = '" + iban + "'";
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Tilin kryptosaldon päivittäminen ei onnistunut!");
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä muuttaa tietokannassa olevaa tilin lainamäärää muutaLainaVelka metodilla.
     * 
     * @param iban
     * @param laina
     * 
     */
	
	@Override
	public void muutaLainaVelka(String iban, double laina) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			String query = "update Tili set lainavelka = " + laina + " where iban = '" + iban + "'";
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Tilin saldon päivittäminen ei onnistunut!");
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä poistaa maksutapahtuman tietokannasta poistaMaksutapahtuma metodilla.
     * 
     * @param tapahtumaID
     * 
     */

	@Override
	public void poistaMaksutapahtuma(int tapahtumaID) {
		Transaction transaktio = null;
		Session istunto = null;
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			String query = "delete from Maksutapahtuma where tapahtuma_id = " + tapahtumaID;
			istunto.createQuery(query).executeUpdate();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Maksutapahtuman poistaminen ei onnistunut!");
			throw e;
		} finally {
			istunto.close();
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä hakee ja vie maksuhistorian tietokannasta ArrayList:iin haeMaksuhistoria metodilla.
     * 
     * @param iban
     * 
     */

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Maksutapahtuma> haeMaksuhistoria(String iban) {
		Session istunto = null;
		ArrayList<Maksutapahtuma> lista = new ArrayList<Maksutapahtuma>();
		try {
			istunto = istuntotehdas.openSession();
			istunto.beginTransaction();
			String query = "FROM Maksutapahtuma WHERE maksajaiban = '" + iban + "' OR vastaanottajaiban = '" + iban
					+ "'";
			lista = (ArrayList<Maksutapahtuma>) istunto.createQuery(query).getResultList();
			istunto.getTransaction().commit();
			System.out.println();
		} catch (Exception e) {
			System.err.println("Maksuhistorian hakeminen ei onnistunut!");
		} finally {
			istunto.close();
		}
		return lista;
	}
	
    /**
     * 
     * luo istuntotehtaan sekä hakee ja vie odottavat maksutapahtumat tietokannasta ArrayList:iin haeOdottavatMaksut metodilla.
     * 
     * @param iban
     * @param pvm
     * 
     */
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Maksutapahtuma> haeOdottavatMaksut(String iban, LocalDate pvm) {
		Session istunto = null;
		ArrayList<Maksutapahtuma> lista = new ArrayList<Maksutapahtuma>();
		try {
			istunto = istuntotehdas.openSession();
			istunto.beginTransaction();
			String query = "FROM Maksutapahtuma WHERE vastaanottajaiban = '" + iban + "' AND tila = 'F' AND ajankohta BETWEEN '0001-01-01' AND '"+ pvm +"'";
			lista = (ArrayList<Maksutapahtuma>) istunto.createQuery(query).getResultList();
			istunto.getTransaction().commit();
			System.out.println();
		} catch (Exception e) {
			System.err.println("Maksuhistorian hakeminen ei onnistunut!");
		} finally {
			istunto.close();
		}
		return lista;
	}
	
    /**
     * 
     * luo istuntotehtaan sekä merkkaa maksutapahtuman maksetuksi tietokantaan merkkaaMaksetuksi metodilla.
     * 
     * @param ID
     * 
     */
	
	@Override
	public void merkkaaMaksetuksi(int ID) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			String query = "update Maksutapahtuma set tila = 'T' where tapahtuma_id = " + ID;
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Maksutapahtuman tilan muutos ei onnistunut.");
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä muuttaa tietokannassa olevaa käyttäjän sähköpostia muutaSposti metodilla.
     * 
     * @param sposti
     * 
     */
	
	@Override
	public void muutaSposti(String sposti) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			String query = "update Asiakas set sahkoposti = '" + sposti + "' where asiakas_id = '" + AktiivinenKayttaja.getInstance().getAsiakastiedot().getId() + "'";
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Asiakkaan sähköpostin päivittäminen ei onnistunut!");
			throw e;
		}

	}
	
    /**
     * 
     * luo istuntotehtaan sekä muuttaa tietokannassa olevaa käyttäjän puhelinnumeroa muutaPuh metodilla.
     * 
     * @param puh
     * 
     */

	@Override
	public void muutaPuh(String puh) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			String query = "update Asiakas set puhelin = '" + puh + "' where asiakas_id = '" + AktiivinenKayttaja.getInstance().getAsiakastiedot().getId() + "'";
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Asiakkaan sähköpostin päivittäminen ei onnistunut!");
			throw e;
		}
		
	}
	
    /**
     * 
     * luo istuntotehtaan sekä muuttaa tietokannassa olevaa käyttäjän lähiosoitetta muutaOsoite metodilla.
     * 
     * @param osoite
     * 
     */
	
	@Override
	public void muutaOsoite(String osoite) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			String query = "update Osoite set lahiosoite = '" + osoite + "' where osoite_id = " + AktiivinenKayttaja.getInstance().getOsoite().getOsoite_id();
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Asiakkaan sähköpostin päivittäminen ei onnistunut!");
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä muuttaa tietokannassa olevaa käyttäjän postinumeroa muutaPostiNro metodilla.
     * 
     * @param postinro
     * 
     */

	@Override
	public void muutaPostiNro(String postinro) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			// TODO pls fix (aka add kaikki osoitteet), testaa tietokannassa olevilla postinumeroilla
			String query = "update Osoite set postinro = '" + postinro + "' where osoite_id = " + AktiivinenKayttaja.getInstance().getOsoite().getOsoite_id();
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Asiakkaan sähköpostin päivittäminen ei onnistunut!");
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä hakee ja vie valuutat tietokannasta ArrayList:iin haeValuutat metodilla.
     * 
     */
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Valuutta> haeValuutat() {
		Session istunto = null;
		ArrayList<Valuutta> lista = new ArrayList<Valuutta>();
		try {
			istunto = istuntotehdas.openSession();
			istunto.beginTransaction();
			String query = "FROM Valuutta";
			lista = (ArrayList<Valuutta>) istunto.createQuery(query).getResultList();
			istunto.getTransaction().commit();
			System.out.println(lista);
		} catch (Exception e) {
			System.err.println("Valuuttojen hakeminen ei onnistunut!");
			e.printStackTrace();
		} finally {
			istunto.close();
		}
		
		return lista;
	}
	
    /**
     * 
     * luo istuntotehtaan sekä päivittää valuuttakurssin tietokantaan paivitaValuutta metodilla.
     * 
     * @param valuutta
     * 
     */
	
	@Override
	public void paivitaValuutta(Valuutta valuutta) {	
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			String query = "update Valuutta set valuuttakurssi = " + valuutta.getKurssi() + " where valuutta_id = '" + valuutta.getTunnus() + "'";
			istunto.createQuery(query).executeUpdate();
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Valuutan päivittäminen ei onnistunut!");
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä hakee ja vie maksupohjat tietokannasta ArrayList:iin haeMaksupohjat metodilla.
     * 
     * @param iban
     * 
     */

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Maksupohja> haeMaksupohjat(String iban) {
		Session istunto = null;
		ArrayList<Maksupohja> lista = new ArrayList<Maksupohja>();
		try {
			istunto = istuntotehdas.openSession();
			istunto.beginTransaction();
			String query = "FROM Maksupohja WHERE tekijaiban = '" + iban + "'";
			lista = (ArrayList<Maksupohja>) istunto.createQuery(query).getResultList();
			istunto.getTransaction().commit();
			System.out.println();
		} catch (Exception e) {
			System.err.println("Maksuhistorian hakeminen ei onnistunut!");
		} finally {
			istunto.close();
		}
		return lista;
	}
	
    /**
     * 
     * luo istuntotehtaan sekä luo maksupohjan tietokantaan luoMaksupohja metodilla.
     * 
     * @param maksupohja
     * 
     */

	@Override
	public void luoMaksupohja(Maksupohja maksupohja) {
		Transaction transaktio = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaktio = istunto.beginTransaction();
			istunto.save(maksupohja);
			transaktio.commit();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		}
	}
	
    /**
     * 
     * luo istuntotehtaan sekä poistaa maksupohjan tietokannasta poistaMaksupohja metodilla.
     * 
     * @param maksupohja_id
     * 
     */
	
	@Override
	public void poistaMaksupohja(int maksupohja_id) {
		Transaction transaktio = null;
		Session istunto = null;
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			String query = "delete from Maksupohja where maksupohja_id = " + maksupohja_id;
			istunto.createQuery(query).executeUpdate();
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println("Maksutapahtuman poistaminen ei onnistunut!");
			throw e;
		} finally {
			istunto.close();
		}
	}	
}
