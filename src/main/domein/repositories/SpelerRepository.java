package domein.repositories;

import domein.Speler;
import persistentie.SpelerMapper;

public class SpelerRepository {
	private final SpelerMapper mapper;

	/**
	 * Maakt een nieuwe SpelerRepository aan met een SpelerMapper.
	 */
	public SpelerRepository() {
		mapper = new SpelerMapper();
	}

	// methods
	/**
	 * Haalt de Speler met de gegeven gebruikersnaam en wachtwoord op uit de
	 * databank.
	 * 
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @return List met namen van de spelletjes
	 */
	public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
		return mapper.geefSpeler(gebruikersnaam, wachtwoord);
	}

	/**
	 * Controleert of er een Speler met de gegeven gebruikersnaam bestaat in de
	 * databank.
	 * 
	 * @param gebruikersnaam
	 * @return true wanneer de Speler bestaat, anders false
	 */
	public boolean bestaatSpeler(String gebruikersnaam) {
		return mapper.bestaatSpeler(gebruikersnaam);
	}

	/**
	 * Voegt de gegeven Speler toe aan de databank.
	 * 
	 * @param speler
	 */
	public void voegToe(Speler speler) {
		mapper.voegToe(speler);
	}

}
