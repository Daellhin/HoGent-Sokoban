package gui.speelSpel;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import gui.menu.SchermMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SchermSpeelSpel extends GridPane {
	private DomeinController dc;
	private ResourceBundle bundle;

	private PaneelZijbalkSpeelSpel paneelZijbalkSpeelSpel;
	private PaneelSpeelSpel paneelSpeelSpel;

	public SchermSpeelSpel(DomeinController dc, ResourceBundle bundle) {
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

		this.getStylesheets().add(getClass().getResource("/resources/css/SchermSpeelSpel.css").toString());

		dc.maakSpelstukkenAan();
		this.paneelSpeelSpel = new PaneelSpeelSpel(this);
		this.paneelZijbalkSpeelSpel = new PaneelZijbalkSpeelSpel(this);

		this.add(paneelZijbalkSpeelSpel, 1, 0);
		this.add(paneelSpeelSpel, 0, 0);
	}

	private void keyboardHandler(KeyEvent e) {
		if (!dc.isSpelbordVoltooid()) {
			switch (e.getCode()) {
			case UP:
				paneelZijbalkSpeelSpel.afhandelenVerplaatsing(0);
				e.consume();
				break;
			case RIGHT:
				paneelZijbalkSpeelSpel.afhandelenVerplaatsing(1);
				e.consume();
				break;
			case DOWN:
				paneelZijbalkSpeelSpel.afhandelenVerplaatsing(2);
				e.consume();
				break;
			case LEFT:
				paneelZijbalkSpeelSpel.afhandelenVerplaatsing(3);
				e.consume();
				break;
			case P:
				veranderStijl("purple");
				e.consume();
				break;
			default:
				break;
			}
		}
	}

	public String geefString(String key) {
		return bundle.getString(key);
	}

	public void updatePaneelZijbalkSpeelSpel() {
		paneelZijbalkSpeelSpel.update();
	}

	public void updatePaneelSpeelSpel() {
		paneelSpeelSpel.update();
	}

	public void initPaneelSpeelSpel() {
		paneelSpeelSpel.initGUI();
	}

	public void toonSchermMenu() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermMenu(dc, bundle));
		stage.setScene(scene);
	}

	public void resetPaneelSpeelSpel() {
		paneelSpeelSpel.resetSpelstukken();
	}

	// dc methodes

	public String geefSpelnaam() {
		return dc.geefSpelnaam();
	}

	public int geefTotaalAantalSpelborden() {
		return dc.geefTotaalAantalSpelborden();
	}

	public int geefAantalVerplaatsingen() {
		return dc.geefAantalVerplaatsingen();
	}

	public int geefAantalVoltooideSpelborden() {
		return dc.geefAantalVoltooideSpelborden();
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

	public void verplaatsMannetje(int richting) {
		dc.verplaatsMannetje(richting);
	}

	public void resetSpelstukken() {
		dc.resetSpelStukken();
	}

	public boolean isSpelbordVoltooid() {
		return dc.isSpelbordVoltooid();
	}

	public void toonWinBoodschap() {
		paneelSpeelSpel.toonWinBoodschap();
	}

	public void selecteerVolgendSpelbord() {
		dc.selecteerVolgendSpelbord();
		dc.maakSpelstukkenAan();
	}

	public void maakSpelstukkenAan() {
		dc.maakSpelstukkenAan();
	}

	public void veranderStijl(String stijl) {
		paneelSpeelSpel.veranderStijl(stijl);
	}
}
