package com.controlefinanceiro.app;

import java.util.List;

import com.controlefinanceiro.app.Registro;
import com.controlefinanceiro.app.RegistroDTO;


public interface RegistroRepository {
    void save(Registro registro) throws Exception;
    void delete(int registroId) throws Exception;
    List<Registro> list() throws Exception;
    List<Double> total() throws Exception;
}
