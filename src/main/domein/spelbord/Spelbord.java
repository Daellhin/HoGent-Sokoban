package domein.spelbord;

import java.util.Arrays;

import domein.Spel;

public class Spelbord {
	private Vak[][] vakken;
	private Spelbord volgendSpelBord;
	private int[] startPositieMannetje;
	private int[][] startPositiesKisten;
	private int aantalVerplaatsingen;
	private int aantalDoelen;
	private Vak[][] origineleVakken;

	/**
	 * Maakt een nieuw Spelbord aan met de meegegeven Vakken, startPositieMannetje
	 * en startPositiesKisten.
	 * 
	 * @param vakken
	 * @param startPositieMannetje
	 * @param startPositiesKisten
	 */
	public Spelbord(Vak[][] vakken, int[] startPositieMannetje, int[][] startPositiesKisten) {
		setVakken(vakken);
		setStartPositieMannetje(startPositieMannetje);
		setStartPositiesKisten(startPositiesKisten);
		setVolgendSpelBord(volgendSpelBord);
		setAantalVerplaatsingen(0);

		// aantal doelen bepaalen
		int doelen = 0;
		for (Vak[] rij : vakken) {
			for (Vak vak : rij) {
				if (vak instanceof Doel)
					doelen++;
			}
		}
		setAantalDoelen(doelen);

		// vakken kopieren om te kunnen resetten
		origineleVakken = new Vak[Spel.getMaxRijPos()][Spel.getMaxKolPos()];
		for (int i = 0; i < vakken.length; i++) {
			origineleVakken[i] = Arrays.copyOf(vakken[i], vakken[i].length);
		}
	}

	/**
	 * Maakt een nieuw spelbord aan met enkel Velden.
	 */
	public Spelbord() {
		vakken = new Vak[Spel.getMaxRijPos()][Spel.getMaxKolPos()];
		aantalDoelen = 0;

		for (int i = 0; i < Spel.getMaxRijPos(); i++) {
			for (int j = 0; j < Spel.getMaxKolPos(); j++) {
				vakken[i][j] = new Veld(i, j);
			}
		}
	}

	// setters
	private void setVakken(Vak[][] vakken) {
		this.vakken = vakken;
	}

	public void setVolgendSpelBord(Spelbord volgendSpelBord) {
		this.volgendSpelBord = volgendSpelBord;
	}

	public void setStartPositieMannetje(int[] startPositieMannetje) {
		this.startPositieMannetje = startPositieMannetje;
	}

	public void setStartPositiesKisten(int[][] startPositiesKisten) {
		this.startPositiesKisten = startPositiesKisten;
	}

	public void setAantalVerplaatsingen(int aantalVerplaatsingen) {
		this.aantalVerplaatsingen = aantalVerplaatsingen;
	}

	public void setOrigineleVakken(Vak[][] origineleVakken) {
		this.origineleVakken = origineleVakken;
	}

	public void setAantalDoelen(int aantalDoelen) {
		this.aantalDoelen = aantalDoelen;
	}

	// getters
	public Vak[][] getVakken() {
		return vakken;
	}

	public final Spelbord getVolgendSpelBord() {
		return volgendSpelBord;
	}

	public int[] getStartPositieMannetje() {
		return startPositieMannetje;
	}

	public int[][] getStartPositiesKisten() {
		return startPositiesKisten;
	}

	public int getAantalVerplaatsingen() {
		return aantalVerplaatsingen;
	}

	public int getAantalDoelen() {
		return aantalDoelen;
	}

	public Vak[][] getOrigineleVakken() {
		return origineleVakken;
	}

	// methods
	/**
	 * Verhoogt het aaantalVerplaatsingen met 1.
	 */
	public void verhoogAantalVerplaatsingen() {
		this.aantalVerplaatsingen++;
	}

	/**
	 * Retourneert het Vak op de gegeven positie.
	 * 
	 * @param positie [0]=rijPos, [1]=kolPos
	 * @return het gevonden vak
	 */
	public Vak geefVak(int[] positie) {
		if ((positie[0] < 0 || positie[1] < 0)
				|| (positie[0] > Spel.getMaxRijPos() - 1 || positie[1] > Spel.getMaxRijPos() - 1)) {
			return new Muur(0, 0);
		}

		return vakken[positie[0]][positie[1]];
	}

	/**
	 * Stelt het aantal verplaatsingen in op 0.
	 */
	public void resetAantalVerplaatsingen() {
		setAantalVerplaatsingen(0);
	}

	/**
	 * Controleert of er een Muur op de gegeven positie staat. Als er een Muur
	 * staat, wordt die vervangen door een Veld.
	 * 
	 * @param positie [0]=rijPos, [1]=kolPos
	 * @return true als er een muur op de gegeven positie staat, anders false
	 */
	public boolean controleerEnVerwijderMuur(int[] positie) {
		if (vakken[positie[0]][positie[1]] instanceof Muur) {
			vakken[positie[0]][positie[1]] = new Veld(positie[0], positie[1]);
			return true;
		}
		return false;
	}

	/**
	 * Voegt een Muur toe aan het Spelbord op de gegeven positie.
	 * 
	 * @param positie [0]=rijPos, [1]=kolPos
	 */
	public void voegMuurToe(int[] positie) {
		if (vakken[positie[0]][positie[1]] instanceof Doel) {
			aantalDoelen--;
		}
		vakken[positie[0]][positie[1]] = new Muur(positie[0], positie[1]);
	}

	/**
	 * Voegt een Doel toe aan het Spelbord op de gegeven positie.
	 * 
	 * @param positie [0]=rijPos, [1]=kolPos
	 */
	public void voegDoelToe(int[] positie) {
		if (!(vakken[positie[0]][positie[1]] instanceof Doel)) {
			vakken[positie[0]][positie[1]] = new Doel(positie[0], positie[1]);
			aantalDoelen++;
		}
	}

	/**
	 * Voegt een Veld toe aan het Spelbord op de gegeven positie.
	 * 
	 * @param positie [0]=rijPos, [1]=kolPos
	 */
	public void voegVeldToe(int[] positie) {
		if ((vakken[positie[0]][positie[1]] instanceof Doel)) {
			aantalDoelen--;

		}
		vakken[positie[0]][positie[1]] = new Veld(positie[0], positie[1]);
	}

	/**
	 * Zet de Vakken van het huidigeSpelbord terug op hun beginposities.
	 */
	public void resetVakken() {
		for (int i = 0; i < this.vakken.length; i++) {
			this.vakken[i] = Arrays.copyOf(this.origineleVakken[i], this.origineleVakken[i].length);
		}
	}

}
