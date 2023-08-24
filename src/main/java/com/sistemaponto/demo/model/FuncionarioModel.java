package com.sistemaponto.demo.model;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="TBL_Funcionario")
public class FuncionarioModel {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID idFuncionario;

    @NotBlank
    private String nomeFuncionario;

    @NotBlank
    private String matricula;
}
