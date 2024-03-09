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
	

	


	

}
