package com.sebasgoy.dto;


import lombok.*;

import java.util.List;

import jakarta.persistence.*;
@Data
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="voluntario")
public class Voluntario {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "dni")
	private String dni;
	@Column(name="correo")
	private String correo;
	@Column(name = "edad")
	private String edad;
	@Column(name = "estado")
	private boolean estado = false;
	@Column(name="numero")
	private String telefono;

	@OneToMany(mappedBy = "voluntario" )
	private List<Participante> participante;

	public void changeEstate(){
    	setEstado( !estado);
    }
}
