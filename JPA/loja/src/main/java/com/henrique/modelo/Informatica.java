package com.henrique.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "informaticas")
public class Informatica extends Produto {
    private String marca;
    private String modelo;

    public Informatica() {
        super();
    }

    public Informatica(String nome, String descricao, BigDecimal preco, Categoria categoria, String marca,
            String modelo) {
        super(nome, descricao, preco, categoria);
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

}
