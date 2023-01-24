package controller;

import java.util.ArrayList;
import model.Valuutta;
import dao.ValuuttaDAO;
import dao.DAO;
import dao.IDAO;

/**
 * 
 * ValuuttaKontrolleri hakee tietokannasta valuutat ja vie ne ArrayList:iin.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class ValuuttaKontrolleri {
	
	private ArrayList<Valuutta> valuuttaLista;
	private ValuuttaDAO vDao = new ValuuttaDAO();
	private IDAO dao = new DAO();
	private static boolean valuutatHaettu = false;
	
	public ValuuttaKontrolleri() {}
	
	/**
	 * 
	 * Hakee tietokannan valuutat valuuttaListaan dao:n  haeValuutat -metodilla, ja päivittää niiden kurssit ajantasalla dao:n haeValuutta -metodilla.
	 * 
	 * @return lista tietokannan valuutoista.
	 * 
	 */

	public ArrayList<Valuutta> alustaValuutat() {
		
		valuuttaLista = dao.haeValuutat();
		if(valuutatHaettu) return valuuttaLista;
		
		for(int i = 0; i < valuuttaLista.size();i++) {
			Valuutta v = valuuttaLista.get(i);
			v.setKurssi(vDao.haeValuutta(valuuttaLista.get(i).getTunnus()));
			dao.paivitaValuutta(valuuttaLista.get(i));
		}
		System.out.println(valuuttaLista.get(0));
		valuutatHaettu = true;
		return valuuttaLista;
	}
	
	/**
	 * 
	 * haeKryptot() hakee valuuttaListasta vain kryptovaluutat ja lisää ne kryptoListaan.
	 * 
	 * @return kryptoLista.
	 *  
	 */
	
	public ArrayList<Valuutta> haeKryptot() {
		
		ArrayList<Valuutta> kryptoLista = new ArrayList<Valuutta>();
		for(int i=0; i < valuuttaLista.size(); i++) {
			if(valuuttaLista.get(i).getTunnus().equals("BTC") || valuuttaLista.get(i).getTunnus().equals("ADA") || valuuttaLista.get(i).getTunnus().equals("DOGE") || valuuttaLista.get(i).getTunnus().equals("ETH")) {
				kryptoLista.add(valuuttaLista.get(i));
			}
		}
		return kryptoLista;
	}	
}