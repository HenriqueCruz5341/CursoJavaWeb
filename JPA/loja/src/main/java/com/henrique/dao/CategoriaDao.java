package com.henrique.dao;

import javax.persistence.EntityManager;

import com.henrique.modelo.Categoria;

public class CategoriaDao {
    private EntityManager em;

    public CategoriaDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Categoria categoria) {
        em.persist(categoria);
    }

    public void atualizar(Categoria categoria) {
        em.merge(categoria);
    }

    public void remover(Categoria categoria) {
        em.remove(em.merge(categoria));
    }
}
