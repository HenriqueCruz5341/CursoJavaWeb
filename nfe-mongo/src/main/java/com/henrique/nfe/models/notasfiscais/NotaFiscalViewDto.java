package com.henrique.nfe.models.notasfiscais;

import java.time.LocalDate;

import com.henrique.nfe.entities.StatusNota;
import com.henrique.nfe.models.empresas.EmpresaViewDto;

import lombok.Data;

@Data
public class NotaFiscalViewDto {
    private String id;
    private String servico;
    private Double valor;
    private LocalDate dataEmissao;
    private StatusNota status;
    private EmpresaViewDto prestador;
    private EmpresaViewDto tomador;

}
