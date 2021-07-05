package uniandes.isis2304.parranderos.negocio;

public class OficinaEPSRegional implements VOOficinaEPSRegional{
	
	/* ****************************************************************
	 * 			Atributos 
	 *****************************************************************/
	
	/**
	 * Id de la oficina EPS regional
	 */
	private long id;
	/**
	 * Nombre de la oficina EPS regional
	 */
	private String nombre;
	/**
	 * NIT de la oficina EPS regional
	 */
	private String NIT;
	/**
	 * Región a la que pertenece la oficina EPS regional
	 */
	private String region;
	/**
	 * Dirección de la oficina EPS regional
	 */
	private String direccion;
	/**
	 * Numero de contacto de la oficina EPS regional
	 */
	private String numeroContacto;
	/**
	 * Ciudad de la oficina EPS regional
	 */
	private String ciudad;
	/**
	 * Id de Vacunacion
	 */
	private long idVacunacion;
	
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/

	public OficinaEPSRegional(long id, String nombre, String NIT, String region, String direccion, String numeroContacto, String ciudad, long idVacunacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.NIT = NIT;
		this.region = region;
		this.direccion = direccion;
		this.numeroContacto = numeroContacto;
		this.ciudad = ciudad;
		this.idVacunacion = idVacunacion;
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

	public String getNIT() {
		return NIT;
	}

	public void setNIT(String nIT) {
		NIT = nIT;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNumeroContacto() {
		return numeroContacto;
	}

	public void setNumeroContacto(String numeroContacto) {
		this.numeroContacto = numeroContacto;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public long getIdVacunacion() {
		return idVacunacion;
	}

	public void setIdVacunacion(long idVacunacion) {
		this.idVacunacion = idVacunacion;
	}
	
	
}
