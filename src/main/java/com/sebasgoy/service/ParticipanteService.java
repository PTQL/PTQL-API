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
import java.util.stream.Collectors;

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
    
    public void saveVoluntariosToActividad(List<Long> ListIdVoluntarios, Long idActividad,Long idModalidad) {
        System.out.println("Guardando lista de voluntarios en una actividad");
        for (Long idVoluntario : ListIdVoluntarios) {
			iParticipanteRepository.save(
					Participante.builder()
					.idActividad(idActividad)
					.idVoluntario(idVoluntario)
					.isParticipant(false)
					.idTipoParticipacion(idModalidad)
					.build()
			);
		}
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

    public List<Participante> findParticipantesFromActividad(Long idActividad){
        return iParticipanteRepository.findByIdActividad(idActividad);

    }

    public List<Participante> getLibresFromListParticipante(Long idActividad){
        List<Participante> listParticipantes = iParticipanteRepository.findByIdActividad(idActividad);
        return listParticipantes.stream().filter( participante -> (participante.getIdTipoParticipacion() == Modalidades.ID_LIBRE)&& (participante.getIsParticipant() == Boolean.TRUE)).collect(Collectors.toList());

    }


    public void deleteListOfParticipante(List<Participante> lstaParticipantesFromActividad) {
        System.out.println("Eliminando lista de participantes de una actividad");
        for (Participante participante: lstaParticipantesFromActividad) {
            iParticipanteRepository.delete(participante);
        }


    }
}
