package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Etapa;

public class SQLEtapa {

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
	public SQLEtapa (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarEtapa (PersistenceManager pm, long idEtapa, int fase, String descripcion, int edadMinima, int edadMaxima, long idVacunacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEtapa() + "(id, fase, descripcion, edad_Minima, edad_Maxima, idVacunacion) values (?, ?, ?, ?, ?, ?)");
        q=q.setParameters(idEtapa, fase, descripcion, edadMinima, edadMaxima, idVacunacion);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODAS LAS ETAPAS de la base de datos de vacuandes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEtapa(PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEtapa());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA ETAPA de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idEtapa - el id de Etapa 
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarEtapaPorId(PersistenceManager pm, long idEtapa) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEtapa() + " WHERE id = ?");
        q.setParameters(idEtapa);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los ETAPA de la 
	 * base de datos de Vacuandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ETAPA
	 */
	public List<Etapa> darEtapas(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEtapa());
		q.setResultClass(Etapa.class);
		return (List<Etapa>) q.execute();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA ETAPA de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idEtapa- El identificador de la etapa
	 * @return El objeto ETAPA que tiene el identificador dado
	 */
	public Etapa darEtapaPorId (PersistenceManager pm, long idEtapa) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEtapa() + " WHERE id = ?");
		q.setResultClass(Etapa.class);
		q.setParameters(idEtapa);
		return (Etapa) q.executeUnique();
	}

}
