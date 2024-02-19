package com.sebasgoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;

  
@Controller
public class WebController {

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
		
		Long id = idActividad;
		model.addAttribute("idActividad",id);
		
		return "lectores/lectorParticipantes";
	}
	

}
