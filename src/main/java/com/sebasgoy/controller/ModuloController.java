package com.sebasgoy.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.service.ActividadService;
import com.sebasgoy.service.ModuloService;

import Constantes.Mensajes;

 
@Controller
@AllArgsConstructor
public class ModuloController {

	private final ModuloService moduloService;
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
	
	@GetMapping("/generar_modulo")
	public String cargarCrudModulo(Model model) {
		try {
			
			Modulo modulo = Modulo.builder().Id(moduloService.UltimoId()).build();
			moduloService.saveModulo(modulo);
			
			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());
			model.addAttribute("modulo", modulo);
			System.out.println( Mensajes.MODULO_OK_REGISTRO);
			model.addAttribute("mensaje", Mensajes.MODULO_OK_REGISTRO);
		} catch (Exception e) {
			System.out.println(Mensajes.MODULO_ERROR_REGISTRO.concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.MODULO_ERROR_REGISTRO.concat(e.toString()) );
		}
		return "CRUD_Modulo";
	}
	

	@GetMapping("/addActividadToModulo/{actividadId}")
	public String agregarActividad(@PathVariable Long actividadId , @RequestParam Long moduloId ,Model model){
		
		Modulo modulo = moduloService.findById(moduloId);		
		Actividad actividad = actividadService.findById(actividadId);

		List<Actividad> ListActividad = modulo.getActividad();

		if (!ListActividad.contains(actividad) ) {
			actividad.setModulo(modulo);
			actividadService.saveActividad(actividad);
			
			ListActividad.add(actividad);
			modulo.setActividad(ListActividad );
			model.addAttribute("modulo", modulo);
			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());

		}else {
			model.addAttribute("modulo", modulo);
			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());
			
		}
		
		

		
		

		
		

		return "CRUD_Modulo";
	}
	
	@PostMapping("/guardar_modulo")
	public String guardar_modulo(@ModelAttribute Modulo modulo,Model model) {
	
		try {
			
			moduloService.saveModulo(modulo);
			
			
			System.out.println( Mensajes.MODULO_OK_REGISTRO);
			model.addAttribute("mensaje", Mensajes.MODULO_OK_REGISTRO);
		} catch (Exception e) {
			model.addAttribute("mensaje", Mensajes.MODULO_ERROR_REGISTRO.concat(e.toString()) );
		}
		
		return "redirect:/";
	}






	
}
