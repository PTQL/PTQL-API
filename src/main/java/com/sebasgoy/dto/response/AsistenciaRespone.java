package com.sebasgoy.dto.response;
import java.util.ArrayList;
import java.util.List;

import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
@Getter
public class AsistenciaRespone {

	public AsistenciaRespone() {
		this.lstRegistrados = new ArrayList<Participante>();
		this.lstNoRegistrados = new ArrayList<Voluntario>();
		this.lstDniNoValidos = new ArrayList<String>();
	}
	
	private String listDni;
	private List<Participante> lstRegistrados;
	private List<Voluntario> lstNoRegistrados;
	private List<String> lstDniNoValidos;
	

	
}
