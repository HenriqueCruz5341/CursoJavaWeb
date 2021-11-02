package com.henrique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoComParametro {

    public static void main(String[] args) throws SQLException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection con = connectionFactory.recuperarConexao()) {
            con.setAutoCommit(false);

            System.out.println("Conexão estabelecida com sucesso!");

            try (PreparedStatement stm = con.prepareStatement("INSERT INTO produto (nome, descricao) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                inserirProduto("SmartTV", "45 polegadas", stm);
                inserirProduto("Radio", "Radio a bateria", stm);
                con.commit();
            } catch (Exception e) {
                e.printStackTrace();
                con.rollback();
                System.out.println("ROLLBACK EXECUTADO!");
            }
        }
    }

    private static void inserirProduto(String nome, String descricao, PreparedStatement stm) throws SQLException {
        stm.setString(1, nome);
        stm.setString(2, descricao);

        stm.execute();

        try (ResultSet rst = stm.getGeneratedKeys()) {
            if (rst.next()) {
                System.out.println("Produto inserido com sucesso! ID: " + rst.getInt(1));
            }
        }
    }
}
