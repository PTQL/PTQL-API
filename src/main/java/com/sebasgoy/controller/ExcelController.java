package com.sebasgoy.controller;

import java.util.List;

import com.sebasgoy.constantes.Modalidades;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sebasgoy.Mapper.ExcelMapper;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.VoluntarioResponse;

import lombok.AllArgsConstructor;

import javax.sound.midi.InvalidMidiDataException;

@Controller
@AllArgsConstructor
public class ExcelController {
	
	
	private final ActividadService actividadService;
	private final VoluntarioService voluntarioService;
	private final ModuloService moduloService;
	private final ParticipanteService participanteService;
	private final TipoParticipacionService tipoParticipacionService;
	
    @PostMapping("/cargarExcelVoluntariostoActividad/{id}")
    public String cargarExceltoActividad(
    		@RequestParam("excelFile") MultipartFile file,
    		@PathVariable("id") Long idActividad,
    		@RequestParam(value="actionType") String action,
    		Model model) {
    	
    	 // Procesar el archivo Excel según tus necesidades
		VoluntarioResponse voluntarioResponse = ExcelMapper.DevolverEntidadFromExcel(file,VoluntarioResponse.class);
        if (action.equals("verEstado")) {
        	try {
    			model.addAttribute("voluntarioResponse", voluntarioResponse);
    			return "GestorExcel";
             } catch (Exception e) {
                model.addAttribute("result", "Error processing the Excel file."+ e);
                return "redirect:/info_actividad/".concat(idActividad.toString());
             }        	
		}else if(action.equals("insertarVoluntario")) {
			try {
			    if (isValid(voluntarioResponse)) {
			        Actividad actividad = actividadService.findByIdOptional(idActividad)
							.orElseThrow(() -> new EntityNotFoundException("Actividad con ID " + idActividad + " no encontrada."));

			        for (Voluntario voluntario: voluntarioResponse.getListVoluntarioValido()) {
						System.out.println("Iteracion para :"+voluntario.toString());
						// Validar duplicación con DNI
						if ( !voluntarioService.existsByDni(voluntario.getDni())) {
							voluntarioService.saveVoluntario(voluntario);
						}else {
							voluntario = voluntarioService.findByDni(voluntario.getDni());
							voluntario.setEstado(true);
						}

						if (!participanteService.existeParticipanteParaVoluntarioYActividad( voluntario.getId(),actividad.getId())) {
							Participante participante = crearParticipante(actividad.getId(), voluntario.getId(),Modalidades.LIBRE);
							participanteService.saveParticipante(participante);
							System.out.println("Registro de participante Ok :" + participante.toString());
						} else {
							System.out.println("Participante Existe");
						}
					}

					return "redirect:/info_actividad/" + idActividad;
			    } else {
					throw new InvalidMidiDataException("Error en el excel response");
			    }
			} catch (Exception e) {
			    model.addAttribute("result", "Error processing the Excel file." + e.toString());
			    System.out.println("Error processing the Excel file." + e.toString());
			}        	
        	 return "redirect:/info_actividad/".concat(idActividad.toString());
		}
        return "redirect:/info_actividad/".concat(idActividad.toString());
    }

	@PostMapping("/cargarExcelVoluntariostoModulo/{id}")
	public String cargarExcelVoluntariostoModulo(
			@PathVariable("id") Long idModulo,
			@RequestParam("excelFile") MultipartFile file,
			@RequestParam(value="actionType") String action,
			Model model,
			HttpServletRequest request
	) {
		String pagina_anterior =request.getHeader("referer");
		try {
			VoluntarioResponse response = ExcelMapper.DevolverEntidadFromExcel(file,VoluntarioResponse.class);

			if (action.equals("verEstado")) {
				try {
					model.addAttribute("voluntarioResponse", response);
					return "GestorExcel";
				} catch (Exception e) {
					model.addAttribute("result", "Error processing the Excel file."+ e.toString());
					return "redirect:"+pagina_anterior;
				}
			}else if(action.equals("insertarVoluntario")){
				if (isValid(response)){
					Modulo modulo = moduloService.findByIdOptional(idModulo)
						.orElseThrow(() -> new EntityNotFoundException("Modulo con ID " + idModulo + " no encontrada."));
 					List<Voluntario> listaVoluntarios = response.getListVoluntarioValido();
					List<Actividad> listActividad = modulo.getActividad();
					System.out.println("Empezando registro de voluntario a modulo");
					try {
						voluntario_loop:
						for ( Voluntario voluntario: listaVoluntarios) {
							System.out.println(voluntario.toString());
							if ( !voluntarioService.existsByDni(voluntario.getDni())) {
								voluntarioService.saveVoluntario(voluntario);
							}else {
								voluntario = voluntarioService.findByDni(voluntario.getDni());
								voluntario.setEstado(true);
							}

							for(Actividad actividad :listActividad ){
								if (voluntario.getParticipante().stream().anyMatch(participante -> participante.getActividad().getId().equals(actividad.getId()))) {
									System.out.println("match");
									continue voluntario_loop;
								}else {
									if (!participanteService.existeParticipanteParaVoluntarioYActividad(voluntario.getId(),actividad.getId() )){
										Participante participante = crearParticipante(actividad.getId(), voluntario.getId(),Modalidades.MODULO);
										participanteService.saveParticipante(participante);
									}else {
										System.out.println("Participante Existe");
									}
								}



							}
						}

					}catch (Exception e){
						System.out.println(e);
					}

					System.out.println("Guardado de participantes en modulo OK");
					return "redirect:"+pagina_anterior;

				}else{
					throw new Exception("Error en el excel response - Datos Invalidos");
				}
			}
		} catch (Exception e) {
			model.addAttribute("result", "Error processing the Excel file." + e.toString());
			System.out.println("Error processing the Excel file." + e.toString());
		}

		return "redirect:"+pagina_anterior;

	}

	private Participante crearParticipante(Long idActividad, Long idVoluntario,String modalidad) {
		return Participante.builder()
				.idActividad(idActividad)
				.isParticipant(false)
				.idVoluntario(idVoluntario)
				.idTipoParticipacion(tipoParticipacionService.findByDescripcion(modalidad).getId())
				.build();
	}

    private Boolean isValid(VoluntarioResponse voluntarioResponse) {
    	
    	return (voluntarioResponse.getListVoluntarioInvalido().isEmpty()) &&
    			(!voluntarioResponse.getListVoluntarioValido().isEmpty());
    	
    }
}
