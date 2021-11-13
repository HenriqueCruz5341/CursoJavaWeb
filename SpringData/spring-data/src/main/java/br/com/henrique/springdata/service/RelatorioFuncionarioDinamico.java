package br.com.henrique.springdata.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

import br.com.henrique.springdata.orm.Funcionario;
import br.com.henrique.springdata.repository.FuncionarioRepository;
import br.com.henrique.springdata.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamico {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FuncionarioRepository funcionarioRepository;

    public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        System.out.println("Digite um nome");
        String nome = scanner.nextLine();
        System.out.println("Digite um cpf");
        String cpf = scanner.nextLine();
        System.out.println("Digite um salario");
        Double salario = scanner.nextDouble();
        System.out.println("Digite a data de contratação");
        String data = scanner.nextLine();
        LocalDate dataContratacao;

        if (nome.equalsIgnoreCase("NULL"))
            nome = null;
        if (cpf.equalsIgnoreCase("NULL"))
            cpf = null;
        if (salario == 0)
            salario = null;
        if (data.equalsIgnoreCase("NULL"))
            dataContratacao = null;
        else
            dataContratacao = LocalDate.parse(data, formatter);

        List<Funcionario> funcionarios = this.funcionarioRepository.findAll(Specification.where(SpecificationFuncionario
                .nome(nome).or(SpecificationFuncionario.cpf(cpf)).or(SpecificationFuncionario.salario(salario))
                .or(SpecificationFuncionario.dataContratacao(dataContratacao))));

        funcionarios.forEach(System.out::println);
    }
}
