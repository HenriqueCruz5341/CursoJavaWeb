package com.henrique.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.henrique.forum.modelo.Topico;
import com.henrique.forum.repository.TopicoRepository;

import org.hibernate.validator.constraints.Length;

public class AtualizacaoTopicoForm {
    @NotNull
    @NotEmpty()
    @Length(min = 2)
    private String titulo;

    @NotNull
    @NotEmpty()
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getById(id);

        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);

        return topico;
    }
}
