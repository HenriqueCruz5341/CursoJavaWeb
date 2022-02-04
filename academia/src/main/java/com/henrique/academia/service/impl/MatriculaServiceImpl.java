package com.henrique.academia.service.impl;

import java.util.List;
import java.util.Optional;

import com.henrique.academia.entity.Aluno;
import com.henrique.academia.entity.Matricula;
import com.henrique.academia.entity.form.MatriculaForm;
import com.henrique.academia.infra.errors.AlunoNotFoundException;
import com.henrique.academia.repository.AlunoRepository;
import com.henrique.academia.repository.MatriculaRepository;
import com.henrique.academia.service.IMatriculaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatriculaServiceImpl implements IMatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public Matricula create(MatriculaForm form) throws AlunoNotFoundException {
        Optional<Aluno> aluno = alunoRepository.findById(form.getAlunoId());
        if (!aluno.isPresent())
            throw new AlunoNotFoundException();

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno.get());

        return matriculaRepository.save(matricula);
    }

    @Override
    public Matricula get(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Matricula> getAll(String bairro) {
        if (bairro == null)
            return matriculaRepository.findAll();
        else
            return matriculaRepository.findByAlunoBairro(bairro);
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub

    }
}
