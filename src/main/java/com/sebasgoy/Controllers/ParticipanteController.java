package com.sebasgoy.Controllers;

import com.sebasgoy.Models.Actividad;
import com.sebasgoy.Models.Participante;
import com.sebasgoy.Service.ActividadService;
import com.sebasgoy.Service.ParticipanteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participante")
@AllArgsConstructor
public class ParticipanteController {

	private final ParticipanteService participanteService;
	
	@GetMapping
	public List<Participante> getAllParticipantes(){
		return participanteService.getAll();
	}
	@PostMapping("/registro")
	public void guardarParticipante(@RequestBody Participante participante){
		participanteService.saveParticipante(participante);
	}





	
}
