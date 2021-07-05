package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de ADMIN_PLAN_VACUNACION
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 * 
 * @author Juan Manuel Rivera
 */
public interface VOAdminPlanVacunacion {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El id del Plan de Vacunación del cual es administrador
	 */
	public long getId_Plan_Vacunacion();
}
