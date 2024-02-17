package domein;

public class Speler {
	private String gebruikersnaam;
	private String wachtwoord;
	private boolean adminrechten;
	private String naam;
	private String voornaam;
	private final int MINLENGTE = 8;

	/**
	 * Maakt een nieuwe speler aan met de meegegeven parameters.
	 * 
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @param adminrechten
	 * @param naam
	 * @param voornaam
	 */
	public Speler(String gebruikersnaam, String wachtwoord, boolean adminrechten, String naam, String voornaam) {
		setGebruikersnaam(gebruikersnaam);
		setWachtwoord(wachtwoord);
		setAdminrechten(adminrechten);
		setNaam(naam);
		setVoornaam(voornaam);
	}

	// setters
	private void setGebruikersnaam(String gebruikersnaam) {
		controleerGebruikersnaam(gebruikersnaam);
		this.gebruikersnaam = gebruikersnaam;
	}

	private void setWachtwoord(String wachtwoord) {
		controleerWachtwoord(wachtwoord);
		this.wachtwoord = wachtwoord;
	}

	private void setAdminrechten(boolean adminrechten) {
		this.adminrechten = adminrechten;
	}

	private void setNaam(String naam) {
		this.naam = naam;
	}

	private void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	// getters
	public String getWachtwoord() {
		return wachtwoord;
	}

	public String getNaam() {
		return naam;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public boolean getAdminrechten() {
		return adminrechten;
	}

	// controles
	/**
	 * Controleert of de gegeven gerbuikersnaam langer is dan de MINLENGTE.
	 * 
	 * @param gebruikersnaam
	 */
	private void controleerGebruikersnaam(String gebruikersnaam) {
		if (gebruikersnaam.length() < MINLENGTE) {
			throw new IllegalArgumentException("gebruikersnaam_te_kort");
		}
	}

	/**
	 * Controleert of het gegeven wachtwoord langer is dan de MINLENGTE en of het
	 * geldig is(bevat minstens 1 hoofdletter, kleine letter en nummer).
	 * 
	 * @param wachtwoord
	 */
	private void controleerWachtwoord(String wachtwoord) {
		if (wachtwoord.length() < MINLENGTE) {
			throw new IllegalArgumentException("wachtwoord_te_kort");
		}
		if (!wachtwoord.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
			throw new IllegalArgumentException("wachtwoord_incorect");
		}
	}

}
