package com.henrique.nfe.controllers;

import com.henrique.nfe.domains.Empresas;
import com.henrique.nfe.entities.Empresa;
import com.henrique.nfe.models.empresas.EmpresaEditDto;
import com.henrique.nfe.models.empresas.EmpresaInputDto;
import com.henrique.nfe.models.empresas.EmpresaViewDto;
import com.henrique.nfe.models.envelopes.ResponseEnvelopePage;
import com.henrique.nfe.models.envelopes.ResponseEnvelopeSingleObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresa")
public class EmpresasController {
    @Autowired
    private Empresas empresas;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopePage<Empresa, EmpresaViewDto>> get(
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return empresas.get(pageIndex, pageSize).toResponse();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto>> getById(@PathVariable String id) {
        return empresas.getById(id).toResponse();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto>> post(
            @RequestBody EmpresaInputDto empresaInputDto) {
        return empresas.post(empresaInputDto).toResponse();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto>> put(@PathVariable String id,
            @RequestBody EmpresaEditDto empresaFormUpdate) {
        return empresas.put(id, empresaFormUpdate).toResponse();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopeSingleObject<Empresa, EmpresaViewDto>> delete(@PathVariable String id) {
        return empresas.delete(id).toResponse();
    }

}
