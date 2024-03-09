package com.sebasgoy.controller;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.AsistenciaRespone;
import com.sebasgoy.service.ActividadService;
import com.sebasgoy.service.ParticipanteService;
import com.sebasgoy.service.VoluntarioService;
import com.sebasgoy.constantes.Mensajes;
import com.sebasgoy.constantes.Modalidades;
import com.sebasgoy.constantes.ValoresPersonaRegex;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class ParticipanteController {

	private final ParticipanteService participanteService;
	private final ActividadService actividadService;
	private final VoluntarioService voluntarioService;

	@GetMapping("/change_participacion/{id}")
	public String change_participacion(@PathVariable("id") Long idParticipante ,Model model,
			HttpServletRequest request) {
		String pagina_anterior = request.getHeader("referer");

		try {
			Participante participante = participanteService.findById(idParticipante);
			participante.changeParticipacion();
			participanteService.saveParticipante(participante);
				
			
			
			System.out.println(Mensajes.success("PARTICIPANTE", "BUSQUEDA"));

			return "redirect:/info_actividad/".concat(participante.getIdActividad().toString());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(Mensajes.error("PARTICIPANTE", "BUSQUEDA").concat(e.getMessage()));

		}
		
		return "redirect:"+pagina_anterior;
	}
	
	
	@GetMapping("/editar_voluntario_formParticipante/{id}")
	public String editar_voluntario(@PathVariable("id")Long idParticipante,Model model) {
		
		Participante participante = participanteService.findById(idParticipante);

		try {
 			model.addAttribute("voluntario", participante.getVoluntario());
			model.addAttribute("actividad",participante.getActividad());
 			System.out.println(Mensajes.success("PARTICIPANTE","BUSQUEDA"));

			return "FormNewVoluntario";
		} catch (Exception e) {
			
			System.out.println(Mensajes.error("PARTICIPANTE","BUSQUEDA") +e.toString());
		}
		return "/info_actividad/".concat(participante.getIdActividad().toString());
		
	}
	
	@GetMapping("/retirar_voluntario/{id}")
	public String retirar_voluntario(@PathVariable("id")Long idParticipante ,Model model,HttpServletRequest request)  {
		String pagina_anterior = request.getHeader("referer");

		try {
 			participanteService.deleteParticipanteById(idParticipante);
			System.out.println(Mensajes.success("PARTICIPANTE","Eliminacion"));

		} catch (Exception e) {
			System.out.println(Mensajes.error("PARTICIPANTE","Eliminacion") +e.toString());
		}
		return "redirect:"+pagina_anterior;
	}
	
	@GetMapping("/retirar_todos_voluntarios/{id}")
	public String retirar_todos_voluntarios(@PathVariable("id")Long idActividad ,Model model,HttpServletRequest request)  {
		String pagina_anterior = request.getHeader("referer");
		
		try {
			
			Actividad actividad = actividadService.findById(idActividad);
			
			
			actividad.getParticipante().forEach(
				(x) -> {
					participanteService.deleteParticipanteById(x.getId());
				});
			
 			System.out.println(Mensajes.success("Actividad - Participante - Voluntario","Eliminacion"));
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("Actividad - Participante - Voluntario","Eliminacion") +e.toString());
		}
		return "redirect:"+pagina_anterior;
	}




	@PostMapping("/incluirVoluntarios")
	public String incluirVoluntarios(
			Model model,
			HttpServletRequest  request,
			@RequestParam("idActividad") Long idActividad,
			@RequestParam("voluntariosSeleccionados") List<Long> voluntariosSeleccionados
	){
		String pagina_anterior = request.getHeader("referer");
		try {

			participanteService.saveVoluntariosToActividad(voluntariosSeleccionados, idActividad, Modalidades.ID_LIBRE,true);
			
			
			return "redirect:/info_actividad/"+idActividad;



		}catch (Exception e){
			System.out.println(Mensajes.error("VOLUNTARIO", "Registro"));
			return "redirect:"+pagina_anterior;
		}
	}


	@PostMapping("/leerParticipantesFromTxt/{id}")
	public String leerVoluntariosFromTxt(
			HttpServletRequest  request,
			@PathVariable("id") Long idActividad,
			Model model,
    		@RequestParam(value="type") String action,
			@RequestParam("dni") String dnis
			){
 
        AsistenciaRespone asistenciaResponse = participanteService.verDetallesListOfDni(idActividad, model, dnis);

		if (action.equals("verDetalles")) {
			try {
				model.addAttribute("actividad",actividadService.findById(idActividad));
				model.addAttribute("asistenciaRespone",asistenciaResponse 	);
		        
				return "gestorAsistencia/GestorAsistencia";
			} catch (Exception e) {
				System.out.println(Mensajes.error("VOLUNTARIO", "Registro") + e.getMessage());
				return "redirect:/dashboard_voluntario";		
			}
	 
		}else if (action.equals("marcarAsistencia")) {

			List<Voluntario> voluntarios_guardados = voluntarioService.saveAndGetVoluntarios(asistenciaResponse.getLstNoRegistrados());
			List<Participante> participante_creados_libre = participanteService.saveVoluntariosToActividadAndGetParticipantes(voluntarios_guardados,idActividad,Modalidades.ID_LIBRE);

			participanteService.makeParticipantionTrue(participante_creados_libre);
			participanteService.makeParticipantionTrue(asistenciaResponse.getLstRegistrados());
			
		}
		
		return "redirect:/info_actividad/"+idActividad;
	}



	
 
	
	
	
	



	
}
