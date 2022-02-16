package com.henrique.nfe.models.notasfiscais;

import java.util.List;

import com.henrique.nfe.entities.Servico;

import lombok.Data;

@Data
public class NotaFiscalInputDto {
    private String dataEmissao;
    private String prestador;
    private String tomador;
    private List<Servico> servicos;

}
