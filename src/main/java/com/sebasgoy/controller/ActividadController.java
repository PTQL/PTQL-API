package com.sebasgoy.controller;

import com.sebasgoy.Parser.PlantillaParser;
import com.sebasgoy.constantes.Plantillas;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.request.PlantillaActividadDto;
import com.sebasgoy.service.*;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.constantes.Mensajes;

import java.util.List;

@Controller
@AllArgsConstructor
public class ActividadController {

	private final ActividadService actividadService;
	private final VoluntarioService voluntarioService;
	private final ParticipanteService participanteService;
	private final UbicacionConstanciasService ubiConstanciasService;
	private final ModuloService moduloService;
 	
	@GetMapping("/generar_actividad")
	public String cargarCrudActividad(Model model) {
		model.addAttribute("actividad", Actividad
				.builder()
				.id(actividadService.UltimoId())
				.estado(true)
				.build()
				);

		model.addAttribute("modulos",moduloService.findActivos()) ;
		return "FormNewActividad";
	}
	
	@GetMapping("/editar_actividad/{id}")
	public String cargarCrudActividadEdit(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("actividad", actividadService.findById(id));
		model.addAttribute("modulos",moduloService.findActivos()) ;

		return "FormNewActividad";
	}
	
	@GetMapping("/dashboard_actividad")	
	public String cargarDashboardActividad(Model model) {
			
		model.addAttribute("listaActividades", actividadService.findAll() );
		return "DashboardActividades";
	}
	
	@PostMapping("/guardar_actividad")
	public String guardar_actividad(@ModelAttribute Actividad actividad,Model model) {
		try {
			
			actividadService.saveActividad(actividad);
			System.out.println(Mensajes.success("ACTIVIDAD", "REGISTRO"));
			model.addAttribute("mensaje",Mensajes.success("ACTIVIDAD", "REGISTRO"));
		} catch (Exception e) {
			System.out.println(Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()) );
		}
		
		return "redirect:/dashboard_actividad";
	}
	
	@GetMapping("/info_actividad/{id}")
	public String info_actividad(@PathVariable("id")Long id,Model model) {
		try {
			model.addAttribute("actividad", actividadService.findById(id));
			System.out.println(Mensajes.success("ACTIVIDAD", "BUSQUEDA"));
			model.addAttribute("mensaje", Mensajes.success("ACTIVIDAD", "BUSQUEDA"));
			
			return "/InfoActividad";
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("ACTIVIDAD", "BUSQUEDA").concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.error("ACTIVIDAD", "BUSQUEDA").concat(e.toString()) );
		}
		return "redirect:/dashboard_actividad";
	}

	@GetMapping("/desactivar_actividad/{id}")
	public String desactivar_Actividad(@PathVariable("id") Long id,Model model  ){
		try {
			Actividad actividad =  actividadService.findById(id);
			
			actividad.setEstado(false);

			actividadService.saveActividad(actividad);
			
			System.out.println(Mensajes.success("ACTIVIDAD", "REGISTRO"));
			model.addAttribute("mensaje", Mensajes.success("ACTIVIDAD", "REGISTRO"));
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()));
			model.addAttribute("mensaje", Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()) );
		}
		
		
		return "redirect:/dashboard_actividad";
		
	}

	@GetMapping("/eliminar_actividad/{id}")
	public String eliminar_actividad(@PathVariable("id") Long id,Model model  ){
		try {
			Actividad actividad =  actividadService.findById(id);
			
			participanteService.deleteListOfParticipante(actividad.getParticipante());
			
			System.out.println(Mensajes.success("Participacion", "Eliminacion"));
			model.addAttribute("mensaje", Mensajes.success("Participacion", "Eliminacion"));
			
			
			actividadService.deleteActividad(actividad);
			
			
			System.out.println(Mensajes.success("Actividad", "Eliminacion"));
			model.addAttribute("mensaje", Mensajes.success("Actividad", "Eliminacion"));
			
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()));
			model.addAttribute("mensaje", Mensajes.error("ACTIVIDAD", "REGISTRO").concat(e.toString()) );
		}
		
		
		return "redirect:/dashboard_actividad";
		
	}
	
	@PostMapping("/generar_constancia_actividad/{id}")
	public String generar_constancia_actividad(
	        @PathVariable("id") Long idActividad,
	        @RequestParam("pathFile") String path,
	        HttpServletRequest request,
	        Model model) {
	    String pagina_anterior = request.getHeader("referer");
		//TODO generar carpeta automaticamente
	    try {
	        Actividad actividad = actividadService.findById(idActividad);
			path = ubiConstanciasService.validarPath(path,actividad);
			List<Voluntario> lstVoluntarios =voluntarioService.getListVoluntarioFromListParticipante(
					participanteService.getLibresFromListParticipante(idActividad)
			);
	        List<PlantillaActividadDto> listPlantillaDto = PlantillaParser.listParticipanteToPlantillaDtoActividad(lstVoluntarios, actividad);
	        for (PlantillaActividadDto plantillaDto : listPlantillaDto) {

	            Plantillas.convertirHTMLaPDF(
						Plantillas.GenerarPlantillaActividad(plantillaDto),
						path + "/" + plantillaDto.getVoluntario().getDni()+ ".pdf");
	        }
	    } catch (Exception e) {
	        System.out.println(Mensajes.error("Archivo", "Generacion").concat(e.toString()));
	    }
	    return "redirect:" + pagina_anterior;
	}




}
