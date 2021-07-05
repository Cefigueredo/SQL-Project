package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los métodos get de CITA
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivers
 */
public interface VOCita {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id de la cita
	 */
	public long getId();
	
	/**
	 * @return La fecha y hora de la cita
	 */
	public Timestamp getFechaYHora();
	
	/**
	 * @return La duración de la cita en minutos
	 */
	public int getDuracionCita();
	
	/**
	 * @return El estado en el que se encuentra la cita
	 */
	public String getEstadoCita();
	
	/**
	 * @return El ciudadano al que se le asigna la cita
	 */
	public long getIdCiudadano();
	
	/**
	 * @return El punto de vacunacion en donde se le asigna la cita
	 */
	public long getIdPuntoVacunacion();
}
