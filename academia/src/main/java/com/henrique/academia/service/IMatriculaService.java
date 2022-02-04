package com.henrique.academia.service;

import java.util.List;

import com.henrique.academia.entity.Matricula;
import com.henrique.academia.entity.form.MatriculaForm;
import com.henrique.academia.infra.errors.AlunoNotFoundException;

public interface IMatriculaService {
    public Matricula create(MatriculaForm form) throws AlunoNotFoundException;

    public Matricula get(Long id);

    public List<Matricula> getAll(String bairro);

    public void delete(Long id);
}
