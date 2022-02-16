package com.henrique.nfe.models.notasfiscais;

import java.time.LocalDate;
import java.util.List;

import com.henrique.nfe.entities.Servico;
import com.henrique.nfe.entities.StatusNota;
import com.henrique.nfe.models.empresas.EmpresaViewDto;

import lombok.Data;

@Data
public class NotaFiscalViewDto {
    private String id;
    private List<Servico> servicos;
    private Double valorTotal;
    private LocalDate dataEmissao;
    private StatusNota status;
    private EmpresaViewDto prestador;
    private EmpresaViewDto tomador;

}
