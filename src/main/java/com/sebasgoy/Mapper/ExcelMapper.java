package com.sebasgoy.Mapper;

import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.ExcelResponse;

import Constantes.ValoresPersonaRegex;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RecursiveTask;

@Component
public class ExcelMapper {

    public static ExcelResponse LeerExcel(MultipartFile archivoExcel,Long id){
    	DataFormatter dataFormatter = new DataFormatter();
        List<Voluntario> listaValidos = new ArrayList<>();
        List<Voluntario> listaInvalidos = new ArrayList<>();
        try {
            Workbook libro;

        	if (archivoExcel.getOriginalFilename().endsWith(".xlsx")) {
                libro = new XSSFWorkbook(archivoExcel.getInputStream());
            } else if (archivoExcel.getOriginalFilename().endsWith(".xls")) {
                libro = new HSSFWorkbook(archivoExcel.getInputStream());
            } else {
                throw new IllegalArgumentException("El archivo no tiene una extensión de Excel válida.");
            }            Sheet hoja =  libro.getSheetAt(0);
            Iterator<Row>   filas = hoja.iterator();

            filas.next(); // Salta la primera fila (encabezados)

            while (filas.hasNext()){
                Row fila = filas.next();
                /*Depende de la estructura del Excel*/
                Voluntario voluntario = new Voluntario();
                voluntario.setEstado(true);
                voluntario.setParticipante(new ArrayList<>());
				voluntario.setNombre(dataFormatter.formatCellValue(fila.getCell(0)));
				voluntario.setApellido(dataFormatter.formatCellValue(fila.getCell(1)));
				voluntario.setEdad(dataFormatter.formatCellValue(fila.getCell(2)));
				voluntario.setDni(dataFormatter.formatCellValue(fila.getCell(3)));

				
				
				if (areVoluntarioFieldsValid(voluntario)) {
	                if (ValoresPersonaRegex.isValidVoluntario(voluntario) ){
	                    listaValidos.add(voluntario);
	                }else{
	                	listaInvalidos.add(voluntario);
	                }
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
                .idActividad(id)
                .totalPersonas( listaValidos.size()+listaInvalidos.size())
                .build() ;
    }
    
    private static boolean areVoluntarioFieldsValid(Voluntario voluntario) {
        return isStringNotBlank(voluntario.getNombre()) &&
               isStringNotBlank(voluntario.getApellido()) &&
               isStringNotBlank(voluntario.getEdad()) &&
               isStringNotBlank(voluntario.getDni());
    }
    
    private static boolean isStringNotBlank(String value) {
        return value != null && !value.isEmpty();
    }
    
 

    


}
