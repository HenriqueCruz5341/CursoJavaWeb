package com.henrique.nfe.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "empresa")
public class Empresa {
    @Id
    private String id;

    @Indexed(unique = true)
    private String cnpj;
    private String razaoSocial;
    private String cidade;

    @DBRef(lazy = true)
    private List<NotaFiscal> notasFiscais = new ArrayList<>();

}