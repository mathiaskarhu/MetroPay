package model;

import javax.persistence.*;

@Entity
@Table(name="asiakas")
public class Asiakas {
	@Id
	@Column(name="asiakas_id")
	private int id;
	
	@Column(name="etunimi")
	private String etunimi;
	
	@Column(name="sukunimi")
	private String sukunimi;
	
	@Column(name="sahkoposti")
	private String sposti;
	
	@Column(name="puhelin")
	private String puhelin;
	
	@ManyToOne
	@JoinColumn(name="osoite_id")
	private Osoite osoiteID;
	
	private static int i = 1;
	
	public Asiakas(String etunimi, String sukunimi, String sposti, String puhelin, Osoite osoiteID) {
		super();
		id = i++;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.sposti = sposti;
		this.puhelin = puhelin;
		this.osoiteID = osoiteID;
	}
	
	public Asiakas() {
		
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getEtunimi() {
		return etunimi;
	}
	
	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}
	//
	public String getSukunimi() {
		return sukunimi;
	}
	
	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}
	
	public String getSposti() {
		return sposti;
	}
	
	public void setSposti(String sposti) {
		this.sposti = sposti;
	}
	
	public String getPuhelin() {
		return puhelin;
	}
	
	public void setPuhelin(String puhelin) {
		this.puhelin = puhelin;
	}
	
	public Osoite getOsoiteID() {
		return osoiteID;
	}
	
	public void setOsoiteID(Osoite osoiteID) {
		this.osoiteID = osoiteID;
	}
	
	@Override
	public String toString() {
		return "Asiakas [Asiakas_ID=" + id + ", Etunimi=" + etunimi + ", Sukunimi=" + sukunimi + ", Sahkoposti=" + sposti
				+ ", Puhelin=" + puhelin + ", OsoiteID=" + osoiteID + "]";
	}
}
