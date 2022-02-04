package com.henrique.academia.service.impl;

import java.util.List;
import java.util.Optional;

import com.henrique.academia.entity.Aluno;
import com.henrique.academia.entity.AvaliacaoFisica;
import com.henrique.academia.entity.form.AvaliacaoFisicaForm;
import com.henrique.academia.entity.form.AvaliacaoFisicaUpdateForm;
import com.henrique.academia.infra.errors.AlunoNotFoundException;
import com.henrique.academia.repository.AlunoRepository;
import com.henrique.academia.repository.AvaliacaoFisicaRepository;
import com.henrique.academia.service.IAvaliacaoFisicaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoFisicaServiceImpl implements IAvaliacaoFisicaService {

    @Autowired
    private AvaliacaoFisicaRepository avaliacaoFisicaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public AvaliacaoFisica create(AvaliacaoFisicaForm form) {
        Optional<Aluno> aluno = alunoRepository.findById(form.getAlunoId());
        if (!aluno.isPresent())
            throw new AlunoNotFoundException();

        AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
        avaliacaoFisica.setAltura(form.getAltura());
        avaliacaoFisica.setPeso(form.getPeso());
        avaliacaoFisica.setAluno(aluno.get());

        return avaliacaoFisicaRepository.save(avaliacaoFisica);
    }

    @Override
    public AvaliacaoFisica get(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AvaliacaoFisica> getAll() {
        return avaliacaoFisicaRepository.findAll();
    }

    @Override
    public AvaliacaoFisica update(Long id, AvaliacaoFisicaUpdateForm updateForm) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub

    }

}
