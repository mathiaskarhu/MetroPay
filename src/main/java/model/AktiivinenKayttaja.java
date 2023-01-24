package model;

/**
 * 
 * AktiivinenKayttaja singleton luokkaan lisätään aktiivisen käyttäjän tietoja kuten
 * tili, asiakastiedot, valuutta, teema, viimeisin maksutapahtuma ja osoite oliot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class AktiivinenKayttaja {
	private Tili tili;
	private Maksutapahtuma m;
	private Asiakas asiakas;
	private Osoite osoite;
	private Postitoimipaikka postitoimipaikka;
	private Valuutta valuutta;
	private String teema;
	
	private static AktiivinenKayttaja instanssi;
	
	private AktiivinenKayttaja() {
		tili = null;
	}
	
	/**
	 * 
	 * Luo ja/tai palauttaa olemassa olevan singleton instanssin.
	 * 
	 * @return saa-olion instanssi.
	 * 
	 */
	
	public static AktiivinenKayttaja getInstance() {
		if(instanssi == null) {
			instanssi = new AktiivinenKayttaja();
		}
		return instanssi;
	}
	
	public void setAsiakas(Asiakas asiakas) {
		this.asiakas = asiakas;
	}
	
	public Asiakas getAsiakastiedot() {
		return this.asiakas;
	}
	
	public Osoite getOsoite() {
		return osoite;
	}

	public void setOsoite(Osoite osoite) {
		this.osoite = osoite;
	}

	public Postitoimipaikka getPostitoimipaikka() {
		return postitoimipaikka;
	}

	public void setPostitoimipaikka(Postitoimipaikka postitoimipaikka) {
		this.postitoimipaikka = postitoimipaikka;
	}

	public void setTili(Tili tili) {
		this.tili = tili;
	}
	
	public Tili getTili() {
		return this.tili;
	}
	
	/**
	 * 
	 * alustus metodi asettaa kaikki käyttäjään liittyvät tiedot talteen muuttujiin.
	 * 
	 */
	
	public void alustus() {
		this.asiakas = tili.getAsiakasID();
		this.osoite = asiakas.getOsoiteID();
		this.postitoimipaikka = osoite.getPostinro();
		this.valuutta = new Valuutta("EUR",1);
	}
	
	public void asetaMaksu(Maksutapahtuma m) {
		this.m = m; 
	}
	
	public Maksutapahtuma getMaksutapahtuma() {
		return m;
	}
	
	public Valuutta getValuutta() {
		return valuutta;
	}

	public void setValuutta(Valuutta valuutta) {
		this.valuutta = valuutta;
	}	
	
	public void setTeema(String teema) {
		this.teema = teema;
	}
	
	public String getTeema() {
		return teema;
	}
}
