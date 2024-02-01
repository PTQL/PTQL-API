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
@Table(name="actividad")
public class Actividad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(name = "nombreActividad")
	private String nombreActividad;
	@Column(name = "ubicacionActividad")
	private String ubicacionActividad;
	@Column(name = "fechaActividad")
	private String fechaActividad;
	@Column(name = "horaInicio")
	private String horaInicio;
	@Column(name = "horaFin")
	private String horaFin;
	@Column(name = "estado")
	private int estado = 1;
	
	@OneToMany(mappedBy = "actividad" )
	private List<Participante> participante;

	@ManyToOne
	@JoinColumn(name = "moduloId")
	private Modulo modulo;

	public long obtenerDuracionActividad(){
		LocalTime  inicio = LocalTime.parse(horaInicio);
		LocalTime  fin = LocalTime.parse(horaFin);
		return Duration.between(inicio,fin).toHours();
	}

}
