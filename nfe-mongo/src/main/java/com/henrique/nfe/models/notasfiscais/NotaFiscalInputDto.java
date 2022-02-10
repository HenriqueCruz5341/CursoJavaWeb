package com.henrique.nfe.models.notasfiscais;

import lombok.Data;

@Data
public class NotaFiscalInputDto {
    private String servico;
    private Double valor;
    private String dataEmissao;
    private String prestador;
    private String tomador;

}
