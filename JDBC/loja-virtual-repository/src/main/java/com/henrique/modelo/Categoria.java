package com.henrique.modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private Integer id;
    private String nome;
    private List<Produto> produtos = new ArrayList<Produto>();

    public Categoria(String nome, String descricao) {
        super();
        this.nome = nome;
    }

    public Categoria(Integer id, String nome) {
        super();
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public List<Produto> getProdutos() {
        return this.produtos;
    }

    public void adicionar(Produto produto) {
        this.produtos.add(produto);
    }

    @Override
    public String toString() {
        return String.format("Categoria: %d, %s", this.id, this.nome);
    }

}
