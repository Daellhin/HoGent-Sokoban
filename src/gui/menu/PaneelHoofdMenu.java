package gui.menu;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PaneelHoofdMenu extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblGebruikersnaam;
	@FXML
	private Hyperlink hypSpeel;
	@FXML
	private Hyperlink hypMaak;
	@FXML
	private Hyperlink hypWijzig;
	@FXML
	private Hyperlink hypAfsluiten;
	@FXML
	private Hyperlink hypCredits;

	private SchermMenu scherm;

	public PaneelHoofdMenu(SchermMenu scherm) {
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

	}

	public void initGUI() {
		lblSokoban.setText(scherm.geefString("sokoban"));

		lblGebruikersnaam.setText(scherm.geefGebruikersnaam());
		if (scherm.geefAdminrechten()) {
			hypMaak.setText(scherm.geefString("maak_nieuw_spel"));
			hypWijzig.setText(scherm.geefString("wijzig_spel"));
		} else {
			this.getChildren().remove(hypMaak);
			this.getChildren().remove(hypWijzig);

		}
		hypSpeel.setText(scherm.geefString("speel_spel"));
		hypCredits.setText(scherm.geefString("credits"));
		hypAfsluiten.setText(scherm.geefString("afsluiten"));

	}

	@FXML
	public void hypSpeelOnAction(ActionEvent event) {
		scherm.toonPaneelSpeelSpelSelectie();
	}

	@FXML
	public void hypMaakOnAction(ActionEvent event) {
		scherm.toonSchermMaakSpel();
	}

	@FXML
	public void hypWijzigOnAction(ActionEvent event) {
		scherm.toonPaneelWijzigSpelSelectie();
	}

	@FXML
	public void hypCreditsOnAction(ActionEvent event) {
		scherm.toonSchermCredits();
	}

	@FXML
	public void hypAfsluitenOnAction(ActionEvent event) {
		Stage stage = (Stage) this.getScene().getWindow();
		stage.close();
	}

}
