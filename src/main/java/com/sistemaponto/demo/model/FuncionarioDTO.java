package com.sistemaponto.demo.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FuncionarioDTO {
    
    @NotBlank
    private String nome_funcionario;

    @NotBlank
    private String matricula;
}
