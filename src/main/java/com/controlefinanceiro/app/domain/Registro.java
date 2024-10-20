package com.controlefinanceiro.app;

import java.util.Date;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Registro {
    private int id;
    private String nome;
    private String tipo;
    private double valor;
    private String classificacao;
    private Date data;
    private Date dataCadastro;

    public Registro(
        String nome,
        String tipo,
        double valor,
        String classificacao,
        Date data,
        Date dataCadastro
    ) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.classificacao = classificacao;
        this.data = data;
        this.dataCadastro = dataCadastro;
    }
}