package com.sebasgoy.service;

import java.util.List;

import com.sebasgoy.dto.Modulo;
import com.sebasgoy.repository.IModuloRepository;

 
public class ModuloService {
    private IModuloRepository iModuloRepository;
    public Modulo findById(Long id){
        return iModuloRepository.findById(id).orElse(new Modulo());
    }
    public List<Modulo> getAll(){
        return iModuloRepository.findAll();
    }
    public Modulo saveParticipante(Modulo modulo) {
        return iModuloRepository.save(modulo);
    }
}
