package com.sebasgoy.Service;


import com.sebasgoy.Models.Actividad;
import com.sebasgoy.Models.Participante;
import com.sebasgoy.Repository.IParticipanteRepository;
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
    public List<Participante> getAll(){
        return iParticipanteRepository.findAll();
    }
    public void saveParticipante(Participante participante) {
        iParticipanteRepository.save(participante);
    }

}
