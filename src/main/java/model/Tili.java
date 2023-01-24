package model;

import javax.persistence.*;

@Entity
@Table(name="tili")
public class Tili {
	@Id
	@Column(name="iban")
	private String iban;
	
	@Column(name="tunnus")
	private String tunnus;

	@Column(name="salasana")
	private String salasana;
	
	@Column(name="saldo")
	private double saldo;
	
	@Column(name="saldo_btc")
	private double saldoBtc;
	
	@Column(name="saldo_eth")
	private double saldoEth;
	
	@Column(name="saldo_doge")
	private double saldoDoge;
	
	@Column(name="saldo_ada")
	private double saldoAda;
	
	@ManyToOne
	@JoinColumn(name="asiakas_id")
	private Asiakas asiakas;
	
	@Column(name="lainavelka")
	private double laina;
	
	
	public Tili(String iban, String tunnus, String salasana, double saldo, double saldoBtc, double saldoEth,
			double saldoDoge, double saldoAda, Asiakas asiakas) {
		this.iban = iban;
		this.tunnus = tunnus;
		this.salasana = salasana;
		this.saldo = saldo;
		this.saldoBtc = saldoBtc;
		this.saldoEth = saldoEth;
		this.saldoDoge = saldoDoge;
		this.saldoAda = saldoAda;
		this.asiakas = asiakas;
	}

	public Tili() {
		
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getTunnus() {
		return tunnus;
	}

	public void setTunnus(String tunnus) {
		this.tunnus = tunnus;
	}

	public String getSalasana() {
		return salasana;
	}

	public void setSalasana(String salasana) {
		this.salasana = salasana;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public Asiakas getAsiakasID() {
		return asiakas;
	}

	public void setAsiakasID(Asiakas asiakasID) {
		this.asiakas = asiakasID;
	}

	public double getSaldoBtc() {
		return saldoBtc;
	}

	public void setSaldoBtc(double saldoBtc) {
		this.saldoBtc = saldoBtc;
	}

	public double getSaldoEth() {
		return saldoEth;
	}

	public void setSaldoEth(double saldoEth) {
		this.saldoEth = saldoEth;
	}

	public double getSaldoDoge() {
		return saldoDoge;
	}

	public void setSaldoDoge(double saldoDoge) {
		this.saldoDoge = saldoDoge;
	}

	public double getSaldoAda() {
		return saldoAda;
	}

	public void setSaldoAda(double saldoAda) {
		this.saldoAda = saldoAda;
	}

	public double getLaina() {
		return laina;
	}

	public void setLaina(double laina) {
		this.laina = laina;
	}

	@Override
	public String toString() {
		return "Tili [iban=" + iban + ", tunnus=" + tunnus + ", salasana=" + salasana + ", saldo=" + saldo
				+ ", asiakasID=" + asiakas + "]";
	}	
	
}
