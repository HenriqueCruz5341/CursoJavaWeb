package com.henrique;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.henrique.dao.ProdutoDAO;
import com.henrique.modelo.Produto;

public class TestaInsercaoEListagemComProduto {
    public static void main(String[] args) throws SQLException {
        Produto produto = new Produto("Cômoda", "Cômoda vertical");

        try (Connection conn = new ConnectionFactory().recuperarConexao()) {
            ProdutoDAO produtoDAO = new ProdutoDAO(conn);
            produtoDAO.salvar(produto);
            List<Produto> produtos = produtoDAO.listar();
            produtos.stream().forEach(p -> System.out.println(p));
        }
    }
}
