package com.sebasgoy.controller;



import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.service.ActividadService;

import Constantes.Mensajes;

 
@Controller
@AllArgsConstructor
public class ActividadController {

	private final ActividadService actividadService;

	
	/*
	@PostMapping("/registro")
	public  ResponseEntity<Actividad> guardarActividad(@RequestBody Actividad actividad) {
		Actividad response = null;
		try {
			response = actividadService.saveActividad(actividad);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}*/
	
	@GetMapping("/generar_actividad")
	public String cargarCrudActividad(Model model) {
		model.addAttribute("actividad", new Actividad());
		return "CRUD_Actividad";
	}
	
	@PostMapping("/guardar_actividad")
	public String guardar_actividad(@ModelAttribute Actividad actividad,Model model) {
	
		try {
			
			actividadService.saveActividad(actividad);
			System.out.println(Mensajes.ACTIVIDAD_OK_REGISTRO);
			model.addAttribute("mensaje", Mensajes.ACTIVIDAD_OK_REGISTRO);
		} catch (Exception e) {
			System.out.println(Mensajes.ACTIVIDAD_ERROR_REGISTRO.concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.ACTIVIDAD_ERROR_REGISTRO.concat(e.toString()) );
		}
		
		return "redirect:/";
	}






	
}
