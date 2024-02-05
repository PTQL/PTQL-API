package com.sebasgoy.service;


import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.repository.IVoluntarioRepository;
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
    
    
    public Voluntario findByDni(String dni){
        return  iVoluntarioRepository.findByDni(dni);
    }
    public boolean existsByDni(String dni){
        return  iVoluntarioRepository.existsByDni(dni);
    }
    
    public List<Voluntario> getAll(){
        return iVoluntarioRepository.findAll();
    }
    public Voluntario saveVoluntario(Voluntario voluntario) {
       return iVoluntarioRepository.save(voluntario);
       
       
    }

}
