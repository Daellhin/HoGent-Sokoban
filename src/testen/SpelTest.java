package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Spel;

class SpelTest {
	@BeforeEach
	public void before() {

	}

	// naamTesten
	@Test
	void maakSpel_naamOK_maaktObject() {
		Spel testSpel = new Spel("Spel1");
		Assertions.assertEquals("Spel1", testSpel.getNaam());
	}

	@Test
	void maakSpel_naamBevatSpatie_exception() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel("Spel 1"));
	}

	@Test
	void maakSpel_naamIsLeeg_exception() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Spel(""));
	}

}
