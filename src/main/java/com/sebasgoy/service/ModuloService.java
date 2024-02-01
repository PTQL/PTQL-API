package com.sebasgoy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.repository.IModuloRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModuloService {
    private IModuloRepository iModuloRepository;
	public Long UltimoId() {
		List<Modulo> activos = getAll();
		    
	    if (activos.isEmpty()) {
	        return 1L;
	    }
	     
	    Modulo ultimoElemento = activos.get(activos.size() - 1);
	    
	    return ultimoElemento.getId() + 1;
	}
    
    public Modulo findById(Long id){
        return iModuloRepository.findById(id).orElse(new Modulo());
    }
    public List<Modulo> getAll(){
        return iModuloRepository.findAll();
    }
    public Modulo saveModulo(Modulo modulo) {
        return iModuloRepository.save(modulo);
    }
}
