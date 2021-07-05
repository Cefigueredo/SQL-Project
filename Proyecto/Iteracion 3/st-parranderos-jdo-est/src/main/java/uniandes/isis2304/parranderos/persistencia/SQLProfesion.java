package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Profesion;

public class SQLProfesion {
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
	public SQLProfesion (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * 
	 * @param pm
	 * @param idProfesion
	 * @param descripcion
	 * @param idEtapa
	 * @return
	 */
	public long adicionarProfesion (PersistenceManager pm, long idProfesion, String descripcion, long idEtapa) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProfesion() + "(id, descripcion, idEtapa) values (?, ?, ?)");
        q.setParameters(idProfesion, descripcion, idEtapa);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODAS LAS PROFESIONES de la base de datos de vacuandes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProfesion(PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProfesion());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA ETAPA de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idProfesion - el id de Profesion
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarProfesionPorId(PersistenceManager pm, long idProfesion) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProfesion() + " WHERE id = ?");
        q.setParameters(idProfesion);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los PROFESION de la 
	 * base de datos de Vacuandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PROFESION 
	 */
	public List<Profesion> darProfesiones(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProfesion());
		q.setResultClass(Profesion.class);
		return (List<Profesion>) q.execute();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA PROFESION de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProfesion - El identificador de la profesion
	 * @return El objeto PROFESION que tiene el identificador dado
	 */
	public Profesion darProfesionPorId (PersistenceManager pm, long idProfesion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProfesion() + " WHERE id = ?");
		q.setResultClass(Profesion.class);
		q.setParameters(idProfesion);
		return (Profesion) q.executeUnique();
	}

}
