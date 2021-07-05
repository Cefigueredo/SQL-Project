package uniandes.isis2304.parranderos.negocio;

public class Vacunacion implements VOVacunacion {
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	/**
	 * Id de Vacunacion
	 */
	private long id;
	/**
	 * Número total de dosis en Vacunacion
	 */
	private int dosisTotales;
	/**
	 * Número total de dosis aplicadas en Vacunacion
	 */
	private int dosisAplicadas;
	/**
	 * Número total de dosis perdidas en Vacunacion
	 */
	private int dosisPerdidas;
	/**
	 * Etapa actual en la que se encuentra Vacunacion
	 */
	private long idEtapaActual;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	
	/**
     * Constructor por defecto
     */
	public Vacunacion() 
    {
    	this.id = 0;
		this.dosisTotales = 0;
		this.dosisAplicadas= 0;
		this.dosisPerdidas= 0;
		this.idEtapaActual = 0;
	}

	/**
	 * Constructor con valores
	 * @param id - El id de Vacunacion
	 * @param dosisTotales - Las dosis totales de Vacunacion
	 * @param dosisAplicadas - Las dosis aplicadas de Vacunacion
	 * @param dosisPerdidas - Las dosis perdidas de Vacunacion
	 * @param etapaActual - Etapa actual en la que se encuentra Vacunacion
	 */
    public Vacunacion(long id, int dosisTotales, int dosisAplicadas, int dosisPerdidas, long idEtapaActual) 
    {
    	this.id = id;
		this.dosisTotales = dosisTotales;
		this.dosisAplicadas = dosisAplicadas;
		this.dosisPerdidas = dosisPerdidas;
		this.idEtapaActual = idEtapaActual;
	}

	/**
	 * @return id de Vacunacion
	 */
	public long getId() {	
		return this.id;
	}
	/**
	 * @return Nuevo id de Vacunacion
	 */
    public void setId(long id) {
		this.id = id;
	}
	/**
	 * 
	 * @return dosis totales de Vacunacion
	 */
	public int getDosisTotales() {	
		return this.dosisTotales;
	}
	/**
	 * 
	 * @return Nuevas dosis totales de Vacunacion
	 */
	public void setDosisTotales(int dosisTotales) {
		this.dosisTotales = dosisTotales;
	}
	/**
	 * 
	 * @return dosis aplicadas de Vacunacion
	 */
	public int getDosisAplicadas() {		
		return this.dosisAplicadas;
	}
	/**
	 * 
	 * @return Nuevas dosis aplicadas de Vacunacion
	 */
	public void setDosisAplicadas(int dosisAplicadas) {
		this.dosisAplicadas = dosisAplicadas;
	}
	/**
	 * 
	 * @return dosis perdidas de Vacunacion
	 */
	public int getDosisPerdidas() {
		return this.dosisPerdidas;
	}
	/**
	 * 
	 * @return Nuevas dosis perdidas de Vacunacion
	 */
	public void setDosisPerdidas(int dosisPerdidas) {
		this.dosisPerdidas = dosisPerdidas;
	}
	/**
	 * 
	 * @return etapa actual en la que está Vacunacion
	 */
	public long getIdEtapaActual() {		
		return this.idEtapaActual;
	}
	/**
	 * 
	 * @return Nueva etapa actual en la que está Vacunacion
	 */
	public void setIdEtapaActual(long idEtapaActual) {
		this.idEtapaActual = idEtapaActual;
	}
}
