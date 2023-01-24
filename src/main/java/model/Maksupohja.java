package model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name="maksupohja")
public class Maksupohja {
	
	@Id
	@Column(name="maksupohja_id")
	private int tapahtuma_id;
	
	@Column(name="maksupohjanimi")
	private String nimi;
	
	@Column(name="ajankohta")
	private String ajankohta;
	
	@Column(name="maara")
	private String maara;
	
	@Column(name="viite")
	private String viite;
	
	@Column(name="viesti")
	private String viesti;
	
	@Column(name="vastaanottajanimi")
	private String vastaanottajaNimi;
	
	@Column(name="vastaanottajaiban")
	private String vastaanottajaiban;

	@Column(name="tekijaiban")
	private String tekija;

	public Maksupohja(String nimi, String ajankohta, String maara, String viite, String viesti,
			String vastaanottajaNimi, String vastaanottaja, String tekija) {
		this.nimi = nimi;
		this.ajankohta = ajankohta;
		this.maara = maara;
		this.viesti = viesti;
		this.vastaanottajaNimi = vastaanottajaNimi;
		this.vastaanottajaiban = vastaanottaja;
		this.tekija = tekija;
		this.viite = viite;
	}
	public Maksupohja(int id, String nimi, String ajankohta, String maara, String viite, String viesti,
			String vastaanottajaNimi, String vastaanottaja, String tekija) {
		this.tapahtuma_id = id;
		this.nimi = nimi;
		this.ajankohta = ajankohta;
		this.maara = maara;
		this.viesti = viesti;
		this.vastaanottajaNimi = vastaanottajaNimi;
		this.vastaanottajaiban = vastaanottaja;
		this.tekija = tekija;
		this.viite = viite;
	}
	
	public Maksupohja() {}

	public int getTapahtuma_id() {
		return tapahtuma_id;
	}

	public void setTapahtuma_id(int tapahtuma_id) {
		this.tapahtuma_id = tapahtuma_id;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public String getAjankohta() {
		return ajankohta;
	}

	public void setAjankohta(String ajankohta) {
		this.ajankohta = ajankohta;
	}

	public String getMaara() {
		return maara;
	}

	public void setMaara(String maara) {
		this.maara = maara;
	}

	public String getViesti() {
		return viesti;
	}

	public void setViesti(String viesti) {
		this.viesti = viesti;
	}

	public String getVastaanottajaNimi() {
		return vastaanottajaNimi;
	}

	public void setVastaanottajaNimi(String vastaanottajaNimi) {
		this.vastaanottajaNimi = vastaanottajaNimi;
	}

	public String getVastaanottajaiban() {
		return vastaanottajaiban;
	}

	public void setVastaanottajaiban(String vastaanottaja) {
		this.vastaanottajaiban = vastaanottaja;
	}

	public String getTekija() {
		return tekija;
	}

	public void setTekija(String tekija) {
		this.tekija = tekija;
	}

	public String getViite() {
		return viite;
	}

	public void setViite(String viite) {
		this.viite = viite;
	}
	
}
