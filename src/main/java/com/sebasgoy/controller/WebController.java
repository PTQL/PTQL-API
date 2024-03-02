package com.sebasgoy.controller;

import com.sebasgoy.dto.response.AsistenciaRespone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sebasgoy.service.ActividadService;

import lombok.AllArgsConstructor;

  
@Controller
@AllArgsConstructor
public class WebController {
	private final ActividadService actividadService;

	@GetMapping("/")
	public String cargarPaginaPrincipal() {
		return "PaginaPrincipal";
	}
	

	
	@GetMapping("/herramienta_lectorTexto")
	public String herramienta_lectorTexto(){
		return "lectores/lectorVoluntarios";
	}
	
	@GetMapping("/herramienta_lectorParticipanteTexto/{id}")
	public String herramienta_lectorParticipanteTexto(@PathVariable("id")Long idActividad,Model model){
		
 
		model.addAttribute("actividad",actividadService.findById(idActividad));
		
		model.addAttribute("asistenciaRespone",new AsistenciaRespone());
		
		return "lectores/lectorParticipantes";
	}
	

}
