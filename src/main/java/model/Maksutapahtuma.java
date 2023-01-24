package model;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="maksutapahtuma")
public class Maksutapahtuma {

	@Id
	@Column(name="tapahtuma_id")
	private int tapahtuma_id;
	
	@Column(name="ajankohta")
	private LocalDate ajankohta;
	
	@Column(name="maara")
	private double maara;
	
	@Column(name="viesti")
	private String viesti;
	
	@Column(name="nimi")
	private String nimi;
	
	@Column(name="viite")
	private String viite;
	
	@ManyToOne
	@JoinColumn(name="valuutta_id")
	private Valuutta valuutta;
	
	@ManyToOne
	@JoinColumn(name="vastaanottajaiban")
	private Tili vastaanottaja;

	@ManyToOne
	@JoinColumn(name="maksajaiban")
	private Tili maksaja;
	
	@Column(name="tila")
	@org.hibernate.annotations.Type(type="true_false")
	private Boolean tila;

	public Maksutapahtuma(LocalDate ajankohta, double maara, String viite, String viesti, String nimi, Tili iban,
			Tili maksajaIban, Valuutta valuutta, Boolean tila) {
		this.ajankohta = ajankohta;
		this.maara = maara;
		this.viesti = viesti;
		this.nimi = nimi;
		this.vastaanottaja = iban;
		this.maksaja = maksajaIban;
		this.viite = viite;
		this.valuutta = valuutta;
		this.tila = tila;
	}
	
	public Maksutapahtuma(int tapahtumaID, LocalDate ajankohta, double maara, String viite, String viesti, String nimi, Tili iban,
			Tili maksajaIban, Valuutta valuutta, Boolean tila) {
		this.tapahtuma_id = tapahtumaID;
		this.ajankohta = ajankohta;
		this.maara = maara;
		this.viesti = viesti;
		this.nimi = nimi;
		this.vastaanottaja = iban;
		this.maksaja = maksajaIban;
		this.viite = viite;
		this.valuutta = valuutta;
		this.tila = tila;
	}
	
	public Maksutapahtuma() {
		
	}

	public LocalDate getAjankohta() {
		return ajankohta;
	}

	public void setAjankohta(LocalDate ajankohta) {
		this.ajankohta = ajankohta;
	}

	public double getMaara() {
		return maara;
	}

	public void setMaara(double maara) {
		this.maara = maara;
	}

	public int getTapahtuma_ID() {
		return tapahtuma_id;
	}

	public void setTapahtuma_ID(int tapahtuma_ID) {
		this.tapahtuma_id = tapahtuma_ID;
	}

	public String getViesti() {
		return viesti;
	}

	public void setViesti(String viesti) {
		this.viesti = viesti;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public Tili getVastaanottajaIban() {
		return vastaanottaja;
	}

	public void setVastaanottajaIban(Tili iban) {
		this.vastaanottaja = iban;
	}

	public Tili getMaksajaIban() {
		return maksaja;
	}

	public void setMaksajaIban(Tili maksajaIban) {
		this.maksaja = maksajaIban;
	}
	

	public String getViite() {
		return viite;
	}

	public void setViite(String viite) {
		this.viite = viite;
	}
	
	public Valuutta getValuutta() {
		return valuutta;
	}

	public void setValuutta(Valuutta valuutta) {
		this.valuutta = valuutta;
	}

	public Boolean getTila() {
		return tila;
	}

	public void setTila(Boolean tila) {
		this.tila = tila;
	}

	@Override
	public String toString() {
		return "Maksutapahtuma [tapahtuma_id=" + tapahtuma_id + ", ajankohta=" + ajankohta + ", maara=" + maara
				+ ", viesti=" + viesti + ", nimi=" + nimi + ", vastaanottaja=" + vastaanottaja + ", maksaja=" + maksaja
				+ "]";
	}
	
	
}

