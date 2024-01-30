package com.sebasgoy.dto.response;


import com.sebasgoy.dto.Voluntario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelResponse {

    public LocalDateTime fecha;
    public int totalPersonas;
    public List<Voluntario> listVoluntarioValido;
    public List<Voluntario> listVoluntarioInvalido;

}
