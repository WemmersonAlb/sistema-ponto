package com.sistemaponto.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="TBL_Batida_Ponto")
@Data
public class BatidaModel {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID idBatidaPonto;
    
    @NotNull
    private LocalTime horaEntrada;

    private LocalTime horaSaida;

    @NotNull
    private LocalDate dia;

    @OneToOne
    @NotNull
    private FuncionarioModel funcionario;
}
