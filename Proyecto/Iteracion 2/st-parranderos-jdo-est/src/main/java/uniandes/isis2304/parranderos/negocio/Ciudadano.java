package uniandes.isis2304.parranderos.negocio;

import java.util.Date;

public class Ciudadano implements VOCiudadano {
	/* ****************************************************************
	 * 			Atributos 
	 *****************************************************************/
	
	/**
	 * Id del ciudadano
	 */
	private long id;
	/**
	 * Nombre del ciudadano
	 */
	private String nombre;
	/**
	 * Documento del ciudadano
	 */
	private String documento;
	/**
	 * Estado actual del ciudadano
	 */
	private int estadoActual;
	/**
	 * Fecha de nacimineto del ciudadano
	 */
	private Date fechaNacimiento;
	/**
	 * Dirección de residencia del ciudadano
	 */
	private String direccion;
	/**
	 * Ciudad de residencia del ciudadano
	 */
	private String ciudad;
	/**
	 * Numero de contacto del ciudadano
	 */
	private String numeroContacto;
	/**
	 * Id de Vacunacion
	 */
	private long idVacunacion;
	/**
	 * Id de la Oficina EPS Regional
	 */
	private long idOficinaEPS;
	/**
	 * Id del punto de vacunación
	 */
	private long idPuntoVacunacion;
	/**
	 * Id de la profesión del ciudadano
	 */
	private long idProfesion;
	/**
	 * Id del estado de salud del ciudadano
	 */
	private long idEstadoSalud;
	/**
	 * Id de la etapa en la que se encuentra el ciudadano
	 */
	private long idEtapa;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	public Ciudadano(){
		this.id = 0;
		this.nombre = "";
		this.documento = "";
		this.estadoActual = 0;
		this.fechaNacimiento = null;
		this.direccion = "";
		this.ciudad = "";
		this.numeroContacto = "";
		this.idVacunacion= 0;
		this.idOficinaEPS = 0;
		this.idPuntoVacunacion = 0;
		this.idProfesion = 0;
		this.idEstadoSalud = 0;
		this.idEtapa = 0;
	}
	
	public Ciudadano(long id, String nombre, String documento, int estadoActual, Date fechaNacimiento, String direccion, String ciudad, String numeroContacto, long idVacunacion, long idOficinaEPSRegional, long idPuntoVacunacion, long idProfesion, long idEstadoSalud, long idEtapa) {
		this.id = id;
		this.nombre = nombre;
		this.documento = documento;
		this.estadoActual = estadoActual;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.numeroContacto = numeroContacto;
		this.idVacunacion = idVacunacion;
		this.idOficinaEPS = idOficinaEPSRegional;
		this.idPuntoVacunacion = idPuntoVacunacion;
		this.idProfesion = idProfesion;
		this.idEstadoSalud = idEstadoSalud;
		this.idEtapa = idEtapa;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public int getEstadoActual() {
		return estadoActual;
	}

	public void setEstadoActual(int estadoActual) {
		this.estadoActual = estadoActual;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getNumeroContacto() {
		return numeroContacto;
	}

	public void setNumeroContacto(String numeroContacto) {
		this.numeroContacto = numeroContacto;
	}

	public long getIdVacunacion() {
		return idVacunacion;
	}

	public void setIdVacunacion(long idVacunacion) {
		this.idVacunacion = idVacunacion;
	}

	public long getIdOficinaEPS() {
		return idOficinaEPS;
	}

	public void setIdOficinaEPS(long idOficinaEPSRegional) {
		this.idOficinaEPS = idOficinaEPSRegional;
	}

	public long getIdPuntoVacunacion() {
		return idPuntoVacunacion;
	}

	public void setIdPuntoVacunacion(long idPuntoVacunacion) {
		this.idPuntoVacunacion = idPuntoVacunacion;
	}

	public long getIdProfesion() {
		return idProfesion;
	}

	public void setIdProfesion(long idProfesion) {
		this.idProfesion = idProfesion;
	}

	public long getIdEstadoSalud() {
		return idEstadoSalud;
	}

	public void setIdEstadoSalud(long idEstadoSalud) {
		this.idEstadoSalud = idEstadoSalud;
	}

	public long getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(long idEtapa) {
		this.idEtapa = idEtapa;
	}

	

	
}
