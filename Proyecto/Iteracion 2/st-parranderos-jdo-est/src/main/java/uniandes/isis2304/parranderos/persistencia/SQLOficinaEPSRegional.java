package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.OficinaEPSRegional;

public class SQLOficinaEPSRegional {
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
	public SQLOficinaEPSRegional(PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * 
	 * @param pm
	 * @param idProfesion
	 * @param nombre
	 * @param NIT
	 * @param region
	 * @param direccion
	 * @param numeroContacto
	 * @param ciudad
	 * @param idVacuandes
	 * @return
	 */
	public long adicionarOficinaEPSRegional(PersistenceManager pm, long idOficinaEPSRegional, String nombre, String NIT, String region, String direccion, String numeroContacto, String ciudad, long idVacuandes) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOficinaEPSRegional() + "(id, nombre, NIT, region, direccion, numero_Contacto, ciudad, idVacunacion) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idOficinaEPSRegional, nombre, NIT, region, direccion, numeroContacto, ciudad, idVacuandes);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODAS LAS OFICINAS EPS REGIONALESde la base de datos de vacuandes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarOficinaEPSRegional(PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOficinaEPSRegional());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA OFICINA EPS REGIONAL de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idProfesion - el id de Oficina EPS regional
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarOficinaEPSRegionalPorId(PersistenceManager pm, long idOficinaEPSRegional) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOficinaEPSRegional() + " WHERE id = ?");
        q.setParameters(idOficinaEPSRegional);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de las OFICNAS EPS REGIONALES de la 
	 * base de datos de Vacuandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos OFICINA EPS REGIONAL
	 */
	public List<OficinaEPSRegional> darOficinasEPSRegional(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOficinaEPSRegional());
		q.setResultClass(OficinaEPSRegional.class);
		return (List<OficinaEPSRegional>) q.execute();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA OFICINA EPS REGIONAL de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idOficinaEPSRegional- El identificador de la OficinaEPSRegional
	 * @return El objeto OFICINA EPS REGIONAL que tiene el identificador dado
	 */
	public OficinaEPSRegional darOficinaEPSRegionalPorId(PersistenceManager pm, long idOficinaEPSRegional) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOficinaEPSRegional() + " WHERE id = ?");
		q.setResultClass(OficinaEPSRegional.class);
		q.setParameters(idOficinaEPSRegional);
		return (OficinaEPSRegional) q.executeUnique();
	}

}
