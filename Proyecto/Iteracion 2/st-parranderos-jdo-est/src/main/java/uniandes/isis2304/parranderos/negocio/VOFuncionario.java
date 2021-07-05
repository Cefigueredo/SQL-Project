package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de FUNCIONARIO
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivera
 */
public interface VOFuncionario {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del funcionario
	 */
	public long getId();
	
	/**
	 * @return El correo del funcionario
	 */
	public String getCorreo();
	
	/**
	 * @return El id del plan de vacunación del funcionario
	 */
	public long getId_Vacunacion();
}
