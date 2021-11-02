package com.henrique;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.henrique.dao.CategoriaDAO;
import com.henrique.modelo.Categoria;
import com.henrique.modelo.Produto;

public class TestaListagemDeCategorias {
    public static void main(String[] args) throws SQLException {

        try (Connection conn = new ConnectionFactory().recuperarConexao()) {
            CategoriaDAO categoriaDAO = new CategoriaDAO(conn);
            List<Categoria> categorias = categoriaDAO.listarComProduto();
            categorias.stream().forEach(c -> {
                System.out.println(c);
                for (Produto p : c.getProdutos())
                    System.out.println("\t" + p.getNome());
            });
        }
    }
}
