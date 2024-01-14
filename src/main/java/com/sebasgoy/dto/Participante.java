package com.sebasgoy.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_participante")
@Data
public class Participante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private Long idActividad;
	
	private Long idVoluntario;
	private Boolean esParticipante;
	
	@ManyToOne
	@JoinColumn(name="idActividad",insertable = false, updatable = false)
	private Actividad obj_Actividad;
	
	
	@ManyToOne
	@JoinColumn(name="idVoluntario",insertable = false, updatable = false)
	private Voluntario obj_Voluntario;

}
