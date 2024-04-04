package com.sebasgoy.Parser;

import com.sebasgoy.constantes.Mensajes;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.request.PlantillaActividadDto;
import com.sebasgoy.dto.request.PlantillaModuloDto;
import com.sebasgoy.dto.response.StatusVoluntarioModulo;
import com.sebasgoy.util.Tools;

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
    public static List<PlantillaActividadDto> listParticipanteToPlantillaDtoActividad(List<Voluntario> listVoluntarios , Actividad actividad) {
        List<PlantillaActividadDto> dtos = new ArrayList<>();
        String fechaActividad = PlantillaParser.parserFechaActividad(actividad.getFechaActividad());
        String fechaGeneralActividad = PlantillaParser.parserFechaGeneralActividad(actividad.getFechaActividad());
        String horasActividad = String.valueOf( actividad.obtenerDuracionActividad());
        String nombreActividad = actividad.getNombreActividad();
        String ubicacionActividad = actividad.getUbicacionActividad();
        try {
            for (Voluntario voluntario : listVoluntarios) {
                dtos.add(
                        PlantillaActividadDto.builder()
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
    public static List<PlantillaModuloDto> listStatusVoluntarioToPlantillaDtoModulo(List<StatusVoluntarioModulo> listVoluntarios , Modulo modulo) {

        List<PlantillaModuloDto> lstaPlantillas = new ArrayList<>();
        String fechaGeneralActividad = PlantillaParser.parserFechaGeneralActividad(Tools.ObtenerUltimoDateModulo(modulo));
        String nombreModulo = modulo.getNombre();
        String mesInicio = Tools.getMes(Tools.ObtenerPrimerDateModulo(modulo));
        String mesFin =Tools.getMes(Tools.ObtenerUltimoDateModulo(modulo));
        try {
            for (StatusVoluntarioModulo voluntario : listVoluntarios) {
                lstaPlantillas.add(
                        PlantillaModuloDto.builder()
                                .fechaGeneralActividad(fechaGeneralActividad)
                                .nombreModulo(nombreModulo)
                                .statusVoluntarioModulo(voluntario)
                                .mesInicio(mesInicio)
                                .mesFin(mesFin)
                                .build()
                );
            }
            System.out.println("Parseo de entidades a PlantillaDTO ok");
        }catch (Exception e){
            System.out.println(Mensajes.error("Error al convertir participante a plantillaDTo").concat(e.toString()));
        }

        return lstaPlantillas;
    }

    public static PlantillaModuloDto statusVoluntarioToPlantillaDtoModulo(StatusVoluntarioModulo statusVoluntario , Modulo modulo) {


        String fechaGeneralActividad = PlantillaParser.parserFechaGeneralActividad(Tools.ObtenerUltimoDateModulo(modulo));
        String nombreModulo = modulo.getNombre();
        String mesInicio = Tools.getMes(Tools.ObtenerPrimerDateModulo(modulo));
        String mesFin =Tools.getMes(Tools.ObtenerUltimoDateModulo(modulo));
        try {
            System.out.println("Parseo de entidades a PlantillaDTO ok");
            return PlantillaModuloDto.builder()
                                .fechaGeneralActividad(fechaGeneralActividad)
                                .nombreModulo(nombreModulo)
                                .statusVoluntarioModulo(statusVoluntario)
                                .mesInicio(mesInicio)
                                .mesFin(mesFin)
                                .build();

        }catch (Exception e){
            System.out.println(Mensajes.error("Error al convertir participante a plantillaDTo").concat(e.toString()));
            throw new RuntimeException("Error al convertir participante a plantillaDto");
        }

    }
    public static PlantillaActividadDto participanteToPlantillaDto(Voluntario voluntario , Actividad actividad) {

        String fechaActividad = PlantillaParser.parserFechaActividad(actividad.getFechaActividad());
        String fechaGeneralActividad = PlantillaParser.parserFechaGeneralActividad(actividad.getFechaActividad());
        String horasActividad = String.valueOf( actividad.obtenerDuracionActividad().toString());
        String nombreActividad = actividad.getNombreActividad();
        String ubicacionActividad = actividad.getUbicacionActividad();

        try {
            return PlantillaActividadDto.builder()
                    .fechaActividad(fechaActividad)
                    .fechaGeneralActividad(fechaGeneralActividad)
                    .horasActividad(horasActividad)
                    .nombreActividad(nombreActividad)
                    .voluntario(voluntario)
                    .ubicacionActividad(ubicacionActividad)
                    .build();
        }catch (Exception e){
            System.out.println(Mensajes.error("Error al convertir participante a plantillaDTo").concat(e.toString()));
            throw new RuntimeException("Error al convertir participante a plantillaDto");
        }

    }


}
