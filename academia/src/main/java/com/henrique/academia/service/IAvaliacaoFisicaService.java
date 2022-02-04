package com.henrique.academia.service;

import java.util.List;

import com.henrique.academia.entity.AvaliacaoFisica;
import com.henrique.academia.entity.form.AvaliacaoFisicaForm;
import com.henrique.academia.entity.form.AvaliacaoFisicaUpdateForm;

public interface IAvaliacaoFisicaService {
    public AvaliacaoFisica create(AvaliacaoFisicaForm form);

    public AvaliacaoFisica get(Long id);

    public List<AvaliacaoFisica> getAll();

    public AvaliacaoFisica update(Long id, AvaliacaoFisicaUpdateForm updateForm);

    public void delete(Long id);
}
