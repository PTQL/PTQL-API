package com.sebasgoy.service;


import com.sebasgoy.constantes.Modalidades;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.dto.response.StatusVoluntarioModulo;
import com.sebasgoy.repository.IParticipanteRepository;
import com.sebasgoy.repository.IVoluntarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoluntarioService {

    private IVoluntarioRepository iVoluntarioRepository;
    private IParticipanteRepository iParticipanteRepository;

    public Long UltimoId() {
        List<Voluntario> activos = getAll();

        if (activos.isEmpty()) {
            return 1L;
        }
        Voluntario ultimoElemento = activos.get(activos.size() - 1);

        return ultimoElemento.getId() + 1;
    }


    public Voluntario findById(Long id){
        return  iVoluntarioRepository.findById(id).orElse(new Voluntario());
    }
    public Optional<Voluntario> findByIdOptional(Long id){
        return  iVoluntarioRepository.findById(id);
    }
    
    public void deleteVoluntario(Voluntario voluntario) {
    	
    	iVoluntarioRepository.delete(voluntario);
    	
    }

    public List<Voluntario> findVoluntarioOfModulo(Long idModulo){
        List<Voluntario> findVoluntarioByModuloId = iVoluntarioRepository.findVoluntarioByModuloId(idModulo);

    	if (findVoluntarioByModuloId.isEmpty()) {
			return new ArrayList<>();
		}
    	
		return findVoluntarioByModuloId;
    }


    public List<Voluntario> getListVoluntarioFromListParticipante(List<Participante> lstParticipante ){
        return lstParticipante.stream().map(
                Participante::getVoluntario).collect(Collectors.toList());
    }

    public Voluntario findByDni(String dni){
        return  iVoluntarioRepository.findByDni(dni).orElse(null);
    }
    public boolean existsByDni(String dni){
        return  iVoluntarioRepository.existsByDni(dni);
    }



    public boolean validarExistencia(Voluntario voluntario) {

        return findByDni(voluntario.getDni()) != null;

    }
    public List<Voluntario> getAll(){

        return iVoluntarioRepository.findAll();
    }
    public List<Voluntario> getAllWithoutActivity(Actividad actividad){

        List<Voluntario> listVoluntario = iVoluntarioRepository.findAll();

        return listVoluntario.stream()
                .filter(voluntario -> voluntario.getParticipante().stream()
                        .noneMatch(participante -> Objects.equals(participante.getIdActividad(), actividad.getId())))
                .collect(Collectors.toList());
    }
    public void saveVoluntario(Voluntario voluntario) {
    	if (findByDni(voluntario.getDni()) == null) {
        	if (voluntario.getId() == null || voluntario.getId().toString().isEmpty()) {
        		voluntario.setId(UltimoId());
    		}
		}
        iVoluntarioRepository.save(voluntario);
    }
    public void persistirVoluntario(Voluntario voluntario) {
        voluntario.setId(findByDni(voluntario.getDni()).getId());
        voluntario.setEstado(true);
        saveVoluntario(voluntario);
    }
	public void  saveVoluntarios(List<Voluntario> lstVoluntarios) {

		lstVoluntarios.forEach(
                this::saveVoluntario
		);
	}
    
    
    public Voluntario saveAndGetVoluntario(Voluntario voluntario) {
    	if (findByDni(voluntario.getDni()) == null) {
        	if (voluntario.getId() == null || voluntario.getId().toString().isEmpty()) {
        		voluntario.setId(UltimoId());
    		}
		}
    	voluntario.setEstado(true);
        return iVoluntarioRepository.save(voluntario);
    }
    public List<StatusVoluntarioModulo> getVoluntarioFromModuloHoursOkAndIsParticipant(Modulo modulo){
        Long horasMin = ObtenerHorasMinimasModulo(modulo);
        return findVoluntarioByModuloId(modulo.getId()).stream()
                .map(voluntario -> {
                    long horas = obtenerHoras(voluntario, modulo.getActividad());
                    return new AbstractMap.SimpleEntry<>(voluntario, horas);
                })
                .filter(entry -> cumpleHoras(horasMin, entry.getValue())) //ES SOLO UN EQUALS
                .map(entry -> new StatusVoluntarioModulo(entry.getKey(), entry.getValue()))
                .toList();
    }

    private Long ObtenerHorasMinimasModulo(Modulo modulo) {
        /**
         * Ejemplo:
         * Actividades de modulo : 7
         * MinimoActividaes : 5
         * Valor a iterar  : 2
         */

        Long totalHoras = 0L;
        List<Actividad> actividades = modulo.getActividad();
        int iterador = actividades.size()-modulo.getMinimoActividades();
        // Si hay menos de dos actividades, simplemente suma todas las horas
        if (actividades.size() <= 2) {
            for (Actividad actividad : actividades) {
                totalHoras += actividad.obtenerDuracionActividad();
            }
        } else {
            // Itera sobre las actividades exceptuando las dos primeras
            for (int i = iterador; i < actividades.size(); i++) {
                totalHoras += actividades.get(i).obtenerDuracionActividad();
            }
        }

        return totalHoras;
    }

    public List<Voluntario> findVoluntarioByModuloId(Long id){
        return iVoluntarioRepository.findVoluntarioByModuloId(id);
    }

    public Long obtenerHoras(Voluntario voluntario, List<Actividad> actividadList){
        Long hrsVoluntario = 0L;
        for (Actividad a:
             actividadList) {
            try {
                Participante participante = iParticipanteRepository.findByVoluntarioIdAndActividadId(voluntario.getId(),a.getId());
                if ((participante.getIsParticipant() && participante.getTipoParticipacion().getId().equals(Modalidades.ID_MODULO))){
                    hrsVoluntario += a.obtenerDuracionActividad();
                }
            }catch (Exception e){
                System.out.println(e +"\n"+ voluntario.toString() );//Porsiaca aca venia el error de stackoverflow
                //TODO crear metodos toString en todos los dto
            }

        }
        return hrsVoluntario;
    }
    public Boolean cumpleHoras( Long horasMin,Long hrsVoluntario){
        return hrsVoluntario>=horasMin;
    }




	public List<Voluntario>  saveAndGetVoluntarios(List<Voluntario> lstVoluntarios) {

		 List<Voluntario> v = new ArrayList<>();
		 
		 lstVoluntarios.forEach(
				 x-> v.add(saveAndGetVoluntario(x)));
		 return v;
	}
    

}
