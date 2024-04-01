package com.sebasgoy.dto.request;

import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.StatusVoluntarioModulo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlantillaModuloDto {
    private StatusVoluntarioModulo statusVoluntarioModulo;
    private String nombreModulo ;
    private String mesInicio;
    private String mesFin;
    private String fechaGeneralActividad;
}
