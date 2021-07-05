package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto DOSIS del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class Dosis implements VODosis{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
     * El id de la dosis
     */
	private long id;
	
	/**
    * El lote de la dosis
    */
	private String lote;
	
	/**
    * El ciudadano al que se le aplicó la dosis
    */
	private long idCiudadano;
	
	/**
    * La oficina regional de la EPS a la que fue asignada la vacuna
    */
	private long idOficinaEpsRegional;
	
	/**
    * El punto de vacunación al que fue asignada la dosis
    */
	private long idPuntoVacunacion;
	
	/**
    * La vacuna de la cuál se tiene una dosis
    */
	private long idVacuna;
	
	/**
    * Indica si la dosis está disponible. Si no puede
    * deberse a algún daño en la dosis
    */
	private boolean disponible;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public Dosis() {
		this.id=0;
		this.lote="";
		this.idCiudadano=0;
		this.idOficinaEpsRegional=0;
		this.idPuntoVacunacion=0;
		this.idVacuna=0;
		this.disponible=true;
	}
	
	/**
     * Constructor con valores
     */
	public Dosis(long id, String lote, long idCiudadano, long idOficinaEpsRegional, long idPuntoVacunacion, long idVacuna, boolean disponible) {
		this.id=id;
		this.lote=lote;
		this.idCiudadano=idCiudadano;
		this.idOficinaEpsRegional=idOficinaEpsRegional;
		this.idPuntoVacunacion=idPuntoVacunacion;
		this.idVacuna=idVacuna;
		this.disponible=disponible;
	}

	/**
     * @return El id de la dosis
     */
	public long getId() {
		return id;
	}

	/**
     * @param El id de la dosis
     */
	public void setId(long id) {
		this.id = id;
	}

	/**
     * @return El lote de la dosis
     */
	public String getLote() {
		return lote;
	}
	
	/**
     * @param El lote de la dosis
     */
	public void setLote(String lote) {
		this.lote = lote;
	}

	/**
     * @return El ciudadano al que se le aplicó la dosis
     */
	public long getIdCiudadano() {
		return idCiudadano;
	}
	/**
     * @param El ciudadano al que se le aplicó la dosis
     */
	public void setIdCiudadano(long idCiudadano) {
		this.idCiudadano = idCiudadano;
	}

	/**
     * @return La oficina regional de la EPS a la que fue asignada la vacuna
     */
	public long getIdOficinaEpsRegional() {
		return idOficinaEpsRegional;
	}
	/**
     * @param La oficina regional de la EPS a la que fue asignada la vacuna
     */
	public void setIdOficinaEpsRegional(long idOficinaEpsRegional) {
		this.idOficinaEpsRegional = idOficinaEpsRegional;
	}

	/**
     * @return El punto de vacunación al que fue asignada la dosis
     */
	public long getIdPuntoVacunacion() {
		return idPuntoVacunacion;
	}
	/**
     * @param El punto de vacunación al que fue asignada la dosis
     */
	public void setIdPuntoVacunacion(long idPuntoVacunacion) {
		this.idPuntoVacunacion = idPuntoVacunacion;
	}
	/**
     * @return La vacuna de la cuál se tiene una dosis
     */
	public long getIdVacuna() {
		return idVacuna;
	}
	/**
     * @param La vacuna de la cuál se tiene una dosis
     */
	public void setIdVacuna(long idVacuna) {
		this.idVacuna = idVacuna;
	}
	/**
     * @return Indica si la dosis está disponible. Si no puede
     * deberse a algún daño en la dosis
     */
	public boolean isDisponible() {
		return disponible;
	}
	/**
     * @return Indica si la dosis está disponible. Si no puede
     * deberse a algún daño en la dosis
     */
	public boolean getDisponible() {
		return disponible;
	}
	/**
     * @param Indica si la dosis está disponible. Si no puede
     * deberse a algún daño en la dosis
     */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	
	
}
