package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sebasgoy.dto.TipoParticipacion;

import java.util.Optional;


@Repository
public interface ITipoParticipacionRepository extends JpaRepository<TipoParticipacion, Long>{
    
	Optional<TipoParticipacion> findByDescripcionParticipacion(String descripcionParticipacion);
}


