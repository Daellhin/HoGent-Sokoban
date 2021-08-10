package main;

import java.util.Locale;
import java.util.ResourceBundle;

import domein.DomeinController;
import gui.begin.SchermBegin;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartUpGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		DomeinController dc = new DomeinController();

		Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Caribbean.ttf"), 40);
		Font.loadFont(getClass().getResourceAsStream("/resources/fonts/ManuskriptAntDReg.otf"), 40);

		Locale locale = new Locale("en");
		ResourceBundle bundle = ResourceBundle.getBundle("bundles.bundle", locale);

		SchermBegin scherm = new SchermBegin(dc, bundle);

		Scene scene = new Scene(scherm);
		stage.setResizable(false);
		stage.setTitle("Sokoban");
		stage.getIcons()
				.add(new Image(getClass().getResource("/resources/tiles/classic/mannetje.png").toExternalForm()));
		stage.setScene(scene);
		stage.setMinWidth(1000);
		stage.setMinHeight(750);
		stage.show();
	}
}
