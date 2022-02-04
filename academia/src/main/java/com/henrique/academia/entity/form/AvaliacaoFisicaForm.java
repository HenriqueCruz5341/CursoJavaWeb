package com.henrique.academia.entity.form;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoFisicaForm {

    @NotNull(message = "O campo alunoId é obrigatório")
    @Positive(message = "O campo alunoId deve ser um número positivo")
    private Long alunoId;

    @NotNull(message = "O campo peso é obrigatório")
    @Positive(message = "O campo peso deve ser um número positivo")
    private double peso;

    @NotNull(message = "O campo altura é obrigatório")
    @Positive(message = "O campo altura deve ser um número positivo")
    @DecimalMin(value = "100", message = "'{validatedValue}' deve ser maior que {value}")
    private double altura;
}
