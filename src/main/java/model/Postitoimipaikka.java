package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="postitoimipaikka")
public class Postitoimipaikka {
	@Id
	@Column(name="postinro")
	private String postinro;
	
	@Column(name="paikan_nimi")
	private String paikan_nimi;
	
	public Postitoimipaikka() {
	}
	

	public Postitoimipaikka(String postinro, String paikan_nimi) {
		super();
		this.postinro = postinro;
		this.paikan_nimi = paikan_nimi;
	}


	public String getPostinro() {
		return postinro;
	}

	public void setPostinro(String postinro) {
		this.postinro = postinro;
	}

	public String getPaikan_nimi() {
		return paikan_nimi;
	}

	public void setPaikan_nimi(String paikan_nimi) {
		this.paikan_nimi = paikan_nimi;
	}

	
}
