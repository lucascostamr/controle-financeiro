package com.controlefinanceiro.app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM registro WHERE id = ?";

        PreparedStatement pstmt = DAOHelper
                                    .connect()
                                    .prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public List<Registro> list() throws Exception {
        List<Registro> registros = new ArrayList<>();

        String sql = "SELECT id, nome, tipo, valor, classificacao, data, data_cadastro "
                    + "FROM registro";

        PreparedStatement pstmt = DAOHelper.connect().prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            registros.add (
                new Registro(
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getDouble("valor"),
                    rs.getString("classificacao"),
                    rs.getDate("data"),
                    rs.getDate("data_cadastro")
                )
            );
        }

        rs.close();
        pstmt.close();

        return registros;
    }

    public List<Double> total() throws Exception{
        List<Double> total = new ArrayList<>();

        String sql = "SELECT "
            + "SUM(CASE WHEN tipo = 'Ganho' THEN valor ELSE 0 END) AS total_ganho, "
            + "SUM(CASE WHEN tipo = 'Gasto' THEN valor ELSE 0 END) AS total_gasto, "
            + "SUM(CASE WHEN tipo = 'Ganho' THEN valor ELSE 0 END) - "
            + "SUM(CASE WHEN tipo = 'Gasto' THEN valor ELSE 0 END) AS diff "
            + "FROM registro;";

        PreparedStatement pstmt = DAOHelper.connect().prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            total.add(rs.getDouble("total_ganho"));
            total.add(rs.getDouble("total_gasto"));
            total.add(rs.getDouble("diff"));
        }
        rs.close();
        pstmt.close();

        return total;
    }
}
