package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.UbicacionConstancias;

import java.util.List;
import java.util.Optional;


@Repository
public interface IUbicacionRepository extends JpaRepository<UbicacionConstancias, Long>{
    @Override
    Optional<UbicacionConstancias> findById(Long aLong);
 
}


