package com.sistemaponto.demo.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BatidaDTO {
    @NotBlank
    private String matricula;
}
