package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 import com.sebasgoy.dto.Voluntario;
import java.util.List;
import java.util.Optional;



@Repository
public interface IVoluntarioRepository extends JpaRepository<Voluntario, Long>{

	boolean existsByDni(String dni);
	Voluntario findByDni(String dni);
}
