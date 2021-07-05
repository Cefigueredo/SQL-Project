package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.AdminPlanVacunacion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ADMIN_PLAN_VACUNACION de VacuAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Manuel Rivera
 */
class SQLAdminPlanVacunacion {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaVacuandes.SQL;
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaVacuandes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLAdminPlanVacunacion (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un ADMIN_PLAN_VACUNACION a la base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @param idFuncionario - El id del funcionario
	 * @param idPlanVacunacion - El id del plan que administra el funcionario
	 * @return El número de tuplas insertadas
	 */
	public long adicionarAdminPlanVacunacion (PersistenceManager pm, long idFuncionario, long idPlan)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAdminPlan () + "(ID_FUNCIONARIO,ID_PLAN_VACUNACION) values (?, ?)");
        q.setParameters(idFuncionario, idPlan);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ADMIN PLAN VACUNACION de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminPlanVacunacionPorId(PersistenceManager pm, long id) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAdminPlan() + " WHERE ID_FUNCIONARIO = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un ADMIN PLAN VACUNACION de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * @return El objeto PUNTO_VACUNACION que tiene el identificador dado
	 */
	public AdminPlanVacunacion darAdminPlanVacunacionPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAdminPlan() + " WHERE ID_FUNCIONARIO = ?");
		q.setResultClass(AdminPlanVacunacion.class);
		q.setParameters(id);
		return (AdminPlanVacunacion) q.executeUnique();
	}
}
