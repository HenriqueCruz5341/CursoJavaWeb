package com.henrique.vo;

import java.time.LocalDate;

public class RelatorioDeVendasVo {
    private String nomeProduto;
    private Long quantidadeVendida;
    private LocalDate dataUltimaVenda;

    public RelatorioDeVendasVo(String nomeProduto, Long quantidadeVendida, LocalDate dataUltimaVenda) {
        super();
        this.nomeProduto = nomeProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.dataUltimaVenda = dataUltimaVenda;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Long getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public LocalDate getDataUltimaVenda() {
        return dataUltimaVenda;
    }

    @Override
    public String toString() {
        return "RelatorioDeVendasVo [dataUltimaVenda=" + dataUltimaVenda + ", nomeProduto=" + nomeProduto
                + ", quantidadeVendida=" + quantidadeVendida + "]";
    }

}