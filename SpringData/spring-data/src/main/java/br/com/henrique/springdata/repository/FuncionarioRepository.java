package br.com.henrique.springdata.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.henrique.springdata.orm.Funcionario;
import br.com.henrique.springdata.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository
        extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario> {
    List<Funcionario> findByNome(String nome);

    // List<Funcionario> findByNameAndSalarioGreaterThanAndDataContratacao(String
    // nome, Double salario,
    // LocalDate dataContratacao);

    // List<Funcionario> findByUnidadeTrabalhos_Descricao(String descricao);

    @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome "
            + "AND f.salario >= :salario AND f.dataContratacao = :dataContratacao")
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate dataContratacao);

    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :dataContratacao", nativeQuery = true)
    List<Funcionario> findDataContratacaoMaior(LocalDate dataContratacao);

    @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();
}
