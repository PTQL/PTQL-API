package com.sebasgoy.Controllers;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sebasgoy.Models.Actividad;
import com.sebasgoy.Service.ActividadService;

@RestController
@RequestMapping("/api/actividad")
@AllArgsConstructor
public class ActividadController {

	private final ActividadService actividadService;
	
	@GetMapping
	public List<Actividad> getAllActividades(){
		return actividadService.getAll();
	}
	@PostMapping("/registro")
	public void guardarActividad(@RequestBody Actividad actividad){
		actividadService.saveActividad(actividad);
	}





	
}
