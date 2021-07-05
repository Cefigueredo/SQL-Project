package uniandes.isis2304.parranderos.negocio;

public interface VOOficinaEPSRegional {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del estado de salud
	 */
	public long getId();
	/**
	 * 
	 * @return Nombre e la oficina EPS regional
	 */
	public String getNombre();
	/**
	 * 
	 * @return NIT de la oficina EPS regional
	 */
	public String getNIT();
	/**
	 * 
	 * @return Región a la que pertenece la oficina EPS regional
	 */
	public String getRegion();
	/**
	 * 
	 * @return Dirección de la oficina EPS regional
	 */
	public String getDireccion();
	/**
	 * 
	 * @return Número de contacto de la oficina EPS regional
	 */
	public String getNumeroContacto();
	/**
	 * 
	 * @return Ciudad de la oficina EPS regional
	 */
	public String getCiudad();
	/**
	 * 
	 * @return Id Vacunacion
	 */
	public long getIdVacunacion();
	

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del estado de salud
	 */
	public String toString();
}
