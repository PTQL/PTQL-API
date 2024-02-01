package com.sebasgoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;

  
@Controller
public class WebController {

	@GetMapping("/")
	public String cargarPaginaPrincipal() {
		return "PaginaPrincipal";
	}
	
	
	

	
	

}
