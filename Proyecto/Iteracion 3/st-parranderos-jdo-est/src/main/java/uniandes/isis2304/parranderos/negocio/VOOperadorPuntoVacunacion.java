package uniandes.isis2304.parranderos.negocio;
/**
 * Interfaz para los métodos get de OPERADOR_PUNTO_VACUNACION
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivera
 */
public interface VOOperadorPuntoVacunacion {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del Punto de Vacunación del cual es operador
	 */
	public long getId_Punto_Vacunacion();
}
