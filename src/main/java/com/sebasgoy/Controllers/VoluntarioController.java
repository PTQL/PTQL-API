package com.sebasgoy.Controllers;

import com.sebasgoy.Models.Participante;
import com.sebasgoy.Models.Voluntario;
import com.sebasgoy.Service.ParticipanteService;
import com.sebasgoy.Service.VoluntarioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voluntario")
@AllArgsConstructor
public class VoluntarioController {

	private final VoluntarioService voluntarioService;
	
	@GetMapping
	public List<Voluntario> getAllVoluntarios(){
		return voluntarioService.getAll();
	}
	@PostMapping("/registro")
	public void guardarVoluntario(@RequestBody Voluntario voluntario){
		voluntarioService.saveVoluntario(voluntario);
	}





	
}
