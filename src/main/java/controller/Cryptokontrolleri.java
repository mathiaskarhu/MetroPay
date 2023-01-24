package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import dao.CryptoChartModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.AktiivinenKayttaja;
import model.Valuutta;
import view.GUI;
import view.IGUI;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Cryptokontrollerissa on kaikki krypto näkymään liittyvät metodit sekä toiminnot.
 * 
 * @author Mathias Karhu
 * @author Tatu Talvikko
 * @author Otso Poussa
 * @author Joni Jääskeläinen
 *
 */

public class Cryptokontrolleri implements Initializable {
	
	@FXML
    private Label takaisinBtn;
	
	@FXML
	private Label saldoTxt;
	
	@FXML
	private ChoiceBox<Valuutta> valuuttaBox;
	
	@FXML
	private Button ostoBtn;

	@FXML
	private Button myyntiBtn;
	
	private IGUI gui = new GUI();

	private ValuuttaKontrolleri vKontrolleri = new ValuuttaKontrolleri();

	private ArrayList<Valuutta> valuuttaLista;

	private static int valuuttaBoxIndex = 1;
	
	@FXML
    private LineChart cryptoChart;
	
	@FXML
    private CategoryAxis x;
	
	@FXML
    private NumberAxis y;
	
	@FXML
    private Label bitcoinPriceLabel;
	
    @FXML
    private Label cryptoProsenttiLabel;

	@FXML
	private Label cryptoNimikeLabel;
	
    @FXML
    private ImageView cryptoKuvakeImg;
    
    private ArrayList<Float> bitcoinPrice = new ArrayList<Float>();
    
    private ArrayList<Double> priceChangePercentage = new ArrayList<Double>();
    
    private ObservableList<XYChart.Series<String,Double>> graphs= FXCollections.observableArrayList();
    
    public Cryptokontrolleri() {
    	System.out.println("TÄÄLLÄ TOIMII CRYPTOT");
    }
    
    /**
     * 
     * haeSaldo hakee käyttäjän saldon ja saldon tyypin AktiivinenKayttaja luokasta.
     * 
     * @return String (saldon määrä)
     * 
     */
    
    public String haeSaldo() {
		String valuutta = AktiivinenKayttaja.getInstance().getValuutta().getTunnus();
		double saldo;
		FileInputStream input;
		switch (valuutta) {
			case "BTC":
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoBtc();
				this.cryptoNimikeLabel.setText("Bitcoin BTC");
				try {
					input = new FileInputStream("src/main/java/view/resources/img/bitcoin-icon.png");
					this.cryptoKuvakeImg.setImage(new Image(input));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				chartInit("BTC");
				break;
			case "DOGE":
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoDoge();
				this.cryptoNimikeLabel.setText("Dogecoin DOGE");
				try {
					input = new FileInputStream("src/main/java/view/resources/img/doge-icon.png");
					this.cryptoKuvakeImg.setImage(new Image(input));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				chartInit("DOGE");
				break;
			case "ADA":
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoAda();
				this.cryptoNimikeLabel.setText("Cardano ADA");
				try {
					input = new FileInputStream("src/main/java/view/resources/img/cardano-icon.png");
					this.cryptoKuvakeImg.setImage(new Image(input));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				chartInit("ADA");
				break;
			case "ETH":
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoEth();
				this.cryptoNimikeLabel.setText("Ethereum ETH");
				try {
					input = new FileInputStream("src/main/java/view/resources/img/ethereum-icon.png");
					this.cryptoKuvakeImg.setImage(new Image(input));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				chartInit("ETH");
				break;
			default:
				saldo = AktiivinenKayttaja.getInstance().getTili().getSaldoBtc();
				chartInit("BTC");
				break;
		}
		
		if (saldo == 0) {
			return String.format("%.2f", saldo);
		} 
		return String.format("%.4f", saldo);
	}
    
    /**
     * Asetaa saldon näkymään.
     * @param saldo
     */
    
	public void asetaSaldo(String saldo) {
		String valuutta = AktiivinenKayttaja.getInstance().getValuutta().getTunnus();
		
		switch (valuutta) {
		case "BTC":
			saldoTxt.setText("₿ " + saldo);
			break;
		case "DOGE":
			saldoTxt.setText("Ð " + saldo);
			break;
		case "ADA":
			saldoTxt.setText("₳ " + saldo);
			break;
		case "ETH":
			saldoTxt.setText("Ξ " + saldo);
			break;
		default:
			saldoTxt.setText("₿ " + saldo);
			break;
		}
	}
	
	/**
	 * osto() metodi vaihtaa näkymää krypton osto sivulle.
	 * @param e
	 * @throws IOException
	 * 
	 */
	
	public void osto(ActionEvent e) throws IOException {
		gui.vaihdaNäkymä("cryptoostaminen.fxml");
	}
	
	/**
	 * myynti vaihtaa näkymää krypton myynti sivulle.
	 * @param e
	 * @throws IOException
	 * 
	 */
	
	public void myynti(ActionEvent e) throws IOException {
		gui.vaihdaNäkymä("cryptomyynti.fxml");
	}
    
	/**
	 * palaaTakaisin() vaihtaa näkymän takaisin sovelluksen etusivulle.
	 * @param e
	 * @throws IOException
	 * 
	 */
	
    public void palaaTakaisin(MouseEvent e) throws IOException {
        gui.vaihdaNäkymä("etusivu.fxml");
    }
    
    /**
     * chartInit luo graafin, joka näyttää missä valitun kryptovaluutan kurssi menee.
     * chartInit tekee paljon kutsuja CryptoChartModel luokkaan, joka taas kutsuu cryptocompare apia.
     * @param valuutta
     * 
     */
    
    public void chartInit(String valuutta) {
    	CryptoChartModel.setValuutta(valuutta);

        XYChart.Series<String,Double> seriesMinute=new XYChart.Series<>();
        XYChart.Series<String,Double> seriesHour=new XYChart.Series<>();
        XYChart.Series<String,Double> seriesDay=new XYChart.Series<>();

        bitcoinPrice.add(Float.valueOf(CryptoChartModel.getCurrentPrice()));
        priceChangePercentage.add(Double.valueOf(CryptoChartModel.getCurrentPrice()));
        // 1582 1611 1569ms when below is done sync significantly slower vs 466 482 480ms with no sync
        bitcoinPriceLabel.setText("€ " + CryptoChartModel.getCurrentPrice());
        
        CryptoChartModel.getCryptoInfoInitialize();
        CryptoChartModel.getCryptoInfoInitializeHistoHour();
        CryptoChartModel.getCryptoInfoInitializeHistoDay();
        //The Code Below When synced improved performance by ~12ms
        CompletableFuture<Void> addSeriesData=CompletableFuture.runAsync(() ->CryptoChartModel.addSeriesData(seriesMinute)).
                                                                    thenRun(()->{CryptoChartModel.addSeriesDataHour(seriesHour);}).
                                                                    thenRun(()->{CryptoChartModel.addSeriesDataDay(seriesDay);});
        cryptoChart.getData().clear();
        cryptoChart.getData().add(seriesMinute);
        graphs.addAll(seriesMinute,seriesHour,seriesDay);
        
        //Timers
        Calendar calendar = Calendar.getInstance();
        Timer bitcoinCurrentPriceTimer = new Timer();
        BGTask t2 = new BGTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Float newBitcoinPrice = Float.valueOf(CryptoChartModel.getCurrentPrice());
                    if (bitcoinPrice.get(bitcoinPrice.size()-1) > newBitcoinPrice) {
                        bitcoinPriceLabel.setText("⯆ € " + CryptoChartModel.getCurrentPrice());
                        bitcoinPriceLabel.setStyle("-fx-text-fill: #ea3943;");
                    } else if (bitcoinPrice.get(bitcoinPrice.size()-1) < newBitcoinPrice) {
                        bitcoinPriceLabel.setText("⯅ € " + CryptoChartModel.getCurrentPrice());
                        bitcoinPriceLabel.setStyle("-fx-text-fill: #16c784;");
                    } else {
                    	bitcoinPriceLabel.setStyle("-fx-text-fill: #ebecf0;");
                    }
                });
            }
        };
        
        bitcoinCurrentPriceTimer.scheduleAtFixedRate(t2, 10000, 10000);
        
        /*
         * 
         * Käytetään prosentin laskemiseen
         * 
         * */
        
        Timer currentPercentTimer = new Timer();
        BGTask ptc = new BGTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
            
                	Double newPriceChangePercentage = Double.valueOf(CryptoChartModel.getCurrentPrice());
                    Double finalPriceChangePercentage;
                	
                    if (priceChangePercentage.get(priceChangePercentage.size()-1) > newPriceChangePercentage) {
                    	Double openPrice = priceChangePercentage.get(priceChangePercentage.size()-1);
                    	Double lastPrice = newPriceChangePercentage;
                    	finalPriceChangePercentage = (lastPrice - openPrice) / openPrice * 100;
                    	String formatPercentage = String.format("%.3f", finalPriceChangePercentage);
                    	cryptoProsenttiLabel.setText("⯆ " + formatPercentage + " %");
                    	cryptoProsenttiLabel.setStyle("-fx-text-fill: #ea3943;");       
                    } else if (priceChangePercentage.get(priceChangePercentage.size()-1) < newPriceChangePercentage) {
                    	Double openPrice = priceChangePercentage.get(priceChangePercentage.size()-1);
                    	Double lastPrice = newPriceChangePercentage;
                    	finalPriceChangePercentage = (lastPrice - openPrice) / openPrice * 100;
                    	String formatPercentage = String.format("%.3f", finalPriceChangePercentage);
                    	cryptoProsenttiLabel.setText("⯅ " + formatPercentage + " %");
                    	cryptoProsenttiLabel.setStyle("-fx-text-fill: #16c784;");
                    } else {
                    	cryptoProsenttiLabel.setStyle("-fx-text-fill: #ebecf0;");
                    }
                });
            }
        };
        
        currentPercentTimer.scheduleAtFixedRate(ptc, 10000, 10000);

        Timer minChartUpdate = new Timer();
        BGTask t = new BGTask();
        t.series = seriesMinute;
        minChartUpdate.scheduleAtFixedRate(t, CryptoChartModel.millisToNextMinute(calendar), 60000);

        Timer hourChartUpdate = new Timer();
        BGTask t3 = new BGTask() {
             @Override
             public void run() {
                 CryptoChartModel.getCryptoInfoInitializeHistoHour();
                 Platform.runLater(() -> {
                     CryptoChartModel.addSeriesDataUpdateHoursChart(seriesHour);
                 });
             }
        };

        hourChartUpdate.scheduleAtFixedRate(t3,CryptoChartModel.millisToNextHour(calendar), 3600000);

        Timer dayChartUpdate = new Timer();
        BGTask t4 = new BGTask() {
            @Override
            public void run() {
                CryptoChartModel.getCryptoInfoInitializeHistoDay();
                Platform.runLater(() -> {
                    CryptoChartModel.addSeriesDataUpdateDaysChart(seriesDay);
                });
            }
        };
        
        dayChartUpdate.scheduleAtFixedRate(t4,CryptoChartModel.millisToNextDay(calendar), 86400000);
    }
    
	/**
	 * 
	 * initialize metodi ajetaan heti näkymän avautumassa. Metodi kutsuu asetaSaldo(ja haeSaldo()) metodeja, jotka alustavat valuutan näytön. 
	 * 
	 */
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initialize");
		System.out.println(AktiivinenKayttaja.getInstance().getTili().getSaldoBtc());
		asetaSaldo(haeSaldo());
		valuuttaLista = vKontrolleri.alustaValuutat();
		valuuttaLista = vKontrolleri.haeKryptot();

		for (int i = 0; i < valuuttaLista.size(); i++) {
			valuuttaBox.getItems().add(valuuttaLista.get(i));
		}
		valuuttaBox.getSelectionModel().select(valuuttaBoxIndex);
		 
		valuuttaBox.setOnAction((event) -> {
			Valuutta vValittu = valuuttaBox.getSelectionModel().getSelectedItem();
			valuuttaBoxIndex = valuuttaBox.getSelectionModel().getSelectedIndex();
			AktiivinenKayttaja.getInstance().setValuutta(vValittu);
			asetaSaldo(haeSaldo());
		});
    }

    /**
     * 
     * päivittää kryptovaluutta kurssin ja siihen liittyvän graafin.
     *
     */
    
    private class BGTask extends TimerTask {
        XYChart.Series<String, Double> series;
        @Override
        public void run() {
            CryptoChartModel.getCryptoInfoInitialize();
            Platform.runLater(() -> {
                CryptoChartModel.addSeriesDataUpdateMinsChart(series);
            });

        }
    }
}
