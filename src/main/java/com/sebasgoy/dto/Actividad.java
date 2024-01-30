package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_actividad")
public class Actividad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String nombreActivdad;
	private String ubicacionActividad;
	private String fechaActividad;
	private String horaIncio;
	private String horaFin;

	@OneToMany(mappedBy = "actividad" )
	private List<Participante> participante;

	@ManyToOne
	@JoinColumn(name = "modulo_id")
	private Modulo modulo;

	private long obtenerDuracionActividad(){
		LocalTime  inicio = LocalTime.parse(horaIncio);
		LocalTime  fin = LocalTime.parse(horaFin);
		return Duration.between(inicio,fin).toHours();
	}

}
