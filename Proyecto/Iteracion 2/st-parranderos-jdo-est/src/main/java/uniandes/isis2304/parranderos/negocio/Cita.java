package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Clase para modelar el concepto Cita del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class Cita implements VOCita{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la cita
	 */
	private long id;
	
	/**
	 * La fecha y hora de la cita
	 */
	private Timestamp fechaYHora;
	
	/**
	 * La duración de la cita en minutos
	 */
	private int duracionCita;
	
	/**
	 * El estado en el que se encuentra la cita
	 */
	private String estadoCita;
	
	/**
	 * El ciudadano al que se le asigna la cita
	 */
	private long idCiudadano;
	
	/**
	 * El punto de vacunacion en donde se le asigna la cita
	 */
	private long idPuntoVacunacion;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public Cita() {
		this.id=0;
		this.fechaYHora=new Timestamp(0);
		this.duracionCita=0;
		this.estadoCita="";
		this.idCiudadano=0;
		this.idPuntoVacunacion=0;
	}
	
	/**
     * Constructor con valores
     */
	public Cita(long id, Timestamp fechaYHora, int duracionCita, String estadoCita,long idCiudadano, long idPuntoVacunacion) {
		this.id=id;
		this.fechaYHora=fechaYHora;
		this.duracionCita=duracionCita;
		this.estadoCita=estadoCita;
		this.idCiudadano=idCiudadano;
		this.idPuntoVacunacion=idPuntoVacunacion;
	}
	/**
	 * @return El id de la cita
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param El id de la cita
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return La fecha y hora de la cita
	 */
	public Timestamp getFechaYHora() {
		return fechaYHora;
	}
	/**
	 * @param La fecha y hora de la cita
	 */
	public void setFechaYHora(Timestamp fechaYHora) {
		this.fechaYHora = fechaYHora;
	}
	/**
	 * @return La duración de la cita en minutos
	 */
	public int getDuracionCita() {
		return duracionCita;
	}
	/**
	 * @param La duración de la cita en minutos
	 */
	public void setDuracionCita(int duracionCita) {
		this.duracionCita = duracionCita;
	}
	/**
	 * @return El estado en el que se encuentra la cita
	 */
	public String getEstadoCita() {
		return estadoCita;
	}
	/**
	 * @param El estado en el que se encuentra la cita
	 */
	public void setEstadoCita(String estadoCita) {
		this.estadoCita = estadoCita;
	}
	/**
	 * @return El ciudadano al que se le asigna la cita
	 */
	public long getIdCiudadano() {
		return idCiudadano;
	}
	/**
	 * @param El ciudadano al que se le asigna la cita
	 */
	public void setIdCiudadano(long idCiudadano) {
		this.idCiudadano = idCiudadano;
	}
	/**
	 * @return El punto de vacunacion en donde se le asigna la cita
	 */
	public long getIdPuntoVacunacion() {
		return idPuntoVacunacion;
	}
	/**
	 * @param El punto de vacunacion en donde se le asigna la cita
	 */
	public void setIdPuntoVacunacion(long idPuntoVacunacion) {
		this.idPuntoVacunacion = idPuntoVacunacion;
	}
	
	
}
