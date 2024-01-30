package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sebasgoy.dto.Modulo;
import com.sebasgoy.dto.Participante;

public interface IModuloRepository  extends JpaRepository<Modulo, Long>{

}
