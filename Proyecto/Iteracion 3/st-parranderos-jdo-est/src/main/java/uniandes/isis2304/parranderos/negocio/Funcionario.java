package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Punto Vacunación del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public abstract class Funcionario implements VOFuncionario{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del funcionario
	 */
	private long id;
	
	/**
	 * El correo del funcionario
	 */
	private String correo;
	
	/**
	 * El id del plan de vacunación del funcionario
	 */
	private long id_Vacunacion;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public Funcionario() {
		this.id=0;
		this.correo="";
		this.id_Vacunacion=0;
	}
	
	/**
     * Constructor con valores
     */
	public Funcionario(long id, String correo, long id_Vacunacion) {
		this.id=id;
		this.correo=correo;
		this.id_Vacunacion=id_Vacunacion;
	}
	
	/**
	 * @return El id del funcionario
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param El id del funcionario
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return El correo del funcionario
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param El correo del funcionario
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return El id del plan de vacunación del funcionario
	 */
	public long getId_Vacunacion() {
		return id_Vacunacion;
	}
	/**
	 * @param El id del plan de vacunación del funcionario
	 */
	public void setId_Vacunacion(long id_Vacunacion) {
		this.id_Vacunacion = id_Vacunacion;
	}
}
