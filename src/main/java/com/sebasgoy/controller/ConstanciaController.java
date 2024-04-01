package com.sebasgoy.controller;

import com.sebasgoy.Parser.PlantillaParser;
import com.sebasgoy.constantes.Plantillas;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.UbicacionConstancias;
import com.sebasgoy.service.ParticipanteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ConstanciaController {

    private final ParticipanteService participanteService;
     @GetMapping("/generar_voluntario_constancia/{id}")
    public String generar_voluntario_constancia(@PathVariable("id") Long idParticipante,
    	        HttpServletRequest request,Model model) throws Exception {
        String paginaAnterior = request.getHeader("referer");
        Participante participante = participanteService.findById(idParticipante);
        Optional<UbicacionConstancias> ubicacionConstancias = Optional.ofNullable(participante.getActividad().getUbicacionConstancias());
        if (ubicacionConstancias.isPresent() && Boolean.TRUE.equals(participante.getIsParticipant())){
            System.out.println("Ruta : " + ubicacionConstancias.get().getUbicacion()+"<--");
            Plantillas.convertirHTMLaPDF(
                    Plantillas.GenerarPlantillaActividad(
                            PlantillaParser.participanteToPlantillaDto(participante.getVoluntario(),participante.getActividad())
                    ),ubicacionConstancias.get().getUbicacion()+ "/" + participante.getVoluntario().getDni() + ".pdf");
        }

        return "redirect:" + paginaAnterior;

    }


}
