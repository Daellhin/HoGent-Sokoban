package gui.speelSpel;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PaneelZijbalkSpeelSpel extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblSpelnaam;
	@FXML
	private Label lblAantalVerplaatsingen;
	@FXML
	private Label lblAantalZettenNr;
	@FXML
	private Label lblAantalVoltooideSpelborden;
	@FXML
	private Label lblAantalVoltooideSpelbordenNr;
	@FXML
	private Label lblBoodschap;
	@FXML
	private Button btnBoven;
	@FXML
	private Button btnOnder;
	@FXML
	private Button btnRechts;
	@FXML
	private Button btnLinks;
	@FXML
	private Button btnStoppen;
	@FXML
	private Button btnVolgendSpelbord;
	@FXML
	private Button btnResetSpelbord;
	@FXML
	private ComboBox<String> cboStijl;
	@FXML
	private Label lblStijl;

	private SchermSpeelSpel scherm;

	public PaneelZijbalkSpeelSpel(SchermSpeelSpel scherm) {
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
		cboStijl.getItems().addAll("Classic", "Yellow");

	}

	public void initGUI() {
		lblSokoban.setText(scherm.geefString("sokoban"));
		lblStijl.setText(scherm.geefString("selecteer_stijl"));
		cboStijl.setValue("Classic");
		lblAantalVerplaatsingen.setText(scherm.geefString("aantal_verplaatsingen"));
		lblAantalVoltooideSpelborden.setText(scherm.geefString("aantal_voltooide_spelborden"));
		lblSpelnaam.setText(scherm.geefSpelnaam());
		lblBoodschap.setText("");
		btnStoppen.setText(scherm.geefString("stoppen"));
		btnVolgendSpelbord.setText(scherm.geefString("voltooi_volgend_spelbord"));
		btnResetSpelbord.setText(scherm.geefString("spelbord_resetten"));

		btnBoven.setDisable(false);
		btnLinks.setDisable(false);
		btnOnder.setDisable(false);
		btnRechts.setDisable(false);
		btnResetSpelbord.setDisable(false);
		btnVolgendSpelbord.setDisable(true);
	}

	public void update() {
		if (scherm.isSpelbordVoltooid()) {
			if (scherm.geefAantalVoltooideSpelborden() == scherm.geefTotaalAantalSpelborden()) {
				btnVolgendSpelbord.setDisable(true);
			} else {
				btnVolgendSpelbord.setDisable(false);
			}

			btnBoven.setDisable(true);
			btnLinks.setDisable(true);
			btnOnder.setDisable(true);
			btnRechts.setDisable(true);
			btnResetSpelbord.setDisable(true);
			scherm.toonWinBoodschap();

		}

		lblAantalZettenNr.setText(String.valueOf(scherm.geefAantalVerplaatsingen()));
		lblAantalVoltooideSpelbordenNr.setText(
				String.format("%d/%d", scherm.geefAantalVoltooideSpelborden(), scherm.geefTotaalAantalSpelborden()));

	}

	public void afhandelenVerplaatsing(int richting) {
		try {
			scherm.verplaatsMannetje(richting);
			lblBoodschap.setText("");
			scherm.updatePaneelSpeelSpel();
			update();
		} catch (IllegalArgumentException e) {
			lblBoodschap.setText(scherm.geefString(e.getMessage()));
		}
	}

	@FXML
	public void btnBovenOnAction(ActionEvent event) {
		afhandelenVerplaatsing(0);
	}

	@FXML
	public void btnRechtsOnAction(ActionEvent event) {
		afhandelenVerplaatsing(1);
	}

	@FXML
	public void btnOnderOnAction(ActionEvent event) {
		afhandelenVerplaatsing(2);
	}

	@FXML
	public void btnLinksOnAction(ActionEvent event) {
		afhandelenVerplaatsing(3);
	}

	@FXML
	public void btnStoppenOnAction(ActionEvent event) {
		scherm.toonSchermMenu();
	}

	@FXML
	public void btnVolgendSpelbordOnAction(ActionEvent event) {
		scherm.selecteerVolgendSpelbord();
		scherm.maakSpelstukkenAan();
		scherm.initPaneelSpeelSpel();
		initGUI();
		update();
	}

	@FXML
	public void btnResetSpelbordOnAction(ActionEvent event) {
		scherm.resetSpelstukken();
		scherm.resetPaneelSpeelSpel();
		update();
	}

	@FXML
	public void cboStijlOnAction(ActionEvent event) {
		String stijl = cboStijl.getValue().toString().toLowerCase();
		scherm.veranderStijl(stijl);
	}

}
