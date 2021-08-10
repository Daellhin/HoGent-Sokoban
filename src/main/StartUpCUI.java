package main;

import cui.Sokoban;
import domein.DomeinController;

public class StartUpCUI {
	public static void main(String[] args) {
		DomeinController domeinController = new DomeinController();
		Sokoban sokoban = new Sokoban(domeinController);
		sokoban.start();
	}

}
