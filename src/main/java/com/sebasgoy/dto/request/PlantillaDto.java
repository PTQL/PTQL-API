package com.sebasgoy.dto.request;

import com.sebasgoy.dto.Voluntario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlantillaDto {
    private Voluntario voluntario;
    private String nombreActividad;
    private String fechaActividad;
    private String ubicacionActividad;
    private String horasActividad;
    private String fechaGeneralActividad;
}
