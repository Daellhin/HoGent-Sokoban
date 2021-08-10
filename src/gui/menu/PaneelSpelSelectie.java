package gui.menu;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PaneelSpelSelectie extends GridPane {
	@FXML
	private Label lblKiesSpel;
	@FXML
	private Button btnBoven;
	@FXML
	private Button btnOnder;
	@FXML
	private Button btnReturn;

	private SchermMenu scherm;
	private int plaats;
	private List<String> spelnamen;
	private boolean spelen;
	private int aantalHyperlinks;
	private final int maxAantalHyperlinks = 12;

	public PaneelSpelSelectie(SchermMenu scherm, List<String> spelnamen, boolean spelen) {
		super();
		this.scherm = scherm;
		this.spelnamen = spelnamen;
		this.spelen = spelen;

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
		lblKiesSpel.setText(scherm.geefString("selecteer_spel"));
		this.plaats = 0;
		toonSpelnamen();
	}

	private void toonSpelnamen() {
		aantalHyperlinks = (spelnamen.size() / maxAantalHyperlinks == plaats ? spelnamen.size() % maxAantalHyperlinks
				: maxAantalHyperlinks);

		for (int i = 0; i < aantalHyperlinks; i++) {
			Hyperlink hyper = new Hyperlink(spelnamen.get(plaats * maxAantalHyperlinks + i));
			hyper.getStyleClass().add("auto-hyperlink");
			this.add(hyper, 0, 2 + i);
			final int j = i;
			hyper.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					scherm.kiesSpel(spelnamen.get(plaats * maxAantalHyperlinks + j));
					if (spelen) {
						scherm.toonSchermSpeelSpel();
					} else {
						scherm.toonSchermWijzigSpel();
					}

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
		toonSpelnamen();
	}

	@FXML
	public void btnOnderOnAction(ActionEvent event) {
		verwijderHyperlinks();
		plaats++;
		toonSpelnamen();

	}

	@FXML
	public void btnReturnOnAction(ActionEvent event) {
		scherm.toonPaneelHoofdMenu();
	}

	private void verwijderHyperlinks() {
		for (int i = 0; i < aantalHyperlinks; i++) {
			this.getChildren().remove(4);
		}
	}

}
