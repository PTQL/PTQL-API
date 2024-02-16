package com.sebasgoy.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlantillaDto {
    private String nombreVoluntario;
    private String nombreActividad;
    private String fechaActividad;
    private String ubicacionActividad;
    private String horasActividad;
    private String fechaGeneralActividad;
}
