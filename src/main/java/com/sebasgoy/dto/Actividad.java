package com.sebasgoy.dto;

import com.sebasgoy.constantes.Modalidades;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalTime;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="actividad")
public class Actividad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nombreActividad")
	private String nombreActividad;
	@Column(name = "ubicacionActividad")
	private String ubicacionActividad;
	@Column(name = "fechaActividad")
	private Date fechaActividad;
	@Column(name = "horaInicio")
	private LocalTime horaInicio;
	@Column(name = "horaFin")
	private LocalTime horaFin;
	@Column(name = "estado")
	private boolean estado = true;

	private Long idModuloActividad;


	@Column
	private Long idUbicacionConstancias;
	@ManyToOne
	@JoinColumn(name="idUbicacionConstancias" ,insertable = false,updatable = false)
	private UbicacionConstancias ubicacionConstancias;

	@OneToMany(mappedBy = "actividad" )
	private List<Participante> participante;

	@ManyToOne
 	@JoinColumn(name = "idModuloActividad",insertable = false, updatable = false)
 	private Modulo modulo;

	public int cantidadParticipanteOf(int modalidad) {
		int contadorModulo = 0;
		int contadorLibre = 0;

		for (Participante participante : participante) {
			if (participante.getIdTipoParticipacion() == (Modalidades.ID_MODULO)) {
				contadorModulo++;
			} else if (participante.getIdTipoParticipacion()  == (Modalidades.ID_LIBRE)) {
				contadorLibre++;
			}
		}

		if (modalidad == 1) {
			return contadorModulo;
		} else {
			return contadorLibre;
		}
	}

	public Long obtenerDuracionActividad(){
 
		return Duration.between(horaInicio,horaFin).toHours();
	}

}
