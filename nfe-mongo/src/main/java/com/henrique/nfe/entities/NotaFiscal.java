package com.henrique.nfe.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "nota_fiscal")
public class NotaFiscal {
    @Id
    private String id;
    private String servico;
    private Double valor;
    private LocalDate dataEmissao;
    private StatusNota status = StatusNota.ABERTA;

    @DBRef
    private Empresa prestador;

    @DBRef
    private Empresa tomador;

}
