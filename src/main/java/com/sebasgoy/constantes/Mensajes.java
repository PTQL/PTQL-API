package com.sebasgoy.constantes;

public class Mensajes {

	public static String success(String entidad,String accion) {
		return accion+" de "+entidad+" OK :";
	}
	public static String error(String entidad,String accion) {
		return "Error al "+accion+" una "+entidad+" :";
	}

	public static String error(String entidad,String accion,String mensaje) {
		return "Error al "+accion+" una "+entidad+" ,"+"Mensaje :"+mensaje+" ";
	}

	public static String error(String mensaje) {
		return "Mensaje :"+mensaje+" ";
	}
}
