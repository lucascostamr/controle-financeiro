package com.controlefinanceiro.app;

import java.util.Date;

import com.controlefinanceiro.app.Registro;
import com.controlefinanceiro.app.RegistroDTO;

public class RegistroMapper {

    public Registro toDomain(RegistroDTO registroDTO) {
        return new Registro(
            registroDTO.getTipo(),
            registroDTO.getValor(),
            registroDTO.getClassificacao(),
            registroDTO.getData(),
            new Date()
        );
    }

    public RegistroDTO toDTO(Registro registro) {
        return new RegistroDTO(
            registro.getTipo(),
            registro.getValor(),
            registro.getClassificacao(),
            registro.getData()
        );
    }
}
