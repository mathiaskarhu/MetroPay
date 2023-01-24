package model;

import javax.persistence.*;

@Entity
@Table(name="osoite")
public class Osoite {
	@Id
	@Column(name="osoite_id")
	private int osoite_id;
	
	@Column(name="lahiosoite")
	private String lahiosoite;
	
	@ManyToOne
	@JoinColumn(name="postinro")
	private Postitoimipaikka postinro;

	public Osoite(int osoite_ID, String lahiosoite, Postitoimipaikka postinro) {
		this.osoite_id = osoite_ID;
		this.lahiosoite = lahiosoite;
		this.postinro = postinro;
	}
	
	public Osoite() {}

	public int getOsoite_id() {
		return osoite_id;
	}

	public void setOsoite_id(int osoite_id) {
		this.osoite_id = osoite_id;
	}

	public String getLahiosoite() {
		return lahiosoite;
	}

	public void setLahiosoite(String lahiosoite) {
		this.lahiosoite = lahiosoite;
	}

	public Postitoimipaikka getPostinro() {
		return postinro;
	}

	public void setPostinro(Postitoimipaikka postinro) {
		this.postinro = postinro;
	};
	
	
}
