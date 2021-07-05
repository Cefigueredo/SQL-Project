package uniandes.isis2304.parranderos.negocio;
/**
 * Interfaz para los métodos get de ADMIN_PUNTO_VACUNACION
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivera
 */
public interface VOAdminPuntoVacunacion {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del Punto de Vacunación del cual es administrador
	 */
	public long getId_Punto_Vacunacion();
}
