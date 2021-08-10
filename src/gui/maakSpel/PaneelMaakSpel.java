package gui.maakSpel;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PaneelMaakSpel extends Pane {

	public static double TILE_SIZE = 75;

	private Image imgVeld;
	private Image imgDoel;
	private Image imgMuur;
	private Image imgMannetje;
	private Image imgKistToe;
	private Image imgKistOpen;
	private Image imgSelectie;

	private SchermMaakSpel scherm;
	private int[] geselecteerdePositie;

	private ImageView mannetje;
	private ImageView selectie;
	private List<ImageView> kisten;
	private ImageView[][] vakkenImages;

	public PaneelMaakSpel(SchermMaakSpel scherm) {
		super();
		this.scherm = scherm;

		maakImagesAan("classic");
		initGUI();
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
		imgSelectie = new Image(getClass().getResource("/resources/tiles/" + stijl + "/selectie.png").toExternalForm(),
				TILE_SIZE, TILE_SIZE, false, true);

	}

	private void initGUI() {
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this::muisOnClick);

		kisten = new ArrayList<>();
		vakkenImages = new ImageView[10][10];
		scherm.maakLeegSpelbordAan();

		// tekent de vakken
		String[][] vakken = scherm.geefVakken();
		for (int rijPos = 0; rijPos < vakken.length; rijPos++) {
			for (int kolPos = 0; kolPos < vakken[rijPos].length; kolPos++) {
				ImageView image = new ImageView(imgVeld);
				vakkenImages[rijPos][kolPos] = image;
				image.relocate(kolPos * TILE_SIZE, rijPos * TILE_SIZE);
				this.getChildren().add(image);
			}
		}

		// tekent de selectie
		geselecteerdePositie = new int[] { 0, 0 };
		selectie = new ImageView(imgSelectie);
		this.getChildren().add(selectie);
	}

	private void muisOnClick(MouseEvent e) {
		geselecteerdePositie[0] = (int) ((e.getY() - 0.1) / TILE_SIZE);
		geselecteerdePositie[1] = (int) ((e.getX() - 0.1) / TILE_SIZE);
		updateSelectie(geselecteerdePositie);
	}

	public void update(int[] positie) {
		// vak veranderen
		String[][] vakken = scherm.geefVakken();

		ImageView image = null;
		if (vakken[positie[0]][positie[1]].contains("Doel")) {
			image = new ImageView(imgDoel);
		} else if (vakken[positie[0]][positie[1]].contains("Muur")) {
			image = new ImageView(imgMuur);
		} else if (vakken[positie[0]][positie[1]].contains("Veld")) {
			image = new ImageView(imgVeld);
		}

		this.getChildren().remove(vakkenImages[positie[0]][positie[1]]);
		image.relocate(positie[1] * TILE_SIZE, positie[0] * TILE_SIZE);
		this.getChildren().add(image);

		vakkenImages[positie[0]][positie[1]] = image;

		// mannetje veranderen
		int[] positieMannetje = scherm.geefPositieMannetje();

		this.getChildren().remove(mannetje);
		if (positieMannetje != null) {
			mannetje = new ImageView(imgMannetje);
			mannetje.relocate(positieMannetje[1] * TILE_SIZE, positieMannetje[0] * TILE_SIZE);
			this.getChildren().add(mannetje);
		}

		// kisten veranderen
		int[][] positiesKisten = scherm.geefPositiesKisten();

		for (ImageView kistenI : kisten) {
			this.getChildren().remove(kistenI);
		}

		kisten = new ArrayList<>();

		for (int[] positieKist : positiesKisten) {
			ImageView kist = new ImageView(bepaalKistImage(positieKist));
			kist.relocate(positieKist[1] * TILE_SIZE, positieKist[0] * TILE_SIZE);
			this.getChildren().add(kist);
			kisten.add(kist);
		}

		selectie.toFront();

	}

	// geeft de juiste image terug
	private Image bepaalKistImage(int[] positie) {
		if (scherm.geefVakken()[positie[0]][positie[1]].contains("Doel")) {
			return imgKistOpen;
		} else {
			return imgKistToe;
		}
	}

	public void updateSelectie(int[] positie) {
		selectie.relocate(positie[1] * TILE_SIZE, positie[0] * TILE_SIZE);
	}

	public int[] getGeselecteerdePositie() {
		return geselecteerdePositie;
	}

	public int geefAantalDoelen() {
		int aantalDoelen = 0;
		for (ImageView[] rij : vakkenImages) {
			for (ImageView imageView : rij) {
				if (imageView.getImage().equals(imgDoel)) {
					aantalDoelen++;
				}
			}
		}
		return aantalDoelen;
	}

	public int geefAantalKisten() {
		return kisten.size();
	}

}