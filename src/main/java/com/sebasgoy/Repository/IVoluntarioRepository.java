package com.sebasgoy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 import com.sebasgoy.Models.Voluntario;


@Repository
public interface IVoluntarioRepository extends JpaRepository<Voluntario, Long>{

}
