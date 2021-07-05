package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de VACUNA
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Juan Manuel Rivera
 */
public interface VOVacuna {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id de la vacuna
	 */
	public long getId();
	
	/**
	 * @return El nombre de la vacuna
	 */
	public String getNombre();
	
	/**
	 * @return Los requerimientos de almacenamiento de la vacuna
	 */
	public String getRequerimientosDeAlmacenamiento();
}
