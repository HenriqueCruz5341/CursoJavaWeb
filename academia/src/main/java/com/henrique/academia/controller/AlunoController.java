package com.henrique.academia.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.henrique.academia.entity.Aluno;
import com.henrique.academia.entity.form.AlunoForm;
import com.henrique.academia.entity.view.AvaliacaoFisicaView;
import com.henrique.academia.service.impl.AlunoServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoServiceImpl alunoService;

    @GetMapping
    public ResponseEntity<List<Aluno>> getAll(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "dataDeNascimento", required = false) String dataDeNascimento) {
        List<Aluno> listaAlunos = alunoService.getAll(nome, dataDeNascimento);

        return ResponseEntity.ok(listaAlunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> getOne(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoService.get(id);

        if (aluno.isPresent())
            return ResponseEntity.ok(aluno.get());
        else
            return ResponseEntity.notFound().build();

    }

    @PostMapping
    public ResponseEntity<Aluno> create(@Valid @RequestBody AlunoForm alunoForm) {
        return ResponseEntity.ok(alunoService.create(alunoForm));
    }

    @GetMapping("/avaliacoes/{id}")
    public ResponseEntity<List<AvaliacaoFisicaView>> getAllAvaliacaoFisica(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.getAllAvaliacaoFisica(id));
    }

}
