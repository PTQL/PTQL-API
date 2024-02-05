package Constantes;

public class Mensajes {

	public static String success(String entidad,String accion) {
		return accion+" de "+entidad+" OK :";
	}
	public static String error(String entidad,String accion) {
		return "Error al "+accion+" una "+entidad+" :";
	}
	
}
