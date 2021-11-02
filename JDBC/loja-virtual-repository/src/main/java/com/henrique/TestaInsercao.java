package com.henrique;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection con = connectionFactory.recuperarConexao();

        System.out.println("Conexão estabelecida com sucesso!");

        Statement stm = con.createStatement();
        stm.execute("INSERT INTO produto (nome, descricao) VALUES ('Mouse', 'Mouse sem fio')",
                Statement.RETURN_GENERATED_KEYS);
        ResultSet rst = stm.getGeneratedKeys();

        while (rst.next())
            System.out.println("O item inserido foi: " + rst.getInt(1));

        rst.close();
        stm.close();
        con.close();
    }
}
