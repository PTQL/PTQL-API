package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="actividad")
public class Actividad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nombreActividad")
	private String nombreActividad;
	@Column(name = "ubicacionActividad")
	private String ubicacionActividad;
	@Column(name = "fechaActividad")
	private Date fechaActividad;
	@Column(name = "horaInicio")
	private LocalTime   horaInicio;
	@Column(name = "horaFin")
	private LocalTime  horaFin;
	@Column(name = "estado")
	private boolean estado = true;
	
	@OneToMany(mappedBy = "actividad" )
	private List<Participante> participante;

	@ManyToOne
	@JoinColumn(name = "moduloId")
	private Modulo modulo;

	public long obtenerDuracionActividad(){
 
		return Duration.between(horaInicio,horaFin).toHours();
	}

}
