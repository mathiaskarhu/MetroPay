package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="valuutta")
public class Valuutta {
	@Id
	@Column(name="valuutta_id")
	private String tunnus;
	@Column(name="valuuttakurssi")
	private double kurssi;
	
	public Valuutta() {}
	public Valuutta(String tunnus, double kurssi) {
		this.tunnus = tunnus;
		this.kurssi = kurssi;
	}

	public String getTunnus() {
		return tunnus;
	}

	public void setTunnus(String tunnus) {
		this.tunnus = tunnus;
	}

	public double getKurssi() {
		return kurssi;
	}

	public void setKurssi(double kurssi) {
		this.kurssi = kurssi;
	}

	@Override
	public String toString() {
		return tunnus;
	}
	
	
	
	
}
