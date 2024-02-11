package com.sebasgoy.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.sebasgoy.constantes.Modalidades;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.service.ParticipanteService;
import com.sebasgoy.service.VoluntarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.service.ActividadService;
import com.sebasgoy.service.ModuloService;

import com.sebasgoy.constantes.Mensajes;
 
@Controller
@AllArgsConstructor
public class ModuloController {

	private final ModuloService moduloService;
	private final ActividadService actividadService;
	private final VoluntarioService voluntarioService;
	private final ParticipanteService participanteService;
	@GetMapping("/dashboard_modulo")
	public String cargarDashboardModulo(Model model) {
		model.addAttribute("listaModulo", moduloService.findActivos() );
		return "DashboardModulo";
	}
	
	@GetMapping("/generar_modulo")
	public String cargarCrudModulo(Model model) {
		try {
			
			Modulo modulo = Modulo.builder().id(moduloService.UltimoId()).build();
			moduloService.saveModulo(modulo);

			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());
			model.addAttribute("modulo", modulo);
			System.out.println(Mensajes.success("MODULO", "REGISTRO"));
			model.addAttribute("mensaje",Mensajes.success("MODULO", "REGISTRO"));
		} catch (Exception e) {
			System.out.println(Mensajes.error("MODULO", "REGISTRO").concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.error("MODULO", "REGISTRO").concat(e.toString()) );
		}
		return "FormNewModulo";
	}
	
	@GetMapping("/editar_modulo/{id}")
	public String cargarCrudModulo(@PathVariable("id") Long id,Model model) {
		try {
			
			Modulo modulo = moduloService.findById(id) ;

			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());
			model.addAttribute("modulo", modulo);
			System.out.println( Mensajes.success("MODULO", "REGISTRO"));
			model.addAttribute("mensaje", Mensajes.success("MODULO", "REGISTRO"));
		} catch (Exception e) {
			System.out.println(Mensajes.error("MODULO", "REGISTRO").concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.error("MODULO", "REGISTRO").concat(e.toString()) );
		}
		return "FormNewModulo";
	}
	
	@GetMapping("/info_modulo/{id}")
	public String infoModulo(@PathVariable("id") Long id,Model model) {
		
		try {
			//Logica :
			// En el modulo todas las actividades deberian tener los mismo volutnarios
			//pero se generan las tablas participaciones en la actividad para contabilizar su participacion
			// segun regla de negocio , deberian tener como minimo 6 actividades o 36hrs de actividad para generar constancia

			List<Voluntario> listaVoluntarioModulo = voluntarioService.findVoluntarioOfModulo(id);

			model.addAttribute("modulo", moduloService.findById(id));
 			model.addAttribute("listaVoluntario",listaVoluntarioModulo );

			System.out.println( Mensajes.success("MODULO", "BUSQUEDA"));
			model.addAttribute("mensaje",  Mensajes.success("MODULO", "BUSQUEDA"));
			
			return "/InfoModulo";
			
		} catch (Exception e) {
			System.out.println(Mensajes.error("MODULO", "BUSQUEDA").concat(e.toString()));

			model.addAttribute("mensaje", Mensajes.error("MODULO", "BUSQUEDA").concat(e.toString()) );
		}
		return "redirect:/dashboard_modulo";
		
		
	}



	
	@GetMapping("/addActividadToModulo/{actividadId}")
	public String agregarActividad(@PathVariable Long actividadId , @RequestParam Long moduloId ,Model model){
		
		Modulo modulo = moduloService.findById(moduloId);		
		Actividad actividad = actividadService.findById(actividadId);

		List<Actividad> ListActividad = modulo.getActividad();

		if (!ListActividad.contains(actividad) ) {
			actividad.setModulo(modulo);
			actividadService.saveActividad(actividad);
			
			ListActividad.add(actividad);
			modulo.setActividad(ListActividad );
			model.addAttribute("modulo", modulo);
			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());
		
		}else {
			model.addAttribute("modulo", modulo);
			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());
		}
		return "FormNewModulo";
	}
	
	@GetMapping("/deleteActividadToModulo/{actividadId}")
	public String quitarActividad(@PathVariable Long actividadId , @RequestParam Long moduloId ,Model model){
		
		Modulo modulo = moduloService.findById(moduloId);		
		Actividad actividad = actividadService.findById(actividadId);

		List<Actividad> ListActividad = modulo.getActividad();	
		if (ListActividad.contains(actividad) ) {
			actividad.setModulo(null);
			actividadService.saveActividad(actividad);
			
			ListActividad.remove(actividad);
			modulo.setActividad(ListActividad );
			model.addAttribute("modulo", modulo);
			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());

		}else {
			model.addAttribute("modulo", modulo);
			model.addAttribute("listaActividades",actividadService.findActivosSinModulo());
			
		}
		return "FormNewModulo";
	}
	
	@PostMapping("/guardar_modulo")
	public String guardar_modulo(@ModelAttribute Modulo modulo,Model model) {
	
		try {
			
			moduloService.saveModulo(modulo);
			
			
			System.out.println( Mensajes.success("MODULO", "REGISTRO"));
			model.addAttribute("mensaje", Mensajes.success("MODULO", "REGISTRO"));
		} catch (Exception e) {
			System.out.println( Mensajes.error("MODULO", "REGISTRO").concat(e.toString()) );

			model.addAttribute("mensaje",Mensajes.error("MODULO", "REGISTRO").concat(e.toString()) );
		}
		
		return "redirect:/dashboard_modulo";
	}

	@GetMapping("/regresar_dashboard/{id}")
	public String regresar_dashboard(@PathVariable("id") Long id,Model model) {
	
		try {
			Modulo modulo = moduloService.findById(id);		
			if (
					modulo.getNombre().isEmpty() &&
					modulo.getActividad().isEmpty()
					) {				
				moduloService.deleteModulo(modulo);
				System.out.println(Mensajes.error("MODULO", "ELIMINACION"));
				model.addAttribute("mensaje", Mensajes.error("MODULO", "ELIMINACION"));
			}
			
			
			
		} catch (Exception e) {
			System.out.println( Mensajes.error("MODULO", "ELIMINACION").concat(e.toString()) );

			model.addAttribute("mensaje", Mensajes.error("MODULO", "ELIMINACION").concat(e.toString()) );
		}
		
		return "redirect:/dashboard_modulo";
	}

	@GetMapping("/retirarActividadFromModulo/{actividadId}")
	public String retirarActividadFromModulo(@PathVariable Long actividadId , @RequestParam Long moduloId , Model model, HttpServletRequest request){
		String pagina_anterior = request.getHeader("referer");
		try {
			Modulo modulo = moduloService.findById(moduloId);
			Actividad actividad = actividadService.findById(actividadId);


			List<Participante> lstaParticipantesFromActividad  = participanteService.findParticipantesFromActividad(actividadId);


			participanteService.deleteListOfParticipante(lstaParticipantesFromActividad.stream().filter(participante -> participante.getIdTipoParticipacion() == Modalidades.ID_MODULO).collect(Collectors.toList()));

			actividad.setModulo(null);
			actividadService.saveActividad(actividad);
			modulo.getActividad().remove(actividad);
			moduloService.saveModulo(modulo);

		} catch (Exception e) {
			System.out.println(Mensajes.error("Actividad from Modulo", "ELIMINACION").concat(e.toString()));
			model.addAttribute("mensaje" , Mensajes.error("Actividad from Modulo" , "ELIMINACION").concat(e.toString()));

		}
		return "redirect:"+pagina_anterior;
	}





	
}
