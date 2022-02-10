package com.henrique.nfe.models.empresas;

import lombok.Data;

@Data
public class EmpresaInputDto {
    private String cnpj;
    private String razaoSocial;
    private String cidade;

}
