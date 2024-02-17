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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

public class PaneelRegistreer extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblRegistreren;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Label lblWachtwoord;
	@FXML
	private Label lblNaam;
	@FXML
	private Label lblVoornaam;
	@FXML
	private Label lblMessage;
	@FXML
	private TextField txfGebruikersnaam;
	@FXML
	private TextField txfVoornaam;
	@FXML
	private TextField txfNaam;
	@FXML
	private PasswordField pwdWachtwoord;
	@FXML
	private TextField txfWachtwoord;
	@FXML
	private CheckBox chbWachtwoord;
	@FXML
	private Button btnRegistreer;
	@FXML
	private Hyperlink hypAanmelden;
	@FXML
	private Tooltip tipGebruikersnaam;
	@FXML
	private Tooltip tipWachtwoordPassword;
	@FXML
	private Tooltip tipWachtwoordText;
	@FXML
	private Button btnEn;
	@FXML
	private Button btnNl;
	@FXML
	private Button btnFr;

	private SchermBegin scherm;

	public PaneelRegistreer(SchermBegin scherm) {
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

	@FXML
	public void btnRegistreerOnAction(ActionEvent event) {
		try {
			scherm.registreer(txfNaam.getText(), txfVoornaam.getText(), txfGebruikersnaam.getText(),
					chbWachtwoord.isSelected() ? txfWachtwoord.getText() : pwdWachtwoord.getText());
			scherm.toonSchermMenu();
		} catch (IllegalArgumentException e) {
			lblMessage.setText(scherm.geefString(e.getMessage()));
		}

	}

	@FXML
	public void hypAanmeldenOnAction(ActionEvent event) {
		scherm.toonPaneelInlog();
	}

	private void initGUI() {
		pwdWachtwoord.setVisible(true);
		txfWachtwoord.setVisible(false);
	}

	public void update() {
		lblRegistreren.setText(scherm.geefString("registreren"));
		lblGebruikersnaam.setText(scherm.geefString("vraag_gebruikersnaam"));
		lblWachtwoord.setText(scherm.geefString("vraag_wachtwoord"));
		lblVoornaam.setText(scherm.geefString("vraag_voornaam"));
		lblNaam.setText(scherm.geefString("vraag_naam"));
		btnRegistreer.setText(scherm.geefString("registreren"));
		hypAanmelden.setText(scherm.geefString("aanmelden"));
		chbWachtwoord.setText(scherm.geefString("toon_wachtwoord"));
		tipGebruikersnaam.setText(scherm.geefString("tooltip_gebruikersnaam"));
		tipWachtwoordPassword.setText(scherm.geefString("tooltip_wachtwoord"));
		tipWachtwoordText.setText(scherm.geefString("tooltip_wachtwoord"));

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
