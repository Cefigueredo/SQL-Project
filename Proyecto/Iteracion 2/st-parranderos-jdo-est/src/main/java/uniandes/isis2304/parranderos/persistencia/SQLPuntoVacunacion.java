package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.PuntoVacunacion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PUNTO_VACUNACION de VacuAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Manuel Rivera
 */
class SQLPuntoVacunacion {
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
	public SQLPuntoVacunacion (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PUNTO_VACUNACION a la base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * @param localización - La localización del Punto de Vacunación
	 * @param cupoMaximo - El máximo de ciudadanos que pueden estar al tiempo en el Punto de Vacunación
	 * @param maxCiudadanosPorDia - La capacidad de atención por día del Punto de Vacunación
	 * @param totalVacunados - El número de ciudadanos que ha vacunado el Punto de Vacunación
	 * @param infraestructura - La infraestructura para almacenar dosis del punto de vacunación
	 * @param capacidadAlmacenamiento - El número de dosis máximo que puede tener el punto
	 * @param idEps - El id de la Oficina de EPS Regional al que pertenece el Punto de Vacunación 
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPuntoVacunacion (PersistenceManager pm, long idPtoVacunacion, String localizacion, int cupoMaximo,
			int maxCiudadanosPorDia, int totalVacunados, String infraestructura, int capacidadAlmacenamiento, long idEps)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPuntoVacunacion () + "(ID, LOCALIZACION, CUPOMAXIMO, MAXCIUDADANOSPORDIA, TOTALVACUNADOS, INFRAESTRUCTURAALMACENAMIENTO, CAPACIDADALMACENAMIENTODOSIS, IDEPSREGIONAL) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idPtoVacunacion, localizacion, cupoMaximo, maxCiudadanosPorDia, totalVacunados, infraestructura, capacidadAlmacenamiento, idEps);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN PUNTO_VACUNACION de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPuntoVacunacionPorId(PersistenceManager pm, long idPtoVacunacion) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPuntoVacunacion() + " WHERE id = ?");
        q.setParameters(idPtoVacunacion);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN PUNTO_VACUNACION de la base de datos de Vacuandes, por el identificador de su EPS Regional
	 * @param pm - El manejador de persistencia
	 * @param idEps - El identificador de la Oficina de la EPS Regional 
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPuntoVacunacionPorIdEps(PersistenceManager pm, long idEps) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPuntoVacunacion() + " WHERE IDEPSREGIONAL = ?");
        q.setParameters(idEps);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los PUNTO_VACUNACION de la
	 * base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PUNTO_VACUNACION
	 */
	public List<PuntoVacunacion> darPuntosVacunacion (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPuntoVacunacion());
		q.setResultClass(PuntoVacunacion.class);
		return (List<PuntoVacunacion>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PUNTO_VACUNACION de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * @return El objeto PUNTO_VACUNACION que tiene el identificador dado
	 */
	public PuntoVacunacion darPuntoVacunacionPorId (PersistenceManager pm, long idPtoVacunacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPuntoVacunacion() + " WHERE id = ?");
		q.setResultClass(PuntoVacunacion.class);
		q.setParameters(idPtoVacunacion);
		return (PuntoVacunacion) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PUNTO_VACUNACION de la 
	 * base de datos de Vacuandes, por el id de su EPS regional
	 * @param pm - El manejador de persistencia
	 * @param idEpsRegional - El identificador de la EPS regional al que pertenece
	 * @return La lista de objetos PUNTO_VACUNACION que pertenecen a una EPS dada
	 */
	public List<PuntoVacunacion> darPuntoVacunacionPorIdEps (PersistenceManager pm, long idEPS) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPuntoVacunacion() + " WHERE IDEPSREGIONAL = ?");
		q.setResultClass(PuntoVacunacion.class);
		q.setParameters(idEPS);
		return (List<PuntoVacunacion>) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para aumentar el número de ciudadanos totales que ha vacunado el punto
	 * @param pm - El manejador de persistencia
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * @return El número de tuplas modificadas
	 */
	public long aumentarTotalVacunados (PersistenceManager pm, long idPtoVacunacion)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaPuntoVacunacion() + " SET TOTALVACUNADOS = TOTALVACUNADOS + 1 WHERE id = ?");
        q.setParameters(idPtoVacunacion);
        return (long) q.executeUnique();
	}
}
