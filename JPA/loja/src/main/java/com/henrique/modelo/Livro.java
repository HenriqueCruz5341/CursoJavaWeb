package com.henrique.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro extends Produto {
    private String autor;
    private int numPaginas;

    public Livro() {
        super();
    }

    public Livro(String nome, String descricao, BigDecimal preco, Categoria categoria, String autor, int numPaginas) {
        super(nome, descricao, preco, categoria);
        this.autor = autor;
        this.numPaginas = numPaginas;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

}
