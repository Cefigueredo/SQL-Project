package uniandes.isis2304.parranderos.negocio;


/**
* Interfaz para los métodos get de DOSIS
* Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
*
* @author Juan Manuel Rivera
*/
public interface VODosis {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
      * @return El id de la dosis
      */
	public long getId();
	
	/**
     * @return El lote de la dosis
     */
	public String getLote();
	
	/**
     * @return El ciudadano al que se le aplicó la dosis
     */
	public long getIdCiudadano();
	
	/**
     * @return La oficina regional de la EPS a la que fue asignada la vacuna
     */
	public long getIdOficinaEpsRegional();
	
	/**
     * @return El punto de vacunación al que fue asignada la dosis
     */
	public long getIdPuntoVacunacion();
	
	/**
     * @return La vacuna de la cuál se tiene una dosis
     */
	public long getIdVacuna();
	
	/**
     * @return Indica si la dosis está disponible. Si no puede
     * deberse a algún daño en la dosis
     */
	public boolean isDisponible();
}
