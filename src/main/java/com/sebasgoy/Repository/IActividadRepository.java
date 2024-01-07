package com.sebasgoy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sebasgoy.Models.Actividad;

import java.util.Optional;


@Repository
public interface IActividadRepository extends JpaRepository<Actividad, Long>{
    @Override
    Optional<Actividad> findById(Long aLong);
}
