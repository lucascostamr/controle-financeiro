package controller;

import java.util.List;

import model.ControleFinanceiroService;
import model.dto.RecordDTO;
import model.dto.TotalDTO;
import view.ControleFinanceiroView;

public class ControleFinanceiroController {
	
	private ControleFinanceiroView view;
	private ControleFinanceiroService service;
	
	public ControleFinanceiroController(ControleFinanceiroView view, ControleFinanceiroService service) {
		this.view = view;
		this.service = service;
	}

	public void insertRecord(RecordDTO record) {
		service.insertRecord(record);
	}

	public List<RecordDTO> getAllRecords() {
		return service.getAllRecords();
	}

	public RecordDTO getRecordAt(int row) {
		return service.getRecordAt(row);
	}
	
	public TotalDTO getTotal() {
		return service.getTotal();
	}

	public void deleteRecord(RecordDTO recordToDelete) {
		service.deleteRecord(recordToDelete);
	}
	
}