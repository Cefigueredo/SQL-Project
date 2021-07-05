package uniandes.isis2304.parranderos.negocio;

public interface VOEstadoDeSalud {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del estado de salud
	 */
	public long getId();
	/**
	 * 
	 * @return Descripción del estado de salud
	 */
	public String getDescripcion();
	/**
	 * 
	 * @return Id de la etapa en la que se prioriza el estado de salud
	 */
	public long getIdEtapa();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del estado de salud
	 */
	public String toString();
}
