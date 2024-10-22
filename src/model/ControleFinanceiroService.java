package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.ControleFinanceiroController;
import model.dao.RecordDAO;
import model.dto.RecordDTO;
import model.dto.TotalDTO;

public class ControleFinanceiroService {

	private ControleFinanceiroController controller;
	private RecordDAO recordDAO;
	private List<RecordDTO> currentRecords;


	public ControleFinanceiroService() {
		recordDAO = new RecordDAO();
		currentRecords = new ArrayList<>();
	}

	public void setController(ControleFinanceiroController controller) {
		this.controller = controller;
	}

	public void insertRecord(RecordDTO record) {
		try {
			recordDAO.save(record);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public List<RecordDTO> getAllRecords() {
		try {
			currentRecords = recordDAO.list();
			return currentRecords;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteRecord(RecordDTO recordToDelete) {
		try {
			recordDAO.delete(recordToDelete.id());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public RecordDTO getRecordAt(int row) {
		return currentRecords.get(row);
	}

	public TotalDTO getTotal() {
	    float totalIncome = (float) currentRecords.stream()
	            .mapToDouble(RecordDTO::value)
	            .filter(value -> value > 0)
	            .sum();

	    float totalExpense = (float) currentRecords.stream()
	            .mapToDouble(RecordDTO::value)
	            .filter(value -> value < 0)
	            .sum();

	    float difference = totalIncome + totalExpense;

	    return new TotalDTO(totalIncome, totalExpense, difference);
	}

}
