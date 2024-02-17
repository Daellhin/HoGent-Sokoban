package gui.wijzigSpel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PaneelSpelbordSelectie extends GridPane {
	@FXML
	private Label lblKiesSpelbord;

	@FXML
	private Button btnBoven;

	@FXML
	private Button btnOnder;

	private SchermWijzigSpel scherm;
	private int plaats;
	private List<String> spelnamen;
	private int aantalHyperlinks;
	private final int maxAantalHyperlinks = 12;

	public PaneelSpelbordSelectie(SchermWijzigSpel scherm) {
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

	private void initGUI() {
		lblKiesSpelbord.setText(scherm.geefString("selecteer_spelbord"));
		this.plaats = 0;

		spelnamen = new ArrayList<>();
		for (int i = 1; i <= scherm.geefTotaalAantalSpelborden(); i++) {
			spelnamen.add(String.format("%s %d", scherm.geefString("spelbord_hoofdletter"), i));
		}

		toonSpelbordnamen();
	}

	public void update() {
		verwijderHyperlinks();
		toonSpelbordnamen();
	}

	private void toonSpelbordnamen() {
		int aantalHyperlinks = (spelnamen.size() / maxAantalHyperlinks == plaats
				? spelnamen.size() % maxAantalHyperlinks
				: maxAantalHyperlinks);

		for (int i = 0; i < aantalHyperlinks; i++) {
			Hyperlink hyper = new Hyperlink(spelnamen.get(plaats * maxAantalHyperlinks + i));
			hyper.getStyleClass().add("auto-hyperlink");
			this.add(hyper, 0, 2 + i);
			final int j = i;
			hyper.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					scherm.stelVolgnummerDatabankIn(
							Integer.parseInt(spelnamen.get(plaats * maxAantalHyperlinks + j).replaceAll("[^\\d]", "")));
					scherm.stelVolgnummerProgrammaIn(plaats * maxAantalHyperlinks + j);
					scherm.selecteerSpelbord();
					scherm.toonPanelenWijzigSpel();
				}
			});

		}

		if (plaats == 0) {
			btnBoven.setDisable(true);
		}
		if (aantalHyperlinks < maxAantalHyperlinks) {
			btnOnder.setDisable(true);
			btnBoven.setDisable(false);
		}
		if (spelnamen.size() <= maxAantalHyperlinks) {
			btnBoven.setDisable(true);
			btnOnder.setDisable(true);
		}

	}

	@FXML
	public void btnBovenOnAction(ActionEvent event) {
		verwijderHyperlinks();
		plaats--;
		toonSpelbordnamen();
	}

	@FXML
	public void btnOnderOnAction(ActionEvent event) {
		verwijderHyperlinks();
		plaats++;
		toonSpelbordnamen();

	}

	private void verwijderHyperlinks() {
		for (int i = 0; i < aantalHyperlinks; i++) {
			this.getChildren().remove(3);
		}
	}

	public void verwijderHyperlink(int volgnummer) {
		spelnamen.remove(volgnummer);
	}
}
