package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sebasgoy.dto.Actividad;

import java.util.List;


@Repository
public interface IActividadRepository extends JpaRepository<Actividad, Long>{
    
    List<Actividad> findByEstadoIsTrue();
    

 
}


