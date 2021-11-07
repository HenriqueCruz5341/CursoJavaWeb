package com.henrique.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.henrique.modelo.Pedido;
import com.henrique.vo.RelatorioDeVendasVo;

public class PedidoDao {
    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido) {
        em.persist(pedido);
    }

    public BigDecimal valorTotalVendido() {
        return em.createQuery("SELECT SUM(p.valorTotal) FROM Pedido p", BigDecimal.class).getSingleResult();
    }

    public List<RelatorioDeVendasVo> relatorioDeVendas() {
        String jpql = "SELECT new com.henrique.vo.RelatorioDeVendasVo(produto.nome, SUM(item.quantidade), MAX(pedido.data)) "
                + "FROM Pedido pedido JOIN pedido.itens item JOIN item.produto produto "
                + "GROUP BY produto.nome ORDER BY item.quantidade DESC";
        return em.createQuery(jpql, RelatorioDeVendasVo.class).getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id) {
        return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id", Pedido.class)
                .setParameter("id", id).getSingleResult();
    }
}
