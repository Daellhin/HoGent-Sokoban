package gui.maakSpel;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class PaneelZijbalkMaakSpel extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblSpelnaam;
	@FXML
	private Label lblAantalSpelborden;
	@FXML
	private Label lblAantalSpelbordenNr;
	@FXML
	private Button btnSlaSpelOp;
	@FXML
	private Label lblBoodschap;
	@FXML
	private Label lblGeefSpelnaam;
	@FXML
	private Label lblSucces;
	@FXML
	private Button btnVoegSpelbordToe;
	@FXML
	private Button btnPlaatsDoel;
	@FXML
	private Label lblAantalDoelen;
	@FXML
	private Button btnPlaatsMuur;
	@FXML
	private Button btnPlaatsMannetje;
	@FXML
	private Button btnPlaatsKist;
	@FXML
	private Label lblAantalKisten;
	@FXML
	private Button btnVeldLeegmaken;
	@FXML
	private Button btnResetSpelbord;
	@FXML
	private TextField txfSpelnaam;
	@FXML
	private Button btnStoppen;

	private SchermMaakSpel scherm;

	public PaneelZijbalkMaakSpel(SchermMaakSpel scherm) {
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
		lblAantalSpelborden.setText(scherm.geefString("totaal_aantal_spelborden"));
		lblAantalSpelbordenNr.setText("0");
		lblBoodschap.setText("");
		lblGeefSpelnaam.setText(scherm.geefString("geef_naam"));
		lblAantalDoelen.setText(String.valueOf(0));
		lblAantalKisten.setText(String.valueOf(0));

		btnPlaatsDoel.setText(scherm.geefString("plaats_doel"));
		btnPlaatsKist.setText(scherm.geefString("plaats_kist"));
		btnPlaatsMannetje.setText(scherm.geefString("plaats_mannetje"));
		btnPlaatsMuur.setText(scherm.geefString("plaats_muur"));
		btnVeldLeegmaken.setText(scherm.geefString("veld_leegmaken"));
		btnSlaSpelOp.setText(scherm.geefString("spel_opslaan"));
		btnVoegSpelbordToe.setText(scherm.geefString("spelbord_opslaan"));
		btnStoppen.setText(scherm.geefString("stoppen"));
		btnResetSpelbord.setText(scherm.geefString("spelbord_resetten"));

		lblSpelnaam.setText("");
		txfSpelnaam.setVisible(true);

		// alles disablen tot een spelnaam is ingegeven
		setDisableAanpassingsKnoppen(true);
		btnSlaSpelOp.setDisable(true);
		btnVoegSpelbordToe.setDisable(true);
		btnResetSpelbord.setDisable(true);
	}

	public void update() {
		lblAantalDoelen.setText(String.valueOf(scherm.geefAantalDoelen()));
		lblAantalKisten.setText(String.valueOf(scherm.geefAantalKisten()));
	}

	private void setDisableAanpassingsKnoppen(boolean disable) {
		btnPlaatsMuur.setDisable(disable);
		btnPlaatsDoel.setDisable(disable);
		btnPlaatsMannetje.setDisable(disable);
		btnPlaatsKist.setDisable(disable);
		btnVeldLeegmaken.setDisable(disable);
	}

	@FXML
	public void txfSpelnaamOnKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			try {
				scherm.maakNieuwSpelAan(txfSpelnaam.getText());
				lblSpelnaam.setText(txfSpelnaam.getText());
				txfSpelnaam.setVisible(false);
				lblGeefSpelnaam.setText("");
				setDisableAanpassingsKnoppen(false);
				lblBoodschap.setText("");
				btnResetSpelbord.setDisable(false);
				scherm.toonPaneelMaakSpel();
			} catch (IllegalArgumentException e) {
				lblBoodschap.setText(scherm.geefString(e.getMessage()));
			}
		}
	}

	private void afhandelenAanpassing(int aanpassing) {
		lblBoodschap.setText("");
		lblSucces.setText("");
		btnVoegSpelbordToe.setDisable(false);
		int[] positie = scherm.geefGeselecteerdePositie();
		scherm.pasSpelbordAan(positie, aanpassing);
		scherm.updatePaneelMaakSpel(positie);
		update();
	}

	@FXML
	public void btnPlaatsMuurOnAction(ActionEvent event) {
		afhandelenAanpassing(0);
	}

	@FXML
	public void btnPlaatsDoelOnAction(ActionEvent event) {
		afhandelenAanpassing(1);
	}

	@FXML
	public void btnPlaatsMannetjeOnAction(ActionEvent event) {
		afhandelenAanpassing(2);
	}

	@FXML
	public void btnPlaatsKistOnAction(ActionEvent event) {
		afhandelenAanpassing(3);
	}

	@FXML
	public void btnVeldLeegmakenOnAction(ActionEvent event) {
		afhandelenAanpassing(4);
	}

	@FXML
	public void btnVoegSpelbordToeOnAction(ActionEvent event) {

		try {
			scherm.voegSpelbordToe();
			lblSucces.setText(scherm.geefString("spelbord_geldig"));
			lblAantalSpelbordenNr.setText(String.valueOf(scherm.geefTotaalAantalSpelborden()));
			// scherm.maakLeegSpelbordAan();
			scherm.toonPaneelMaakSpel();
			btnVoegSpelbordToe.setDisable(true);
			btnSlaSpelOp.setDisable(false);
		} catch (IllegalArgumentException e) {
			lblBoodschap.setText(scherm.geefString(e.getMessage()));
		}
	}

	@FXML
	public void btnSlaSpelOpOnAction(ActionEvent event) {
		if (scherm.valideerSpel()) {
			scherm.registreerSpel();
			lblSucces.setText(scherm.geefString("spel_succesvol_opgeslagen"));
			setDisableAanpassingsKnoppen(true);
			btnResetSpelbord.setDisable(true);
			btnVoegSpelbordToe.setDisable(true);
			btnSlaSpelOp.setDisable(true);
		}
	}

	@FXML
	public void btnStoppenOnAction(ActionEvent event) {
		scherm.toonSchermMenu();
	}

	@FXML
	public void btnResetSpelbordOnAction(ActionEvent event) {
		scherm.maakLeegSpelbordAan();
		scherm.toonPaneelMaakSpel();
		btnVoegSpelbordToe.setDisable(true);
		update();
	}
}
