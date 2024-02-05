package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
 


@Repository
public interface IParticipanteRepository extends JpaRepository<Participante, Long>{

    Participante findByVoluntario_DniAndActividad(String dni, Actividad actividad);

}
