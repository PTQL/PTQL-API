package com.sebasgoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sebasgoy.dto.Participante;
import com.sebasgoy.repository.IActividadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.sebasgoy.dto.Actividad;


/*En un service va la logica de negocio*/
@Service
@AllArgsConstructor
public class ActividadService {

	private final IActividadRepository iActividadRepository;

	public Long UltimoId() {
		List<Actividad> activos = findActivos();
		    
	    if (activos.isEmpty()) {
	        return 1L;
	    }
	     
	    Actividad ultimoElemento = activos.get(activos.size() - 1);
 
	    return ultimoElemento.getId() + 1;
	}
	
	
	public Actividad findById(Long id){
		return  iActividadRepository.findById(id).orElse(new Actividad());
	}
	
	public Optional<Actividad> findByIdOptional(Long id){
		return  iActividadRepository.findById(id);
	}


	public List<Actividad> findActivos(){
		return iActividadRepository.findByEstadoIsTrue();
	}
	
	public void deleteActividad(Actividad actividad) {
		iActividadRepository.delete(actividad);
	}
	
	public List<Actividad> findAll(){
		return iActividadRepository.findAll();
	}
	
	
	public List<Actividad> findActivosSinModulo(){

		return iActividadRepository.findAll().stream().filter(
				
				actividad -> (actividad.getIdModuloActividad() == null )
					&& (actividad.isEstado())
				).toList();
	}
	public void saveActividad(Actividad actividad) {

		if (actividad.getIdModuloActividad() == null || actividad.getIdModuloActividad()==-1){
			actividad.setIdModuloActividad(null);
		}
		iActividadRepository.save(actividad);
	}

	public List<Actividad> obtenerActividesFromParticipaciones(List<Participante> lstParticipante){
		return lstParticipante.stream().map(
				Participante::getActividad
		).toList();

	}
    public List<Actividad> recuperarFaltantes(List<Actividad> actividadesParticipadas ,List<Actividad> totalActivides) {
		List<Actividad> faltantes = new ArrayList<>();
		for (Actividad a: totalActivides) {
			if ( !actividadesParticipadas.contains(a)){
				faltantes.add(a);
			}
		}

		return faltantes;


	}


}
