package com.henrique.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.henrique.dao.CategoriaDao;
import com.henrique.dao.ProdutoDao;
import com.henrique.modelo.Categoria;
import com.henrique.modelo.Produto;
import com.henrique.util.JPAUtil;

public class CadastroDeProduto {
    public static void main(String[] args) {
        cadastrarProduto();
        EntityManager em = JPAUtil.getEntityManager();

        ProdutoDao produtoDao = new ProdutoDao(em);
        Produto produto = produtoDao.buscar(1L);
        System.out.println(produto.getNome());

        List<Produto> produtos = produtoDao.buscarPorNomeDaCategoria("CELULARES");
        produtos.forEach(p -> System.out.println(p.getNome()));

        BigDecimal preco = produtoDao.buscarPrecoProduto("Xiaomi Redmi");
        System.out.println(preco);
    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");
        Produto produto = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);

        EntityManager em = JPAUtil.getEntityManager();

        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();
        categoriaDao.cadastrar(celulares);
        produtoDao.cadastrar(produto);
        em.getTransaction().commit();
        em.close();
    }
}
