package com.sebasgoy.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.persistence.*;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="voluntario")
public class Voluntario {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido")
	private String apellido;
	@Column(name = "dni")
	private String dni;
	@Column(name = "edad")
	private String edad;
	@Column(name = "estado")
	private boolean estado = false;
	
	@OneToMany(mappedBy = "voluntario" )
	private List<Participante> participante;


	
}
