package com.henrique.forum.repository;

import java.util.List;

import com.henrique.forum.modelo.Topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByCursoNome(String nomeCurso);
}
