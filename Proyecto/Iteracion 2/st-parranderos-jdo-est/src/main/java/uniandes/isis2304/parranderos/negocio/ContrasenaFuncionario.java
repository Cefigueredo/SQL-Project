package uniandes.isis2304.parranderos.negocio;
/**
 * Clase para modelar el concepto Contraseña Funcionario del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class ContrasenaFuncionario implements VOContrasenaFuncionario{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO del funcionario
	 */
	private long id_Funcionario;
	
	/**
	 * La contraseña del funcionario
	 */
	private String contrasena;

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public ContrasenaFuncionario() {
		this.id_Funcionario=0;
		this.contrasena="";
	}
	
	/**
     * Constructor con valores
     */
	public ContrasenaFuncionario(long id_Funcionario, String contrasena) {
		this.id_Funcionario=id_Funcionario;
		this.contrasena=contrasena;
	}
	
	/**
	 * @return El id del funcionario
	 */
	public long getId_Funcionario() {
		return id_Funcionario;
	}
	/**
	 * @param El id del funcionario
	 */
	public void setId_Funcionario(long id_Funcionario) {
		this.id_Funcionario = id_Funcionario;
	}
	/**
	 * @return La contraseña del funcionario
	 */
	public String getContrasena() {
		return contrasena;
	}
	/**
	 * @param La contraseña del funcionario
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la Contraseña Funcionario
	 */
	public String toString() 
	{
		return "contraseñaFuncionario [id=" + id_Funcionario + ", contraseña="+contrasena+"]";
	}
}
