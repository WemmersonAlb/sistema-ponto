package com.sistemaponto.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sistemaponto.demo.model.FuncionarioDTO;
import com.sistemaponto.demo.model.FuncionarioModel;
import com.sistemaponto.demo.repository.FuncionarioRepository;
import com.sistemaponto.demo.service.PlanilhaService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class FuncionarioController {
    
    private final FuncionarioRepository funcionarioRepository;

    private final PlanilhaService planilhaService;

    @GetMapping("/listarFuncionario")
    public String listarFuncionario(Model model){
        List<FuncionarioModel> funcionarios = funcionarioRepository.findAll();
        model.addAttribute("funcionarios", funcionarios);
        return "listarFuncionario";
    }

    @GetMapping("/cadastrarFuncionario")
    public String showCadastrarFuncionario(Model model){
        model.addAttribute("funcionario", new FuncionarioDTO());
        System.out.println("Carregando DTO");
        return "cadastrarFuncionario";
    }
    @PostMapping("/cadastrarFuncionario")
    public String cadastrarFuncionario(@Valid @ModelAttribute("funcionario") FuncionarioDTO funcionario, Model model, BindingResult result){
        if(result.hasErrors()){
            return "cadastrarFuncionario";
        }
        Optional<FuncionarioModel> funcionarioOptional = funcionarioRepository.findByMatricula(funcionario.getMatricula());
        System.out.println("Post etapa1");
        if(funcionarioOptional.isPresent()){
            String message = String.format("A matrícula %s já está em uso. Se deseja editar, vá para a página de edição de funcionários", funcionario.getMatricula());
            model.addAttribute("message", message);
            System.out.println(message);
            return "cadastrarFuncionario";
        }else{
            FuncionarioModel funcionarioModel = new FuncionarioModel();
            funcionarioModel.setNomeFuncionario(funcionario.getNome_funcionario());
            funcionarioModel.setMatricula(funcionario.getMatricula());
            funcionarioRepository.save(funcionarioModel);
            String message = String.format("%s, matrícula %s, foi salvo com sucesso.", funcionario.getNome_funcionario(), funcionario.getMatricula());
            model.addAttribute("message", message);
            System.out.println(message);
            return listarFuncionario(model);
        }
    }

    @GetMapping("/editarFuncionario/{matricula}")
    public String showEditarFuncionario(@PathVariable String matricula,  Model model){

        Optional<FuncionarioModel> funcionarOptional = funcionarioRepository.findByMatricula(matricula);

        if(funcionarOptional.isPresent()){
            FuncionarioModel funcionarioModel = funcionarOptional.get();
            FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
            funcionarioDTO.setNome_funcionario(funcionarioModel.getNomeFuncionario());
            funcionarioDTO.setMatricula(funcionarioModel.getMatricula());
            model.addAttribute("funcionario", funcionarioDTO);
            return "editarFuncionario";
        }else{
            model.addAttribute("erro", "Funcionário não encontrado.");
            return listarFuncionario(model);
        }
    }

    @PostMapping("/editarFuncionario")
    public String editarFuncionario(@Valid @ModelAttribute("funcionario") FuncionarioDTO funcionario,
                                    BindingResult result,
                                    Model model){
        if(result.hasErrors()){
            model.addAttribute("erro", "Erro inesperado");
            return listarFuncionario(model);
        }

        Optional<FuncionarioModel> funcionarioOptional = funcionarioRepository.findByMatricula(funcionario.getMatricula());

        if(funcionarioOptional.isPresent()){
            FuncionarioModel funcionarioModel = funcionarioOptional.get();
            funcionarioModel.setNomeFuncionario(funcionario.getNome_funcionario());
            funcionarioModel.setMatricula(funcionario.getMatricula());
            funcionarioRepository.save(funcionarioModel);
            String message = String.format("%s atualizado com sucesso.", funcionarioModel.getNomeFuncionario());
            model.addAttribute("message", message);
            return listarFuncionario(model);
        }else{
            model.addAttribute("erro", "Erro inesperado");
            return listarFuncionario(model);
        }
    }

    @GetMapping("/downloadPlanilha/{matricula}")
    public ResponseEntity<ByteArrayResource> downloadPlanilha(@PathVariable String matricula){
        Optional<FuncionarioModel> funcionarioOptional = funcionarioRepository.findByMatricula(matricula);
        
        FuncionarioModel funcionarioModel = new FuncionarioModel();
        if(funcionarioOptional.isPresent()){
            funcionarioModel = funcionarioOptional.get();
        }else{
            System.out.println("Erro, funcionário ausente");
        }

        Workbook workbook = planilhaService.criarPlanilha(funcionarioModel);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = outputStream.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(bytes);

        String titulo = String.format("attachment; filename=Frequência de %s.xlsx", funcionarioModel.getNomeFuncionario());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, titulo);

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
            
    }
}
