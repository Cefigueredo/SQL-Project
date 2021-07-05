package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de CONTRASENA_FUNCIONARIO
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivera
 */
public interface VOContrasenaFuncionario {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del funcionario
	 */
	public long getId_Funcionario();
	
	/**
	 * @return La contraseña del funcionario
	 */
	public String getContrasena();
}
