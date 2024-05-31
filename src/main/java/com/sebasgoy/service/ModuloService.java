package com.sebasgoy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sebasgoy.dto.Modulo;
import com.sebasgoy.repository.IModuloRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModuloService {
    private IModuloRepository iModuloRepository;
	public Long UltimoId() {
		List<Modulo> activos = findAll();
		    
	    if (activos.isEmpty()) {
	        return 1L;
	    }
	     
	    Modulo ultimoElemento = activos.get(activos.size() - 1);
	    
	    return ultimoElemento.getId() + 1;
	}
    
    public Modulo findById(Long id){
        return iModuloRepository.findById(id).orElse(new Modulo());
    }
    public Optional<Modulo> findByIdOptional(Long id){
        return iModuloRepository.findById(id);
    }

    public List<Modulo> findAll(){
    	return iModuloRepository.findAll();
    }
    
    public List<Modulo> findActivos(){
        return iModuloRepository.findByEstadoIsTrue();
    }
    public void saveModulo(Modulo modulo) {
        if (modulo.getId() == null ){
            modulo.setId(UltimoId());
        }
        iModuloRepository.save(modulo);
    }
    
    public void deleteModulo(Modulo modulo) {
    	
    	iModuloRepository.delete(modulo);
    }
}
