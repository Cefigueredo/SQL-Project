package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Vacunacion;

class SQLVacunacion {
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
	public SQLVacunacion (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarVacunacion (PersistenceManager pm, long idVacunacion, int dosisTotales, int dosisAplicadas, int dosisPerdidas, long idEtapaActual) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaVacunacion() + "(id, dosis_Totales, dosis_Aplicadas, dosis_Perdidas, idEtapa_Actual) values (?, ?, ?, ?, ?)");
        q.setParameters(idVacunacion, dosisTotales, dosisAplicadas, dosisPerdidas, idEtapaActual);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODOS LOS VACUANDES de la base de datos de vacuandes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVacunacion(PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVacunacion());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN VACUANDES de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idVacunacion - el id de Vacunacion
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarVacunacionPorId(PersistenceManager pm, long idVacunacion) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVacunacion() + " WHERE id = ?");
        q.setParameters(idVacunacion);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los VACUNACION de la 
	 * base de datos de Vacunacion
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos VACUNACION
	 */
	public List<Vacunacion> darVacunaciones(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVacunacion());
		q.setResultClass(Vacunacion.class);
		return (List<Vacunacion>) q.execute();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA VACUNACION de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idVacunacion - El identificador de la vacunacion
	 * @return El objeto VACUNACION que tiene el identificador dado
	 */
	public Vacunacion darVacunacionPorId (PersistenceManager pm, long idVacunacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaVacunacion() + " WHERE id = ?");
		q.setResultClass(Vacunacion.class);
		q.setParameters(idVacunacion);
		return (Vacunacion) q.executeUnique();
	}
}
