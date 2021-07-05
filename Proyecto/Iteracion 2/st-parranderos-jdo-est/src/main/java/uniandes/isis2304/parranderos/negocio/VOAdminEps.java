package uniandes.isis2304.parranderos.negocio;
/**
 * Interfaz para los métodos get de ADMIN_EPS
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivera
 */
public interface VOAdminEps {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id de la Oficina de EPS Regional del cual es administrador
	 */
	public long getId_Oficina_Eps();
}
