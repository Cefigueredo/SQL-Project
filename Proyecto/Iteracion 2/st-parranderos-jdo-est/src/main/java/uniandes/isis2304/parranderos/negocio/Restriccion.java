package uniandes.isis2304.parranderos.negocio;
/**
 * Clase para modelar el concepto RESTRICCION del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class Restriccion implements VORestriccion{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la restricción
	 */
	private long id;
	
	/**
	 * La descripción de la restricción
	 */
	private String descripcion;
	
	/**
	 * El id de la vacuna de la cuál indica las restricciones
	 */
	private long idVacuna;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public Restriccion() {
		this.id=0;
		this.descripcion="";
		this.idVacuna=0;
	}
	
	/**
     * Constructor con valores
     */
	public Restriccion(long id, String descripcion, long idVacuna) {
		this.id=id;
		this.descripcion=descripcion;
		this.idVacuna=idVacuna;
	}
	
	/**
	 * @return El id de la restricción
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param El id de la restricción
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return La descripción de la restricción
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param La descripción de la restricción
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return El id de la vacuna de la cuál indica las restricciones
	 */
	public long getIdVacuna() {
		return idVacuna;
	}
	/**
	 * @param El id de la vacuna de la cuál indica las restricciones
	 */
	public void setIdVacuna(long idVacuna) {
		this.idVacuna = idVacuna;
	}
}
