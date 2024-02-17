package gui.begin;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PaneelMeldAan extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblAanmelden;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblWachtwoord;
	@FXML
	private PasswordField pwdWachtwoord;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private Hyperlink hypRegistreer;
	@FXML
	private Button btnAanmelden;
	@FXML
	private Label lblFoutInloggen;
	@FXML
	private CheckBox chbWachtwoord;
	@FXML
	private TextField txfWachtwoord;
	@FXML
	private Button btnEn;
	@FXML
	private Button btnNl;
	@FXML
	private Button btnFr;

	private SchermBegin scherm;

	public PaneelMeldAan(SchermBegin scherm) {
		super();
		this.scherm = scherm;

		FXMLLoader loader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ex) {
			System.out.println("Probleem met tonen " + this.getClass().getSimpleName());
		}

		initGUI();
		update();

	}

	@FXML
	public void hypRegistreerOnAction(ActionEvent event) {
		scherm.toonPaneelRegistreer();
	}

	@FXML
	public void btnAanmeldenOnAction(ActionEvent event) {
		try {
			scherm.meldAan(txfGebruikersnaam.getText(),
					chbWachtwoord.isSelected() ? txfWachtwoord.getText() : pwdWachtwoord.getText());
			scherm.toonSchermMenu();
		} catch (IllegalArgumentException e) {
			lblFoutInloggen.setText(scherm.geefString(e.getMessage()));
		}

	}

	@FXML
	public void chbWachtwoordOnAction(ActionEvent event) {
		if (chbWachtwoord.isSelected()) {
			txfWachtwoord.setText(pwdWachtwoord.getText());
			txfWachtwoord.setVisible(true);
			pwdWachtwoord.setVisible(false);
			return;
		}
		pwdWachtwoord.setText(txfWachtwoord.getText());
		pwdWachtwoord.setVisible(true);
		txfWachtwoord.setVisible(false);
	}

	public void initGUI() {
		pwdWachtwoord.setVisible(true);
		txfWachtwoord.setVisible(false);
	}

	public void update() {
		lblSokoban.setText(scherm.geefString("sokoban"));
		lblAanmelden.setText(scherm.geefString("aanmelden"));
		lblGebruikersnaam.setText(scherm.geefString("vraag_gebruikersnaam"));
		lblWachtwoord.setText(scherm.geefString("vraag_wachtwoord"));
		btnAanmelden.setText(scherm.geefString("aanmelden"));
		hypRegistreer.setText(scherm.geefString("registreren"));
		chbWachtwoord.setText(scherm.geefString("toon_wachtwoord"));

	}

	@FXML
	public void btnEnOnAction(ActionEvent event) {
		scherm.stelTaalIn("en");
		update();
	}

	@FXML
	public void btnNlOnAction(ActionEvent event) {
		scherm.stelTaalIn("nl");
		update();

	}

	@FXML
	public void btnFrOnAction(ActionEvent event) {
		scherm.stelTaalIn("fr");
		update();

	}
}
