package com.henrique.academia.infra.errors;

public class AlunoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlunoNotFoundException() {
        super("Aluno não encontrado");
    }

}
