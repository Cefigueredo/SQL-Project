package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de BAR.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VOVacunacion {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id de Vacunacion
	 */
	public long getId();
	
	/**
	 * 
	 * @return Las dosis totales de Vacunacion
	 */
	public int getDosisTotales();
	
	/**
	 * 
	 * @return Las dosis aplicadas de Vacunacion
	 */
	public int getDosisAplicadas();
	
	/**
	 * 
	 * @return Las dosis perdidas de Vacunacion
	 */
	public int getDosisPerdidas();
	
	/**
	 * 
	 * @return La etapa actual en la que está Vacunacion
	 */
	public long getIdEtapaActual();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de Vacunacion
	 */
	public String toString();
}
