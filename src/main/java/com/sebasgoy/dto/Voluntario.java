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
	private String Nombre;
	private String Apellido;
	private String Dni;
	private String Edad;

	@OneToMany(mappedBy = "voluntario" )
	private List<Participante> participante;


	
}
