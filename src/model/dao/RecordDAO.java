package model.dao;

import java.sql.*;
import java.util.*;

import database.ConnectionManager;
import model.dto.RecordDTO;

public class RecordDAO {

	public void save(RecordDTO registro) throws Exception {
		String sql = "INSERT INTO registro (name, classification, value, entryDate, registrationDate)"
				+ "VALUES (?, ?, ?, ?, ?)";

		PreparedStatement pstmt = ConnectionManager.getConnection().prepareStatement(sql);

		pstmt.setString(1, registro.name());
		pstmt.setString(2, registro.classification());
		pstmt.setDouble(3, registro.value());
		pstmt.setDate(4, java.sql.Date.valueOf(registro.entryDate()));
        pstmt.setDate(5, java.sql.Date.valueOf(registro.registrationDate()));
		pstmt.executeUpdate();
	}

	public void delete(Long id) throws Exception {
		String sql = "DELETE FROM registro WHERE id = ?";

		PreparedStatement pstmt = ConnectionManager.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		pstmt.executeUpdate();
	}

	private java.time.LocalDate getDate(String date) {
		try {
			var dateFormat = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
			var formattedDate = java.time.LocalDate.parse(date, dateFormat);
			return formattedDate;
		} catch (Exception e) {
			return null;
		}

	}

	public List<RecordDTO> list() throws SQLException {
		List<RecordDTO> registros = new ArrayList<>();

		String sql = "SELECT id, name, classification, value, entryDate, registrationDate " + "FROM registro";

		PreparedStatement pstmt = ConnectionManager.getConnection().prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			registros.add(new RecordDTO(rs.getLong("id"), rs.getString("name"), rs.getString("classification"), rs.getFloat("value"),
					rs.getDate("entryDate").toLocalDate(), rs.getDate("entryDate").toLocalDate()));
		}

		rs.close();
		pstmt.close();

		return registros;
	}
}
