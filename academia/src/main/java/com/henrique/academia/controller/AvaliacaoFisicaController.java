package com.henrique.academia.controller;

import java.util.List;

import javax.validation.Valid;

import com.henrique.academia.entity.AvaliacaoFisica;
import com.henrique.academia.entity.form.AvaliacaoFisicaForm;
import com.henrique.academia.infra.errors.AlunoNotFoundException;
import com.henrique.academia.service.impl.AvaliacaoFisicaServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoFisicaController {

    @Autowired
    private AvaliacaoFisicaServiceImpl avaliacaoFisicaService;

    @GetMapping
    public ResponseEntity<List<AvaliacaoFisica>> getAll() {
        return ResponseEntity.ok(avaliacaoFisicaService.getAll());
    }

    @PostMapping
    public ResponseEntity<AvaliacaoFisica> create(@Valid @RequestBody AvaliacaoFisicaForm avaliacaoFisicaForm) {
        try {
            return ResponseEntity.ok(avaliacaoFisicaService.create(avaliacaoFisicaForm));
        } catch (AlunoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
