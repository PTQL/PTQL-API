package com.sebasgoy.controller;

import com.sebasgoy.Parser.PlantillaParser;
import com.sebasgoy.constantes.Mensajes;
import com.sebasgoy.constantes.Plantillas;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.UbicacionConstancias;
import com.sebasgoy.dto.response.StatusVoluntarioModulo;
import com.sebasgoy.service.ModuloService;
import com.sebasgoy.service.ParticipanteService;
import com.sebasgoy.service.UbicacionConstanciasService;
import com.sebasgoy.service.VoluntarioService;
import com.sebasgoy.util.Tools;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ConstanciaController {

    private final ParticipanteService participanteService;
    private final ModuloService moduloService;
    private final VoluntarioService voluntarioService;
    private final UbicacionConstanciasService ubicacionConstanciasService;

    @GetMapping("/generar_voluntario_constancia/{id}")
    public String generar_voluntario_constancia(@PathVariable("id") Long idParticipante ,
                                                HttpServletRequest request , Model model) throws Exception {
        Participante participante = participanteService.findById(idParticipante);
        Optional<UbicacionConstancias> ubicacionConstancias = Optional.ofNullable(participante.getActividad().getUbicacionConstancias());
        if (ubicacionConstancias.isPresent() && Boolean.TRUE.equals(participante.getIsParticipant())) {
            System.out.println("Ruta : " + ubicacionConstancias.get().getUbicacion() + "<--");
            Plantillas.convertirHTMLaPDF(
                    Plantillas.GenerarPlantillaActividad(
                            PlantillaParser.participanteToPlantillaDto(participante.getVoluntario() , participante.getActividad())
                    ) , ubicacionConstancias.get().getUbicacion() + "/" + participante.getVoluntario().getDni() + ".pdf");
        }

        return Tools.paginaAnterior(request);

    }

    @GetMapping("/generarConstanciaUnitariaModulo/{voluntarioId}")
    public String generarConstanciaUnitariaModulo(
            @PathVariable Long voluntarioId ,
            @RequestParam Long moduloId ,
            Model model,
            HttpServletRequest request){
        try {
            Modulo modulo = moduloService.findById(moduloId);
            Optional<UbicacionConstancias> ubicacionConstancias = Optional.ofNullable(modulo.getUbicacionConstancias());
            if (ubicacionConstancias.isPresent()) {
                System.out.println("Ruta : " + ubicacionConstancias.get().getUbicacion() + "<--");

                List<StatusVoluntarioModulo> lstaVoluntario = voluntarioService.getVoluntarioFromModuloHoursOkAndIsParticipant(modulo);

                StatusVoluntarioModulo statusVoluntario = lstaVoluntario.stream().filter(
                        statusVoluntarioModulo -> Objects.equals(statusVoluntarioModulo.getVoluntario().getId(), voluntarioId)
                    ).findFirst().orElseThrow();

                Plantillas.convertirHTMLaPDF(
                        Plantillas.GenerarPlantillaModulo(
                                PlantillaParser.statusVoluntarioToPlantillaDtoModulo(statusVoluntario, modulo)
                        ) , ubicacionConstancias.get().getUbicacion() + "/" + statusVoluntario.getVoluntario().getDni() + ".pdf");
            }

            Mensajes.Success("Archivo", "Generacion");
        }catch (Exception e){
            Mensajes.Error("Archivo", "Generacion",e.toString());
        }
        return Tools.paginaAnterior(request);




    }
}