package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de PUNTO_VACUNACION
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 *
 * @author Juan Manuel Rivera
 */
public interface VOPuntoVacunacion {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
      * @return El id del punto de vacunación
      */
	public long getId();
	
	/**
     * @return La localización del punto de vacunación
     */
	public String getLocalizacion();
	
	/**
     * @return El cupo máximo o aforo del punto de vacunación 
     */
	public int getCupoMaximo();
	
	/**
     * @return La capacidad de atención total diaria del punto de vacunación
     */
	public int getMaxCiudadanosPorDia();
	
	/**
     * @return El número total de ciudadanos vacunados en este punto de vacunación
     */
	public int getTotalVacunados();
	
	/**
     * @return La infraestructura de almacenamiento disponible en el punto de vacunación
     */
	public String getInfraestructuraAlmacenamiento();
	
	/**
     * @return La capacidad de almacenamiento de dosis del punto de vacunación
     */
	public int getCapacidadAlmacenamientoDosis();
	
	/**
     * @return El id de la Eps Regional a la que pertenece este punto de vacunación
     */
	public long getIdEpsRegional();
}