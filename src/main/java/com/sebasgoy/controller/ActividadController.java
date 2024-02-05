package com.sebasgoy.controller;



import lombok.AllArgsConstructor;

import org.apache.commons.compress.harmony.unpack200.bytecode.forms.ThisFieldRefForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.repository.IActividadRepository;
import com.sebasgoy.service.ActividadService;

import Constantes.Mensajes;

 
@Controller
@AllArgsConstructor
public class ActividadController {

	private final ActividadService actividadService;

	
 	
	@GetMapping("/generar_actividad")
	public String cargarCrudActividad(Model model) {
		
		
		model.addAttribute("actividad", Actividad
				.builder()
				.id(actividadService.UltimoId())
				.estado(true)
				.build()
				);
		return "FormNewActividad";
	}
	
	@GetMapping("/editar_actividad/{id}")
	public String cargarCrudActividadEdit(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("actividad", actividadService.findById(id));
		
		return "FormNewActividad";
	}
	
	@GetMapping("/dashboard_actividad")	
	public String cargarDashboardActividad(Model model) {
			
		model.addAttribute("listaActividades", actividadService.findAll() );
		return "DashboardActividades";
	}
	
	
	@PostMapping("/guardar_actividad")
	public String guardar_actividad(@ModelAttribute Actividad actividad,Model model) {
	
		try {
			
			actividadService.saveActividad(actividad);
			System.out.println(Mensajes.success("ACTIVIDAD", "REGISTRO"));
			model.addAttribute("mensaje",Mensajes.success("ACTIVIDAD", "REGISTRO"));
		} catch (Exception e) {
			System.out.println(Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()) );
		}
		
		return "redirect:/dashboard_actividad";
	}
	
	@GetMapping("/info_actividad/{id}")
	public String info_actividad(@PathVariable("id")Long id,Model model) {
		try {
			model.addAttribute("actividad", actividadService.findById(id));
			System.out.println(Mensajes.success("ACTIVIDAD", "BUSQUEDA"));
			model.addAttribute("mensaje", Mensajes.success("ACTIVIDAD", "BUSQUEDA"));
			
			return "/InfoActividad";
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("ACTIVIDAD", "BUSQUEDA").concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.error("ACTIVIDAD", "BUSQUEDA").concat(e.toString()) );
		}
		return "redirect:/dashboard_actividad";
	}
	

	@GetMapping("/desactivar_actividad/{id}")
	public String desactivar_Actividad(@PathVariable("id") Long id,Model model  ){
		try {
			Actividad actividad =  actividadService.findById(id);
			
			actividad.setEstado(false);
			
			
			actividadService.saveActividad(actividad);
			
			System.out.println(Mensajes.success("ACTIVIDAD", "REGISTRO"));
			model.addAttribute("mensaje", Mensajes.success("ACTIVIDAD", "REGISTRO"));
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()));
			model.addAttribute("mensaje", Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()) );
		}
		
		
		return "redirect:/dashboard_actividad";
		
	}


	
}
