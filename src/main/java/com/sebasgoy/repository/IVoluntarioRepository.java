package com.sebasgoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
 import com.sebasgoy.dto.Voluntario;
import java.util.List;


@Repository
public interface IVoluntarioRepository extends JpaRepository<Voluntario, Long>{
	@Query("SELECT DISTINCT p.voluntario FROM Participante p JOIN p.actividad a WHERE a.modulo.id = :idModulo AND p.tipoParticipacion.id = 1")
	List<Voluntario> findVoluntarioByModuloId(@Param("idModulo") Long idModulo);
	boolean existsByDni(String dni);
	Voluntario findByDni(String dni);
}
