DROP DATABASE IF EXISTS metropay;

CREATE DATABASE metropay;

USE metropay;

CREATE TABLE valuutta
(
  valuutta_id VARCHAR(3) NOT NULL,
  valuuttakurssi FLOAT NOT NULL,
  PRIMARY KEY (valuutta_id)
);

CREATE TABLE postitoimipaikka
(
  postinro varchar(5) NOT NULL,
  paikan_nimi VARCHAR(32) NOT NULL,
  PRIMARY KEY (postinro)
);

CREATE TABLE osoite
(
  osoite_id INT NOT NULL AUTO_INCREMENT,
  lahiosoite VARCHAR(64) NOT NULL,
  postinro varchar(5) NOT NULL,
  PRIMARY KEY (osoite_id),
  FOREIGN KEY (postinro) REFERENCES postitoimipaikka(postinro)
);

CREATE TABLE asiakas
(
  asiakas_id INT NOT NULL AUTO_INCREMENT,
  etunimi VARCHAR(32) NOT NULL,
  sukunimi VARCHAR(32) NOT NULL,
  sahkoposti VARCHAR(64) NOT NULL,
  puhelin varchar(10) NOT NULL,
  osoite_id INT NOT NULL,
  PRIMARY KEY (asiakas_id),
  FOREIGN KEY (osoite_id) REFERENCES osoite(osoite_id)
);

CREATE TABLE tili
(
  iban VARCHAR(18) NOT NULL,
  tunnus VARCHAR(8) NOT NULL,
  salasana VARCHAR(4) NOT NULL,
  saldo FLOAT NOT NULL,
  asiakas_id INT NOT NULL,
  saldo_btc FLOAT NOT NULL,
  saldo_eth FLOAT NOT NULL,
  saldo_doge FLOAT NOT NULL,
  saldo_ada FLOAT NOT NULL;
  PRIMARY KEY (iban),
  FOREIGN KEY (asiakas_id) REFERENCES asiakas(asiakas_id),
  UNIQUE (tunnus)
);

CREATE TABLE maksutapahtuma
( 
  tapahtuma_id INT NOT NULL AUTO_INCREMENT,
  ajankohta DATE NOT NULL,
  maara FLOAT NOT NULL,
  viesti VARCHAR(100) NOT NULL,
  nimi VARCHAR(64) NOT NULL,
  vastaanottajaiban VARCHAR(18) NOT NULL,
  maksajaiban VARCHAR(18) NOT NULL,
  viite varchar(25),
  PRIMARY KEY (tapahtuma_id),
  FOREIGN KEY (vastaanottajaiban) REFERENCES tili(iban),
  FOREIGN KEY (maksajaiban) REFERENCES tili(iban)
);

CREATE TABLE maksupohja 
(
	maksupohja_id int NOT NULL AUTO_INCREMENT,
	maksupohjanimi varchar(64) NOT NULL,
	ajankohta varchar(10),
	maara varchar(16),
	viesti varchar(100),
	vastaanottajanimi varchar(64),
	viite varchar(25);
	tekijaiban varchar(18) NOT NULL,
	PRIMARY KEY (maksupohja_id)
);