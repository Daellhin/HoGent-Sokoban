package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Speler;

class SpelerTest {

	@BeforeEach
	public void before() {

	}

	// wachtwoordTesten
	@Test
	void maakSpeler__maaktObject() {
		Speler testSpeler = new Speler("SpelerAdmin", "Pa123456", false, "", "");
		Assertions.assertEquals("Pa123456", testSpeler.getWachtwoord());
	}

	@Test
	void maakSpeler_wachtwoordTeKort_exeption() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Speler("SpelerAdmin", "Pa123", false, "", ""));
	}

	@Test
	void maakSpeler_wachtwoordHoofdlettterOntbreekt_exeption() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Speler("SpelerAdmin", "pa123456", false, "", ""));
	}

	@Test
	void maakSpeler_wachtwoordKleineLetterOntbreekt_exeption() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Speler("SpelerAdmin", "PA123456", false, "", ""));
	}

	@Test
	void maakSpeler_wachtwoordCijferOntbreekt_exeption() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Speler("SpelerAdmin", "Paswoord", false, "", ""));
	}

	// gebruikersnaamTesten
	@Test
	void maakSpeler_gebruikersnaamOK_maaktObject() {
		Speler testSpeler = new Speler("SpelerAdmin", "Pa123456", false, "", "");
		Assertions.assertEquals("SpelerAdmin", testSpeler.getGebruikersnaam());
	}

	@Test
	void maakSpeler_gebruikersnaamTeKort_exeption() {
		Speler testSpeler = new Speler("SpelerAdmin", "Pa123456", false, "", "");
		Assertions.assertEquals("SpelerAdmin", testSpeler.getGebruikersnaam());
	}

	// voornaamTesten
	@Test
	void maakSpeler_voornaamOK_maaktObject() {
		Speler testSpeler = new Speler("SpelerAdmin", "Pa123456", false, "", "Tom");
		Assertions.assertEquals("Tom", testSpeler.getVoornaam());
	}

	// naamTesten
	@Test
	void maakSpeler_naamOK_maaktObject() {
		Speler testSpeler = new Speler("SpelerAdmin", "Pa123456", false, "Scott", "");
		Assertions.assertEquals("Scott", testSpeler.getNaam());
	}

}
