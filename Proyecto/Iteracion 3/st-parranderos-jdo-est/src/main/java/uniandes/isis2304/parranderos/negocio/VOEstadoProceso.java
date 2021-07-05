package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

/**
 * Interfaz para los métodos get de ESTADO_PROCESO
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivera
 */
public interface VOEstadoProceso {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del Estado 
	 */
	public long getId();
	
	/**
	 * @return El estado correspondiente
	 */
	public String getEstado();
	
	/**
	 * @return La fecha en que cambio el estado 
	 */
	public Date getFecha_Cambio();
	
	/**
	 * @return El comentario que se hizo al cambiar el estado
	 */
	public String getComentario_Descripcion();
	
	/**
	 * @return El id del ciudadano al que corresponde el estado
	 */
	public long getId_Ciudadano();
}
