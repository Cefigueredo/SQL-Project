package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.VencimientoDosis;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto VENCIMIENTO_DOSIS de VacuAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Manuel Rivera
 */
class SQLVencimientoDosis {
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
	public SQLVencimientoDosis (PersistenciaVacuandes pp)
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
	public long adicionarVencimientoDosis (PersistenceManager pm, String lote, Date fVenc)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVencimientoDosis () + "(LOTE, FECHAVENCIMIENTO) values (?, ?)");
        q.setParameters(lote, fVenc);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las VENCIMIENTO_DOSIS de la
	 * base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos VENCIMIENTO_DOSIS
	 */
	public List<VencimientoDosis> darVencimientosDosis (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVencimientoDosis());
		q.setResultClass(VencimientoDosis.class);
		return (List<VencimientoDosis>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PUNTO_VACUNACION de la 
	 * base de datos de Vacuandes, por su lote
	 * @param pm - El manejador de persistencia
	 * @param lote - El lote del que se quiere saber su fecha de vencimiento 
	 * @return El objeto VencimientoDosis que tiene el identificador dado
	 */
	public VencimientoDosis darVencimientoDosisPorLote (PersistenceManager pm, String lote) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVencimientoDosis () + " WHERE LOTE = ?");
		q.setResultClass(VencimientoDosis.class);
		q.setParameters(lote);
		return (VencimientoDosis) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para cambiar la fecha de vencimiento de un lote
	 * @param pm - El manejador de persistencia
	 * @param lote - El lote de las dosis
	 * @param nFecha - La nueva fecha de vencimiento que se va a asignar
	 * @return El número de tuplas modificadas
	 */
	public long modificarVencimientoDosis (PersistenceManager pm, String lote, Date nFecha)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaVencimientoDosis() + " SET (FECHAVENCIMIENTO = ?) WHERE LOTE = ?");
        q.setParameters(lote,nFecha);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN VENCIMIENTO_DOSIS de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param lote - El lote de la dosis a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVencimientoDosisPorLote(PersistenceManager pm, String lote)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVencimientoDosis() + " WHERE lote = ?");
        q.setParameters(lote);
        return (long) q.executeUnique();
	}
}
