package com.sebasgoy.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="UbicacionConstancias")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UbicacionConstancias {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String ubicacion;
	
}
