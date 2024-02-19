package com.sebasgoy.controller;

 
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.service.ActividadService;
import com.sebasgoy.service.ParticipanteService;
import com.sebasgoy.service.TipoParticipacionService;
import com.sebasgoy.service.VoluntarioService;

import com.sebasgoy.constantes.Mensajes;
import com.sebasgoy.constantes.Modalidades;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

 

@Controller
@AllArgsConstructor
public class VoluntarioController {
	
	
	private final VoluntarioService voluntarioService;
	private final TipoParticipacionService TipoParticipacionService;
	private final ActividadService actividadService;
	private final ParticipanteService participanteService;
	

	@GetMapping("/dashboard_voluntario")
	public String dashboard_voluntario(Model model) {
		try {
			
			model.addAttribute("listaVoluntarios",voluntarioService.getAll());
			
			
			
			return "DashboardVoluntarios";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/";

	}
	@GetMapping("/editar_voluntario/{id}")
	public String editar_voluntario(@PathVariable("id")Long id,Model model) {
		try {
			
			
			model.addAttribute("voluntario", voluntarioService.findById(id));
			System.out.println(Mensajes.success("Voluntario", "Busqueda"));

			return "FormNewVoluntario";
		} catch (Exception e) {

			System.out.println(Mensajes.error("Voluntario", "Busqueda"));
			
		}
		return "redirect:/dashboard_voluntario";

	}
	
	@GetMapping("/info_voluntario/{id}")
	public String info_voluntario(@PathVariable("id")Long id,Model model) {
		try {
			
			Voluntario voluntario = voluntarioService.findById(id);
 
			
			List<Participante> actividadesVoluntario = voluntario.getParticipante().stream()
					.filter(participante -> participante.getIdTipoParticipacion()
							.equals(TipoParticipacionService.findByDescripcion(Modalidades.LIBRE).getId()))
					.toList();
			
			List<Participante> moduloVoluntario = voluntario.getParticipante().stream()
					.filter(participante -> participante.getIdTipoParticipacion()
							.equals(TipoParticipacionService.findByDescripcion(Modalidades.MODULO).getId()) )
					.toList();
			
			
			
			model.addAttribute("voluntario",voluntario);
			model.addAttribute("moduloVoluntario",moduloVoluntario);
			model.addAttribute("actividadesVoluntario",actividadesVoluntario);
			
			return "InfoVoluntario";
		} catch (Exception e) {
			System.out.println(Mensajes.error("Voluntario", "Buscar").concat(e.toString()));

		}
		return "redirect:/dashboard_voluntario";

		
	}
	@GetMapping("/eliminar_voluntario/{id}")
	public String desactivar_voluntario(@PathVariable("id")Long id,Model model) {
		try {
			
			Voluntario voluntario = voluntarioService.findById(id);
			List<Participante> listParticipante = voluntario.getParticipante();
			
			participanteService.deleteListOfParticipante(listParticipante);
			System.out.println(Mensajes.success("Participante", "Eliminacion"));

			
			voluntarioService.deleteVoluntario(voluntario);
			
			model.addAttribute("voluntario", voluntarioService.getAll());
			System.out.println(Mensajes.success("Voluntario", "Eliminacion"));

		} catch (Exception e) {

			System.out.println(Mensajes.error("Voluntario", "Eliminacion").concat(e.toString()));
			
		}
		return "redirect:/dashboard_voluntario";

	}
	
	
	
	
	@GetMapping("/generar_voluntario")
	public String generar_voluntario(
			Model model
	){
		model.addAttribute("voluntario",

				Voluntario.builder()
						.Id(voluntarioService.UltimoId())
						.estado(true)
						.build()
				);

		return  "FormNewVoluntario";
	}
	@PostMapping("/guardar_voluntario")
	public String guardar_voluntario(HttpServletRequest  request ,@ModelAttribute Voluntario voluntario,
			 Model model ) {
		String pagina_anterior = request.getHeader("referer");
		try {
			
			voluntarioService.saveVoluntario(voluntario);
			
			System.out.println(Mensajes.success("VOLUNTARIO", "REGISTRO"));
			
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("VOLUNTARIO", "REGISTRO"));
		}
		
		return "redirect:"+pagina_anterior;
		
	}

 
	
	@GetMapping("/agregarVoluntarioToActividad/{id}")
	public String agregarVoluntarioToActividad(
			HttpServletRequest  request,
			@PathVariable("id")Long idActividad,
			Model model
	){
		String pagina_anterior = request.getHeader("referer");

		try {

		Actividad actividad = actividadService.findById(idActividad);
		List<Voluntario> listVoluntario = voluntarioService.getAllWithoutActivity(actividad);

		model.addAttribute("actividad",actividad);
		model.addAttribute("listVoluntario",listVoluntario);
		return "IncluirVoluntario";

		}catch (Exception e){

			System.out.println(Mensajes.error("VOLUNTARIO", "BUSQUEDA"));
			return "redirect:"+pagina_anterior;
		}
	}


	
	@PostMapping("/leerVoluntariosFromTxt")
	public String leerVoluntariosFromTxt(
			HttpServletRequest  request,
			Model model,
			@RequestParam("nombres") String nombres
			){

		String pagina_anterior = request.getHeader("referer");

		try {
			System.out.println("Iniciando lectura de Txt");
	        List<String> listaNombres = Arrays.asList(nombres.split("\\r?\\n"));
	        
	        listaNombres.toString();
        	//TODO agregar validaciones
	        for (String nombre : listaNombres) {
				
	        	voluntarioService.saveVoluntario(
	        			Voluntario.builder()
	        			.dni("")
	        			.edad("")
	        			.correo("")
	        			.Id(voluntarioService.UltimoId())
	        			.nombre(nombre)
	        			.estado(true).build());
			}
			System.out.println("Finalizando lectura de Txt");

	        		
		} catch (Exception e) {
			System.out.println(Mensajes.error("VOLUNTARIO", "Registro") + e.getMessage());
		} 
		return "redirect:/dashboard_voluntario";		

		
	}

}
