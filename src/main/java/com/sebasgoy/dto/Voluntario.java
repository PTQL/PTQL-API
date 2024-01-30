package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="tb_voluntario")
public class Voluntario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String nombre;
	private String apellido;
	private String dni;
	private String edad;

	@OneToMany(mappedBy = "voluntario" )
	private List<Participante> participante;


	
}
