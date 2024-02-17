package gui.speelSpel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PaneelSpeelSpel extends Pane {

	public final double TILE_SIZE = 75;

	private Image imgVeld;
	private Image imgDoel;
	private Image imgMuur;
	private Image imgMannetje;
	private Image imgKistToe;
	private Image imgKistOpen;

	private SchermSpeelSpel scherm;
	private Label winBoodschap;
	private ImageView mannetje;
	private List<ImageView> kistenImages;
	private int[][] vorigePositiesKisten;
	private int[] vorigePositieMannetje;
	private ImageView[][] vakkenImages;

	public PaneelSpeelSpel(SchermSpeelSpel scherm) {
		super();
		this.scherm = scherm;

		maakImagesAan("classic");
		initGUI();

	}

	public void initGUI() {
		// tekent de vakken
		String[][] vakken = scherm.geefVakken();

		vakkenImages = new ImageView[vakken.length][vakken.length];

		for (int rijPos = 0; rijPos < vakken.length; rijPos++) {
			for (int kolPos = 0; kolPos < vakken[rijPos].length; kolPos++) {
				ImageView image = null;
				if (vakken[rijPos][kolPos].contains("Doel")) {
					image = new ImageView(imgDoel);
				} else if (vakken[rijPos][kolPos].contains("Muur")) {
					image = new ImageView(imgMuur);
				} else if (vakken[rijPos][kolPos].contains("Veld")) {
					image = new ImageView(imgVeld);
				}
				vakkenImages[rijPos][kolPos] = image;
				image.relocate(kolPos * TILE_SIZE, rijPos * TILE_SIZE);
				this.getChildren().add(image);
			}
		}

		// tekent het mannetje
		int[] positieMannetje = scherm.geefPositieMannetje();
		vorigePositieMannetje = positieMannetje;

		mannetje = new ImageView(imgMannetje);
		mannetje.relocate(positieMannetje[1] * TILE_SIZE, positieMannetje[0] * TILE_SIZE);
		this.getChildren().add(mannetje);

		// tekent de kisten
		kistenImages = new ArrayList<>();
		int[][] positiesKisten = scherm.geefPositiesKisten();
		vorigePositiesKisten = positiesKisten;

		for (int[] positie : positiesKisten) {
			ImageView kist = new ImageView(bepaalKistImage(positie));
			kist.relocate(positie[1] * TILE_SIZE, positie[0] * TILE_SIZE);
			this.getChildren().add(kist);
			kistenImages.add(kist);
		}

	}

	public void update() {
		// mannetje updaten
		int[] positieMannetje = scherm.geefPositieMannetje();
		mannetje.relocate(positieMannetje[1] * TILE_SIZE, positieMannetje[0] * TILE_SIZE);

		if (vorigePositieMannetje[1] - positieMannetje[1] == -1) {
			mannetje.setScaleX(-1);
		} else if (vorigePositieMannetje[1] - positieMannetje[1] == 1) {
			mannetje.setScaleX(1);
		}

		vorigePositieMannetje = positieMannetje;

		// kist updaten
		int[][] positiesKisten = scherm.geefPositiesKisten();

		int teVerplaatsenKist = geefTeVerplaatsenKist(positiesKisten);
		if (teVerplaatsenKist != -1) {
			ImageView kist = kistenImages.get(teVerplaatsenKist);
			kist.relocate(positiesKisten[teVerplaatsenKist][1] * TILE_SIZE,
					positiesKisten[teVerplaatsenKist][0] * TILE_SIZE);
			kist.setImage(bepaalKistImage(positiesKisten[teVerplaatsenKist]));
		}
		vorigePositiesKisten = positiesKisten;

	}

	// geeft de juiste image terug
	private Image bepaalKistImage(int[] positie) {
		if (scherm.geefVakken()[positie[0]][positie[1]].contains("Doel")) {
			return imgKistOpen;
		} else {
			return imgKistToe;
		}
	}

	// bepaalt welke kist moet verplaatst worden
	private int geefTeVerplaatsenKist(int[][] positiesKisten) {
		for (int i = 0; i < vorigePositiesKisten.length; i++) {
			if (!Arrays.equals(vorigePositiesKisten[i], positiesKisten[i])) {
				return i;
			}
		}
		return -1;
	}

	// reset de spelstukken
	public void resetSpelstukken() {
		// mannetje
		int[] positieMannetje = scherm.geefPositieMannetje();
		mannetje.relocate(positieMannetje[1] * TILE_SIZE, positieMannetje[0] * TILE_SIZE);

		// kisten
		int[][] positiesKisten = scherm.geefPositiesKisten();
		vorigePositiesKisten = positiesKisten;

		int teller = 0;
		for (ImageView kist : kistenImages) {
			kist.relocate(positiesKisten[teller][1] * TILE_SIZE, positiesKisten[teller][0] * TILE_SIZE);
			kist.setImage(bepaalKistImage(positiesKisten[teller]));
			teller++;
		}

	}

	public void toonWinBoodschap() {
		// toont de winboodschap
		winBoodschap = new Label(scherm.geefString("spelbord_gewonnen"));
		winBoodschap.setId("winboodschap");
		winBoodschap.setTranslateX(TILE_SIZE * 3);
		winBoodschap.setTranslateY(TILE_SIZE * 4);
		this.getChildren().add(winBoodschap);
	}

	public void veranderStijl(String stijl) {
		maakImagesAan(stijl);
		veranderAlleImages();

	}

	private void maakImagesAan(String stijl) {
		imgVeld = new Image(getClass().getResource("/resources/tiles/" + stijl + "/veld.png").toExternalForm(),
				TILE_SIZE, TILE_SIZE, false, true);
		imgDoel = new Image(getClass().getResource("/resources/tiles/" + stijl + "/doel.png").toExternalForm(),
				TILE_SIZE, TILE_SIZE, false, true);
		imgMuur = new Image(getClass().getResource("/resources/tiles/" + stijl + "/muur.png").toExternalForm(),
				TILE_SIZE, TILE_SIZE, false, true);
		imgMannetje = new Image(getClass().getResource("/resources/tiles/" + stijl + "/mannetje.png").toExternalForm(),
				TILE_SIZE, TILE_SIZE, false, true);
		imgKistToe = new Image(getClass().getResource("/resources/tiles/" + stijl + "/kist_toe.png").toExternalForm(),
				TILE_SIZE, TILE_SIZE, false, true);
		imgKistOpen = new Image(getClass().getResource("/resources/tiles/" + stijl + "/kist_open.png").toExternalForm(),
				TILE_SIZE, TILE_SIZE, false, true);

	}

	private void veranderAlleImages() {
		String[][] vakken = scherm.geefVakken();

		for (int rijPos = 0; rijPos < vakken.length; rijPos++) {
			for (int kolPos = 0; kolPos < vakken[rijPos].length; kolPos++) {
				if (vakken[rijPos][kolPos].contains("Doel")) {
					vakkenImages[rijPos][kolPos].setImage(imgDoel);
				} else if (vakken[rijPos][kolPos].contains("Muur")) {
					vakkenImages[rijPos][kolPos].setImage(imgMuur);
				} else if (vakken[rijPos][kolPos].contains("Veld")) {
					vakkenImages[rijPos][kolPos].setImage(imgVeld);
				}

			}
		}

		for (ImageView kist : kistenImages) {
			this.getChildren().remove(kist);
		}

		kistenImages = new ArrayList<>();
		int[][] positiesKisten = scherm.geefPositiesKisten();
		vorigePositiesKisten = positiesKisten;

		for (int[] positie : positiesKisten) {
			ImageView kist = new ImageView(bepaalKistImage(positie));
			kist.relocate(positie[1] * TILE_SIZE, positie[0] * TILE_SIZE);
			this.getChildren().add(kist);
			kistenImages.add(kist);
		}

		mannetje.setImage(imgMannetje);
	}

}