package com.henrique.academia.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.henrique.academia.entity.Aluno;
import com.henrique.academia.entity.form.AlunoForm;
import com.henrique.academia.entity.form.AlunoUpdateForm;
import com.henrique.academia.entity.view.AvaliacaoFisicaView;
import com.henrique.academia.infra.criteria.GenericSpecification;
import com.henrique.academia.infra.criteria.SearchCriteria;
import com.henrique.academia.infra.criteria.SearchOperation;
import com.henrique.academia.infra.utils.JavaTimeUtils;
import com.henrique.academia.repository.AlunoRepository;
import com.henrique.academia.service.IAlunoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoServiceImpl implements IAlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public Aluno create(AlunoForm form) {
        Aluno aluno = new Aluno();
        aluno.setNome(form.getNome());
        aluno.setCpf(form.getCpf());
        aluno.setBairro(form.getBairro());
        aluno.setDataDeNascimento(form.getDataDeNascimento());

        return alunoRepository.save(aluno);
    }

    @Override
    public Optional<Aluno> get(Long id) {
        return alunoRepository.findById(id);
    }

    @Override
    public List<Aluno> getAll(String nome, String dataDeNascimento) {
        GenericSpecification<Aluno> genericSpecification = new GenericSpecification<>();

        if (nome != null)
            genericSpecification.add(new SearchCriteria("nome", nome, SearchOperation.EQUAL));
        if (dataDeNascimento != null) {
            LocalDate localDate = LocalDate.parse(dataDeNascimento, JavaTimeUtils.LOCAL_DATE_FORMATTER);
            genericSpecification.add(new SearchCriteria("dataDeNascimento", localDate, SearchOperation.EQUAL));
        }

        return alunoRepository.findAll(genericSpecification);
    }

    @Override
    public Aluno update(Long id, AlunoUpdateForm updateForm) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub

    }

    public List<AvaliacaoFisicaView> getAllAvaliacaoFisica(Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        if (!aluno.isPresent())
            throw new RuntimeException("Aluno não encontrado");

        return aluno.get().getAvaliacoes().stream()
                .map(a -> AvaliacaoFisicaView.convertAvaliacaoFisicaView(a.getId(), a.getPeso(), a.getAltura()))
                .collect(
                        java.util.stream.Collectors.toList());

    }

}
