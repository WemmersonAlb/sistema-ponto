package com.sistemaponto.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sistemaponto.demo.model.FuncionarioModel;
import com.sistemaponto.demo.repository.FuncionarioRepository;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class FuncionarioController {
    
    private final FuncionarioRepository funcionarioRepository;

    @GetMapping("/listarFuncionario")
    public String listarFuncionario(Model model){
        List<FuncionarioModel> funcionarios = funcionarioRepository.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "listarFuncionario";
    }
}
