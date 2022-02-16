package com.henrique.nfe.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "empresa")
public class Empresa {
    @Id
    private String id;
    private String cnpj;
    private String razaoSocial;
    private String cidade;
}