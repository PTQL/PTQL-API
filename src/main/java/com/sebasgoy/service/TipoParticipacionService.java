package com.sebasgoy.service;

import java.util.List;

import com.sebasgoy.repository.ITipoParticipacionRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.sebasgoy.dto.TipoParticipacion;


/*En un service va la logica de negocio*/
@Service
@AllArgsConstructor
public class TipoParticipacionService {

	private final ITipoParticipacionRepository iTipoParticipacionRepository;

	public Long UltimoId() {
		List<TipoParticipacion> activos = findActivos();
		    
	    if (activos.isEmpty()) {
	        return 1L;
	    }
	    TipoParticipacion ultimoElemento = activos.get(activos.size() - 1);
	    return ultimoElemento.getId() + 1;
	}
	
	
	public TipoParticipacion findById(Long id){
		return  iTipoParticipacionRepository.findById(id).orElse(new TipoParticipacion());
	}

	public TipoParticipacion findByDescripcion(String des){
		return  iTipoParticipacionRepository.findByDescripcionParticipacion(des).orElse(new TipoParticipacion());
	}
	
	public List<TipoParticipacion> findActivos(){
		return iTipoParticipacionRepository.findAll();
	}
	
	public TipoParticipacion saveTipoParticipacion(TipoParticipacion tipoParticipacion) {
		return iTipoParticipacionRepository.save(tipoParticipacion);
	}


}
