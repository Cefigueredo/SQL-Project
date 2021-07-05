package uniandes.isis2304.parranderos.negocio;

public interface VOEtapa {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del ciudadano
	 */
	public long getId();
	/**
	 * 
	 * @return La fase en la que se encuentra la etapa
	 */
	public int getFase();
	/**
	 * 
	 * @return Descripción de la etapa
	 */
	public String getDescripcion();
	/**
	 * 
	 * @return Edad mínima para estar en la etapa
	 */
	public int getEdadMinima();
	/**
	 * 
	 * @return Edad Máxima para estar en la etapa
	 */
	public int getEdadMaxima();
	/**
	 * 
	 * @return Id de Vacunacion
	 */
	public long getIdVacunacion();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la etapa
	 */
	public String toString();
}
