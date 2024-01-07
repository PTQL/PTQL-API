package com.sebasgoy.Service;


import com.sebasgoy.Models.Participante;
import com.sebasgoy.Models.Voluntario;
import com.sebasgoy.Repository.IParticipanteRepository;
import com.sebasgoy.Repository.IVoluntarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoluntarioService {

    private IVoluntarioRepository iVoluntarioRepository;

    public Voluntario findById(Long id){
        return  iVoluntarioRepository.findById(id).orElse(new Voluntario());
    }
    public List<Voluntario> getAll(){
        return iVoluntarioRepository.findAll();
    }
    public void saveVoluntario(Voluntario voluntario) {
        iVoluntarioRepository.save(voluntario);
    }

}
