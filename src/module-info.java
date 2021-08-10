module sokoban_g02 {
	exports persistentie;
	exports cui;
	exports gui.begin;
	exports gui.maakSpel;
	exports gui.menu;
	exports gui.speelSpel;
	exports gui.wijzigSpel;
	exports gui.credits;
	exports main;
	exports domein;
	exports domein.spelbord;
	exports domein.repositories;
	exports testen;
	exports domein.spelstukken;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires org.junit.jupiter.api;
	requires mysql.connector.java;
	requires transitive javafx.graphics;
	requires javafx.media;

	opens main to javafx.graphics, javafx.fxml, javafx.media;
	opens gui.begin to javafx.graphics, javafx.fxml, javafx.media;
	opens gui.maakSpel to javafx.graphics, javafx.fxml, javafx.media;
	opens gui.menu to javafx.graphics, javafx.fxml, javafx.media;
	opens gui.speelSpel to javafx.graphics, javafx.fxml, javafx.media;
	opens gui.wijzigSpel to javafx.graphics, javafx.fxml, javafx.media;
	opens gui.credits to javafx.graphics, javafx.fxml, javafx.media;
}