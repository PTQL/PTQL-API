package com.sebasgoy.constantes;

import com.sebasgoy.dto.Voluntario;

public class ValoresPersonaRegex {

    public static final String DNI = "^([0-9]{8})$";
    public static final String CARNET_EXTRANJERIA = "^([0]{2}[0-9]{7})$";

    public static final String EDAD = "^(\\d{2})\\s?(?:años|AÑOS|años.)?$";
    public static final String CELULAR_PERUANO = "^(?:\\+?51)?(9[0-9]{8})$";

    //public static final String CELULAR_GLOBAL = "";
    public static final String CORREO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String NOMBRE = "^([a-zA-Z\\s]{0,100})$";

    public static boolean isValidVoluntario(Voluntario voluntario){
        return  voluntario.getDni().matches(ValoresPersonaRegex.DNI) &&
                voluntario.getEdad().matches(ValoresPersonaRegex.EDAD);
    }
    public static boolean isValidAsistencia(Voluntario voluntario){
        //TODO AGREGAR CAMPO CORREO
        return  voluntario.getDni().matches(ValoresPersonaRegex.DNI) &&
                voluntario.getTelefono().matches(ValoresPersonaRegex.CELULAR_PERUANO);
    }
    public static boolean isValidDni(String dni) {
    	return dni.matches(DNI);
    }
    


}
