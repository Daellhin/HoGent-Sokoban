package domein.spelbord;

import domein.Spel;

public abstract class Vak {
	private int rijPos;
	private int kolPos;

	/**
	 * Maakt een nieuw Vak aan op de gegeven rij en kol positie.
	 * 
	 * @param rijPos
	 * @param kolPos
	 */
	public Vak(int rijPos, int kolPos) {
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
	 * @param positie
	 */
	private void controleerRijPos(int rijPos) {
		if (rijPos < 0 || rijPos > Spel.getMaxRijPos()) {
			throw new IllegalArgumentException("rijpos_buiten_veld");
		}
	}

	/**
	 * Controleert of de gegeven kolPos binnen de dimensies van het Spelbord ligt.
	 * 
	 * @param kolPos
	 */
	private void controleerKolPos(int kolPos) {
		if (kolPos < 0 || kolPos > Spel.getMaxKolPos()) {
			throw new IllegalArgumentException("kolpos_buiten_veld");
		}
	}

}
