package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de RESTRICCION
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivers
 */
public interface VORestriccion {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
	 * @return El id de la restricción
	 */
	public long getId();
	
	/**
	 * @return La descripción de la restricción
	 */
	public String getDescripcion();
	
	/**
	 * @return El id de la vacuna de la cuál indica las restricciones
	 */
	public long getIdVacuna();
}
