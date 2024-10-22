package main;

import controller.ControleFinanceiroController;
import database.DataBaseInitializer;
import model.ControleFinanceiroService;
import view.ControleFinanceiroView;

public class ControleFinanceiroMain {

	public static void main(String[] args) {
		DataBaseInitializer.initialize();

		var view = new ControleFinanceiroView();
		view.setVisible(true);

		var service = new ControleFinanceiroService();
		var controller = new ControleFinanceiroController(view, service);

		view.setController(controller);
		service.setController(controller);

	}

}
