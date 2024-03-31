package com.sebasgoy.Parser;

import com.sebasgoy.constantes.Mensajes;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.request.PlantillaDto;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
    public static List<PlantillaDto> listParticipanteToPlantillaDtoActividad(List<Voluntario> listVoluntarios , Actividad actividad) {

        List<PlantillaDto> dtos = new ArrayList<>();

        String fechaActividad = PlantillaParser.parserFechaActividad(actividad.getFechaActividad());
        String fechaGeneralActividad = PlantillaParser.parserFechaGeneralActividad(actividad.getFechaActividad());
        String horasActividad = String.valueOf( actividad.obtenerDuracionActividad());
        String nombreActividad = actividad.getNombreActividad();
        String ubicacionActividad = actividad.getUbicacionActividad();
        try {
            for (Voluntario voluntario : listVoluntarios) {

                dtos.add(
                        PlantillaDto.builder()
                                .fechaActividad(fechaActividad)
                                .fechaGeneralActividad(fechaGeneralActividad)
                                .horasActividad(horasActividad)
                                .nombreActividad(nombreActividad)
                                .voluntario(voluntario)
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
    public static List<PlantillaDto> listParticipanteToPlantillaDtoModulo(List<Voluntario> listVoluntarios , Modulo actividad,Long horas) {

        List<PlantillaDto> dtos = new ArrayList<>();
        return null;
    }
    public static PlantillaDto participanteToPlantillaDto(Voluntario voluntario , Actividad actividad) {

        String fechaActividad = PlantillaParser.parserFechaActividad(actividad.getFechaActividad());
        String fechaGeneralActividad = PlantillaParser.parserFechaGeneralActividad(actividad.getFechaActividad());
        String horasActividad = String.valueOf( actividad.obtenerDuracionActividad().toString());
        String nombreActividad = actividad.getNombreActividad();
        String ubicacionActividad = actividad.getUbicacionActividad();

        try {

            return PlantillaDto.builder()
                    .fechaActividad(fechaActividad)
                    .fechaGeneralActividad(fechaGeneralActividad)
                    .horasActividad(horasActividad)
                    .nombreActividad(nombreActividad)
                    .voluntario(voluntario)
                    .ubicacionActividad(ubicacionActividad)
                    .build();

        }catch (Exception e){
            System.out.println(Mensajes.error("Error al convertir participante a plantillaDTo").concat(e.toString()));
            throw  new RuntimeException("Error al convertir participante a plantillaDto");
        }

    }


}
