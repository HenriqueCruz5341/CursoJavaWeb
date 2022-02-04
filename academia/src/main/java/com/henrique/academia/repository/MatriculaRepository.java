package com.henrique.academia.repository;

import java.util.List;

import com.henrique.academia.entity.Matricula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    @Query("SELECT m FROM Matricula m WHERE m.aluno.bairro = :bairro")
    List<Matricula> findByAlunoBairro(String bairro);
}
