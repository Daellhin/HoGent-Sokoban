package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Spel;
import domein.spelbord.Doel;
import domein.spelbord.Muur;
import domein.spelbord.Spelbord;
import domein.spelbord.Vak;
import domein.spelbord.Veld;

public class SpelMapper {

	// selects
	private static final String SELECT_SPELNAMEN = "SELECT spelNaam FROM Spel";
	private static final String SELECT_SPEL = "SELECT * FROM Spel WHERE spelNaam = ?";
	private static final String SELECT_COUNT_SPELBORDEN = "SELECT COUNT(*) AS 'aantal' FROM Spelbord WHERE spelNaam = ?";
	private static final String SELECT_SPELBORD = "SELECT * FROM Spelbord WHERE spelNaam = ?";
	private static final String SELECT_SPELSTUK = "SELECT * FROM Spelstuk WHERE spelNaam = ? and spelbordNummer = ?";
	private static final String SELECT_VAK = "SELECT * FROM Vak WHERE spelNaam = ? and spelbordNummer = ?";
	private static final String SELECT_SPELNAAM = "SELECT spelNaam FROM Spel WHERE spelerNaam = ?";

	// inserts
	private static final String INSERT_SPEL = "INSERT INTO Spel (spelNaam, spelerNaam) VALUES (?, ?)";
	private static final String INSERT_SPELBORD = "INSERT INTO Spelbord (spelNaam, volgnummer) VALUES (?, ?)";
	private static final String INSERT_SPELSTUK = "INSERT INTO Spelstuk (spelNaam, spelbordNummer, rijPos, kolPos, spelstukType) VALUES (?, ?, ?, ?, ?)";
	private static final String INSERT_VAK = "INSERT INTO Vak (spelNaam, spelbordNummer, rijPos, kolPos, vakType) VALUES (?, ?, ?, ?, ?)";

	// deletes
	private static final String DELETE_SPEL = "DELETE FROM Spel WHERE spelNaam = ?";
	private static final String DELETE_SPELBORDEN = "DELETE FROM Spelbord WHERE spelNaam = ?";
	private static final String DELETE_SPELSTUKKEN = "DELETE FROM Spelstuk WHERE spelNaam = ?";
	private static final String DELETE_VAKKEN = "DELETE FROM Vak WHERE spelNaam = ?";
	private static final String DELETE_SPELBORD = "DELETE FROM Spelbord WHERE spelNaam = ? and volgnummer = ?";
	private static final String DELETE_VAKKEN_VAN_SPELBORD = "DELETE FROM Vak WHERE spelNaam = ? and spelbordNummer = ?";
	private static final String DELETE_SPELSTUKKEN_VAN_SPELBORD = "DELETE FROM Spelstuk WHERE spelNaam = ? and spelbordNummer = ?";

	/**
	 * Default constructor.
	 */
	public SpelMapper() {
	}

	// methods
	/**
	 * Haalt de namen van alle Spelletje op uit de databank.
	 * 
	 * @return List met namen van de Spelletjes
	 */
	public List<String> geefSpelnamen() {
		List<String> namenVanSpelletjes = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELNAMEN);
				ResultSet rs = query.executeQuery()) {
			while (rs.next()) {
				String naam = rs.getString("spelNaam");
				namenVanSpelletjes.add(naam);
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return namenVanSpelletjes;
	}

	/**
	 * Haalt het Spel en zijn Spelborden op uit de databank, aan de hand van de
	 * gegeven naam.
	 * 
	 * @param naam
	 * @return Spel
	 */
	public Spel geefSpel(String naam) {
		Spel spel = null;
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPEL)) {
			query.setString(1, naam);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				Spelbord eersteSpelbord = geefSpelborden(naam);
				int aantalSpelborden = geefAantalSpelborden(naam);
				spel = new Spel(naam, eersteSpelbord, aantalSpelborden);
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return spel;
	}

	/**
	 * Telt het aantal spelborden in het Spel met de gegeven naam.
	 * 
	 * @param naam
	 * @return
	 */
	private int geefAantalSpelborden(String naam) {
		int aantalSpelborden = 0;
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_COUNT_SPELBORDEN)) {
			query.setString(1, naam);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				aantalSpelborden = rs.getInt("aantal");
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return aantalSpelborden;
	}

	/**
	 * Haalt de Spelborden en hun vakken en spelstukken op uit de databank, aan de
	 * hand van de gegeven spelnaam.
	 * 
	 * @param spelnaam
	 * @return het eerste spelbord met de link naar de andere spelborden
	 */
	private Spelbord geefSpelborden(String spelnaam) {
		Spelbord eersteSpelbord = null;
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELBORD)) {
			query.setString(1, spelnaam);
			ResultSet rs = query.executeQuery();

			int volgnummer = 0;
			if (rs.next()) {
				Vak[][] vakken = geefVakken(spelnaam, volgnummer);
				List<int[]> spelstukPosities = geefSpelstukPosities(spelnaam, volgnummer);
				int[] startPositieMannetje = spelstukPosities.get(0);
				spelstukPosities.remove(0);
				int[][] startPositiesKisten = spelstukPosities.toArray(new int[0][]);
				eersteSpelbord = new Spelbord(vakken, startPositieMannetje, startPositiesKisten);
				volgnummer++;
			}

			Spelbord tijdelijk = eersteSpelbord;
			while (rs.next()) {
				Vak[][] vakken = geefVakken(spelnaam, volgnummer);
				List<int[]> spelstukPosities = geefSpelstukPosities(spelnaam, volgnummer);
				int[] startPositieMannetje = spelstukPosities.get(0);
				spelstukPosities.remove(0);
				int[][] startPositiesKisten = spelstukPosities.toArray(new int[0][]);
				Spelbord spelbord = new Spelbord(vakken, startPositieMannetje, startPositiesKisten);
				tijdelijk.setVolgendSpelBord(spelbord);
				volgnummer++;
				tijdelijk = spelbord;
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return eersteSpelbord;
	}

	/**
	 * Haalt de spelstukken van een spelbord op.
	 * 
	 * @param spelnaam
	 * @param volgnumer van het spelbord
	 * @return posities van de spelstukken, mannetje staat op de eerste positie in
	 *         de lijst
	 */
	private List<int[]> geefSpelstukPosities(String spelnaam, int volgnummer) {
		List<int[]> posities = new ArrayList<int[]>();
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELSTUK)) {
			query.setString(1, spelnaam);
			query.setInt(2, volgnummer);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				if (rs.getString("spelstukType").equals("mannetje")) {
					posities.add(0, new int[] { rs.getInt("rijPos"), rs.getInt("kolPos") });
				} else {
					posities.add(new int[] { rs.getInt("rijPos"), rs.getInt("kolPos") });
				}
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return posities;
	}

	/**
	 * Haalt alle vakken van het spelbord met de gegeven spelnaam en volgnummer op.
	 * 
	 * @param spelnaam
	 * @param volgnummer van het spelbord
	 * @return vakken
	 */
	private Vak[][] geefVakken(String spelnaam, int volgnummer) {
		Vak[][] vakken = new Vak[Spel.getMaxRijPos()][Spel.getMaxKolPos()];
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_VAK)) {
			query.setString(1, spelnaam);
			query.setInt(2, volgnummer);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				int rijPos = rs.getInt("rijPos");
				int kolPos = rs.getInt("kolPos");
				switch (rs.getString("vakType")) {
				case "veld":
					vakken[rijPos][kolPos] = new Veld(rijPos, kolPos);
					break;
				case "muur":
					vakken[rijPos][kolPos] = new Muur(rijPos, kolPos);
					break;
				case "doel":
					vakken[rijPos][kolPos] = new Doel(rijPos, kolPos);
					break;
				}

			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return vakken;
	}

	/**
	 * Controleert of er al een Spel met gegeven naam bestaat in de databank.
	 * 
	 * @param naam
	 * @return true waneer het Spel gevonden word, anders false
	 */
	public boolean bestaatSpel(String naam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPEL)) {
			query.setString(1, naam);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Voegt het gegeven Spel en zijn Spelborden toe in de databank.
	 * 
	 * @param spel
	 * @param gebruikersnaam
	 */
	public void voegSpelToe(Spel spel, String gebruikersnaam) {
		String spelNaam = controleerLengte(spel.getNaam());

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_SPEL)) {
			query.setString(1, spelNaam);
			query.setString(2, gebruikersnaam);
			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);

		}

		Spelbord tempSpelbord = spel.getEersteSpelbord();
		int teller = 0;
		voegSpelbordToe(tempSpelbord, spelNaam, teller);
		while (tempSpelbord.getVolgendSpelBord() != null) {
			teller++;
			tempSpelbord = tempSpelbord.getVolgendSpelBord();
			voegSpelbordToe(tempSpelbord, spelNaam, teller);
		}

	}

	/**
	 * Voegt het gegeven Spelborden en zijn vakken en spelstukken toe in de
	 * databank.
	 * 
	 * @param spelbord
	 * @param spelnaam
	 * @param volgnummer
	 */
	private void voegSpelbordToe(Spelbord spelbord, String spelnaam, int volgnummer) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELBORD)) {
			query.setString(1, spelnaam);
			query.setInt(2, volgnummer);
			query.executeUpdate();

			voegSpelstukkenToe(spelbord, spelnaam, volgnummer);
			voegVakkenToe(spelbord, spelnaam, volgnummer);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Voegt de spelstukken van het gegeven Spelbord toe in de databank.
	 * 
	 * @param spelbord
	 * @param spelnaam
	 * @param spelbordNummer
	 */
	private void voegSpelstukkenToe(Spelbord spelbord, String spelnaam, int spelbordNummer) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELSTUK)) {
			int[] positieMannetje = spelbord.getStartPositieMannetje();
			query.setString(1, spelnaam);
			query.setInt(2, spelbordNummer);
			query.setInt(3, positieMannetje[0]);
			query.setInt(4, positieMannetje[1]);
			query.setString(5, "mannetje");
			query.executeUpdate();

			int[][] positiesKisten = spelbord.getStartPositiesKisten();
			for (int[] is : positiesKisten) {
				query.setString(1, spelnaam);
				query.setInt(2, spelbordNummer);
				query.setInt(3, is[0]);
				query.setInt(4, is[1]);
				query.setString(5, "kist");
				query.executeUpdate();
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Voegt de vakken van het gegeven Spelbord toe in de databank.
	 * 
	 * @param spelbord
	 * @param spelnaam
	 * @param spelbordNummer
	 */
	private void voegVakkenToe(Spelbord spelbord, String spelnaam, int spelbordNummer) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_VAK)) {
			Vak[][] vakken = spelbord.getVakken();
			for (Vak[] vak1 : vakken) {
				for (Vak vak : vak1) {
					query.setString(1, spelnaam);
					query.setInt(2, spelbordNummer);
					query.setInt(3, vak.getrijPos());
					query.setInt(4, vak.getkolPos());
					query.setString(5, vak.getClass().getSimpleName().toLowerCase());
					query.executeUpdate();
				}
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Haalt de spelnamen geassocieerd met de Speler op uit de databank.
	 * 
	 * @param gebruikersnaam
	 * @return spelnamen van de Speler
	 */
	public List<String> geefSpelNamen(String gebruikersnaam) {
		List<String> namenVanSpelletjes = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELNAAM)) {
			query.setString(1, gebruikersnaam);
			try (ResultSet rs = query.executeQuery()) {
				while (rs.next()) {
					String spelNaam = rs.getString("spelNaam");
					namenVanSpelletjes.add(spelNaam);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return namenVanSpelletjes;
	}

	/**
	 * Verwijderd het Spel met de gegeven naam uit de databank.
	 * 
	 * @param spelnaam
	 */
	public void verwijderVolledigSpel(String spelnaam) {
		verwijderVakken(spelnaam);
		verwijderSpelstukken(spelnaam);
		verwijderSpelborden(spelnaam);
		verwijderSpel(spelnaam);
	}

	/**
	 * Verwjderd het Spel uit de databank, aan de hand van de gegeven spelnaam.
	 * 
	 * @param spelnaam
	 */
	private void verwijderSpel(String spelnaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(DELETE_SPEL)) {
			query.setString(1, spelnaam);
			query.execute();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Verwjderd alle Spelborden van een Spel uit de databank, aan de hand van de
	 * gegeven spelnaam.
	 * 
	 * @param spelnaam
	 */
	private void verwijderSpelborden(String spelnaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(DELETE_SPELBORDEN)) {
			query.setString(1, spelnaam);
			query.execute();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Verwjderd alle spelstukken van een Spel uit de databank, aan de hand van de
	 * gegeven spelnaam.
	 * 
	 * @param spelnaam
	 */
	private void verwijderSpelstukken(String spelnaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(DELETE_SPELSTUKKEN)) {
			query.setString(1, spelnaam);
			query.execute();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Verwjderd alle vakken van een Spel uit de databank, aan de hand van de
	 * gegeven spelnaam.
	 * 
	 * @param spelnaam
	 */
	private void verwijderVakken(String spelnaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(DELETE_VAKKEN)) {
			query.setString(1, spelnaam);
			query.execute();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Vervangt het Spelbord in de databank met het aangepaste Spelbord.
	 * 
	 * @param volgnummer om het volgnummer in de databank te bepalen
	 * @param spelbord
	 * @param spelnaam
	 */
	public void vervangSpelbord(int volgnummerProg, Spelbord spelbord, String spelnaam) {
		int volgnummerDatabank = bepaalVolgnummerDatabank(spelnaam, volgnummerProg);
		verwijderSpelstukkenVanSpelbord(volgnummerDatabank, spelnaam);
		verwijderVakkenVanSpelbord(volgnummerDatabank, spelnaam);
		verwijderSpelbord(volgnummerDatabank, spelnaam);

		voegSpelbordToe(spelbord, spelnaam, volgnummerDatabank);
	}

	/**
	 * Bepaal het volgnummer van het Spelbord dat moet vervangen worden in de
	 * databank, aan de hand van het volgnummer in het programma.
	 * 
	 * @param spelnaam
	 * @param volgnummerProg
	 * @return
	 */
	private int bepaalVolgnummerDatabank(String spelnaam, int volgnummerProg) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELBORD)) {
			query.setString(1, spelnaam);
			ResultSet rs = query.executeQuery();

			int teller = 1;
			while (rs.next()) {
				if (teller == volgnummerProg) {
					return rs.getInt("volgnummer");
				}
				teller++;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return -1;

	}

	/**
	 * Verwijderd een Spelbord uit de databank, aan de hand van de gegeven spelnaam
	 * en volgnummer.
	 * 
	 * @param volgnummer
	 * @param spelnaam
	 */
	private void verwijderSpelbord(int volgnummer, String spelnaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(DELETE_SPELBORD)) {
			query.setString(1, spelnaam);
			query.setInt(2, volgnummer);
			query.execute();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Verwjderd alle vakken van een Spelbord uit de databank, aan de hand van de
	 * gegeven spelnaam en volgnummer.
	 * 
	 * @param volgnummer
	 * @param spelnaam
	 */
	private void verwijderVakkenVanSpelbord(int volgnummer, String spelnaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(DELETE_VAKKEN_VAN_SPELBORD)) {
			query.setString(1, spelnaam);
			query.setInt(2, volgnummer);
			query.execute();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}

	/**
	 * Verwjderd alle spelstukken van een Spelbord uit de databank, aan de hand van
	 * de gegeven spelnaam en volgnummer.
	 * 
	 * @param volgnummer
	 * @param spelnaam
	 */
	private void verwijderSpelstukkenVanSpelbord(int volgnummer, String spelnaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(DELETE_SPELSTUKKEN_VAN_SPELBORD)) {
			query.setString(1, spelnaam);
			query.setInt(2, volgnummer);
			query.execute();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Controleerd of de input niet langer is als de maximumlengte van de databank.
	 * Als de input langer is deze ingekort tot 20.
	 * 
	 * @param input
	 * @return input substring(20)
	 */
	private String controleerLengte(String input) {
		if (input == null) {
			return null;
		}
		if (input.length() > 20) {
			return input.substring(0, 20);
		}
		return input;
	}

}
