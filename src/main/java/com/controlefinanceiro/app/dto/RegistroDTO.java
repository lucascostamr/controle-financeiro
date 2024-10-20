package com.controlefinanceiro.app;

import java.util.Date;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class RegistroDTO {
    private int id;
    private String nome;
    private String valor;
    private String classificacao;
    private Date data;

    public RegistroDTO(
        String nome,
        String valor,
        String classificacao,
        Date data
    ) {
        this.nome = nome;
        this.valor = valor;
        this.classificacao = classificacao;
        this.data = data;
    }
}
