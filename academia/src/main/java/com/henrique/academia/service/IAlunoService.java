package com.henrique.academia.service;

import java.util.List;
import java.util.Optional;

import com.henrique.academia.entity.Aluno;
import com.henrique.academia.entity.form.AlunoForm;
import com.henrique.academia.entity.form.AlunoUpdateForm;
import com.henrique.academia.entity.view.AvaliacaoFisicaView;

public interface IAlunoService {
    public Aluno create(AlunoForm form);

    public Optional<Aluno> get(Long id);

    public List<Aluno> getAll(String nome, String dataDeNascimento);

    public List<AvaliacaoFisicaView> getAllAvaliacaoFisica(Long id);

    public Aluno update(Long id, AlunoUpdateForm updateForm);

    public void delete(Long id);
}
