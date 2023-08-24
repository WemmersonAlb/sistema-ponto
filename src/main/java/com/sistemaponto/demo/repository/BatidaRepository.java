package com.sistemaponto.demo.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemaponto.demo.model.BatidaModel;
import com.sistemaponto.demo.model.FuncionarioModel;

public interface BatidaRepository extends JpaRepository<BatidaModel, UUID>{
    Optional<BatidaModel> findByFuncionarioAndDia(FuncionarioModel funcionario, LocalDate dia);
}
