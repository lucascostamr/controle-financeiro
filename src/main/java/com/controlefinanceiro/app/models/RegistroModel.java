package com.controlefinanceiro.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            this.mapRegistro(registroDTO)
        );
    }

    public void delete(int registroId) throws Exception {
        this.registroRepository.delete(registroId);
    }

    public List<RegistroDTO> list() throws Exception {
        return this.mapDTO(
            this.registroRepository.list()
        );
    }

    private Registro mapRegistro(RegistroDTO registroDTO) {
        String valorNormalizado = registroDTO
                                .getValor()
                                .replace(" ", "")
                                .replace(",", ".");

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

    private List<RegistroDTO> mapDTO(List<Registro> registros) {
        List<RegistroDTO> registroDTOs = new ArrayList<>();

        for (Registro registro : registros) {
            registroDTOs.add(
                new RegistroDTO(
                    registro.getNome(),
                    String.valueOf(
                        registro.getValor()
                    ),
                    registro.getClassificacao(),
                    registro.getData()
                )
            );
        }
        return registroDTOs;
    }
}
