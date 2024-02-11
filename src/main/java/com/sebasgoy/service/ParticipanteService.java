package com.sebasgoy.service;


import com.sebasgoy.constantes.Modalidades;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.repository.IParticipanteRepository;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void saveParticipante(Participante participante) {
        iParticipanteRepository.save(participante);
    }

    public boolean existeParticipanteParaVoluntarioYActividad(Long idVoluntario, Long idActividad) {
        return iParticipanteRepository.findByVoluntarioIdAndActividadId(idVoluntario, idActividad).isPresent();
    }
    public Participante findParticipanteParaVoluntarioYActividad(Long idVoluntario, Long idActividad) {
        return iParticipanteRepository.findByVoluntarioIdAndActividadId(idVoluntario, idActividad).orElse(null);
    }
    public void deleteParticipante(Participante participante) {
    	iParticipanteRepository.delete(participante);
    }
    public void deleteParticipanteById(Long id) {
    	iParticipanteRepository.deleteById(id);
    }




}
