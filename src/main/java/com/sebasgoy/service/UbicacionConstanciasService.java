package com.sebasgoy.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import com.sebasgoy.constantes.Mensajes;
import com.sebasgoy.dto.Modulo;
import org.springframework.stereotype.Service;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.UbicacionConstancias;
import com.sebasgoy.repository.IUbicacionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UbicacionConstanciasService {

	private final IUbicacionRepository iUbicacionRepository;
	private final ActividadService actividadService;
	private final ModuloService moduloService;

	public Long UltimoId() {
		List<UbicacionConstancias> activos = findAll();
		    
	    if (activos.isEmpty()) {
	        return 1L;
	    }
	     
	    UbicacionConstancias ultimoElemento = activos.get(activos.size() - 1);
 
	    return ultimoElemento.getId() + 1;
	}
	
	
	public UbicacionConstancias findById(Long id){
		return  iUbicacionRepository.findById(id).orElse(new UbicacionConstancias());
	}
	
	public Optional<UbicacionConstancias> findByIdOptional(Long id){
		return  iUbicacionRepository.findById(id);
	}


	public void deleteUbicacionConstancias(UbicacionConstancias ubicacionConstancias) {
		iUbicacionRepository.delete(ubicacionConstancias);
	}
	
	public List<UbicacionConstancias> findAll(){
		return iUbicacionRepository.findAll();
	}
	
	
 
	public UbicacionConstancias  saveUbicacionConstancias(UbicacionConstancias ubicacionConstancias) {
		if (ubicacionConstancias.getId() == null) {
			ubicacionConstancias.setId(UltimoId());
		}
		
		return iUbicacionRepository.save(ubicacionConstancias);
	}

	public String validarPath(String path, Object entidad) {
		Optional<UbicacionConstancias> ubConstanciasOptional;
		if (entidad instanceof Actividad) {
			Actividad actividad = (Actividad) entidad;
			ubConstanciasOptional = Optional.ofNullable(actividad.getUbicacionConstancias());
			if (ubConstanciasOptional.isEmpty()) {
				if (path == null || path.isEmpty()) {
					throw new RuntimeException("Path nulo o vacio");
				}
				path = path.concat("\\" + actividad.getNombreActividad().trim() + actividad.getFechaActividad().toString().trim());
				actividad.setIdUbicacionConstancias(saveUbicacionConstancias(
						UbicacionConstancias.builder()
								.ubicacion(path)
								.build()
				).getId());
				actividadService.saveActividad(actividad);
			} else {
				path = ubConstanciasOptional.get().getUbicacion().trim();
			}
		} else if (entidad instanceof Modulo) {
			Modulo modulo = (Modulo) entidad;
			ubConstanciasOptional = Optional.ofNullable(modulo.getUbicacionConstancias());
			if (ubConstanciasOptional.isEmpty()) {
				if (path == null || path.isEmpty()) {
					throw new RuntimeException("Path nulo o vacio");
				}
				path = path.concat("\\" + modulo.getNombre().trim());
				modulo.setIdUbicacionConstancias(saveUbicacionConstancias(
						UbicacionConstancias.builder()
								.ubicacion(path)
								.build()
				).getId());
				moduloService.saveModulo(modulo);
			} else {
				path = ubConstanciasOptional.get().getUbicacion().trim();
			}
		} else {
			throw new IllegalArgumentException("Entidad no válida");
		}

		System.out.println("Ruta : " + path);

		File carpeta = new File(path);
		if (!carpeta.exists()) {
			if (carpeta.mkdirs()) {
				System.out.println("Carpeta creada exitosamente.");
			} else {
				System.out.println("Error al crear la carpeta.");
				throw new RuntimeException("Error al crear la carpeta.");
			}
		}

		return path;
	}
	
	
}
