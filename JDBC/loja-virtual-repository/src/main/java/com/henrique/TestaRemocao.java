package com.henrique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestaRemocao {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.recuperarConexao();

        System.out.println("Conexão estabelecida com sucesso!");

        PreparedStatement stm = con.prepareStatement("DELETE FROM produto WHERE id > 2");
        stm.execute();
        Integer linhasModificadas = stm.getUpdateCount();

        System.out.println("Linhas modificadas: " + linhasModificadas);

        stm.close();
        con.close();
    }
}