package domein.repositories;

import java.util.List;

import domein.Spel;
import domein.spelbord.Spelbord;
import persistentie.SpelMapper;

public class SpelRepository {
	private SpelMapper mapper;

	/**
	 * Maakt een nieuwe SpelRepository aan met een SpelMapper.
	 */
	public SpelRepository() {
		mapper = new SpelMapper();
	}

	// methods
	/**
	 * Haalt de namen van alle Spelletje op uit de databank.
	 * 
	 * @return List met namen van de Spelletjes
	 */
	public List<String> geefSpelnamen() {
		return mapper.geefSpelnamen();
	}

	/**
	 * Haalt het Spel met de gegeven naam op uit de databank.
	 * 
	 * @param naam
	 * @return Spel
	 */
	public Spel geefSpel(String naam) {
		return mapper.geefSpel(naam);
	}

	/**
	 * Controleert of er al een Spel met gegeven naam bestaat in de databank.
	 * 
	 * @param naam
	 * @return true waneer het Spel gevonden word, anders false
	 */
	public boolean bestaatSpel(String naam) {
		return mapper.bestaatSpel(naam);
	}

	/**
	 * Voegt het gegeven Spel toe in de databank.
	 * 
	 * @param spel
	 * @param gebruikersnaam
	 */
	public void voegSpelToe(Spel spel, String gebruikersnaam) {
		mapper.voegSpelToe(spel, gebruikersnaam);
	}

	/**
	 * Haalt de spelnamen geassocieerd met de Speler op uit de databank.
	 * 
	 * @param gebruikersnaam
	 * @return spelnamen van de Speler
	 */
	public List<String> geefSpelnamenVanSpeler(String gebruikersnaam) {
		return mapper.geefSpelNamen(gebruikersnaam);
	}

	/**
	 * Verwijdert het Spel met de gegeven naam uit de databank.
	 * 
	 * @param naam
	 */
	public void verwijderHuidigSpel(String naam) {
		mapper.verwijderVolledigSpel(naam);
	}

	/**
	 * Vervangt het spelbord in de databank met het nieuwe spelbord.
	 * 
	 * @param volgnummer om het volgnummer in de databank te bepalen
	 * @param spelbord
	 * @param spelnaam
	 */
	public void vervangSpelbord(int volgnummer, Spelbord spelbord, String spelnaam) {
		mapper.vervangSpelbord(volgnummer, spelbord, spelnaam);
	}

}
