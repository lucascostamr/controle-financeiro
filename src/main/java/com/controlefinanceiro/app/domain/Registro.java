package com.controlefinanceiro.app;

import java.util.Date;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Registro {
    private String tipo;
    private double valor;
    private String classificacao;
    private Date data;
    private Date dataCadastro;
}
