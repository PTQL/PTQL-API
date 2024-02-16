package com.sebasgoy.controller;




import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
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

import org.xhtmlrenderer.pdf.ITextRenderer;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
			Model model){
		String pagina_anterior = request.getHeader("referer");

		try {
			if (path == null || path.isEmpty() ){
				System.out.println(Mensajes.error("Archivo", "Generacion","No se selecciono carpeta"));
				return "redirect:"+pagina_anterior ;
			}else{
				System.out.println("Ruta : "+path);
			}


			List<PlantillaDto> listPlantillaDto = listParticipanteToPlantillaDto( participanteService.getLibresFromListParticipante(idActividad),actividadService.findById(idActividad));
			String plantilla = "";
			for ( PlantillaDto plantillaDto: listPlantillaDto ) {
				plantilla = Plantillas.GenerarPlantilla(plantillaDto);
				String rutaUnitaria = path +"/"+plantillaDto.getNombreVoluntario()+".pdf";
				convertirHTMLaPDF(plantilla,rutaUnitaria );
			}

		}catch (Exception e){

			System.out.println(Mensajes.error("Archivo", "Generacion").concat(e.toString()));

		}

		return "redirect:"+pagina_anterior ;


	}

	private void convertirHTMLaPDF(String html, String rutaPDF) throws Exception {

		try(OutputStream outputStream =  new FileOutputStream(rutaPDF)) {

			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(html);

			renderer.layout();
			renderer.createPDF(outputStream, true);


		}catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	private List<PlantillaDto> listParticipanteToPlantillaDto(List<Participante> libresFromListParticipante , Actividad actividad) {

		List<PlantillaDto> dtos = new ArrayList<>();

		String fechaActividad = PlantillaParser.parserFechaActividad(actividad.getFechaActividad());
		String fechaGeneralActividad = PlantillaParser.parserFechaGeneralActividad(actividad.getFechaActividad());
		String horasActividad = String.valueOf( actividad.obtenerDuracionActividad());
		String nombreActividad = actividad.getNombreActividad();
		String ubicacionActividad = actividad.getUbicacionActividad();
		try {
			for (Participante participante : libresFromListParticipante) {

				dtos.add(
						PlantillaDto.builder()
								.fechaActividad(fechaActividad)
								.fechaGeneralActividad(fechaGeneralActividad)
								.horasActividad(horasActividad)
								.nombreActividad(nombreActividad)
								.nombreVoluntario(participante.getVoluntario().getNombre())
								.ubicacionActividad(ubicacionActividad)
								.build()
				);

			}

			System.out.println("Parseo de entidades a PlantillaDTO ok");


		}catch (Exception e){
			System.out.println(Mensajes.error("Error al convertir participante a plantillaDTo").concat(e.toString()));
		}


		return dtos;
	}

}
