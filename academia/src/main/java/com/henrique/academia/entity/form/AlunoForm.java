package com.henrique.academia.entity.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoForm {

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 50, message = "'${validatedValue}' deve ter entre {min} e {max} caracteres")
    private String nome;

    @NotBlank(message = "O campo nome é obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 3, max = 50, message = "'${validatedValue}' deve ter entre {min} e {max} caracteres")
    private String bairro;

    @NotNull(message = "O campo nome é obrigatório")
    @Past(message = "A data de nascimento deve ser uma data anterior a hoje")
    private LocalDate dataDeNascimento;
}
