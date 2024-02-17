package gui.begin;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import domein.DomeinController;
import gui.menu.SchermMenu;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SchermBegin extends VBox {
	private DomeinController dc;
	private ResourceBundle bundle;
	private PaneelMeldAan paneelInlog;
	private PaneelRegistreer paneelRegistreer;

	public SchermBegin(DomeinController dc, ResourceBundle bundle) {
		super();
		this.dc = dc;
		this.bundle = bundle;

		this.getStylesheets().add(getClass().getResource("/resources/css/SchermBegin.css").toString());

		FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println("Probleem met tonen " + this.getClass().getSimpleName());
		}

		this.paneelInlog = new PaneelMeldAan(this);
		this.paneelRegistreer = new PaneelRegistreer(this);
		this.getChildren().add(paneelInlog);

	}

	public void toonPaneelInlog() {
		this.getChildren().remove(0);
		this.getChildren().add(paneelInlog);
		paneelInlog.update();
	}

	public void toonPaneelRegistreer() {
		this.getChildren().remove(0);
		this.getChildren().add(paneelRegistreer);
		paneelRegistreer.update();
	}

	public void stelTaalIn(String key) {
		if (key != bundle.getLocale().getLanguage()) {
			Locale locale = new Locale(key);
			this.bundle = ResourceBundle.getBundle("bundles.bundle", locale);
		}
	}

	public String geefString(String key) {
		return bundle.getString(key);
	}

	public void meldAan(String gebruikersnaam, String wachtwoord) {
		dc.meldAan(gebruikersnaam, wachtwoord);
	}

	public void registreer(String naam, String voornaam, String gebruikersnaam, String wachtwoord) {
		dc.registreer(naam, voornaam, gebruikersnaam, wachtwoord);
	}

	public void toonSchermMenu() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermMenu(dc, bundle));
		stage.setScene(scene);
	}

}
