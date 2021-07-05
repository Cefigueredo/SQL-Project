package uniandes.isis2304.parranderos.negocio;

public interface VOProfesion {
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id de la profesión
	 */
	public long getId();
	/**
	 * 
	 * @return Descripción de la profesión
	 */
	public String getDescripcion();
	/**
	 * 
	 * @return Id de la etapa en la que se prioriza la profesión
	 */
	public long getIdEtapa();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la profesión
	 */
	public String toString();
}
