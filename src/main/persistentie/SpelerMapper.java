package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domein.Speler;

public class SpelerMapper {

	// selects
	private static final String SELECT_SPELER = "SELECT * FROM Speler WHERE gebruikersnaam = ? and wachtwoord = ?";
	private static final String BESTAAT_SPELER = "SELECT * FROM Speler WHERE gebruikersnaam = ?";

	// inserts
	private static final String INSERT_SPELER = "INSERT INTO Speler (gebruikersnaam, wachtwoord, heeftAdminrechten, naam, voornaam) VALUES (?, ?, ?, ?, ?)";

	/**
	 * Default constructor.
	 */
	public SpelerMapper() {
	}

	/**
	 * Haalt de Speler met de gegeven gebruikersnaam en wachtwoord op uit de
	 * databank.
	 * 
	 * @param gebruikersnaam
	 * @param wachtwoord
	 * @return List met namen van de spelletjes
	 */
	public Speler geefSpeler(String gebruikersnaam, String wachtwoord) {
		Speler speler = null;
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(SELECT_SPELER)) {
			query.setString(1, gebruikersnaam);
			query.setString(2, wachtwoord);
			ResultSet rs = query.executeQuery();
			{
				if (rs.next()) {

					String naam = rs.getString("naam");
					String voornaam = rs.getString("voornaam");
					boolean heeftAdminrechten = rs.getBoolean("heeftAdminrechten");
					speler = new Speler(gebruikersnaam, wachtwoord, heeftAdminrechten, naam, voornaam);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return speler;
	}

	/**
	 * Controleert of er een Speler met de gegeven gebruikersnaam bestaat in de
	 * databank.
	 * 
	 * @param gebruikersnaam
	 * @return true waneer de Speler bestaat, anders false
	 */
	public boolean bestaatSpeler(String gebruikersnaam) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(BESTAAT_SPELER)) {
			query.setString(1, gebruikersnaam);
			ResultSet rs = query.executeQuery();
			{
				if (rs.next()) {
					return true;
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		return false;
	}

	/**
	 * Voegt de gegeven Speler toe aan de databank.
	 * 
	 * @param speler
	 */
	public void voegToe(Speler speler) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
			query.setString(1, controleerLengte(speler.getGebruikersnaam(), "gebruikersnaam_te_lang"));
			query.setString(2, controleerLengte(speler.getWachtwoord(), "wachtwoord_te_lang"));
			query.setBoolean(3, speler.getAdminrechten());
			query.setString(4, controleerLengte(speler.getNaam(), "naam_te_lang"));
			query.setString(5, controleerLengte(speler.getVoornaam(), "voornaam_te_lang"));
			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

	}

	/**
	 * Controleert of de input niet langer is dan de maximumlengte van de databank.
	 * Als de input langer is, wordt de foutmelding gethrowd.
	 * 
	 * @param input
	 * @param foutmelding
	 * @return input
	 */
	private String controleerLengte(String input, String foutmelding) {
		if (input == null) {
			return null;
		}
		if (input.length() > 20) {
			throw new IllegalArgumentException(foutmelding);
		}
		return input;
	}

}
