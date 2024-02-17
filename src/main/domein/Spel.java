package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domein.spelbord.Doel;
import domein.spelbord.Muur;
import domein.spelbord.Spelbord;
import domein.spelbord.Vak;
import domein.spelstukken.Kist;
import domein.spelstukken.Mannetje;

public class Spel {
	private String naam;
	private Spelbord huidigSpelbord;
	private Spelbord eersteSpelbord;
	private Spelbord vorigSpelbord;
	private int aantalVoltooideSpelborden;
	private int totaalAantalSpelborden;
	private Mannetje mannetje;
	private List<Kist> kisten;
	private static final int MAXRIJPOS = 10;
	private static final int MAXKOLPOS = 10;

	/**
	 * Maakt een nieuw Spel aan met de meegegeven naam, het huidigSpelbord en het
	 * aantal spelborden.
	 * 
	 * @param naam
	 * @param huidigSpelbord
	 * @param totaalAantalSpelborden
	 */
	public Spel(String naam, Spelbord huidigSpelbord, int totaalAantalSpelborden) {
		setNaam(naam);
		setHuidigSpelbord(huidigSpelbord);
		setTotaalAantalSpelborden(totaalAantalSpelborden);
		setEersteSpelbord(huidigSpelbord);
	}

	/**
	 * Maakt een nieuw spel aan met de meegegeven naam en 0 spelborden.
	 * 
	 * @param naam
	 */
	public Spel(String naam) {
		setNaam(naam);
		setTotaalAantalSpelborden(0);
	}

	// setters
	/**
	 * Controleert of de naam geldig is. Als de naam geldig is, wordt deze
	 * ingesteld.
	 * 
	 * @param naam
	 */
	private void setNaam(String naam) {
		controleerNaam(naam);
		this.naam = naam;
	}

	private void setHuidigSpelbord(Spelbord huidigSpelbord) {
		this.huidigSpelbord = huidigSpelbord;
	}

	private void setTotaalAantalSpelborden(int totaalAantalSpelborden) {
		this.totaalAantalSpelborden = totaalAantalSpelborden;
	}

	private void setEersteSpelbord(Spelbord eersteSpelbord) {
		this.eersteSpelbord = eersteSpelbord;
	}

	private void setVorigSpelbord(Spelbord vorigSpelbord) {
		this.vorigSpelbord = vorigSpelbord;
	}

	// getters
	public int getAantalVoltooideSpelborden() {
		return aantalVoltooideSpelborden;
	}

	public int getTotaalAantalSpelborden() {
		return totaalAantalSpelborden;
	}

	public String getNaam() {
		return naam;
	}

	public Spelbord getEersteSpelbord() {
		return eersteSpelbord;
	}

	public static int getMaxRijPos() {
		return MAXRIJPOS;
	}

	public static int getMaxKolPos() {
		return MAXKOLPOS;
	}

	public Spelbord getHuidigSpelbord() {
		return huidigSpelbord;
	}

	// controles
	/**
	 * Controleert de gegeven naam: mag niet leeg zijn, mag geen spaties bevatten.
	 * 
	 * @param naam
	 */
	private void controleerNaam(String naam) {
		if (naam.isEmpty()) {
			throw new IllegalArgumentException("spelnaam_leeg");
		}

		Pattern p = Pattern.compile("\\s");
		Matcher matcher = p.matcher(naam);
		if (matcher.find()) {
			throw new IllegalArgumentException("spelnaam_bevat_spatie");
		}

	}

	// methods
	/**
	 * Maakt alle spelstukken(Kisten, Mannetje) aan op de startposities van het
	 * huidigeSpelbord.
	 */
	public void maakSpelstukkenAan() {
		int[] startPositieMannetje = huidigSpelbord.getStartPositieMannetje();
		this.mannetje = new Mannetje(startPositieMannetje[0], startPositieMannetje[1]);

		this.kisten = new ArrayList<Kist>();
		int[][] startPositiesKisten = huidigSpelbord.getStartPositiesKisten();
		for (int[] startPositie : startPositiesKisten) {
			kisten.add(new Kist(startPositie[0], startPositie[1]));
		}
	}

	/**
	 * Retourneert de vakken van het Spelbord.
	 * 
	 * @return keys van vakken = naam van klasse
	 */
	public String[][] geefVakken() {
		Vak[][] vakken = huidigSpelbord.getVakken();
		String[][] stringVakken = new String[Spel.MAXRIJPOS][Spel.MAXKOLPOS];

		for (int rij = 0; rij < Spel.MAXRIJPOS; rij++) {
			for (int kol = 0; kol < Spel.MAXKOLPOS; kol++) {
				stringVakken[rij][kol] = vakken[rij][kol].getClass().getSimpleName();
			}
		}
		return stringVakken;
	}

	/**
	 * Retourneert de positie van het Mannetje.
	 * 
	 * @return positie [0]=rijPos, [1]=kolPos
	 */
	public int[] geefPositieMannetje() {
		if (mannetje != null) {
			int[] posities = new int[2];
			posities[0] = mannetje.getrijPos();
			posities[1] = mannetje.getkolPos();
			return posities;
		}
		return null;
	}

	/**
	 * Retourneert de posities van de Kisten.
	 * 
	 * @return posities [kistnummer][0]=rijPos, [kistnummer][1]=kolPos
	 */
	public int[][] geefPositiesKisten() {
		if (kisten != null) {
			int[][] posities = new int[kisten.size()][2];

			for (int i = 0; i < posities.length; i++) {
				posities[i][0] = kisten.get(i).getrijPos();
				posities[i][1] = kisten.get(i).getkolPos();
			}
			return posities;
		}
		return null;
	}

	/**
	 * Controleert of de verplaatsing geldig is. Als deze geldig is, wordt het
	 * Mannetje verplaatst in de megegeven richting. Als er een Kist voor het
	 * Mannetje staat, zal deze bij een geldige beweging ook verplaatst worden.
	 * 
	 * @param richting 0=boven, 1=rechts, 2=onder, 3=links
	 */
	public void verplaatsMannetje(int richting) {
		boolean isGeldigeVerplaatsing = false;
		int rijPos = mannetje.getrijPos();
		int kolPos = mannetje.getkolPos();
		int[] volgendePosMannetje = berekenVolgendePositie(richting, rijPos, kolPos);
		Kist kist1 = geefKist(volgendePosMannetje);
		int[] volgendePosKist = null;

		// kist1 gevonden
		if (kist1 != null) {
			volgendePosKist = berekenVolgendePositie(richting, volgendePosMannetje[0], volgendePosMannetje[1]);
			Kist kist2 = geefKist(volgendePosKist);
			// kist2 niet gevonden
			if (kist2 == null) {
				// kijken of positie binnen de array is try cath
				Vak vak = huidigSpelbord.geefVak(volgendePosKist);
				isGeldigeVerplaatsing = isVakGeldig(vak);
			}

		}
		// kist1 niet gevonden
		else {
			// kijken of positie binnen de array is try cath
			Vak vak = huidigSpelbord.geefVak(volgendePosMannetje);
			isGeldigeVerplaatsing = isVakGeldig(vak);
		}

		if (isGeldigeVerplaatsing) {
			mannetje.verplaats(volgendePosMannetje);
			if (kist1 != null) {
				kist1.verplaats(volgendePosKist);
			}
			huidigSpelbord.verhoogAantalVerplaatsingen();
		} else {
			throw new IllegalArgumentException("ongeldige_verplaatsing");
		}
	}

	/**
	 * Berekent de volgende positie aan de hand van de vorige positie en de gegeven
	 * verplaatsing.
	 * 
	 * @param richting 0=boven, 1=rechts, 2=onder, 3=links
	 * @param rijPos
	 * @param kolPos
	 * @return volgende positie
	 */
	private int[] berekenVolgendePositie(int richting, int rijPos, int kolPos) {
		int[] positie = { rijPos, kolPos };
		switch (richting) {
		case 0:
			positie[0] -= 1;
			break;
		case 1:
			positie[1] += 1;
			break;
		case 2:
			positie[0] += 1;
			break;
		case 3:
			positie[1] -= 1;
			break;
		default:
			throw new IllegalArgumentException("foute_richting_meegegeven");
		}
		return positie;
	}

	/**
	 * Retourneert de kist op de gegeven positie, anders null.
	 * 
	 * @param positie [0]=rijPos, [1]=kolPos
	 * @return gevonden kist of null
	 */
	private Kist geefKist(int[] positie) {
		if (kisten != null) {
			for (Kist kist : kisten) {
				if (kist.getrijPos() == positie[0] && kist.getkolPos() == positie[1]) {
					return kist;
				}
			}
		}
		return null;
	}

	/**
	 * Controleert of het gegeven vak geldig is(geen muur).
	 * 
	 * @param vak
	 * @return true wanneer het vak geen muur is
	 */
	private boolean isVakGeldig(Vak vak) {
		return !(vak instanceof Muur);
	}

	/**
	 * Retourneert het aantal verplaatsingen.
	 * 
	 * @return aantal verplaatsingen
	 */
	public int geefAantalVerplaatsingen() {
		return huidigSpelbord.getAantalVerplaatsingen();
	}

	/**
	 * Controleert of alle kisten op een doel staan.
	 * 
	 * @return true wanneer alle kisten op een doel staan
	 */
	public boolean isSpelbordVoltooid() {
		int[] positie = new int[2];
		for (Kist kist : kisten) {
			positie[0] = kist.getrijPos();
			positie[1] = kist.getkolPos();
			if (!(huidigSpelbord.geefVak(positie) instanceof Doel)) {
				return false;
			}
		}
		aantalVoltooideSpelborden++;
		return true;
	}

	/**
	 * Gebruikt het volgendeSpelbord van het huidigeSpelbord om het huidigeSpelbord
	 * opnieuw in te stellen.
	 */
	public void selecteerVolgendSpelbord() {
		setHuidigSpelbord(huidigSpelbord.getVolgendSpelBord());
	}

	/*
	 * Stelt het aantal verplaatsingen in op 0 en zet de spelstukken terug op de
	 * startposities.
	 */
	public void resetSpelstukken() {
		maakSpelstukkenAan();
		huidigSpelbord.resetAantalVerplaatsingen();
	}

	/**
	 * Stelt het vorigSpelbord in met het huidigeSpelbord, maakt een leeg Spelbord
	 * aan en stelt dit in als huidigSpelbord.
	 */
	public void maakLeegSpelbordAan() {
		setVorigSpelbord(huidigSpelbord);
		this.mannetje = null;
		this.kisten = new ArrayList<Kist>();
		setHuidigSpelbord(new Spelbord());
	}

	/**
	 * Past het huidigeSpelbord op de gegeven positie aan met de meegegeven
	 * aanpassing.
	 * 
	 * @param positie    [0]= rijPos, [1] = kolPos
	 * @param aanpassing 0=muur, 1=doel, 2=mannetje, 3=kist, 4=leegmaken
	 */
	public void pasSpelbordAan(int[] positie, int aanpassing) {
		boolean muurGevonden;
		boolean mannetjeGevonden;

		switch (aanpassing) {
		// muur
		case 0:
			mannetjeGevonden = controleerEnVerwijderMannetje(positie);

			if (!mannetjeGevonden) {
				controleerEnVerwijderkist(positie);
			}
			huidigSpelbord.voegMuurToe(positie);
			break;

		// doel
		case 1:
			huidigSpelbord.voegDoelToe(positie);
			break;

		// mannetje
		case 2:
			muurGevonden = huidigSpelbord.controleerEnVerwijderMuur(positie);
			if (!muurGevonden) {
				controleerEnVerwijderkist(positie);
			}
			this.mannetje = new Mannetje(positie[0], positie[1]);
			break;

		// kist
		case 3:
			muurGevonden = huidigSpelbord.controleerEnVerwijderMuur(positie);
			if (!muurGevonden) {
				mannetjeGevonden = controleerEnVerwijderMannetje(positie);
				if (!mannetjeGevonden) {
					controleerEnVerwijderkist(positie);
				}
			}
			kisten.add(new Kist(positie[0], positie[1]));
			break;

		// leegmaken
		case 4:
			muurGevonden = huidigSpelbord.controleerEnVerwijderMuur(positie);
			if (!muurGevonden) {
				mannetjeGevonden = controleerEnVerwijderMannetje(positie);
				if (!mannetjeGevonden) {
					controleerEnVerwijderkist(positie);
				}
			}
			huidigSpelbord.voegVeldToe(positie);
			break;

		// default
		default:
			throw new IllegalArgumentException("foute_richting_meegegeven");
		}
	}

	/**
	 * Controleert of het Mannetje op de gegeven positie staat, als dit true is,
	 * wordt het Mannetje ingesteld op null.
	 * 
	 * @param positie
	 * @return true als het Mannetje op de gegeven positie staat, anders false
	 */
	private boolean controleerEnVerwijderMannetje(int[] positie) {
		int[] positieMannetje = geefPositieMannetje();

		if (Arrays.equals(positie, positieMannetje)) {
			this.mannetje = null;
			return true;
		}
		return false;
	}

	/**
	 * Controleert of er een Kist op de gegeven positie staat, als dit true is,
	 * wordt die kist verwijderd.
	 * 
	 * @param positie
	 * @return true als er een Kist op de gegeven positie staat, anders false
	 */
	private boolean controleerEnVerwijderkist(int[] positie) {
		Kist kist = geefKist(positie);
		if (kist != null) {
			kisten.remove(kist);
			return true;
		}
		return false;
	}

	/**
	 * Controleert of het huidigeSpelbord geldig is. Als het geldig is, wordt het
	 * toegevoegd aan het vorigeSpelbord of ingesteld als eersteSpelbord. Verhoogt
	 * het aantal spelborden.
	 */
	public void voegSpelbordToe() {
		valideerSpelbord();

		if (eersteSpelbord == null) {
			setEersteSpelbord(huidigSpelbord);
		} else {
			vorigSpelbord.setVolgendSpelBord(huidigSpelbord);
		}

		totaalAantalSpelborden++;
	}

	/**
	 * Controleert of het HuidigeSpelbord geldig is(heeft een Mannetje, heeft een
	 * Doel en heeft evenveel Kisten als Doelen). Als het huidigeSpelbord geldig is,
	 * worden de startposities ingesteld.
	 */
	public void valideerSpelbord() {
		if (mannetje == null) {
			throw new IllegalArgumentException("geen_mannetje");
		}
		if (kisten.size() == 0) {
			throw new IllegalArgumentException("minstens_1_doel");
		}
		if (kisten.size() != huidigSpelbord.getAantalDoelen()) {
			throw new IllegalArgumentException("kisten_doelen_fout");
		}

		int[] positieMannetje = geefPositieMannetje();
		huidigSpelbord.setStartPositieMannetje(positieMannetje);

		int[][] positieKisten = geefPositiesKisten();
		huidigSpelbord.setStartPositiesKisten(positieKisten);
	}

	/**
	 * Stelt aan de hand van het volgnummer het huidigeSpelbord in.
	 * 
	 * @param volgnummer 0 = eerste spelbord
	 */
	public void selecteerSpelbord(int volgnummer) {
		huidigSpelbord = eersteSpelbord;
		for (int i = 1; i <= volgnummer; i++) {
			vorigSpelbord = huidigSpelbord;
			huidigSpelbord = huidigSpelbord.getVolgendSpelBord();

		}
	}

	/**
	 * Contoleert of het Spel minstens 1 Spelbord bevat. Als er meer dan 1 Spelbord
	 * is, wordt het huidigeSpelbord verwijderd.
	 */
	public void verwijderHuidigSpelbord() {
		if (this.totaalAantalSpelborden == 1) {
			throw new IllegalArgumentException("minstens_1_spelbord");
		}

		if (huidigSpelbord == eersteSpelbord) {
			eersteSpelbord = huidigSpelbord.getVolgendSpelBord();
		} else {
			vorigSpelbord.setVolgendSpelBord(huidigSpelbord.getVolgendSpelBord());
		}
		totaalAantalSpelborden--;
	}

	/**
	 * Zet de Vakken van het huidigeSpelbord terug op hun beginposities.
	 */
	public void resetVakken() {
		huidigSpelbord.resetVakken();
	}
}
