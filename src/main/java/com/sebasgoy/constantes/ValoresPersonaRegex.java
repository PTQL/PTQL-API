package com.sebasgoy.constantes;

import com.sebasgoy.dto.Voluntario;

public class ValoresPersonaRegex {

    public static final String DNI = "^([0-9]{8})$";
    public static final String CARNET_EXTRANJERIA = "^([0]{2}[0-9]{7})$";

    public static final String EDAD = "^([0-9][0-9])$";
    public static final String CELULAR_PERUANO = "^(?:\\+?51)?(9[0-9]{8})$";

    //public static final String CELULAR_GLOBAL = "";
    public static final String CORREO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n";
    public static final String NOMBRE = "^([a-zA-Z\\s]{0,50})$";

    public static boolean isValidVoluntario(Voluntario voluntario){
    	

    
        return  voluntario.getDni().matches(ValoresPersonaRegex.DNI) &&
                voluntario.getEdad().matches(ValoresPersonaRegex.EDAD);
    }


}
