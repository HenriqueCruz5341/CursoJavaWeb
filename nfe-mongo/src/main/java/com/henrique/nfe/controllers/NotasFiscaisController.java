package com.henrique.nfe.controllers;

import com.henrique.nfe.domains.NotasFiscais;
import com.henrique.nfe.entities.NotaFiscal;
import com.henrique.nfe.models.envelopes.ResponseEnvelopePage;
import com.henrique.nfe.models.envelopes.ResponseEnvelopeSingleObject;
import com.henrique.nfe.models.notasfiscais.NotaFiscalInputDto;
import com.henrique.nfe.models.notasfiscais.NotaFiscalViewDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/nfe")
public class NotasFiscaisController {

    @Autowired
    private NotasFiscais notasFiscais;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopePage<NotaFiscal, NotaFiscalViewDto>> get(
            @RequestParam(defaultValue = "0") Integer pageIndex,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return notasFiscais.get(pageIndex, pageSize).toResponse();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto>> getById(
            @PathVariable String id) {
        return notasFiscais.getById(id).toResponse();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto>> post(
            @RequestBody NotaFiscalInputDto notaFiscalForm,
            UriComponentsBuilder uriBuilder) {
        return notasFiscais.post(notaFiscalForm).toResponse();
    }

    @PatchMapping(value = "/baixa/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto>> darBaixa(
            @PathVariable String id) {
        return notasFiscais.darBaixa(id).toResponse();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseEnvelopeSingleObject<NotaFiscal, NotaFiscalViewDto>> delete(@PathVariable String id) {
        return notasFiscais.delete(id).toResponse();
    }

}
