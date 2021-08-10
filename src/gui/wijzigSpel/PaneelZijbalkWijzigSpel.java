package gui.wijzigSpel;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PaneelZijbalkWijzigSpel extends GridPane {
	@FXML
	private Label lblSokoban;
	@FXML
	private Label lblSpelnaam;
	@FXML
	private Button btnPlaatsMuur;
	@FXML
	private Button btnPlaatsDoel;
	@FXML
	private Label lblAantalDoelen;
	@FXML
	private Button btnPlaatsMannetje;
	@FXML
	private Button btnPlaatsKist;
	@FXML
	private Label lblAantalKisten;
	@FXML
	private Button btnVeldLeegmaken;
	@FXML
	private Label lblBoodschap;
	@FXML
	private Label lblSucces;
	@FXML
	private Button btnResetSpelbord;
	@FXML
	private Button btnSlaSpelbordOp;

	@FXML
	private Button btnVerwijderSpelbord;
	@FXML
	private Button btnPasVolgendeSpelbordAan;
	@FXML
	private Button btnSlaSpelOp;
	@FXML
	private Button btnStoppen;

	private SchermWijzigSpel scherm;

	public PaneelZijbalkWijzigSpel(SchermWijzigSpel scherm) {
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
		lblBoodschap.setText("");
		lblSpelnaam.setText(scherm.geefSpelnaam());
		lblAantalDoelen.setText(String.valueOf(scherm.geefAantalDoelen()));
		lblAantalKisten.setText(String.valueOf(scherm.geefAantalKisten()));

		btnPlaatsMuur.setText(scherm.geefString("plaats_muur"));
		btnPlaatsDoel.setText(scherm.geefString("plaats_doel"));
		btnPlaatsMannetje.setText(scherm.geefString("plaats_mannetje"));
		btnPlaatsKist.setText(scherm.geefString("plaats_kist"));
		btnVeldLeegmaken.setText(scherm.geefString("veld_leegmaken"));

		btnResetSpelbord.setText(scherm.geefString("spelbord_resetten"));
		btnSlaSpelbordOp.setText(scherm.geefString("spelbord_opslaan"));
		btnVerwijderSpelbord.setText(scherm.geefString("verwijder_spelbord"));
		btnPasVolgendeSpelbordAan.setText(scherm.geefString("pas_volgende_spelbord_aan"));
		btnSlaSpelOp.setText(scherm.geefString("spel_opslaan"));
		btnStoppen.setText(scherm.geefString("stoppen"));
	}

	public void update() {
		lblAantalDoelen.setText(String.valueOf(scherm.geefAantalDoelen()));
		lblAantalKisten.setText(String.valueOf(scherm.geefAantalKisten()));
	}

	private void afhandelenAanpassing(int aanpassing) {
		lblBoodschap.setText("");
		lblSucces.setText("");
		int[] positie = scherm.geefGeselecteerdePositie();
		scherm.pasSpelbordAan(positie, aanpassing);
		scherm.updatepaneelWijzigSpel(positie);
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
	public void btnResetSpelbordOnAction(ActionEvent event) {
		scherm.resetSpelbord();
		scherm.resetPaneelWijzigSpel();
		update();
	}

	@FXML
	public void btnSlaSpelbordOpOnAction(ActionEvent event) {
		try {
			scherm.vervangSpelbord();
			lblSucces.setText(scherm.geefString("spelbord_aangepast"));
			setDisableAanpassingsKnoppen(true);
			btnResetSpelbord.setDisable(true);
			btnSlaSpelbordOp.setDisable(true);
			btnVerwijderSpelbord.setDisable(true);

		} catch (Exception e) {
			System.out.println("Het lukt niet om het spelbord te vervangen in de databank");
		}
	}

	private void setDisableAanpassingsKnoppen(boolean disable) {
		btnPlaatsMuur.setDisable(disable);
		btnPlaatsDoel.setDisable(disable);
		btnPlaatsMannetje.setDisable(disable);
		btnPlaatsKist.setDisable(disable);
		btnVeldLeegmaken.setDisable(disable);
	}

	@FXML
	public void btnVerwijderSpelbordOnAction(ActionEvent event) {
		try {
			scherm.verwijderHuidigSpelbord();
			scherm.verwijderHyperlink();
			scherm.setSpelbordVerwijderd();

			setDisableAanpassingsKnoppen(true);
			btnResetSpelbord.setDisable(true);
			btnSlaSpelbordOp.setDisable(true);
			lblSucces.setText(scherm.geefString("spelbord_verwijderd"));
		} catch (IllegalArgumentException e) {
			lblBoodschap.setText(scherm.geefString(e.getMessage()));
		}

	}

	@FXML
	public void btnPasVolgendeSpelbordAanOnAction(ActionEvent event) {
		scherm.toonPaneelSpelbordSelectie();
	}

	@FXML
	public void btnSlaSpelOpOnAction(ActionEvent event) {
		if (scherm.getSpelbordVerwijderd()) {
			scherm.wijzigSpel();
		}

		setDisableAanpassingsKnoppen(true);
		btnResetSpelbord.setDisable(true);
		btnSlaSpelOp.setDisable(true);
		btnVerwijderSpelbord.setDisable(true);
		btnPasVolgendeSpelbordAan.setDisable(true);
		btnSlaSpelbordOp.setDisable(true);
		lblSucces.setText(scherm.geefString("spel_gewijzigd"));
	}

	@FXML
	public void btnStoppenOnAction(ActionEvent event) {
		scherm.toonSchermMenu();
	}
}
