package gui.menu;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import gui.credits.SchermCredits;
import gui.maakSpel.SchermMaakSpel;
import gui.speelSpel.SchermSpeelSpel;
import gui.wijzigSpel.SchermWijzigSpel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class SchermMenu extends VBox {
	private DomeinController dc;
	private ResourceBundle bundle;
	private PaneelHoofdMenu paneelHoofdMenu;

	public SchermMenu(DomeinController dc, ResourceBundle bundle) {
		super();
		this.dc = dc;
		this.bundle = bundle;

		this.getStylesheets().add(getClass().getResource("/resources/css/SchermMenu.css").toString());

		Media sound = new Media(this.getClass().getResource("/resources/sounds/pirateslife.mp3").toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
		mediaPlayer.stop();

		FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println("Probleem met tonen " + this.getClass().getSimpleName());
		}

		this.paneelHoofdMenu = new PaneelHoofdMenu(this);
		this.getChildren().add(paneelHoofdMenu);

	}

	public String geefString(String key) {
		return bundle.getString(key);
	}

	public void toonPaneelHoofdMenu() {
		this.getChildren().remove(0);
		this.getChildren().add(paneelHoofdMenu);
	}

	public void toonPaneelSpeelSpelSelectie() {
		this.getChildren().remove(0);
		this.getChildren().add(new PaneelSpelSelectie(this, dc.geefSpelnamen(), true));
	}

	public void toonPaneelWijzigSpelSelectie() {
		this.getChildren().remove(0);
		this.getChildren().add(new PaneelSpelSelectie(this, dc.geefSpelnamenVanSpeler(), false));
	}

	public void toonSchermSpeelSpel() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermSpeelSpel(dc, bundle));
		stage.setScene(scene);
	}

	public void toonSchermMaakSpel() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermMaakSpel(dc, bundle));
		stage.setScene(scene);
	}

	public void toonSchermWijzigSpel() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermWijzigSpel(dc, bundle));
		stage.setScene(scene);
	}

	public void toonSchermCredits() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermCredits(dc, bundle));
		stage.setScene(scene);
	}

	// dc methodes

	public String geefGebruikersnaam() {
		return dc.geefGebruikersnaam();
	}

	public boolean geefAdminrechten() {
		return dc.geefAdminrechten();
	}

	public int geefTotaalAantalSpelborden() {
		return dc.geefTotaalAantalSpelborden();
	}

	public void kiesSpel(String naam) {
		dc.kiesSpel(naam);
	}

}
