package com.sebasgoy.Mapper;

import Regex.ValoresPersonaRegex;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.ExcelResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@Component
public class ExcelMapper {

    public static ExcelResponse LeerExcel(FileInputStream archivoExcel){

         List<Voluntario> listaValidos = new ArrayList<>();
        List<Voluntario> listaInvalidos = new ArrayList<>();
        try {
            Workbook libro = WorkbookFactory.create(archivoExcel);
            Sheet hoja =  libro.getSheetAt(0);
            Iterator<Row>   filas = hoja.iterator();

            filas.next(); // Salta la primera fila (encabezados)

            while (filas.hasNext()){
                Row fila = filas.next();
                /*Depende de la estructura del Excel*/
                Voluntario voluntario = new Voluntario();
                voluntario.setNombre(fila.getCell(0).getStringCellValue());
                voluntario.setApellido(fila.getCell(1).getStringCellValue());
                voluntario.setEdad(fila.getCell(2).getStringCellValue());
                voluntario.setDni(fila.getCell(3).getStringCellValue());

                if (ValoresPersonaRegex.isValidVoluntario(voluntario) ){
                    listaValidos.add(voluntario);
                }else {
                    listaInvalidos.add(voluntario);
                }
            }
        //TODO MAPEAR EXCEPCIONES
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ExcelResponse.builder()
                .fecha(LocalDateTime.now())
                .listVoluntarioInvalido(listaInvalidos)
                .listVoluntarioValido(listaValidos)
                .totalPersonas( listaInvalidos.size()+listaInvalidos.size())
                .build() ;
    }


}
