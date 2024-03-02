package com.sebasgoy.dto;

import jakarta.annotation.Nullable;
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
    private Long id;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "estado")
	private boolean estado = true;
    @Column
    private Long idUbicacionConstancias;
    @ManyToOne
    @JoinColumn(name="idUbicacionConstancias" ,insertable = false,updatable = false)
    private UbicacionConstancias ubicacionConstancias;
    @OneToMany(mappedBy = "idModuloActividad")
    private List<Actividad> actividad;
    
    
    
    

}
