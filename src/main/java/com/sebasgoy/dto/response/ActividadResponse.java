package com.sebasgoy.dto.response;


import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Voluntario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActividadResponse {

    public LocalDateTime fecha;
    public int totalActivadades;
    public List<Actividad> listActividadValido;
    public List<Actividad> listActividadInvalido;

}
