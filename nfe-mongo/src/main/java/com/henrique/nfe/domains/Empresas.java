package com.henrique.nfe.domains;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.henrique.nfe.entities.Empresa;
import com.henrique.nfe.models.empresas.EmpresaEditDto;
import com.henrique.nfe.models.empresas.EmpresaInputDto;
import com.henrique.nfe.models.empresas.EmpresaViewDto;
import com.henrique.nfe.models.envelopes.ResponseEnvelopePage;
import com.henrique.nfe.models.envelopes.ResponseEnvelopeSingleObject;

public interface Empresas {
    public ResponseEnvelopePage<Empresa, EmpresaViewDto> get(Integer pageIndex, Integer pageSize);

    public ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto> getById(String id);

    public ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto> post(EmpresaInputDto empresaInputDto);

    public ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto> put(String id, EmpresaEditDto empresaEditDto);

    public ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto> delete(String id);

}
