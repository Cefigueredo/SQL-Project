package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

public interface VOCiudadano {

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del ciudadano
	 */
	public long getId();
	/**
	 * @return nombre del ciudadano
	 */
	public String getNombre();
	/**
	 * 
	 * @return documento de identificación del ciudadano
	 */
	public String getDocumento();
	/**
	 * 
	 * @return estado actual en el que se encuentra el ciudadano
	 */
	public int getEstadoActual();
	/**
	 * 
	 * @return fecha de nacimiento del ciudadano
	 */
	public Date getFechaNacimiento();
	/**
	 * 
	 * @return dirección de residencia del ciudadano
	 */
	public String getDireccion();
	/**
	 * 
	 * @return ciudad de residencia del ciudadano
	 */
	public String getCiudad();
	/**
	 * 
	 * @return número de contacto del ciudadano
	 */
	public String getNumeroContacto();
	/**
	 * 
	 * @return id de Vacunacion
	 */
	public long getIdVacunacion();
	/**
	 * 
	 * @return id de la oficina EPS Regional al que se asocia el ciudadano
	 */
	public long getIdOficinaEPS();
	/**
	 * 
	 * @return id del punto de vacunación al que se asocia el ciudadano
	 */
	public long getIdPuntoVacunacion();
	/**
	 * 
	 * @return id de la profesión del ciudadano
	 */
	public long getIdProfesion();
	/**
	 * 
	 * @return id del estado de salud en el que se encuentra el ciudadano
	 */
	public long getIdEstadoSalud();
	/**
	 * 
	 * @return id de la etapa en la que se encuentra el ciudadano
	 */
	public long getIdEtapa();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del ciudadano
	 */
	public String toString();
}
