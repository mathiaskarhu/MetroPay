package dao;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import com.mysql.cj.xdevapi.JsonParser;

/**
 * 
 * ValuuttaDAO-luokka.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class ValuuttaDAO {
	private String urlString;
	private String json;
	private URL url;
	private InputStream is;
	
	/**
	 * 
	 * Hakee https-yhteydellä ulkoisesta url-osoitteesta löytyvästä rajapinnasta valuuttojen kursseja euroon verrattuna sisältävän JSON-tiedoston.
	 * Tiedostosta haetaan parametrina annettua valuuttatunnusta vastaava kurssi.
	 * 
	 * @param tunnus Valuutan tunnus, jonka kurssi palautetaan
	 * @return Valuuttakurssi double-muodossa.
	 * 
	 */
	
	public Double haeValuutta(String tunnus) {
		double jsonDouble = 0;
		this.urlString = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/eur/"
				+ tunnus.toLowerCase() + ".min.json";
		try {
			is = new URL(urlString).openStream();
			json = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
			JsonParser jsonparser = new JsonParser();
			System.out.println(json);
			
			if (tunnus.length() == 3) {
				json = json.substring(27);
				json = json.split("}")[0];
			} else if (tunnus.length() == 4) {
				json = json.substring(28);
				json = json.split("}")[0];
			}
			
			jsonDouble = Double.parseDouble(json);

			System.out.println(jsonDouble);
		} catch (Exception e) {
			System.err.println("Error ensimmäisessä catch lohkossa");
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonDouble;
	}

	public ValuuttaDAO() {

	}
}