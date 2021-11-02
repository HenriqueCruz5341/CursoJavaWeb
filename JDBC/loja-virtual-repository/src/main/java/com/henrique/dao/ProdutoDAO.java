package com.henrique.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.henrique.modelo.Categoria;
import com.henrique.modelo.Produto;

public class ProdutoDAO {
    private Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto(nome, descricao) VALUES(?, ?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getDescricao());
            pstm.execute();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                while (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Produto> listar() throws SQLException {
        List<Produto> produtos = new ArrayList<Produto>();
        String sql = "SELECT * FROM produto";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.execute();

            try (ResultSet rs = pstm.getResultSet()) {
                while (rs.next()) {
                    Produto produto = new Produto(rs.getInt(1), rs.getString(2), rs.getString(3));
                    produtos.add(produto);
                }
            }
        }

        return produtos;
    }

    public List<Produto> buscar(Categoria ct) throws SQLException {
        List<Produto> produtos = new ArrayList<Produto>();
        String sql = "SELECT * FROM produto WHERE categoria_id = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, ct.getId());
            pstm.execute();

            try (ResultSet rs = pstm.getResultSet()) {
                while (rs.next()) {
                    Produto produto = new Produto(rs.getInt(1), rs.getString(2), rs.getString(3));
                    produtos.add(produto);
                }
            }
        }

        return produtos;
    }
}
