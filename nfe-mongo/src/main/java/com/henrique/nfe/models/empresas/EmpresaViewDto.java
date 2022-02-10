package com.henrique.nfe.models.empresas;

import lombok.Data;

@Data
public class EmpresaViewDto {
    private String id;
    private String cnpj;
    private String razaoSocial;
    private String cidade;

}
