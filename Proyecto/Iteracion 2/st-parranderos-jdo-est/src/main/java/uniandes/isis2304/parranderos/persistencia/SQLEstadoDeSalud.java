package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.EstadoDeSalud;

public class SQLEstadoDeSalud {
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
	public SQLEstadoDeSalud (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarEstadoDeSalud (PersistenceManager pm, long idEstadoDeSalud, String descripcion, long idEtapa) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEstadoDeSalud() + "(id, descripcion, idEtapa) values (?, ?, ?)");
        q.setParameters(idEstadoDeSalud, descripcion, idEtapa);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODOS LOS ESTADOS DE SALUD de la base de datos de vacuandes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEstadoDeSalud (PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstadoDeSalud());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ESTADO DE SALUD de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idEstadoDeSalud- el id del estado de salud
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEstadoDeSaludPorId(PersistenceManager pm, long idEstadoDeSalud) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstadoDeSalud() + " WHERE id = ?");
        q.setParameters(idEstadoDeSalud);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los ESTADO DE SALUD de la 
	 * base de datos de Vacuandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ESTADO DE SALUD 
	 */
	public List<EstadoDeSalud> darEstadosDeSalud(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstadoDeSalud());
		q.setResultClass(EstadoDeSalud.class);
		return (List<EstadoDeSalud>) q.execute();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA ESTADO DE SALUD  de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idEstadoDeSalud - El identificador de la EstadoDeSalud
	 * @return El objeto ESTADO DE SALUD  que tiene el identificador dado
	 */
	public EstadoDeSalud darEstadoDeSaludPorId (PersistenceManager pm, long idEstadoDeSalud) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstadoDeSalud() + " WHERE id = ?");
		q.setResultClass(EstadoDeSalud.class);
		q.setParameters(idEstadoDeSalud);
		return (EstadoDeSalud) q.executeUnique();
	}
}
