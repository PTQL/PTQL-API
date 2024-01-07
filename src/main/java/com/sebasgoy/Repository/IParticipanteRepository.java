package com.sebasgoy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sebasgoy.Models.Participante;
 


@Repository
public interface IParticipanteRepository extends JpaRepository<Participante, Long>{

}
