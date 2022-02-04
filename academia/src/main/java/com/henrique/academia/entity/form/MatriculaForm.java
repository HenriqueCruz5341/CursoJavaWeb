package com.henrique.academia.entity.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaForm {

    @NotNull(message = "O campo alunoId é obrigatório")
    @Positive(message = "O campo alunoId deve ser um número positivo")
    private Long alunoId;
}
