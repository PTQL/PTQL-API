package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="voluntario")
public class Voluntario {

	@Id
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
	private int estado = 1;
	@OneToMany(mappedBy = "voluntario" )
	private List<Participante> participante;


	
}
