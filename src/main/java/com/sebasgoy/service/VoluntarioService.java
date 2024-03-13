package com.sebasgoy.service;


import com.sebasgoy.constantes.Modalidades;
import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Modulo;
import com.sebasgoy.dto.Participante;
import com.sebasgoy.dto.Voluntario;
import com.sebasgoy.repository.IParticipanteRepository;
import com.sebasgoy.repository.IVoluntarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public List<Voluntario> getVoluntarioFromModuloHoursOkAndIsParticipant(Modulo modulo ){

        List<Voluntario> listaVoluntarios = iVoluntarioRepository.findVoluntarioByModuloId(modulo.getId());
        return listaVoluntarios.stream().filter(
                voluntario -> cumpleHoras(voluntario,modulo.getActividad())
        ).toList();
    }

    public Boolean cumpleHoras(Voluntario voluntario, List<Actividad> actividadList){
        Long horasMix = Modalidades.MIN_HORAS_MODULO;
        Long hrsVoluntario = 0L;
        for (Actividad a:
             actividadList) {
            Optional<Participante> participante = iParticipanteRepository.findByVoluntarioIdAndActividadId(voluntario.getId(),a.getId());
            if (participante.isPresent() &&
                    (participante.get().getIsParticipant() && participante.get().getTipoParticipacion().getId().equals(Modalidades.ID_MODULO))
            ){
                hrsVoluntario += a.obtenerDuracionActividad();
            }
        }
        return Objects.equals(horasMix , hrsVoluntario);


    }

	public List<Voluntario>  saveAndGetVoluntarios(List<Voluntario> lstVoluntarios) {

		 List<Voluntario> v = new ArrayList<>();
		 
		 lstVoluntarios.forEach(
				 x-> v.add(saveAndGetVoluntario(x)));
		 return v;
	}
    

}
