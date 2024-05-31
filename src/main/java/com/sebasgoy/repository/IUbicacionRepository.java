package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sebasgoy.dto.UbicacionConstancias;

@Repository
public interface IUbicacionRepository extends JpaRepository<UbicacionConstancias, Long>{

}


