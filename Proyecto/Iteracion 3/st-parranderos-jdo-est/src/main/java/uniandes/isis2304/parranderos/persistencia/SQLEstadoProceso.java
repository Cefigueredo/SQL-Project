package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.EstadoProceso;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto ESTADO_PROCESO de VacuAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Manuel Rivera
 */
class SQLEstadoProceso {
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
	public SQLEstadoProceso (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un ESTADO_PROCESO a la base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @return El número de tuplas insertadas
	 */
	public long adicionarEstadoProceso (PersistenceManager pm, long id, String estado, Date fecha_Cambio,String comentario_descripcion, long idCiudadano)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEstadoProceso () + "(ID, ESTADO, FECHA_CAMBIO, COMENTARIO_DESCRIPCION, ID_CIUDADANO) values (?, ?, ?, ?, ?)");
        q.setParameters(id, estado, fecha_Cambio, comentario_descripcion, idCiudadano);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN PUNTO_VACUNACION de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Estado Proceso
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstadoProcesoPorId(PersistenceManager pm, long id) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstadoProceso() + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar todos los ESTADOS_PROCESO de Vacuandes, por el id de sus ciudadanos
	 * @param pm - El manejador de persistencia
	 * @param id - El id del ciudadano
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstadoProcesoPorIdCiudadano(PersistenceManager pm, long id) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstadoProceso() + " WHERE id_ciudadano = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los ESTADO_PROCESO de un ciudadano en particular de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del ciudadano
	 * @return Lista de objetos ESTADO_PROCESO que tiene el identificador del ciudadano dado
	 */
	public List<EstadoProceso> darEstadoProcesoPorIdCiudadano (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstadoProceso() + " WHERE id_ciudadano = ?");
		q.setResultClass(EstadoProceso.class);
		q.setParameters(id);
		return (List<EstadoProceso>) q.executeUnique();
	}
}
