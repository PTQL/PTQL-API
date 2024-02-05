package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="participante")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Participante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(name = "isParticipant")
	private Boolean isParticipant = false;
	private Long idActividad;
	private Long idVoluntario;
	private Long idTipoParticipacion;
	
	@ManyToOne
	@JoinColumn(name="idActividad",insertable = false, updatable = false)
	private Actividad actividad;

	@ManyToOne
	@JoinColumn(name="idVoluntario",insertable = false, updatable = false)
	private Voluntario voluntario;

	@ManyToOne
	@JoinColumn(name="idTipoParticipacion" ,insertable = false,updatable = false)
	private TipoParticipacion tipoParticipacion;

	
	public void changeParticipacion() {
		setIsParticipant(!isParticipant);
	}
	

}
