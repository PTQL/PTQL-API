package com.sebasgoy.controller;

import com.sebasgoy.dto.Participante;
import com.sebasgoy.service.ParticipanteService;
import lombok.AllArgsConstructor;
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
