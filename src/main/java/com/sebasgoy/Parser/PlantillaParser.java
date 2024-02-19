package com.sebasgoy.Parser;

import com.sebasgoy.constantes.Mensajes;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.request.PlantillaDto;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlantillaParser {

    public static String parserFechaActividad(Date fechaActividad ){
        SimpleDateFormat formatoSalida = new SimpleDateFormat("EEEE d 'de' MMMM", new Locale("es", "ES"));
        return formatoSalida.format(fechaActividad);
    }

    public static String parserFechaGeneralActividad(Date fechaActividad) {
        SimpleDateFormat formatoSalida = new SimpleDateFormat("MMMM 'de' yyyy", new Locale("es", "ES"));
        return "Lima, " + formatoSalida.format(fechaActividad);

    }
    public static List<PlantillaDto> listParticipanteToPlantillaDto(List<Participante> libresFromListParticipante , Actividad actividad) {

        List<PlantillaDto> dtos = new ArrayList<>();

        String fechaActividad = PlantillaParser.parserFechaActividad(actividad.getFechaActividad());
        String fechaGeneralActividad = PlantillaParser.parserFechaGeneralActividad(actividad.getFechaActividad());
        String horasActividad = String.valueOf( actividad.obtenerDuracionActividad());
        String nombreActividad = actividad.getNombreActividad();
        String ubicacionActividad = actividad.getUbicacionActividad();
        try {
            for (Participante participante : libresFromListParticipante) {

                dtos.add(
                        PlantillaDto.builder()
                                .fechaActividad(fechaActividad)
                                .fechaGeneralActividad(fechaGeneralActividad)
                                .horasActividad(horasActividad)
                                .nombreActividad(nombreActividad)
                                .nombreVoluntario(participante.getVoluntario().getNombre())
                                .ubicacionActividad(ubicacionActividad)
                                .build()
                );

            }

            System.out.println("Parseo de entidades a PlantillaDTO ok");


        }catch (Exception e){
            System.out.println(Mensajes.error("Error al convertir participante a plantillaDTo").concat(e.toString()));
        }


        return dtos;
    }

}
