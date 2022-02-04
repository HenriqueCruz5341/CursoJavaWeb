package com.henrique.academia.repository;

import java.time.LocalDate;
import java.util.List;

import com.henrique.academia.entity.Aluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {
    List<Aluno> findByDataDeNascimento(LocalDate dataDeNascimento);
}
