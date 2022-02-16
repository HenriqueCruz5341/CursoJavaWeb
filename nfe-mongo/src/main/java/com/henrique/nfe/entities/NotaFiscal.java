package com.henrique.nfe.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "nota_fiscal")
public class NotaFiscal {
    @Id
    private String id;
    private List<Servico> servicos;
    private Double valorTotal;
    private LocalDate dataEmissao;
    private StatusNota status = StatusNota.ABERTA;
    private Empresa prestador;
    private Empresa tomador;

}
