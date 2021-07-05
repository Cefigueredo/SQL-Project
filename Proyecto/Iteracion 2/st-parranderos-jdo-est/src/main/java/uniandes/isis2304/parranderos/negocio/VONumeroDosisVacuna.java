package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de NUMERODOSISVACUNA
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Juan Manuel Rivers
 */
public interface VONumeroDosisVacuna {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id de la vacuna
	 */
	public long getIdVacuna();
	
	/**
	 * @return El número de dosis de la vacuna
	 */
	public int getNumeroDosis();
}
