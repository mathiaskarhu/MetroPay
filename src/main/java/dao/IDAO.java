package dao;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Asiakas;
import model.Maksupohja;
import model.Maksutapahtuma;
import model.Tili;
import model.Valuutta;

public interface IDAO {

	public abstract Tili haeTili (String tunnus);
	public abstract Tili haeTiliIBAN(String iban);
	public abstract void luoMaksutapahtuma(Maksutapahtuma maksutapahtuma);
	public abstract void muutaSaldo(String iban, double saldo);
	public abstract void muutaSposti(String sposti);
	public abstract void muutaPuh(String puh);
	public abstract void muutaOsoite(String osoite);
	public abstract void muutaPostiNro(String postinro);
	public abstract Asiakas haeAsiakas(int asiakasID);
	public abstract Maksutapahtuma haeMaksutapahtuma(int tapahtumaID);
	public abstract void poistaMaksutapahtuma(int tapahtumaID);
	public abstract ArrayList<Maksutapahtuma> haeMaksuhistoria(String iban);
	public abstract ArrayList<Valuutta> haeValuutat();
	public abstract void paivitaValuutta(Valuutta valuutta);
	public abstract ArrayList<Maksupohja> haeMaksupohjat(String iban);
	public abstract void luoMaksupohja(Maksupohja maksupohja);
	public abstract void poistaMaksupohja(int maksupohja_id);
	public abstract void muutaKryptoSaldo(String iban, double saldo, String tunnus);
	public abstract ArrayList<Maksutapahtuma> haeOdottavatMaksut(String iban, LocalDate pvm);
	public abstract void merkkaaMaksetuksi(int ID);
	public abstract void muutaLainaVelka(String iban, double laina);
}
