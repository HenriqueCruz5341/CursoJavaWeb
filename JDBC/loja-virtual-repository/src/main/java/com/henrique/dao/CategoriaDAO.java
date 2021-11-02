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

public class CategoriaDAO {
    private Connection connection;

    public CategoriaDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria(nome) VALUES(?)";

        try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, categoria.getNome());
            pstm.execute();

            try (ResultSet rs = pstm.getGeneratedKeys()) {
                while (rs.next()) {
                    categoria.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<Categoria>();
        String sql = "SELECT * FROM categoria";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.execute();

            try (ResultSet rs = pstm.getResultSet()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt(1), rs.getString(2));
                    categorias.add(categoria);
                }
            }
        }

        return categorias;
    }

    public List<Categoria> listarComProduto() throws SQLException {
        List<Categoria> categorias = new ArrayList<Categoria>();
        Categoria ultima = null;
        String sql = "SELECT * FROM categoria c INNER JOIN produto p ON c.id = p.categoria_id";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.execute();

            try (ResultSet rs = pstm.getResultSet()) {
                while (rs.next()) {
                    if (ultima == null || ultima.getId() != rs.getInt(1)) {
                        Categoria categoria = new Categoria(rs.getInt(1), rs.getString(2));
                        ultima = categoria;
                        categorias.add(ultima);
                    }
                    Produto produto = new Produto(rs.getInt(3), rs.getString(4), rs.getString(5));
                    ultima.adicionar(produto);
                }
            }
        }

        return categorias;
    }
}
