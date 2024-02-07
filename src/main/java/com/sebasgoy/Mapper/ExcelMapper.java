package com.sebasgoy.Mapper;

import com.sebasgoy.constantes.ValoresActividadRegex;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.ActividadResponse;
import com.sebasgoy.dto.response.VoluntarioResponse;

import com.sebasgoy.constantes.ValoresPersonaRegex;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ExcelMapper {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");




    public static <T> T DevolverEntidadFromExcel(MultipartFile archivoExcel,Class<T> clase)  {
        if (clase.equals(VoluntarioResponse.class)) {
            return clase.cast(leerExcelOfVoluntarios(archivoExcel));
        } else if (clase.equals(ActividadResponse.class)) {
            return clase.cast(leerExcelOfActividades(archivoExcel));
        } else {
            throw new IllegalArgumentException("Tipo de clase no admitido para mapear desde el archivo Excel.");
        }
    }

    private static ActividadResponse leerExcelOfActividades(MultipartFile archivoExcel) {

        List<Actividad> listaValidos = new ArrayList<>();
        List<Actividad> listaInvalidos = new ArrayList<>();
        try {
            Iterator<Row> filas = getRowIterator(archivoExcel);
            filas.next(); // Salta la primera fila (encabezados)

            while (filas.hasNext()){
                Row fila = filas.next();
                /*Depende de la estructura del Excel*/
                Actividad actividad = Actividad.builder()
                        .nombreActividad(getStringFromCell(fila,0))
                        .ubicacionActividad(getStringFromCell(fila,1))
                        .fechaActividad(getDateFromCell(fila,2))
                        .horaInicio(getLocalTimeFromCell(fila,3))
                        .horaFin(getLocalTimeFromCell(fila,4))
                        .build();


                if (areActividadFieldsValid(actividad)) {
                    if (ValoresActividadRegex.isValidActividad(actividad) ){
                        listaValidos.add(actividad);
                    }else{
                        listaInvalidos.add(actividad);
                    }
                }
            }
            //TODO MAPEAR EXCEPCIONES
        } catch (IOException|ParseException e) {
            throw new RuntimeException(e);
        }
        return ActividadResponse.builder()
                .fecha(LocalDateTime.now())
                .listActividadValido(listaInvalidos)
                .listActividadInvalido(listaValidos)
                .totalActivadades( listaValidos.size()+listaInvalidos.size())
                .build() ;


    }

    private static String getStringFromCell(Row fila,int ubicacion) {
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(fila.getCell(ubicacion));
    }
    private static LocalTime getLocalTimeFromCell(Row fila, int ubicacion) {
        DataFormatter dataFormatter = new DataFormatter();
        String timeString = dataFormatter.formatCellValue(fila.getCell(ubicacion));

        // Parse the time string to a LocalTime object
        return LocalTime.parse(timeString, timeFormatter);
    }
    private static Date getDateFromCell(Row fila, int ubicacion) throws ParseException {
        DataFormatter dataFormatter = new DataFormatter();
        Cell cell = fila.getCell(ubicacion);
        if (cell == null) {
            return null; // or throw an exception if necessary
        }
        String dateString = dataFormatter.formatCellValue(cell);

        // No need to recreate the SimpleDateFormat instance, reuse it
        java.util.Date fecha = dateFormat.parse(dateString);
        return new Date(fecha.getTime());
    }


    private static VoluntarioResponse leerExcelOfVoluntarios(MultipartFile archivoExcel) {
        List<Voluntario> listaValidos = new ArrayList<>();
        List<Voluntario> listaInvalidos = new ArrayList<>();
        try {
            Iterator<Row> filas = getRowIterator(archivoExcel);
            filas.next(); // Salta la primera fila (encabezados)

            while (filas.hasNext()){
                Row fila = filas.next();
                /*Depende de la estructura del Excel*/
                Voluntario voluntario = new Voluntario();
                voluntario.setEstado(true);
                voluntario.setParticipante(new ArrayList<>());
                voluntario.setNombre(getStringFromCell( fila,0));
                voluntario.setApellido(getStringFromCell( fila,1));
                voluntario.setEdad(getStringFromCell( fila,2));
                voluntario.setDni(getStringFromCell( fila,3));

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
        return VoluntarioResponse.builder()
                .fecha(LocalDateTime.now())
                .listVoluntarioInvalido(listaInvalidos)
                .listVoluntarioValido(listaValidos)
                .totalPersonas( listaValidos.size()+listaInvalidos.size())
                .build() ;
    }

    private static Iterator<Row> getRowIterator(MultipartFile archivoExcel) throws IOException {
        Workbook libro;
        if (Objects.requireNonNull(archivoExcel.getOriginalFilename()).endsWith(".xlsx")) {
            libro = new XSSFWorkbook(archivoExcel.getInputStream());
        } else if (archivoExcel.getOriginalFilename().endsWith(".xls")) {
            libro = new HSSFWorkbook(archivoExcel.getInputStream());
        } else {
            throw new IllegalArgumentException("El archivo no tiene una extensión de Excel válida.");
        }
        return libro.getSheetAt(0).iterator();
    }

    private static boolean areVoluntarioFieldsValid(Voluntario voluntario) {
        return isStringNotBlank(voluntario.getNombre()) &&
               isStringNotBlank(voluntario.getApellido()) &&
               isStringNotBlank(voluntario.getEdad()) &&
               isStringNotBlank(voluntario.getDni());
    }

    private static boolean areActividadFieldsValid(Actividad actividad) {
        return isStringNotBlank(actividad.getNombreActividad()) &&
                isStringNotBlank(actividad.getUbicacionActividad()) &&
                isStringNotBlank(String.valueOf(actividad.getFechaActividad())) &&
                isStringNotBlank(String.valueOf(actividad.getHoraFin())) &&
                isStringNotBlank(String.valueOf(actividad.getHoraInicio()));
    }
    
    private static boolean isStringNotBlank(String value) {
        return value != null && !value.isEmpty();
    }
    
 

    


}
