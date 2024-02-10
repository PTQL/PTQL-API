package com.sebasgoy.service;


import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.repository.IParticipanteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ParticipanteService {
	
    private IParticipanteRepository iParticipanteRepository;
    public Participante findById(Long id){
        return  iParticipanteRepository.findById(id).orElse(new Participante());
    }
    
    public Participante findByDniAndActividad(String dni,Actividad actividad){
        return iParticipanteRepository.findByVoluntario_DniAndActividad(dni, actividad);
    }



    public List<Participante> getAll(){
        return iParticipanteRepository.findAll();
    }
    public Participante saveParticipante(Participante participante) {
        return iParticipanteRepository.save(participante);
    }

    public boolean existeParticipanteParaVoluntarioYActividad(Long idVoluntario, Long idActividad) {
        return iParticipanteRepository.findByVoluntarioIdAndActividadId(idVoluntario, idActividad).isPresent();
    }

    
    public void deleteParticipante(Participante participante) {
    	
    	iParticipanteRepository.delete(participante);
    }



    
    public void deleteParticipanteById(Long id) {
    	iParticipanteRepository.deleteById(id);
    }

}
