package domein;

import java.util.List;

import domein.repositories.SpelRepository;
import domein.repositories.SpelerRepository;

public class DomeinController {
	private final SpelerRepository spelerRepository;
	private final SpelRepository spelRepository;
	private Speler speler;
	private Spel spel;

	/**
	 * Maakt een nieuw instantie van SpelerRepository en SpelRepository aan.
	 */
	public DomeinController() {
		spelerRepository = new SpelerRepository();
		spelRepository = new SpelRepository();
	}

	/**
	 * Controleert of er een Speler met de gegeven gebruikersnaam en wachtwoord in
	 * de databank bestaat. Als er een Speler gevonden is, wordt deze als Speler
	 * ingesteld.
	 * 
	 * @param gebruikersnaam
	 * @param wachtwoord
	 */
	public void meldAan(String gebruikersnaam, String wachtwoord) {
		Speler gevondenSpeler = spelerRepository.geefSpeler(gebruikersnaam, wachtwoord);
		if (gevondenSpeler != null) {
			speler = gevondenSpeler;
		} else {
			throw new IllegalArgumentException("speler_niet_gevonden");
		}

	}

	/**
	 * Retourneert de andminrechten van de Speler.
	 * 
	 * @return andminrechten
	 */
	public boolean geefAdminrechten() {
		return speler.getAdminrechten();
	}

	/**
	 * Retourneert de gebruikersnaam van de Speler.
	 * 
	 * @return gebruikersnaam
	 */
	public String geefGebruikersnaam() {
		if (speler != null) {
			return speler.getGebruikersnaam();
		}
		return null;
	}

	/**
	 * Controleert of de gegeven gebruikersnaam en wachtwoord geldig zijn.
	 * Controleert of er nog geen Speler met de gegeven gebruikersnaam in de
	 * databank bestaat. Als er geen Speler gevonden is, wordt die aangemaakt,
	 * ingesteld en toegevoegd aan de databank.
	 * 
	 * @param voornaam
	 * @param gebruikersnaam
	 * @param wachtwoord
	 */
	public void registreer(String naam, String voornaam, String gebruikersnaam, String wachtwoord) {
		if (!spelerRepository.bestaatSpeler(gebruikersnaam)) {
			Speler nieuweSpeler = new Speler(gebruikersnaam, wachtwoord, false, naam, voornaam);
			this.speler = nieuweSpeler;
			spelerRepository.voegToe(nieuweSpeler);
			meldAan(gebruikersnaam, wachtwoord);
		} else {
			throw new IllegalArgumentException("speler_bestaat");
		}

	}

	/**
	 * Haalt de namen van alle spelletjes op uit de databank.
	 * 
	 * @return List met namen van de spelletjes
	 */
	public List<String> geefSpelnamen() {
		return spelRepository.geefSpelnamen();

	}

	/**
	 * Haalt het Spel met de gegeven naam op uit de databank en stelt hiermee het
	 * Spel in.
	 * 
	 * @param naam van het gekozen spel
	 */
	public void kiesSpel(String naam) {
		spel = spelRepository.geefSpel(naam);
	}

	/**
	 * Retourneert het aantal voltooide spelborden.
	 * 
	 * @return aantal voltooide spelborden
	 */
	public int geefAantalVoltooideSpelborden() {
		return spel.getAantalVoltooideSpelborden();

	}

	/**
	 * Retourneert het totaal aantal spelborden.
	 * 
	 * @return totaal aantal spelborden
	 */
	public int geefTotaalAantalSpelborden() {
		return spel.getTotaalAantalSpelborden();
	}

	/**
	 * Selecteert het volgende spelbord.
	 */
	public void selecteerVolgendSpelbord() {
		spel.selecteerVolgendSpelbord();
	}

	/**
	 * Maakt alle spelstukken(kisten, doelen, mannetje) aan met de startPosities uit
	 * het Spelbord.
	 */
	public void maakSpelstukkenAan() {
		spel.maakSpelstukkenAan();
	}

	/**
	 * Retourneert de positie van het mannetje.
	 * 
	 * @return positie mannetje [0]= rijPos, [1] = kolPos
	 */
	public int[] geefPositieMannetje() {
		return spel.geefPositieMannetje();

	}

	/**
	 * Retourneert de posities van de kisten.
	 * 
	 * @return posities kisten [0]= rijPos, [1] = kolPos
	 */
	public int[][] geefPositiesKisten() {
		return spel.geefPositiesKisten();

	}

	/**
	 * Retourneert de vakken van het spelbord.
	 * 
	 * @return vakken [rijPos][kolPos]
	 */
	public String[][] geefVakken() {
		return spel.geefVakken();

	}

	/**
	 * Controleert of het spelbord voltooid is.
	 * 
	 * @return true wanneer alle kisten op een doel staan
	 */
	public boolean isSpelbordVoltooid() {
		return spel.isSpelbordVoltooid();
	}

	/**
	 * Verplaatst het mannetje en de kist die eventueel voor het mannetje staat bij
	 * een geldige verplaatsing.
	 * 
	 * @param richting 0=boven, 1=rechts, 2=onder, 3=links
	 */
	public void verplaatsMannetje(int richting) {
		spel.verplaatsMannetje(richting);
	}

	/**
	 * Zet de spelstukken terug op de startposities en stelt het aantal
	 * verplaatsingen in op 0.
	 */
	public void resetSpelStukken() {
		spel.resetSpelstukken();
	}

	/**
	 * Retourneert het aantal verplaatsingen.
	 * 
	 * @return aantal verplaatsingen
	 */
	public int geefAantalVerplaatsingen() {
		return spel.geefAantalVerplaatsingen();
	}

	/**
	 * Controleert of de meegegeven naam geldig is. Controleert of er een Spel met
	 * de gegeven naam in de databank bestaat. Als er geen Spel gevonden is, wordt
	 * die aangemaakt en ingesteld.
	 * 
	 * @param naam
	 */
	public void maakNieuwSpelAan(String naam) {
		if (spelRepository.bestaatSpel(naam)) {
			throw new IllegalArgumentException("spel_bestaat");
		}
		this.spel = new Spel(naam);
	}

	/**
	 * Retourneert de naam van het huidige Spel.
	 * 
	 * @return naam
	 */
	public String geefSpelnaam() {
		return spel.getNaam();

	}

	/**
	 * Voegt het huidige Spel toe aan de databank.
	 */
	public void registreerSpel() {
		spelRepository.voegSpelToe(spel, speler.getGebruikersnaam());
	}

	/**
	 * Maakt een leeg Spelbord aan en stelt dit in als huidigspelbord.
	 * 
	 */
	public void maakLeegSpelbordAan() {
		spel.maakLeegSpelbordAan();
	}

	/**
	 * Past het Spelbord aan met de gegeven aanpassing op de gegeven positie.
	 * 
	 * @param positie    [0]= rijPos, [1] = kolPos
	 * @param aanpassing 0=muur, 1=doel, 2=mannetje, 3=kist, 4=leegmaken
	 */
	public void pasSpelbordAan(int[] positie, int aanpasing) {
		spel.pasSpelbordAan(positie, aanpasing);
	}

	/**
	 * Voegt het huidigeSpelbord achteraan het vorigeSpelbord toe.
	 */
	public void voegSpelbordToe() {
		spel.voegSpelbordToe();
	}

	/**
	 * Valideert het Spel.
	 * 
	 * @return true waneer er minstens 1 spelbord is
	 */
	public boolean valideerSpel() {
		return spel.getTotaalAantalSpelborden() > 0;
	}

	/**
	 * Haalt de namen van de spelletjes van de speler op uit de databank.
	 * 
	 * @return lijst met spelnamen
	 */
	public List<String> geefSpelnamenVanSpeler() {
		return spelRepository.geefSpelnamenVanSpeler(speler.getGebruikersnaam());
	}

	/**
	 * Stelt aan de hand van het volgnummer het huidigeSpelbord in het Spel in.
	 * 
	 * @param volgnummer 0 = eerste spelbord
	 */
	public void selecteerSpelbord(int volgnummer) {
		spel.selecteerSpelbord(volgnummer);
	}

	/**
	 * Verwijdert het huidigeSpelbord als er daarna minstens nog 1 Spelbord is.
	 */
	public void verwijderHuidigSpelbord() {
		spel.verwijderHuidigSpelbord();
	}

	/**
	 * Update het Spel in de databank.
	 */
	public void wijzigSpel() {
		spelRepository.verwijderHuidigSpel(spel.getNaam());
		spelRepository.voegSpelToe(spel, speler.getGebruikersnaam());
	}

	/**
	 * Gebruikt het meegegeven volgnummer om het huidigeSpelbord in de databank op
	 * te slagen.
	 * 
	 * @param volgnummer
	 */
	public void vervangSpelbord(int volgnummer) {
		String spelnaam = spel.getNaam();
		spel.valideerSpelbord();
		spelRepository.vervangSpelbord(volgnummer, spel.getHuidigSpelbord(), spelnaam);
	}

	/**
	 * Zet de spelstukken en de vakken terug op hun beginPosities.
	 */
	public void resetSpelbord() {
		spel.maakSpelstukkenAan();
		spel.resetVakken();
	}

}