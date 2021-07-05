package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Vacuna;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto VACUNA de VacuAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Manuel Rivera
 */
class SQLVacuna {
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
	public SQLVacuna (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un VENCIMIENTO_DOSIS a la base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @param lote, El lote de la dosis a adicionar
	 * @param fechaVencimiento, La fecha de vencimiento de la dosis 
	 * @return El número de tuplas insertadas
	 */
	public long adicionarVacuna (PersistenceManager pm, long id, String nombre, String requerimientos)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVacuna () + "(ID, NOMBRE, REQUERIMIENTOSDEALMACENAMIENTO) values (?, ?, ?)");
        q.setParameters(id,nombre,requerimientos);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las VACUNAS de la
	 * base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Vacuna
	 */
	public List<Vacuna> darVacunas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVacuna());
		q.setResultClass(Vacuna.class);
		return (List<Vacuna>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de una VACUNA de la 
	 * base de datos de Vacuandes, por su id
	 * @param pm - El manejador de persistencia
	 * @param id - El id de la vacuna a agregar
	 * @return El objeto Vacuna que tiene el identificador dado
	 */
	public Vacuna darVacunaPorId (PersistenceManager pm, long id)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVacuna () + " WHERE ID = ?");
		q.setResultClass(Vacuna.class);
		q.setParameters(id);
		return (Vacuna) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar una VACUNA de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param lote - El id de la vacuna a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVacunaPorId(PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVacuna () + " WHERE ID = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
}
