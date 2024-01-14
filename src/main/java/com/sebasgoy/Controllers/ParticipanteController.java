package com.sebasgoy.Controllers;

import com.sebasgoy.Models.Actividad;
import com.sebasgoy.Models.Participante;
import com.sebasgoy.Models.Voluntario;
import com.sebasgoy.Service.ActividadService;
import com.sebasgoy.Service.ParticipanteService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Participante> guardarParticipante(@RequestBody Participante participante){
		Participante response = null;
		try {
			response = participanteService.saveParticipante(participante);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}	}





	
}
