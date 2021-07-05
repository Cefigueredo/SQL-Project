package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cita;
import uniandes.isis2304.parranderos.negocio.Etapa;

public class SQLCita {
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
	public SQLCita (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	public long adicionarCita (PersistenceManager pm, long idCita, Timestamp fechaYHora, int duracionCita, String estadoCita,long idCiudadano, long idPuntoVacunacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCita() + "(id, fechaYHora, duracionCita, estadoCita, idCiudadano, idPuntoVacunacion) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(idCita, fechaYHora, duracionCita, estadoCita, idCiudadano, idPuntoVacunacion);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODAS LAS CITAS de la base de datos de vacuandes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCita(PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA CITA de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idCita - el id de Cita
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCitaPorId(PersistenceManager pm, long idCita) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita() + " WHERE id= ?");
        q.setParameters(idCita);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los CITA de la 
	 * base de datos de Vacuandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CITA
	 */
	public List<Cita> darCitas(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita());
		q.setResultClass(Cita.class);
		return (List<Cita>) q.execute();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA CITA de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idEtapa- El identificador de la etapa
	 * @return El objeto CITA que tiene el identificador dado
	 */
	public Cita darCitaPorId (PersistenceManager pm, long idCita) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCita() + " WHERE id = ?");
		q.setResultClass(Cita.class);
		q.setParameters(idCita);
		return (Cita) q.executeUnique();
	}
}
