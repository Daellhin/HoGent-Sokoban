package gui.credits;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import gui.menu.SchermMenu;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SchermCredits extends Pane {
	private DomeinController dc;
	private ResourceBundle bundle;
	private MediaPlayer mp;

	public SchermCredits(DomeinController dc, ResourceBundle bundle) {
		super();
		this.dc = dc;
		this.bundle = bundle;
		this.setPrefHeight(750);
		this.setPrefWidth(1000);

		this.getStylesheets().add(getClass().getResource("/resources/css/SchermCredits.css").toString());
		String path = this.getClass().getResource("/resources/sounds/pirateslife.mp3").toString();
		Media media = new Media(path);
		mp = new MediaPlayer(media);
		mp.play();
		maakCredits();
	}

	private void maakCredits() {
		// File file = new
		// File(getClass().getResource("/resources/credits/credits.txt").getPath());
		File file = new File("src/resources/credits/credits.txt");
		List<String> parts = new ArrayList<>();

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				parts.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("err");
		}

		int teller = 0;
		for (String string : parts) {
			Label lbl = new Label();
			if (string.contains("@")) {
				string = string.substring(1, string.length());
				teller++;
				lbl.getStyleClass().add("title");
			} else {
				lbl.getStyleClass().add("name");
			}

			lbl.setText(string);
			lbl.layoutXProperty().bind(this.widthProperty().subtract(lbl.widthProperty()).divide(2));
			lbl.setTranslateY(750);
			this.getChildren().add(lbl);

			TranslateTransition tt = new TranslateTransition(Duration.millis(10000), lbl);
			tt.setByY(-1000);
			tt.setCycleCount(1);
			tt.setInterpolator(Interpolator.LINEAR);
			tt.setDelay(Duration.millis(700 * teller));
			tt.play();
			teller++;
		}
		int total = 10000 + 700 * teller;
		int timeLoud = total / 10 * 8;
		int timeMute = total / 10 * 2;
		new Timeline(new KeyFrame(Duration.millis(total), e -> toonSchermMenu())).play();
		Timeline loud = new Timeline((new KeyFrame(Duration.millis(timeLoud), new KeyValue(mp.volumeProperty(), 1.0))));
		Timeline mute = new Timeline(new KeyFrame(Duration.millis(timeMute), new KeyValue(mp.volumeProperty(), 0)));
		SequentialTransition sequence = new SequentialTransition(loud, mute);
		sequence.play();

	}

	public void toonSchermMenu() {
		Stage stage = (Stage) this.getScene().getWindow();
		Scene scene = new Scene(new SchermMenu(dc, bundle));
		stage.setScene(scene);
	}

}
