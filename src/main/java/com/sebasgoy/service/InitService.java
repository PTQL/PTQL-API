package com.sebasgoy.service;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.TipoParticipacion;
import com.sebasgoy.repository.IActividadRepository;
import com.sebasgoy.repository.ITipoParticipacionRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InitService {

	private final ITipoParticipacionRepository iTipoParticipacionRepository;
	private final IActividadRepository iActividadRepository;

	@PostConstruct
	public void cargarDatos() {
		
		try {
			iTipoParticipacionRepository.save(
					TipoParticipacion.builder()
					.descripcionParticipacion("MODULO")
					.build()
					);
			iTipoParticipacionRepository.save(
					TipoParticipacion.builder()
					.descripcionParticipacion("LIBRE")
					.build()
					);
			iActividadRepository.save(
					Actividad.builder()
					.nombreActividad("Limpieza de playa las conchitas")
					.fechaActividad( java.sql.Date.valueOf("2024-02-02"))
					.horaInicio(LocalTime.parse("09:00"))
					.horaFin(LocalTime.parse("12:00"))
					.ubicacionActividad("Ancon,Lima")
					.modulo(null)
					.estado(true)
					.build()
					);
						
		} catch (Exception e) {
			System.out.println("Error en initService :"+e.toString());
		}
		
		
	}
	
	
}
