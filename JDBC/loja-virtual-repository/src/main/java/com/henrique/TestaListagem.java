package com.henrique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestaListagem {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.recuperarConexao();

        System.out.println("Conexão estabelecida com sucesso!");

        PreparedStatement stm = con.prepareStatement("SELECT * FROM produto");
        stm.execute();
        ResultSet rst = stm.getResultSet();

        while (rst.next()) {
            System.out.println(rst.getInt("id"));
            System.out.println(rst.getString("nome"));
            System.out.println(rst.getString("descricao"));
        }

        rst.close();
        stm.close();
        con.close();
    }
}