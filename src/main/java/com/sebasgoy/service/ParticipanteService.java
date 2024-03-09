package com.sebasgoy.service;


import com.sebasgoy.constantes.Modalidades;
import com.sebasgoy.constantes.ValoresPersonaRegex;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.AsistenciaRespone;
import com.sebasgoy.dto.response.VoluntarioResponse;
import com.sebasgoy.repository.IParticipanteRepository;
import com.sebasgoy.repository.IVoluntarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipanteService {
	
    private IParticipanteRepository iParticipanteRepository;
    private IVoluntarioRepository iVoluntarioRepository;
    private ActividadService actividadService;
    private VoluntarioService voluntarioService;
    private TipoParticipacionService tipoParticipacionService;
    public Participante findById(Long id){
        return  iParticipanteRepository.findById(id).orElse(new Participante());
    }

    public Participante findByDniAndActividad(String dni,Actividad actividad){
        return iParticipanteRepository.findByVoluntario_DniAndActividad(dni, actividad);
    }
    public List<Participante> getAll(){
        return iParticipanteRepository.findAll();
    }
    public void saveParticipante(Participante participante) {

    	
        iParticipanteRepository.save(participante);
    }
    
    public void saveVoluntariosToActividad(List<Long> ListIdVoluntarios, Long idActividad,Long idModalidad,Boolean isParticipants ) {
        System.out.println("Guardando lista de voluntarios en una actividad");
        for (Long idVoluntario : ListIdVoluntarios) {
			iParticipanteRepository.save(
					Participante.builder()
					.idActividad(idActividad)
					.idVoluntario(idVoluntario)
					.isParticipant(isParticipants)
					.idTipoParticipacion(idModalidad)
					.build()
			);
		}
    }
    
    public List<Participante> saveVoluntariosToActividadAndGetParticipantes(List<Voluntario> ListIdVoluntarios, Long idActividad,Long idModalidad) {
        System.out.println("Guardando lista de voluntarios en una actividad");
        List<Participante> v = new ArrayList<>();
        for (Voluntario voluntario : ListIdVoluntarios) {
			v.add( iParticipanteRepository.save(
					Participante.builder()
					.idActividad(idActividad)
					.idVoluntario(voluntario.getId())
					.isParticipant(false)
					.idTipoParticipacion(idModalidad)
					.build()
			));
		}
        return v;
    }

    public boolean existeParticipanteParaVoluntarioYActividad(Long idVoluntario, Long idActividad) {
        return iParticipanteRepository.findByVoluntarioIdAndActividadId(idVoluntario, idActividad).isPresent();
    }

    public Participante findParticipantefromVoluntarioYActividad(Long idVoluntario, Long idActividad) {
        return iParticipanteRepository.findByVoluntarioIdAndActividadId(idVoluntario, idActividad).orElse(null);
    }
    public Optional<Participante> findParticipantefromVoluntarioYActividadOptional(Long idVoluntario, Long idActividad) {
        return iParticipanteRepository.findByVoluntarioIdAndActividadId(idVoluntario, idActividad);
    }
    public void deleteParticipante(Participante participante) {
    	iParticipanteRepository.delete(participante);
    }
    public void deleteParticipanteById(Long id) {
    	iParticipanteRepository.deleteById(id);
    }

    public List<Participante> findParticipantesFromActividad(Long idActividad){
        return iParticipanteRepository.findByIdActividad(idActividad);

    }

    public List<Participante> getLibresFromListParticipante(Long idActividad){
        List<Participante> listParticipantes = iParticipanteRepository.findByIdActividad(idActividad);
        return listParticipantes.stream().filter( participante -> (participante.getIdTipoParticipacion() == Modalidades.ID_LIBRE)&& (participante.getIsParticipant() == Boolean.TRUE)).collect(Collectors.toList());

    }



    public void changeParticipacionById(Long id ) {
    	Participante participante = iParticipanteRepository.findById(id).get();
    	
    	participante.changeParticipacion();
    	iParticipanteRepository.save(participante);
    	
    }
    
    public void makeParticipantionTrue(List<Participante> lstParticipantes){
    	lstParticipantes.forEach(
    			participante -> {
    				participante.setIsParticipant(true);   		
    				saveParticipante(participante);
    			}	
    			);

    }

    public void deleteListOfParticipante(List<Participante> lstaParticipantesFromActividad) {
        System.out.println("Eliminando lista de participantes de una actividad");
        for (Participante participante: lstaParticipantesFromActividad) {
            iParticipanteRepository.delete(participante);
        }


    }

    public AsistenciaRespone verDetallesListOfDni(Long idActividad , Model model , String dnis) {

        System.out.println("Iniciando lectura de Txt");
        Actividad actividad = actividadService.findById(idActividad);
        AsistenciaRespone asistenciaRespone = new AsistenciaRespone();


        List<String> listaDni= Arrays.asList(dnis.split("\\r?\\n"));

        for (String dni : listaDni) {
            if (dni.trim().matches(ValoresPersonaRegex.DNI)) {
                Participante participante = findByDniAndActividad(dni, actividad);
                if (participante != null) {
                    asistenciaRespone.getLstRegistrados().add(participante);
                } else {
                    Voluntario voluntario = voluntarioService.findByDni(dni);
                    if (voluntario != null) {
                        asistenciaRespone.getLstNoRegistrados().add(voluntario);
                    } else {
                        asistenciaRespone.getLstNoRegistrados().add(Voluntario.builder().estado(true).dni(dni).build());
                    }
                }
            } else {
                asistenciaRespone.getLstDniNoValidos().add(dni);
            }
        }

        asistenciaRespone.setListDni(getTxtListDni(listaDni));
        return asistenciaRespone;


    }

    public String getTxtListDni(List<String> listDni) {
        if (listDni.size() == 0) {
            return "";
        }else {
            StringBuilder sb = new StringBuilder();
            for (String dni : listDni) {
                sb.append(dni).append("\n");
            }
            return sb.toString();

        }


    }

    public Participante crearParticipante(Long idActividad , Long idVoluntario , String modalidad,Boolean participacion) {
        return Participante.builder()
                .idActividad(idActividad)
                .isParticipant(participacion)
                .idVoluntario(idVoluntario)
                .idTipoParticipacion(tipoParticipacionService.findByDescripcion(modalidad).getId())
                .build();
    }

    public Boolean isValid(VoluntarioResponse voluntarioResponse) {

        return (voluntarioResponse.getListVoluntarioInvalido().isEmpty()) &&
                (!voluntarioResponse.getListVoluntarioValido().isEmpty());

    }



}
