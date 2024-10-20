package com.controlefinanceiro.app;

import java.util.Date;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class RegistroDTO {
    private String nome;
    private String valor;
    private String classificacao;
    private Date data;
}
