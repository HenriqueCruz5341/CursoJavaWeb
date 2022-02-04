package com.henrique.academia.controller;

import java.util.List;

import javax.validation.Valid;

import com.henrique.academia.entity.Matricula;
import com.henrique.academia.entity.form.MatriculaForm;
import com.henrique.academia.infra.errors.AlunoNotFoundException;
import com.henrique.academia.service.impl.MatriculaServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaServiceImpl matriculaService;

    @GetMapping
    public ResponseEntity<List<Matricula>> getAll(@RequestParam(value = "bairro", required = false) String bairro) {

        return ResponseEntity.ok(matriculaService.getAll(bairro));

    }

    @PostMapping
    public ResponseEntity<Matricula> create(@Valid @RequestBody MatriculaForm avaliacaoFisicaForm) {
        try {
            return ResponseEntity.ok(matriculaService.create(avaliacaoFisicaForm));
        } catch (AlunoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
