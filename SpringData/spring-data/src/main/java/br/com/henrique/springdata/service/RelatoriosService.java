package br.com.henrique.springdata.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import br.com.henrique.springdata.orm.Funcionario;
import br.com.henrique.springdata.orm.FuncionarioProjecao;
import br.com.henrique.springdata.repository.FuncionarioRepository;

public class RelatoriosService {
    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FuncionarioRepository funcionarioRepository;

    public RelatoriosService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner) {
        while (system) {
            System.out.println("Qual acao de cargo deseja executar");
            System.out.println("0 - Sair");
            System.out.println("1 - Busca funcionario nome");
            System.out.println("2 - Busca funcionario nome, data contratação e salario maior");
            System.out.println("3 - Busca funcionario data de contratacao");
            System.out.println("4 - Busca funcionario salario");

            int action = scanner.nextInt();

            switch (action) {
            case 1:
                buscaFuncionarioNome(scanner);
                break;
            case 2:
                buscaFuncionarioNomeSalarioMaiorData(scanner);
                break;
            case 3:
                buscaFuncionarioDataContratacao(scanner);
                break;
            case 4:
                pesquisaFuncionarioSalario();
                break;
            default:
                system = false;
                break;
            }
        }
    }

    private void buscaFuncionarioNome(Scanner scanner) {
        System.out.println("Digite o nome do funcionario");
        String nome = scanner.next();
        List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);
        funcionarios.forEach(System.out::println);
    }

    private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
        System.out.println("Digite o nome do funcionario");
        String nome = scanner.next();
        System.out.println("Digite o salario do funcionario");
        Double salario = scanner.nextDouble();
        System.out.println("Digite a data de contratação do funcionario");
        String data = scanner.next();
        LocalDate dataContratacao = LocalDate.parse(data, formatter);
        List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario,
                dataContratacao);
        funcionarios.forEach(System.out::println);
    }

    private void buscaFuncionarioDataContratacao(Scanner scanner) {
        System.out.println("Digite a data de contratação do funcionario");
        String data = scanner.next();
        LocalDate dataContratacao = LocalDate.parse(data, formatter);
        List<Funcionario> funcionarios = funcionarioRepository.findDataContratacaoMaior(dataContratacao);
        funcionarios.forEach(System.out::println);
    }

    private void pesquisaFuncionarioSalario() {
        List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
        list.forEach(f -> System.out.println(f.getId() + " - " + f.getNome() + " - " + f.getSalario()));
    }
}
