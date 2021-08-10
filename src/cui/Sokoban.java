package cui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import domein.Spel;

public class Sokoban {

	DomeinController dc;
	ResourceBundle bundle;

	public Sokoban(DomeinController dc) {
		this.dc = dc;
	}

	public void start() {
		this.bundle = ResourceBundle.getBundle("bundles.bundle_en");
		int taal = toonMenu(new String[] { "taalmenu_kies", "nederlands", "engels", "frans", "keuze" });
		stelTaalIn(taal);

		int keuze = toonMenu(new String[] { "menu_kies", "aanmelden", "registreren", "keuze" });
		if (keuze == 1) {
			meldSpelerAan();
		} else {
			registreerSpeler();
		}

		boolean adminrechten = dc.geefAdminrechten();
		do {
			keuze = toonHoofdMenu();
			switch (keuze) {
			case 1:
				speelSpel();
				break;
			case 2:
				if (adminrechten) {
					maakNieuwSpelAan();
				} else {
					System.out.println(bundle.getString("goodbye"));
				}
				break;
			case 3:
				if (adminrechten) {
					wijzigSpel();
				} else {
					System.out.println(bundle.getString("goodbye"));
				}
				break;
			case 4:
				System.out.println(bundle.getString("goodbye"));
				break;
			}
		} while (!(keuze == 4) && !(keuze == 2 && !adminrechten));
	}

	/*
	 * Maakt een menu van de gegeven Strings zodat de gebruiker kan antwoorden met
	 * een nummer, controleert of het een geldig nummer is anders word de vraag
	 * herhaald.
	 */
	private int toonMenu(String[] input) {
		Scanner s = new Scanner(System.in);
		do {
			try {
				System.out.printf("%s%n", bundle.getString(input[0]));
				for (int i = 1; i < input.length - 1; i++) {
					if (bundle.containsKey(input[i])) {
						System.out.printf("%d: %s%n", i, bundle.getString(input[i]));

					} else {
						System.out.printf("%d: %s%n", i, input[i]);
					}
				}
				System.out.printf("%s: ", bundle.getString(input[input.length - 1]));

				int keuze = s.nextInt();
				if (keuze < 1 || keuze > input.length - 2) {
					throw new IllegalArgumentException("menu_fout_nummer");
				}

				return keuze;
			} catch (InputMismatchException e) {
				s.nextLine();
				System.out.println(bundle.getString("menu_geen_nummer"));
			} catch (IllegalArgumentException e) {
				s.nextLine();
				System.out.println(bundle.getString("nummer_out_of_bounds"));
			} finally {
				System.out.println();
				s.close();
			}
		} while (true);

	}

	private void stelTaalIn(int nummer) {
		String[] talen = { "nl", "en", "fr" };
		Locale.setDefault(new Locale(talen[nummer - 1]));
		this.bundle = ResourceBundle.getBundle("bundles.bundle");
	}

	private void meldSpelerAan() {
		do {
			try {
				String gebruikersnaam = vraagStringInput("vraag_gebruikersnaam");
				String wachtwoord = vraagStringInput("vraag_wachtwoord");
				dc.meldAan(gebruikersnaam, wachtwoord);
			} catch (IllegalArgumentException e) {
				System.out.println(bundle.getString(e.getMessage()));
			} finally {
				System.out.println();
			}
		} while (dc.geefGebruikersnaam() == null);

	}

	private void registreerSpeler() {
		do {
			try {
				String gebruikersnaam = vraagStringInput("vraag_gebruikersnaam");
				String wachtwoord = vraagStringInput("vraag_wachtwoord");
				String naam = vraagStringInput("vraag_naam");
				String voornaam = vraagStringInput("vraag_voornaam");
				dc.registreer(naam, voornaam, gebruikersnaam, wachtwoord);
			} catch (IllegalArgumentException e) {
				System.out.println(bundle.getString(e.getMessage()));
			} finally {
				System.out.println();
			}
		} while (dc.geefGebruikersnaam() == null);
	}

	private int toonHoofdMenu() {

		System.out.println(dc.geefGebruikersnaam());
		if (dc.geefAdminrechten()) {
			return toonMenu(
					new String[] { "menu_kies", "speel_spel", "maak_nieuw_spel", "wijzig_spel", "afsluiten", "keuze" });
		} else {
			return toonMenu(new String[] { "menu_kies", "speel_spel", "afsluiten", "keuze" });
		}
	}

	private void speelSpel() {
		List<String> namenList = dc.geefSpelnamen();
		namenList.add(0, "selecteer_spel_laatste_stoppen");
		namenList.add(bundle.getString("stoppen"));
		namenList.add("keuze");
		String[] namen = namenList.toArray(new String[0]);
		String gekozenSpel = namen[toonMenu(namen)];
		if (gekozenSpel.equals(bundle.getString("stoppen"))) {
			return;
		}
		dc.kiesSpel(gekozenSpel);
		int totaalAantalSpelborden = dc.geefTotaalAantalSpelborden();
		int aantalVoltooideSpelborden = dc.geefAantalVoltooideSpelborden();

		while (aantalVoltooideSpelborden < totaalAantalSpelborden) {
			int keuze = toonMenu(new String[] { "menu_kies", "voltooi_volgend_spelbord", "spel_verlaten", "keuze" });

			// voltooi_volgend_spelbord
			if (keuze == 1) {
				voltooiSpelbord();
			}
			// spel_verlaten
			else {
				System.out.printf("%s%n%n", bundle.getString("spel_opgeven"));
				return;
			}
			aantalVoltooideSpelborden = dc.geefAantalVoltooideSpelborden();
		}
		System.out.printf("%s%n%n", bundle.getString("spel_voltooid"));
	}

	private void voltooiSpelbord() {
		dc.maakSpelstukkenAan();
		toonLegende();
		toonSpelbord();
		while (!dc.isSpelbordVoltooid()) {
			int keuze = toonMenu(new String[] { "menu_kies", "nieuwe_verplaatsing", "spelbord_resetten",
					"spelbord_verlaten", "keuze" });
			// nieuwe_verplaatsing
			if (keuze == 1) {
				int verplaatsing = toonMenu(
						new String[] { "kies_verplaatsing", "boven", "rechts", "onder", "links", "keuze" });
				try {
					dc.verplaatsMannetje(verplaatsing - 1);
				} catch (IllegalArgumentException e) {
					System.out.println(bundle.getString(e.getMessage()));
				}
				toonSpelbord();
			}
			// spelbord_resetten
			if (keuze == 2) {
				dc.resetSpelStukken();
				toonSpelbord();
			}
			// spelbord_verlaten
			if (keuze == 3) {
				dc.resetSpelStukken();
				System.out.printf("%s%n%n", bundle.getString("spelbord_opgeven"));
				return;
			}
		}

		int aantalVerplaatsingen = dc.geefAantalVerplaatsingen();
		dc.selecteerVolgendSpelbord();
		int totaalAantalSpelborden = dc.geefTotaalAantalSpelborden();
		int aantalVoltooideSpelborden = dc.geefAantalVoltooideSpelborden();

		System.out.printf("%s %d ", bundle.getString("spelbord_voltooid_1"), aantalVerplaatsingen);
		System.out.println(bundle.getString(
				aantalVerplaatsingen == 1 ? "spelbord_voltooid_3_enkelvoud" : "spelbord_voltooid_2_meervoud"));
		System.out.printf("%s: %d%n%s: %d%n%n", bundle.getString("aantal_voltooide_spelborden"),
				aantalVoltooideSpelborden, bundle.getString("totaal_aantal_spelborden"), totaalAantalSpelborden);

	}

	private String[][] maakSpelbordMetSpelstukken() {
		String[][] spelbord = dc.geefVakken();

		int[] positieMannetje = dc.geefPositieMannetje();
		if (positieMannetje != null) {
			spelbord[positieMannetje[0]][positieMannetje[1]] += "-Mannetje";
		}

		int[][] positiesKisten = dc.geefPositiesKisten();
		if (positiesKisten != null) {
			for (int[] posKist : positiesKisten) {
				spelbord[posKist[0]][posKist[1]] += "-Kist";
			}
		}
		return spelbord;
	}

	private void toonSpelbord() {
		System.out.printf("%s: %d%n", bundle.getString("aantal_verplaatsingen"), dc.geefAantalVerplaatsingen());
		String[][] spelbordMetSpelstukken = maakSpelbordMetSpelstukken();

		String bovenIndexen = "";
		for (int i = 0; i < Spel.getMaxKolPos(); i++) {
			bovenIndexen += String.format(" %d", i + 1);
		}
		System.out.printf("  %s%n", bovenIndexen);
		int teller = 1;
		for (String[] rij : spelbordMetSpelstukken) {
			System.out.printf("%s%d ", teller < 10 ? " " : "", teller);
			teller++;
			for (String vak : rij) {
				if (vak.contains("Mannetje")) {
					System.out.print("M");
				} else if (vak.contains("Kist") && vak.contains("Doel")) {
					System.out.print("K");
				} else if (vak.contains("Kist")) {
					System.out.print("k");
				} else if (vak.contains("Doel")) {
					System.out.print("d");
				} else if (vak.contains("Muur")) {
					System.out.print("X");
				} else if (vak.contains("Veld")) {
					System.out.print(" ");
				} else if (vak.contains("VeldBuitenMuur")) {
					System.out.print(" ");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private void toonLegende() {
		System.out.printf("%s:%n", bundle.getString("legende"));
		System.out.printf("M: %s%n", bundle.getString("vak_M"));
		System.out.printf("K: %s%n", bundle.getString("vak_K"));
		System.out.printf("k: %s%n", bundle.getString("vak_k"));
		System.out.printf("d: %s%n", bundle.getString("vak_d"));
		System.out.printf("X: %s%n%n", bundle.getString("vak_X"));
	}

	private void maakNieuwSpelAan() {
		do {
			try {
				String naam = vraagStringInput("geef_naam");
				dc.maakNieuwSpelAan(naam);
				break;
			} catch (IllegalArgumentException e) {
				System.out.println(bundle.getString(e.getMessage()));
			} finally {
				System.out.println();
			}
		} while (true);

		do {
			int keuzeSpelbordAanmaken;
			do {
				maakNieuwSpelbordAan();
				keuzeSpelbordAanmaken = toonMenu(
						new String[] { "menu_kies", "nieuw_spelbord", "stop_aanmaken", "keuze" });
			} while (keuzeSpelbordAanmaken == 1);

			if (dc.valideerSpel()) {
				// spel is geldig
				dc.registreerSpel();
				System.out.printf("%s: %s%n%s: %d%n%n", bundle.getString("naam_geregistreerde_spel"), dc.geefSpelnaam(),
						bundle.getString("totaal_aantal_spelborden"), dc.geefTotaalAantalSpelborden());
				return;
			} else {
				// spel is ongeldig
				System.out.println(bundle.getString("spel_ongeldig"));
				int keuze = toonMenu(new String[] { "keuze", "toevoegen", "stoppen", "keuze" });
				if (keuze == 2) {
					System.out.println(bundle.getString("spel_verwijderd"));
					return;
				}
			}
		} while (true);

	}

	private void maakNieuwSpelbordAan() {
		dc.maakLeegSpelbordAan();
		toonLegende();
		toonSpelbord();
		do {
			pasSpelbordAan();
			try {
				dc.voegSpelbordToe();
				System.out.printf("%s%n%n", bundle.getString("spelbord_geldig"));
				return;
			} catch (IllegalArgumentException e) {
				System.out.println(bundle.getString(e.getMessage()));
				int keuze2 = toonMenu(new String[] { "keuze", "aanpassen", "verwijderen", "keuze" });
				if (keuze2 == 2) {
					System.out.println(bundle.getString("spelbord_verwijderd"));
					return;
				}
			}
		} while (true);

	}

	private void pasSpelbordAan() {
		int keuze;
		do {
			int aanpassing;
			int[] positie = new int[2];

			System.out.printf("%s %d %s %d%n", bundle.getString("geef_getal"), 1, bundle.getString("en"),
					Spel.getMaxRijPos());
			positie[0] = vraagIntInputTussenGrenzen("rijpositie", 1, Spel.getMaxRijPos()) - 1;
			System.out.printf("%s %d %s %d%n", bundle.getString("geef_getal"), 1, bundle.getString("en"),
					Spel.getMaxKolPos());
			positie[1] = vraagIntInputTussenGrenzen("kolpositie", 1, Spel.getMaxKolPos()) - 1;
			System.out.println();
			aanpassing = toonMenu(new String[] { "menu_kies", "plaats_muur", "plaats_doel", "plaats_mannetje",
					"plaats_kist", "veld_leegmaken", "keuze" }) - 1;

			dc.pasSpelbordAan(positie, aanpassing);

			toonSpelbord();
			keuze = toonMenu(new String[] { "spelbord_verder_aanpassen", "ja", "nee", "keuze" });
		} while (keuze == 1);
	}

	private void wijzigSpel() {
		List<String> spelNamen = dc.geefSpelnamenVanSpeler();
		if (spelNamen.size() > 0) {
			spelNamen.add(0, "selecteer_spel_laatste_stoppen");
			spelNamen.add(bundle.getString("stoppen"));
			spelNamen.add("keuze");
			String[] namen = spelNamen.toArray(new String[0]);
			String keuzeSpel = namen[toonMenu(namen)];
			if (keuzeSpel.equals(bundle.getString("stoppen"))) {
				return;
			}
			dc.kiesSpel(keuzeSpel);

			int aanpassingsKeuze;
			int aantalSpelborden = dc.geefTotaalAantalSpelborden();
			List<String> spelbordNummers = new ArrayList<>();
			for (int i = 1; i <= aantalSpelborden; i++) {
				spelbordNummers.add(String.format("%s %d", bundle.getString("spelbord_hoofdletter"), i));
			}
			spelbordNummers.add(0, "selecteer_spelbord");
			spelbordNummers.add("keuze");
			boolean spelbordVerwijderd = false;
			do {
				int keuzeSpelbord = toonMenu(spelbordNummers.toArray(new String[0]));
				dc.selecteerSpelbord(keuzeSpelbord - 1);
				int wijzigSpelKeuzeMenu = toonMenu(
						new String[] { "menu_kies", "verwijder_spelbord", "wijzig_spelbord", "keuze" });
				if (wijzigSpelKeuzeMenu == 1) {
					try {
						dc.verwijderHuidigSpelbord();
						spelbordVerwijderd = true;
						System.out.println(bundle.getString("spelbord_verwijderd"));
						spelbordNummers.remove(keuzeSpelbord);
					} catch (IllegalArgumentException e) {
						System.out.println(bundle.getString(e.getMessage()));
					} finally {
						System.out.println();
					}

				}
				if (wijzigSpelKeuzeMenu == 2) {
					dc.maakSpelstukkenAan();
					toonSpelbord();
					boolean herhalenFlag = true;
					do {
						try {
							pasSpelbordAan();
							dc.vervangSpelbord(keuzeSpelbord);
							System.out.println(bundle.getString("spelbord_aangepast"));
							herhalenFlag = false;
						} catch (IllegalArgumentException e) {
							System.out.println(bundle.getString(e.getMessage()));
							int aanpassen = toonMenu(new String[] { "menu_kies", "spelbord_verder_aanpassen",
									"wijzigingen_ongedaan_maken", "keuze" });
							if (aanpassen == 2) {
								dc.resetSpelbord();
								System.out.println(bundle.getString("wijzigingen_ongedaan_gemaakt"));
								herhalenFlag = false;
							}
						}
					} while (herhalenFlag);

				}
				aanpassingsKeuze = toonMenu(new String[] { "spel_verder_aanpassen", "ja", "nee", "keuze" });
			} while (aanpassingsKeuze == 1);
			if (spelbordVerwijderd) {
				dc.wijzigSpel();
			}
			System.out.printf("%s: %s%n%s: %d%n%n", bundle.getString("naam_geregistreerde_spel"), dc.geefSpelnaam(),
					bundle.getString("totaal_aantal_spelborden"), dc.geefTotaalAantalSpelborden());

		} else {
			System.out.printf("%s%n%n", bundle.getString("geen_spellen"));
		}

	}

	// utils
	/**
	 * Vraagt string input met de meegegeven vraag.
	 * 
	 * @param vraag
	 * @return
	 */
	private String vraagStringInput(String vraag) {
		Scanner s = new Scanner(System.in);
		System.out.printf("%s: ", bundle.getString(vraag));
		s.close();
		return s.nextLine();
	}

	/**
	 * Vraagt int input met de meegegeven vraag.
	 * 
	 * @param vraag
	 * @return int van de gebruiker
	 */
	private int vraagIntInput(String vraag) {
		do {
			try {
				return Integer.parseInt(vraagStringInput(vraag));
			} catch (NumberFormatException e) {
				System.out.println(bundle.getString("menu_geen_nummer"));
				System.out.println();
			}
		} while (true);
	}

	/**
	 * Vraagt int input met de meegegeven vraag, tussen grenzen.
	 * 
	 * @param vraag
	 * @return int van de gebruiker tussen grenzen
	 */
	private int vraagIntInputTussenGrenzen(String vraag, int ondergrens, int bovengrens) {
		int input;
		do {
			try {
				input = vraagIntInput(vraag);
				if (input < ondergrens || input > bovengrens) {
					throw new IllegalArgumentException("nummer_out_of_bounds");
				}

				return input;
			} catch (IllegalArgumentException e) {
				System.out.println(bundle.getString(e.getMessage()));
			}
		} while (true);
	}
}
