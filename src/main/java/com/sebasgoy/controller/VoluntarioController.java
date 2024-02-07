package com.sebasgoy.controller;

 
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.service.TipoParticipacionService;
import com.sebasgoy.service.VoluntarioService;

import Constantes.Mensajes;
import Constantes.Modalidades;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

 

@Controller
@AllArgsConstructor
public class VoluntarioController {
	
	
	private final VoluntarioService voluntarioService;
	private final TipoParticipacionService participacionService;


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
							.equals(participacionService.findByDescripcion(Modalidades.LIBRE).getId()))
					.toList();
			
			List<Participante> moduloVoluntario = voluntario.getParticipante().stream()
					.filter(participante -> participante.getIdTipoParticipacion()
							.equals(participacionService.findByDescripcion(Modalidades.MODULO).getId()) )
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
	
	
	@GetMapping("/desactivar_voluntario/{id}")
	public String desactivar_voluntario(@PathVariable("id")Long id,Model model) {
		try {
			
			Voluntario voluntario = voluntarioService.findById(id);
			voluntario.changeEstate();
			voluntarioService.saveVoluntario(voluntario);
			
			model.addAttribute("voluntario", voluntarioService.getAll());
			System.out.println(Mensajes.success("Voluntario", "Actualizacion"));

		} catch (Exception e) {

			System.out.println(Mensajes.error("Voluntario", "Actualizacion").concat(e.toString()));
			
		}
		return "redirect:/dashboard_voluntario";

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

	




	
}
