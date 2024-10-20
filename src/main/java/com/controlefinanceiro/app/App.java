package com.controlefinanceiro.app;

import java.util.Date;
import java.util.List;

import com.controlefinanceiro.app.RegistroModel;
import com.controlefinanceiro.app.RegistroDTO;
import com.controlefinanceiro.app.RegistroDAO;
import com.controlefinanceiro.app.Registro;
import com.controlefinanceiro.app.RegistroController;

public class App {
    public static void main(String[] args) {
        RegistroDTO registroDTO = new RegistroDTO(
            "saldo",
            "- 5,50",
            "SAUDE",
            new Date()
        );

        RegistroDAO registroDAO = new RegistroDAO();
        RegistroModel registroModel = new RegistroModel(registroDAO);
        RegistroController registroController = new RegistroController(registroModel);

        registroController.handle("save", registroDTO);
        registroController.handle("delete", 1);
        registroController.handle("list");
        registroController.handle("total");
    }
}
