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
    private long id;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "estado")
	private boolean estado = true;
    
    @OneToMany(mappedBy = "modulo")
    private List<Actividad> actividad;
    
    
    
    

}
