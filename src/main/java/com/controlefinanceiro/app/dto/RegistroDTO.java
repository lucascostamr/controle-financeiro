package com.controlefinanceiro.app;

import java.util.Date;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class RegistroDTO {
    private String tipo;
    private double valor;
    private String classificacao;
    private Date data;
}
