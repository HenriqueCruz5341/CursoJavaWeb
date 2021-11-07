package com.henrique.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.henrique.dao.CategoriaDao;
import com.henrique.dao.ClienteDao;
import com.henrique.dao.PedidoDao;
import com.henrique.dao.ProdutoDao;
import com.henrique.modelo.Categoria;
import com.henrique.modelo.Cliente;
import com.henrique.modelo.ItemPedido;
import com.henrique.modelo.Pedido;
import com.henrique.modelo.Produto;
import com.henrique.util.JPAUtil;
import com.henrique.vo.RelatorioDeVendasVo;

public class CadastroDePedido {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        PedidoDao pedidoDao = new PedidoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        popularBanco();

        em.getTransaction().begin();

        Cliente cliente = clienteDao.buscar(1L);
        Produto produto = produtoDao.buscar(1L);
        Produto produto2 = produtoDao.buscar(2L);
        Produto produto3 = produtoDao.buscar(3L);

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));
        pedido.adicionarItem(new ItemPedido(40, pedido, produto2));

        Pedido pedido2 = new Pedido(cliente);
        pedido2.adicionarItem(new ItemPedido(2, pedido2, produto3));

        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido2);

        em.getTransaction().commit();

        BigDecimal valorTotalVendido = pedidoDao.valorTotalVendido();
        System.out.println("Valor total vendido: " + valorTotalVendido);

        List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
        relatorio.forEach(System.out::println);
    }

    private static void popularBanco() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videogames = new Categoria("VIDEOGAMES");
        Categoria informatica = new Categoria("INFORMATICA");

        Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares);
        Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("2000"), videogames);
        Produto macbook = new Produto("Macbook", "Macbook pro", new BigDecimal("3500"), informatica);

        Cliente cliente = new Cliente("Henrique", "11122233344");

        EntityManager em = JPAUtil.getEntityManager();

        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(videogames);
        categoriaDao.cadastrar(informatica);

        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(videogame);
        produtoDao.cadastrar(macbook);

        clienteDao.cadastrar(cliente);

        em.getTransaction().commit();
        em.close();
    }
}
