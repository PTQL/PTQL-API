package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="modulo")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
	@Column(name = "nombre")
    private String Nombre;
	@Column(name = "estado")
	private int estado = 1;
    
    @OneToMany(mappedBy = "modulo")
    private List<Actividad> actividad;


}
