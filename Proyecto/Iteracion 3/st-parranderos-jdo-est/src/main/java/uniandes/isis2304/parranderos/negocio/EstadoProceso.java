package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

/**
 * Clase para modelar el concepto Estado Proceso del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class EstadoProceso implements VOEstadoProceso{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del estado
	 */
	private long id;
	
	/**
	 * El estado correspondiente
	 */
	private String estado;
	
	/**
	 * La fecha en que cambio el estado 
	 */
	private Date fecha_Cambio;
	
	/**
	 * El comentario que se hizo al cambiar el estado
	 */
	private String comentario_Descripcion;
	
	/**
	 * El id del ciudadano al que corresponde el estado
	 */
	private long id_Ciudadano;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public EstadoProceso() {
		this.id=0;
		this.estado="";
		this.fecha_Cambio=new Date(0);
		this.comentario_Descripcion="";
		this.id_Ciudadano=0;
	}
	
	/**
     * Constructor con valores
     */
	public EstadoProceso(long id,String estado, Date fecha_Cambio, String comentario_Descripcion,long id_Ciudadano) {
		this.id=id;
		this.estado=estado;
		this.fecha_Cambio=fecha_Cambio;
		this.comentario_Descripcion=comentario_Descripcion;
		this.id_Ciudadano=id_Ciudadano;
	}
	
	/**
	 * @return El id del Estado 
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param El id del Estado 
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return El estado correspondiente
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param El estado correspondiente
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return La fecha en que cambio el estado 
	 */
	public Date getFecha_Cambio() {
		return fecha_Cambio;
	}
	/**
	 * @param La fecha en que cambio el estado 
	 */
	public void setFecha_Cambio(Date fecha_Cambio) {
		this.fecha_Cambio = fecha_Cambio;
	}
	
	/**
	 * @return El comentario que se hizo al cambiar el estado
	 */
	public String getComentario_Descripcion() {
		return comentario_Descripcion;
	}
	/**
	 * @param El comentario que se hizo al cambiar el estado
	 */
	public void setComentario_Descripcion(String comentario_Descripcion) {
		this.comentario_Descripcion = comentario_Descripcion;
	}

	/**
	 * @return El id del ciudadano al que corresponde el estado
	 */
	public long getId_Ciudadano() {
		return id_Ciudadano;
	}

	/**
	 * @param El id del ciudadano al que corresponde el estado
	 */
	public void setId_Ciudadano(long id_Ciudadano) {
		this.id_Ciudadano = id_Ciudadano;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del estado proceso
	 */
	public String toString() 
	{
		return "EstadoProceso [id=" + id + ", estado=" + estado+ ", fecha cambio=" + fecha_Cambio.toString() + ", comentarios/descripción=" + comentario_Descripcion
				+ ", id ciudadano=" + id_Ciudadano + "]";
	}
}
