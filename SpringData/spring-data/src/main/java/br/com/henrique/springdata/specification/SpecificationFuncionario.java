package br.com.henrique.springdata.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import br.com.henrique.springdata.orm.Funcionario;

public class SpecificationFuncionario {
    public static Specification<Funcionario> nome(String nome) {
        return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
    }

    public static Specification<Funcionario> cpf(String cpf) {
        return (root, query, builder) -> builder.equal(root.get("cpf"), cpf);
    }

    public static Specification<Funcionario> salario(Double salario) {
        return (root, query, builder) -> builder.greaterThan(root.get("salario"), salario);
    }

    public static Specification<Funcionario> dataContratacao(LocalDate dataContratacao) {
        return (root, query, builder) -> builder.greaterThan(root.get("dataContratacao"), dataContratacao);
    }
}
