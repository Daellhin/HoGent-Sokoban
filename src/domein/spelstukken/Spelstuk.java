package domein.spelstukken;

import domein.Spel;

public abstract class Spelstuk {
	private int rijPos;
	private int kolPos;

	/**
	 * Maakt een nieuw Spelstuk aan op de gegeven rij en kol positie.
	 * 
	 * @param rijPos
	 * @param kolPos
	 */
	public Spelstuk(int rijPos, int kolPos) {
		setrijPos(rijPos);
		setkolPos(kolPos);
	}

	// setters
	private void setrijPos(int rijPos) {
		controleerRijPos(rijPos);
		this.rijPos = rijPos;
	}

	private void setkolPos(int kolPos) {
		controleerKolPos(kolPos);
		this.kolPos = kolPos;
	}

	// getters
	public int getrijPos() {
		return rijPos;
	}

	public int getkolPos() {
		return kolPos;
	}

	// controles
	/**
	 * Controleert of de gegeven rijPos binnen de dimensies van het Spelbord ligt.
	 * 
	 * @param rijPos
	 */
	private void controleerRijPos(int rijPos) {
		if (rijPos < 0 || rijPos > Spel.getMaxRijPos()) {
			throw new IllegalArgumentException("rijpos_buiten_spelbord");
		}
	}

	/**
	 * Controleert of de gegeven rijPos binnen de dimensies van het Spelbord ligt.
	 * 
	 * @param kolPos
	 */
	private void controleerKolPos(int kolPos) {
		if (kolPos < 0 || kolPos > Spel.getMaxRijPos()) {
			throw new IllegalArgumentException("kolpos_buiten_spelbord");
		}
	}

	// methods
	/**
	 * Stelt de rijPos en kolPos in met de gegeven positie.
	 * 
	 * @param positie [0]=rijPos, [1]=kolPos
	 */
	public void verplaats(int[] positie) {
		setrijPos(positie[0]);
		setkolPos(positie[1]);
	}

}
