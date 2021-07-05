package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.AdminPuntoVacunacion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ADMIN_PUNTO_VACUNACION de VacuAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Manuel Rivera
 */
class SQLAdminPuntoVacunacion {
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
	public SQLAdminPuntoVacunacion (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un ADMIN_EPS a la base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @param idFuncionario - El id del funcionario
	 * @param idPuntoVacunacion - El id del Punto de Vacunacion que administra el funcionario
	 * @return El número de tuplas insertadas
	 */
	public long adicionarAdminPuntoVacunacion (PersistenceManager pm, long idFuncionario, long idPunto)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAdminPuntoVacunacion () + "(ID_FUNCIONARIO,ID_PUNTO_VACUNACION) values (?, ?)");
        q.setParameters(idFuncionario, idPunto);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ADMIN_PUNTO_VACUNACION de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del funcionario
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminPuntoVacunacionPorId(PersistenceManager pm, long id) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAdminPuntoVacunacion () + " WHERE ID_FUNCIONARIO = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un ADMIN_EPS de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * @return El objeto PUNTO_VACUNACION que tiene el identificador dado
	 */
	public AdminPuntoVacunacion darAdminPuntoVacunacionPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAdminPuntoVacunacion () + " WHERE ID_FUNCIONARIO = ?");
		q.setResultClass(AdminPuntoVacunacion.class);
		q.setParameters(id);
		return (AdminPuntoVacunacion) q.executeUnique();
	}
}
