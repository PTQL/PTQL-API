package com.sebasgoy.Mapper;

import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.VoluntarioResponse;
import com.sebasgoy.constantes.ValoresPersonaRegex;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import static com.sebasgoy.util.Tools.*;

@Component
public class ExcelMapper {

    public static <T> T DevolverEntidadFromExcel(MultipartFile archivoExcel,Class<T> clase)  {
        if (clase.equals(VoluntarioResponse.class)) {
            System.out.println("devolviendo  Voluntario Response");
            return clase.cast(leerExcelOfVoluntarios(archivoExcel));
        } else {
            throw new IllegalArgumentException("Tipo de clase no admitido para mapear desde el archivo Excel.");
        }
    }


    private static VoluntarioResponse leerExcelOfVoluntarios(MultipartFile archivoExcel) {
        List<Voluntario> listaValidos = new ArrayList<>();
        List<Voluntario> listaInvalidos = new ArrayList<>();
        try {
            Iterator<Row> filas = getRowIterator(archivoExcel);
            filas.next(); // Salta la primera fila (encabezados)
            while (filas.hasNext()){
                Row fila = filas.next();
                /*Depende de la estructura del Excel
                Estructura de excel :
                // Marca temporal -> 0
                // CORREO         -> 1
                // DNI            -> 2
                // TELEFONO       -> 3
                // NOMBRE         -> 4
                // EDAD:          -> 5
                // Terminos y condiciones - */
                Voluntario voluntario = new Voluntario();
                voluntario.setEstado(true);
                voluntario.setParticipante(new ArrayList<>());
                voluntario.setCorreo(getStringFromCell(fila,1));
                voluntario.setDni(getStringFromCell( fila,2));
                voluntario.setTelefono(getStringFromCell(fila,3));
                voluntario.setNombre(getStringFromCell( fila,4));
                voluntario.setEdad(getStringFromCell( fila,5));

                if (areVoluntarioFieldsValid(voluntario)) {
                	System.out.println(voluntario);
                    if (ValoresPersonaRegex.isValidVoluntario(voluntario) ){
                        listaValidos.add(voluntario);
                    }else{
                        listaInvalidos.add(voluntario);
                    }
                }
            }
            System.out.println("Lectura correcta de Excel");
        } catch (IOException e) {
            System.out.println("Lectura Incorrecta de Excel" + e);
            throw new RuntimeException(e);
        }
        return VoluntarioResponse.builder()
                .fecha(LocalDateTime.now())
                .listVoluntarioInvalido(listaInvalidos)
                .listVoluntarioValido(listaValidos)
                .totalPersonas( listaValidos.size()+listaInvalidos.size())
                .build();
    }
}
