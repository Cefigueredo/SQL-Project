package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto VACUNA del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class Vacuna implements VOVacuna {

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la vacuna
	 */
	private long id;
	
	/**
	 * El nombre de la vacuna
	 */
	private String nombre;
	
	/**
	 * Los requerimientos de almacenamiento de la vacuna
	 */
	private String requerimientosDeAlmacenamiento;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public Vacuna() {
		this.id=0;
		this.nombre="";
		this.requerimientosDeAlmacenamiento="";
	}
	
	/**
     * Constructor con valores
     */
	public Vacuna(Long id, String nombre, String requerimientosDeAlmacenamiento) {
		this.id=id;
		this.nombre=nombre;
		this.requerimientosDeAlmacenamiento=requerimientosDeAlmacenamiento;
	}

	/**
	 * @return El id de la vacuna
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param El id de la vacuna
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return El nombre de la vacuna
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param El nombre de la vacuna
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return Los requerimientos de almacenamiento de la vacuna
	 */
	public String getRequerimientosDeAlmacenamiento() {
		return requerimientosDeAlmacenamiento;
	}
	/**
	 * @param Los requerimientos de almacenamiento de la vacuna
	 */
	public void setRequerimientosDeAlmacenamiento(String requerimientosDeAlmacenamiento) {
		this.requerimientosDeAlmacenamiento = requerimientosDeAlmacenamiento;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la vacuna
	 */
	public String toString() {
		return "Vacuna [ id="+id+", nombre="+nombre+", requerimientos de almacenamiento="+requerimientosDeAlmacenamiento+"]";
	}
}
