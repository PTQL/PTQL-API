package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
    @Column
    private int minimoActividades;

    @ManyToOne
    @JoinColumn(name="idUbicacionConstancias" ,insertable = false,updatable = false)
    private UbicacionConstancias ubicacionConstancias;
    @OneToMany(mappedBy = "idModuloActividad")
    private List<Actividad> actividad;

    @Override
    public String toString() {
        return "Modulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", idUbicacionConstancias=" + idUbicacionConstancias +
                ", minimoActividades=" + minimoActividades +
                ", ubicacionConstancias=" + ubicacionConstancias +
                ", actividad=" + actividad +
                '}';
    }
}
