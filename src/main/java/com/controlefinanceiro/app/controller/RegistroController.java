package com.controlefinanceiro.app;

import java.util.Date;
import java.util.List;

import com.controlefinanceiro.app.RegistroDAO;
import com.controlefinanceiro.app.RegistroDTO;
import com.controlefinanceiro.app.RegistroModel;


public class RegistroController {
    private RegistroModel registroModel;

    public RegistroController(RegistroModel registroModel) {
        this.registroModel = registroModel;
    }

    public Object handle(String action) {
        try {
            switch (action) {
                case "list":
                    return this.registroModel.list();
                case "total":
                    return this.registroModel.total();
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void handle(String action, Object data) {
        try {
            switch (action) {
                case "save":
                    this.registroModel.save((RegistroDTO) data);
                    break;
                case "delete":
                    this.registroModel.delete((Integer) data);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
