package com.controlefinanceiro.app;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import com.controlefinanceiro.app.RegistroRepository;
import com.controlefinanceiro.app.Registro;
import com.controlefinanceiro.app.DAOHelper;

public class RegistroDAO implements RegistroRepository{

    public void save(Registro registro) throws Exception {
        String sql = "INSERT INTO registro (nome, tipo, valor, classificacao, data, data_cadastro)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement pstmt = DAOHelper
                                    .connect()
                                    .prepareStatement(sql);

        pstmt.setString(1, registro.getNome());
        pstmt.setString(2, registro.getTipo());
        pstmt.setDouble(3, registro.getValor());
        pstmt.setString(4, registro.getClassificacao());
        pstmt.setDate(5, new Date(
            registro
                .getData()
                .getTime()
        ));
        pstmt.setDate(6, new Date(
            registro
                .getDataCadastro()
                .getTime()
        ));
        pstmt.executeUpdate();
    }
}
