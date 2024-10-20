package com.controlefinanceiro.app;

import java.util.Date;

import com.controlefinanceiro.app.RegistroRepository;
import com.controlefinanceiro.app.RegistroDTO;
import com.controlefinanceiro.app.Registro;

public class RegistroModel {
    private RegistroRepository registroRepository;

    public RegistroModel(RegistroRepository registroRepository) {
        this.registroRepository = registroRepository;
    }

    public void save(RegistroDTO registroDTO) throws Exception {
        this.registroRepository.save(
            this.map(registroDTO)
        );
    }

    public void delete(int registroId) throws Exception {
        this.registroRepository.delete(registroId);
    }

    private Registro map(RegistroDTO registroDTO) {
        String valorNormalizado = registroDTO
                                .getValor()
                                .replace(" ", "");

        double valor = Double.parseDouble(
            valorNormalizado.substring(1)
        );

        String tipo = valorNormalizado.charAt(0) == '+' ? "Ganho" : "Gasto";

        return new Registro(
            registroDTO.getNome(),
            tipo,
            valor,
            registroDTO.getClassificacao(),
            registroDTO.getData(),
            new Date()
        );
    }
}
