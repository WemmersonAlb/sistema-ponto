package com.sistemaponto.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sistemaponto.demo.model.BatidaDTO;
import com.sistemaponto.demo.model.BatidaModel;
import com.sistemaponto.demo.model.FuncionarioModel;
import com.sistemaponto.demo.repository.BatidaRepository;
import com.sistemaponto.demo.repository.FuncionarioRepository;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class BatidaController {
    
    private final BatidaRepository batidaRepository;
    private final FuncionarioRepository funcionarioRepository;

    @GetMapping("/baterPonto")
    public String showBaterPonto(Model model){
        model.addAttribute("batida", new BatidaDTO());
        return "baterPonto";
    }
    @PostMapping("/baterPonto")
    public String baterPonto(@Valid @ModelAttribute("batida") BatidaDTO batida, Model model, BindingResult result){
        if(result.hasErrors()){
            return "baterPonto";
        }

        Optional<FuncionarioModel> funcionarioOptional = funcionarioRepository.findByMatricula(batida.getMatricula());
        LocalDate date = LocalDate.now();
        LocalTime hora = LocalTime.now();

        LocalTime inicioExpediente = LocalTime.of(7, 19);

        if(hora.isBefore(inicioExpediente)){
            model.addAttribute("erro", "Não é possível bater o ponto antes das 07h20min.");
            return "baterPonto";
        }
        
        if (funcionarioOptional.isPresent()) {
            Optional<BatidaModel> batidaOptional = batidaRepository.findByFuncionarioAndDia(funcionarioOptional.get(), date);
            if(batidaOptional.isPresent()){//Caso tenha registro de entrada
                BatidaModel batidaModel = batidaOptional.get();
                batidaModel.setHoraSaida(hora);
                String mensagem = String.format("Saída registrada de %s", funcionarioOptional.get().getNomeFuncionario());
                model.addAttribute("sucesso", mensagem);
                batidaRepository.save(batidaModel);
            }else{//Caso não tenha registro de entrada
                String mensagem = String.format("Entrada registrada de %s", funcionarioOptional.get().getNomeFuncionario());
                BatidaModel batidaModel = new BatidaModel();
                batidaModel.setDia(date);
                batidaModel.setHoraEntrada(hora);
                batidaModel.setFuncionario(funcionarioOptional.get());
                model.addAttribute("sucesso", mensagem);
                batidaRepository.save(batidaModel);
            }
            return "baterPonto";
        } else {
            model.addAttribute("erro", "Funcionário não cadastrado");
            return "baterPonto";
        }
    }
}
