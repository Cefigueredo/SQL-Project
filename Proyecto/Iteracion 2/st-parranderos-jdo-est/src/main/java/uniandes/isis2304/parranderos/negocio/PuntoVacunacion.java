package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Punto Vacunación del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class PuntoVacunacion implements VOPuntoVacunacion {

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del punto de vacunación
	 */
	private long id;
	
	/**
     * La localización del punto de vacunación
     */
	private String localizacion;
	
	/**
     * El cupo máximo o aforo del punto de vacunación 
     */
	private int cupoMaximo;
	
	/**
     * La capacidad de atención total diaria del punto de vacunación
     */
	private int maxCiudadanosPorDia;
	
	/**
     * El número total de ciudadanos vacunados en este punto de vacunación
     */
	private int totalVacunados;
	
	/**
     * La infraestructura de almacenamiento disponible en el punto de vacunación
     */
	private String infraestructuraAlmacenamiento;
	
	/**
     * La capacidad de almacenamiento de dosis del punto de vacunación
     */
	private int capacidadAlmacenamientoDosis;
	
	/**
     * El id de la Eps Regional a la que pertenece este punto de vacunación
     */
	private long idEpsRegional;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public PuntoVacunacion() {
    	this.id = 0;
		this.localizacion = "";
		this.cupoMaximo = 0;
		this.maxCiudadanosPorDia = 0;
		this.totalVacunados = 0;
		this.infraestructuraAlmacenamiento="";
		this.capacidadAlmacenamientoDosis=0;
		this.idEpsRegional=0;
	}
	
	/**
     * Constructor con valores
     */
	public PuntoVacunacion(long id, String localizacion, int cupoMaximo, int maxCiudadanosPorDia, int totalVacunados, String infraestructuraAlmacenamiento, int capacidadAlmacenamiento, long idEpsRegional) {
    	this.id = id;
		this.localizacion = localizacion;
		this.cupoMaximo = cupoMaximo;
		this.maxCiudadanosPorDia = maxCiudadanosPorDia;
		this.totalVacunados = totalVacunados;
		this.infraestructuraAlmacenamiento=infraestructuraAlmacenamiento;
		this.capacidadAlmacenamientoDosis=capacidadAlmacenamiento;
		this.idEpsRegional=idEpsRegional;
	}
	
	/**
     * @return El id del punto de vacunación
     */
	public long getId() {
		return id;
	}

	/**
     * @param El id del punto de vacunación
     */
	public void setId(long id) {
		this.id = id;
	}

	/**
     * @return La localización del punto de vacunación
     */
	public String getLocalizacion() {
		return localizacion;
	}

	/**
     * @param La localización del punto de vacunación
     */
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	/**
     * @return El cupo máximo o aforo del punto de vacunación 
     */
	public int getCupoMaximo() {
		return cupoMaximo;
	}

	/**
     * @param El cupo máximo o aforo del punto de vacunación 
     */
	public void setCupoMaximo(int cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
	}

	/**
     * @return La capacidad de atención total diaria del punto de vacunación
     */
	public int getMaxCiudadanosPorDia() {
		return maxCiudadanosPorDia;
	}

	/**
     * @param La capacidad de atención total diaria del punto de vacunación
     */
	public void setMaxCiudadanosPorDia(int maxCiudadanosPorDia) {
		this.maxCiudadanosPorDia = maxCiudadanosPorDia;
	}

	/**
     * @return El número total de ciudadanos vacunados en este punto de vacunación
     */
	public int getTotalVacunados() {
		return totalVacunados;
	}

	/**
     * @param El número total de ciudadanos vacunados en este punto de vacunación
     */
	public void setTotalVacunados(int totalVacunados) {
		this.totalVacunados = totalVacunados;
	}

	/**
     * @return La infraestructura de almacenamiento disponible en el punto de vacunación
     */
	public String getInfraestructuraAlmacenamiento() {
		return infraestructuraAlmacenamiento;
	}

	/**
     * @param La infraestructura de almacenamiento disponible en el punto de vacunación
     */
	public void setInfraestructuraAlmacenamiento(String infraestructuraAlmacenamiento) {
		this.infraestructuraAlmacenamiento = infraestructuraAlmacenamiento;
	}

	/**
     * @return La capacidad de almacenamiento de dosis del punto de vacunación
     */
	public int getCapacidadAlmacenamientoDosis() {
		return capacidadAlmacenamientoDosis;
	}

	/**
     * @param La capacidad de almacenamiento de dosis del punto de vacunación
     */
	public void setCapacidadAlmacenamientoDosis(int capacidadAlmacenamientoDosis) {
		this.capacidadAlmacenamientoDosis = capacidadAlmacenamientoDosis;
	}

	/**
     * @return El id de la Eps Regional a la que pertenece este punto de vacunación
     */
	public long getIdEpsRegional() {
		return idEpsRegional;
	}

	/**
     * @param El id de la Eps Regional a la que pertenece este punto de vacunación
     */
	public void setIdEpsRegional(long idEpsRegional) {
		this.idEpsRegional = idEpsRegional;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del punto de vacunacion
	 */
	public String toString() 
	{
		return "PuntoVacunacion [id=" + id + ", localizacion=" + localizacion + ", cupo máximo=" + cupoMaximo + ", maximo ciuadanos por dia=" + maxCiudadanosPorDia
				+ ", total ciudadanos vacunados=" + totalVacunados + ", infraestructura almacenamiento=" + infraestructuraAlmacenamiento
				+ ", capacidad de almacenamiento=" + capacidadAlmacenamientoDosis + ", id de EPS regional=" + idEpsRegional + "]";
	}
}
