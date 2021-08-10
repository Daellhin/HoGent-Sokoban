package gui.maakSpel;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import gui.menu.SchermMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SchermMaakSpel extends GridPane {
	private DomeinController dc;
	private ResourceBundle bundle;

	private PaneelZijbalkMaakSpel paneelZijbalkMaakSpel;
	private PaneelMaakSpel paneelMaakSpel;
	private boolean spelAangemaakt;

	public SchermMaakSpel(DomeinController dc, ResourceBundle bundle) {
		super();
		this.dc = dc;
		this.bundle = bundle;
		this.spelAangemaakt = false;
		this.addEventFilter(KeyEvent.KEY_PRESSED, this::keyboardHandler);

		FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println("Probleem met tonen " + this.getClass().getSimpleName());
		}
		this.getStylesheets().add(getClass().getResource("/resources/css/SchermMaakSpel.css").toString());

		this.paneelZijbalkMaakSpel = new PaneelZijbalkMaakSpel(this);
		this.add(paneelZijbalkMaakSpel, 1, 0);

	}

	public void keyboardHandler(KeyEvent e) {
		if (spelAangemaakt) {
			int[] positie;
			switch (e.getCode()) {
			case UP:
				positie = paneelMaakSpel.getGeselecteerdePositie();
				if (positie[0] > 0) {
					positie[0]--;
					paneelMaakSpel.updateSelectie(positie);
				}
				e.consume();
				break;
			case RIGHT:
				positie = paneelMaakSpel.getGeselecteerdePositie();
				if (positie[1] < 9) {
					positie[1]++;
					paneelMaakSpel.updateSelectie(positie);
				}
				e.consume();
				break;
			case DOWN:
				positie = paneelMaakSpel.getGeselecteerdePositie();
				if (positie[0] < 9) {
					positie[0]++;
					paneelMaakSpel.updateSelectie(positie);
				}
				e.consume();
				break;
			case LEFT:
				positie = paneelMaakSpel.getGeselecteerdePositie();
				if (positie[1] > 0) {
					positie[1]--;
					paneelMaakSpel.updateSelectie(positie);
				}
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

	public void maakNieuwSpelAan(String naam) {
		dc.maakNieuwSpelAan(naam);
		this.spelAangemaakt = true;
	}

	public void toonPaneelMaakSpel() {
		this.paneelMaakSpel = new PaneelMaakSpel(this);
		this.add(paneelMaakSpel, 0, 0);
	}

	public int[] geefGeselecteerdePositie() {
		return paneelMaakSpel.getGeselecteerdePositie();
	}

	public void toonSchermMenu() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermMenu(dc, bundle));
		stage.setScene(scene);
	}

	// dc methodes

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

	public void updatePaneelMaakSpel(int[] positie) {
		paneelMaakSpel.update(positie);
	}

	public void voegSpelbordToe() {
		dc.voegSpelbordToe();
	}

	public boolean valideerSpel() {
		return dc.valideerSpel();
	}

	public void registreerSpel() {
		dc.registreerSpel();
	}

	public int geefAantalDoelen() {
		return paneelMaakSpel.geefAantalDoelen();
	}

	public int geefAantalKisten() {
		return paneelMaakSpel.geefAantalKisten();
	}
}
