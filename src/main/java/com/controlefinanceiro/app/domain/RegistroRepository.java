package com.controlefinanceiro.app;

import com.controlefinanceiro.app.Registro;
import com.controlefinanceiro.app.RegistroDTO;


public interface RegistroRepository {
    void save(Registro registro) throws Exception;
}
