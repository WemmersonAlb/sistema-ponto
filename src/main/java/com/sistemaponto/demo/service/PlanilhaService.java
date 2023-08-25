package com.sistemaponto.demo.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sistemaponto.demo.model.BatidaModel;
import com.sistemaponto.demo.model.FuncionarioModel;
import com.sistemaponto.demo.repository.BatidaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlanilhaService {

    private final BatidaRepository batidaRepository;
    
    public Workbook criarPlanilha(FuncionarioModel funcionario){
        Workbook workbook = new XSSFWorkbook();

        String titulo = String.format("Frequência de %s", funcionario.getNomeFuncionario());
        Sheet sheet = workbook.createSheet(titulo);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"Data", "Entrada", "Saída"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        List<BatidaModel> batidas = findAllFuncionarioBymatricula(funcionario);

        DateTimeFormatter hoursFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateFormat= DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int rowNum = 1; // Inicia a partir da segunda linha
        for (BatidaModel batida : batidas) {
            Row row = sheet.createRow(rowNum++);

            String horaEntrada = batida.getHoraEntrada().format(hoursFormatter);
            String horaSaida = batida.getHoraSaida() != null ? batida.getHoraSaida().format(hoursFormatter):"";
            String dia = batida.getDia().format(dateFormat);

            row.createCell(0).setCellValue(dia);
            row.createCell(1).setCellValue(horaEntrada);
            row.createCell(2).setCellValue(horaSaida);
        }

        return workbook;

    }

    public List<BatidaModel> findAllFuncionarioBymatricula(FuncionarioModel funcionario){
        return batidaRepository.findAllByFuncionario(funcionario);
    }
}
