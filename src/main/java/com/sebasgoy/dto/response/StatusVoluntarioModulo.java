package com.sebasgoy.dto.response;

import com.sebasgoy.dto.Voluntario;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class StatusVoluntarioModulo {

    private  Voluntario  voluntario;
    private Long horas;

    @Override
    public String toString() {
        return "StatusVoluntarioModulo{" +
                "voluntario=" + voluntario +
                ", horas=" + horas +
                '}';
    }
}
