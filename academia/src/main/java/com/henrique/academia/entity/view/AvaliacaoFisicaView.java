package com.henrique.academia.entity.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoFisicaView {
    private Long alunoId;
    private double peso;
    private double altura;

    public static AvaliacaoFisicaView convertAvaliacaoFisicaView(Long alunoId, double peso, double altura) {
        return new AvaliacaoFisicaView(alunoId, peso, altura);
    }
}
