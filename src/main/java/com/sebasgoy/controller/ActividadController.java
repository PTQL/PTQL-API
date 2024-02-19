package com.sebasgoy.controller;

import com.sebasgoy.Parser.PlantillaParser;
import com.sebasgoy.constantes.Plantillas;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.request.PlantillaDto;
import com.sebasgoy.service.ParticipanteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.service.ActividadService;
import com.sebasgoy.constantes.Mensajes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Controller
@AllArgsConstructor
public class ActividadController {

	private final ActividadService actividadService;

	private final ParticipanteService participanteService;
 	
	@GetMapping("/generar_actividad")
	public String cargarCrudActividad(Model model) {
		
		
		model.addAttribute("actividad", Actividad
				.builder()
				.id(actividadService.UltimoId())
				.estado(true)
				.build()
				);
		return "FormNewActividad";
	}
	
	@GetMapping("/editar_actividad/{id}")
	public String cargarCrudActividadEdit(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("actividad", actividadService.findById(id));
		
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

	@PostMapping("/generar_constancia_actividad/{id}")
	public String generar_constancia_actividad(
	        @PathVariable("id") Long idActividad,
	        @RequestParam("pathFile") String path,
	        HttpServletRequest request,
	        Model model) {
	    String pagina_anterior = request.getHeader("referer");
	    String plantilla = "";
	    try {
	        Actividad actividad = actividadService.findById(idActividad);
	        if (path == null || path.isEmpty()) {
	            System.out.println(Mensajes.error("Archivo", "Generacion", "No se seleccionó carpeta"));
	            return "redirect:" + pagina_anterior;
	        }

	        path = path.concat("\\" + actividad.getNombreActividad().trim()+actividad.getFechaActividad().toString().trim());
	        System.out.println("Ruta : " + path);

	        File carpeta = new File(path);
	        if (!carpeta.exists()) {
	            if (carpeta.mkdirs()) {
	                System.out.println("Carpeta creada exitosamente.");
	            } else {
	                System.out.println("Error al crear la carpeta.");
	                return "redirect:" + pagina_anterior;
	            }
	        }

	        List<PlantillaDto> listPlantillaDto = PlantillaParser.listParticipanteToPlantillaDto(participanteService.getLibresFromListParticipante(idActividad), actividad);
	        for (PlantillaDto plantillaDto : listPlantillaDto) {
	            plantilla = Plantillas.GenerarPlantilla(plantillaDto);
	            String rutaUnitaria = path + "/" + plantillaDto.getNombreVoluntario() + ".pdf";
	            Plantillas.convertirHTMLaPDF(plantilla, rutaUnitaria);
	        }
	    } catch (Exception e) {
	        System.out.println(Mensajes.error("Archivo", "Generacion").concat(e.toString()));
	    }
	    return "redirect:" + pagina_anterior;
	}



}
