package com.sebasgoy.controller;

 
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.service.ParticipanteService;
import com.sebasgoy.service.VoluntarioService;

import Constantes.Mensajes;
import lombok.AllArgsConstructor;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

 

@Controller
@AllArgsConstructor
public class VoluntarioController {
	
	
	private final VoluntarioService voluntarioService;

	
	@PostMapping("/guardar_voluntario")
	public String guardar_voluntario(@ModelAttribute Voluntario voluntario,
			 Model model ) {
		
		try {
			
			voluntarioService.saveVoluntario(voluntario);
			
			System.out.println(Mensajes.success("VOLUNTARIO", "REGISTRO"));
			
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("VOLUNTARIO", "REGISTRO"));
		}
		
		return "redirect:/dashboard_actividad";
		
	}

	




	
}
