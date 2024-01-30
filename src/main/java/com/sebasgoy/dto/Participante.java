package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="tb_participante")
@Data
public class Participante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private Boolean isParticipant;

	@ManyToOne
	@JoinColumn(name="idActividad",insertable = false, updatable = false)
	private Actividad actividad;

	@ManyToOne
	@JoinColumn(name="idVoluntario",insertable = false, updatable = false)
	private Voluntario voluntario;



}
