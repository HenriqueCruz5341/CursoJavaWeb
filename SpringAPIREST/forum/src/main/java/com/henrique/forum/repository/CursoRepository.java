package com.henrique.forum.repository;

import com.henrique.forum.modelo.Curso;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    public Curso findByNome(String nome);
}
