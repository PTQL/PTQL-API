package com.sebasgoy.controller;

import java.util.List;
import java.util.Optional;

import com.sebasgoy.constantes.Modalidades;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sebasgoy.Mapper.ExcelMapper;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.VoluntarioResponse;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ExcelController {
	
	
	private final ActividadService actividadService;
	private final VoluntarioService voluntarioService;
	private final ModuloService moduloService;
	private final ParticipanteService participanteService;
	private final TipoParticipacionService tipoParticipacionService;
	
    @PostMapping("/cargarExcelVoluntariostoActividad/{id}")
    @Transactional
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
                model.addAttribute("result", "Error processing the Excel file."+ e.toString());
                return "redirect:/info_actividad/".concat(idActividad.toString());
             }        	
		}else if(action.equals("insertarVoluntario")) {
			try {
			    if (isValid(voluntarioResponse)) {
			        Optional<Actividad> optionalActividad = actividadService.findByIdOptional(idActividad);

			        if (optionalActividad.isPresent()) {
			            Actividad actividad = optionalActividad.get();
			            List<Participante> listParticipanteActividad = actividad.getParticipante();

			            for (Voluntario voluntario: voluntarioResponse.getListVoluntarioValido()) {
			                // Validar duplicación con DNI
			                if ( !voluntarioService.existsByDni(voluntario.getDni())) {

			                    voluntarioService.saveVoluntario(voluntario);
							}else {
								voluntario.setId(voluntarioService.findByDni(voluntario.getDni()).getId());
								voluntario.setEstado(true);
							}
			                
			               
		                    Participante participante = Participante.builder()
		                            .idActividad(actividad.getId())
		                            .isParticipant(false)
		                            .idVoluntario(voluntario.getId())
		                            .idTipoParticipacion(tipoParticipacionService.findByDescripcion(Modalidades.LIBRE).getId())
		                            .build();

	                        participanteService.saveParticipante(participante);

	                        List<Participante> listParticipanteVoluntario = voluntario.getParticipante();
	                        listParticipanteVoluntario.add(participante);
	                        voluntario.setParticipante(listParticipanteVoluntario);
	                        voluntarioService.saveVoluntario(voluntario);

	                        listParticipanteActividad.add(participante);
		                    
			                
			            }

			            actividad.setParticipante(listParticipanteActividad);
			            actividadService.saveActividad(actividad);
			            		
			            return "redirect:/info_actividad/" + idActividad;
			        } else {
			            throw new Exception("Actividad con ID " + idActividad + " no encontrada.");
			        }
			    } else {
			        throw new Exception("Error en el excel response");
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
	@Transactional
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
					Optional<Modulo> optionalModulo = moduloService.findByIdOptional(idModulo);
					if (optionalModulo.isPresent()) {
						Modulo modulo = optionalModulo.get();
						List<Voluntario> listaVoluntarios = response.getListVoluntarioValido();
						List<Actividad> listActividad = modulo.getActividad();

						for(Actividad actividad :listActividad ){
							List<Participante> listParticipanteActividad = actividad.getParticipante();

							for ( Voluntario voluntario: listaVoluntarios) {
								if ( !voluntarioService.existsByDni(voluntario.getDni())) {
									voluntarioService.saveVoluntario(voluntario);
								}else {
									voluntario.setId(voluntarioService.findByDni(voluntario.getDni()).getId());
									voluntario.setEstado(true);
								}

								Participante participante = Participante.builder()
										.idActividad(actividad.getId())
										.isParticipant(false)
										.idVoluntario(voluntario.getId())
										.idTipoParticipacion(tipoParticipacionService.findByDescripcion(Modalidades.MODULO ).getId())
										.build();

								participanteService.saveParticipante(participante);

								List<Participante> listParticipanteVoluntario = voluntario.getParticipante();
								listParticipanteVoluntario.add(participante);
								voluntario.setParticipante(listParticipanteVoluntario);
								voluntarioService.saveVoluntario(voluntario);

								listParticipanteActividad.add(participante);
							}
							actividad.setParticipante(listParticipanteActividad);
							actividadService.saveActividad(actividad);
						}
						System.out.println("Guardado de participantes en modulo OK");

						return "redirect:"+pagina_anterior;

					} else {
					throw new Exception("Modulo con ID " + idModulo + " no encontrada.");
				}

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


    private Boolean isValid(VoluntarioResponse voluntarioResponse) {
    	
    	return (voluntarioResponse.getListVoluntarioInvalido().isEmpty()) &&
    			(!voluntarioResponse.getListVoluntarioValido().isEmpty());
    	
    }
}
