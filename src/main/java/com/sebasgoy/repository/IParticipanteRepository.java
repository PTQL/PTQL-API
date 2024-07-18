package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;

import java.util.List;
import java.util.Optional;


@Repository
public interface IParticipanteRepository extends JpaRepository<Participante, Long>{

    Participante findByVoluntario_DniAndActividad(String dni, Actividad actividad);

    @Query("SELECT p FROM Participante p WHERE p.voluntario.id = :idVoluntario AND p.actividad.id = :idActividad")
    Optional<Participante> findByVoluntarioIdAndActividadIdOptional(@Param("idVoluntario") Long idVoluntario, @Param("idActividad") Long idActividad);

    @Query("SELECT DISTINCT p FROM Participante p WHERE p.voluntario.id = :idVoluntario AND p.actividad.id = :idActividad")
    Participante findByVoluntarioIdAndActividadId(@Param("idVoluntario") Long idVoluntario, @Param("idActividad") Long idActividad);

    @Query("SELECT p From Participante p WHERE p.actividad.id = :idActividad")
    List<Participante> findByIdActividad(@Param("idActividad") Long idActividad);

}
