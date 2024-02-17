package gui.wijzigSpel;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import gui.menu.SchermMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SchermWijzigSpel extends GridPane {
	private DomeinController dc;
	private ResourceBundle bundle;

	private PaneelZijbalkWijzigSpel paneelZijbalkWijzigSpel;
	private PaneelWijzigSpel paneelWijzigSpel;
	private PaneelSpelbordSelectie paneelSpelbordSelectie;

	private int volgnummerProgramma;
	private int volgnummerDatabank;
	private boolean spelbordVerwijderd;

	public SchermWijzigSpel(DomeinController dc, ResourceBundle bundle) {
		super();
		this.dc = dc;
		this.bundle = bundle;
		this.addEventFilter(KeyEvent.KEY_PRESSED, this::keyboardHandler);

		FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println("Probleem met tonen " + this.getClass().getSimpleName());
		}

		this.getStylesheets().add(getClass().getResource("/resources/css/SchermMenu.css").toString());
		this.getStylesheets().add(getClass().getResource("/resources/css/SchermMaakSpel.css").toString());

		this.paneelSpelbordSelectie = new PaneelSpelbordSelectie(this);
		this.add(paneelSpelbordSelectie, 0, 0, 2, 1);
	}

	public void keyboardHandler(KeyEvent e) {
		int[] positie;
		switch (e.getCode()) {
		case UP:
			positie = paneelWijzigSpel.getGeselecteerdePositie();
			if (positie[0] > 0) {
				positie[0]--;
				paneelWijzigSpel.updateSelectie(positie);
			}
			e.consume();
			break;
		case RIGHT:
			positie = paneelWijzigSpel.getGeselecteerdePositie();
			if (positie[1] < 9) {
				positie[1]++;
				paneelWijzigSpel.updateSelectie(positie);
			}
			e.consume();
			break;
		case DOWN:
			positie = paneelWijzigSpel.getGeselecteerdePositie();
			if (positie[0] < 9) {
				positie[0]++;
				paneelWijzigSpel.updateSelectie(positie);
			}
			e.consume();
			break;
		case LEFT:
			positie = paneelWijzigSpel.getGeselecteerdePositie();
			if (positie[1] > 0) {
				positie[1]--;
				paneelWijzigSpel.updateSelectie(positie);
			}
			e.consume();
			break;
		default:
			break;

		}

	}

	public String geefString(String key) {
		return bundle.getString(key);
	}

	public void toonpaneelWijzigSpel() {
		this.paneelWijzigSpel = new PaneelWijzigSpel(this);
		this.add(paneelWijzigSpel, 0, 0);
	}

	public void toonSchermMenu() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermMenu(dc, bundle));
		stage.setScene(scene);
	}

	public int[] geefGeselecteerdePositie() {
		return paneelWijzigSpel.getGeselecteerdePositie();
	}

	public void updatepaneelWijzigSpel(int[] positie) {
		paneelWijzigSpel.update(positie);
	}

	public void resetPaneelWijzigSpel() {
		paneelWijzigSpel.resetSpelbord();
	}

	public void setSpelbordVerwijderd() {
		this.spelbordVerwijderd = true;
	}

	public boolean getSpelbordVerwijderd() {
		return spelbordVerwijderd;
	}

	public void toonPanelenWijzigSpel() {
		this.paneelWijzigSpel = new PaneelWijzigSpel(this);
		this.paneelZijbalkWijzigSpel = new PaneelZijbalkWijzigSpel(this);

		this.add(paneelZijbalkWijzigSpel, 1, 0);
		this.add(paneelWijzigSpel, 0, 0);
	}

	public void toonPaneelSpelbordSelectie() {
		this.getChildren().remove(paneelWijzigSpel);
		this.getChildren().remove(paneelZijbalkWijzigSpel);
		this.getChildren().remove(0);

		this.add(paneelSpelbordSelectie, 0, 0, 2, 1);
		paneelSpelbordSelectie.update();
	}

	public void verwijderHyperlink() {
		this.paneelSpelbordSelectie.verwijderHyperlink(volgnummerProgramma);
	}

	public void selecteerSpelbord() {
		// volgnummer begint op 0
		dc.selecteerSpelbord(volgnummerProgramma);
	}

	public void stelVolgnummerDatabankIn(int volgnummerDatabank) {
		this.volgnummerDatabank = volgnummerDatabank;
	}

	public void stelVolgnummerProgrammaIn(int volgnummerProgramma) {
		this.volgnummerProgramma = volgnummerProgramma;
	}

	// dc methodes

	public void maakNieuwSpelAan(String naam) {
		dc.maakNieuwSpelAan(naam);
	}

	public void maakLeegSpelbordAan() {
		dc.maakLeegSpelbordAan();
	}

	public int geefTotaalAantalSpelborden() {
		return dc.geefTotaalAantalSpelborden();
	}

	public String[][] geefVakken() {
		return dc.geefVakken();
	}

	public int[] geefPositieMannetje() {
		return dc.geefPositieMannetje();
	}

	public int[][] geefPositiesKisten() {
		return dc.geefPositiesKisten();
	}

	public void pasSpelbordAan(int[] positie, int aanpassing) {
		dc.pasSpelbordAan(positie, aanpassing);
	}

	public String geefSpelnaam() {
		return dc.geefSpelnaam();
	}

	public void resetSpelbord() {
		dc.resetSpelbord();
	}

	public void verwijderHuidigSpelbord() {
		dc.verwijderHuidigSpelbord();
	}

	public void maakSpelstukkenAan() {
		dc.maakSpelstukkenAan();
	}

	public void wijzigSpel() {
		dc.wijzigSpel();
	}

	public void vervangSpelbord() {
		dc.vervangSpelbord(volgnummerDatabank);
	}

	public int geefAantalDoelen() {
		return paneelWijzigSpel.geefAantalDoelen();
	}

	public int geefAantalKisten() {
		return paneelWijzigSpel.geefAantalKisten();
	}

}
