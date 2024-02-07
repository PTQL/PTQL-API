package com.sebasgoy.controller;

import java.util.List;
import java.util.Optional;

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
import com.sebasgoy.service.ActividadService;
import com.sebasgoy.service.ParticipanteService;
import com.sebasgoy.service.TipoParticipacionService;
import com.sebasgoy.service.VoluntarioService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ExcelController {
	
	
	private final ActividadService actividadService;
	private final VoluntarioService voluntarioService;
	private final ParticipanteService participanteService;
	private final TipoParticipacionService tipoParticipacionService;
	
    @PostMapping("/cargarExceltoActividad/{id}")
    @Transactional
    public String handleFileUpload(
    		@RequestParam("excelFile") MultipartFile file,
    		@PathVariable("id") Long idActividad,
    		@RequestParam(value="actionType") String action,
    		Model model) {
    	
    	 // Procesar el archivo Excel según tus necesidades
		VoluntarioResponse voluntarioResponse = ExcelMapper.DevolverEntidadFromExcel(file,VoluntarioResponse.class);
        if (action.equals("verEstado")) {
        	try {
    			model.addAttribute("excelResponse", voluntarioResponse);
    			return "/GestorExcel";
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
							}
			                
			               
		                    Participante participante = Participante.builder()
		                            .idActividad(actividad.getId())
		                            .isParticipant(false)
		                            .idVoluntario(voluntario.getId())
		                            .idTipoParticipacion(tipoParticipacionService.findByDescripcion("LIBRE").getId())
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
	
    private Boolean isValid(VoluntarioResponse voluntarioResponse) {
    	
    	return (voluntarioResponse.getListVoluntarioInvalido().isEmpty()) &&
    			(!voluntarioResponse.getListVoluntarioValido().isEmpty());
    	
    }
}
