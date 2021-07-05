package uniandes.isis2304.parranderos.persistencia;

import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Ciudadano;

class SQLCiudadano {
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
	public SQLCiudadano (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	
	public long adicionarCiudadano (PersistenceManager pm, long idCiudadano, String nombre, String documento, int estadoActual, Date fechaNacimiento, String direccion, String ciudad, String numeroContacto, long idVacuandes, long idOficinaEPSRegional, long idPuntoVacunacion, long idProfesion, long idEstadoSalud, long idEtapa) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCiudadano() + "(id, nombre, documento, estadoActual, fechaNacimiento, direccion, ciudad, numeroContacto, idVacuandes, idOficinaEPSRegional, idPuntoVacunacion, idProfesion, idEstadoSalud, idEtapa) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idCiudadano, nombre, documento, estadoActual, fechaNacimiento, direccion, ciudad, numeroContacto, idVacuandes, idOficinaEPSRegional, idPuntoVacunacion, idProfesion, idEstadoSalud, idEtapa);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar TODOS LOS CIUDADANO de la base de datos de vacuandes
	 * @param pm - El manejador de persistencia
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCiudadano(PersistenceManager pm) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudadano());
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN CIUDADANO de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param idVacuandes - el id de Vacuandes
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCiudadanoPorId(PersistenceManager pm, long idCiudadano) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudadano() + " WHERE id = ?");
        q.setParameters(idCiudadano);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de los CIUDADANO de la 
	 * base de datos de Vacuandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CIUDADANO
	 */
	public List<Ciudadano> darCiudadanos(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCiudadano());
		q.setResultClass(Ciudadano.class);
		return (List<Ciudadano>) q.execute();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA CIUDADANO de la 
	 * base de datos de Vacuandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCiudadano - El identificador de la Ciudadano
	 * @return El objeto CIUDADANO que tiene el identificador dado
	 */
	public Ciudadano darCiudadanoPorId (PersistenceManager pm, long idCiudadano) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCiudadano() + " WHERE id = ?");
		q.setResultClass(Ciudadano.class);
		q.setParameters(idCiudadano);
		return (Ciudadano) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA CIUDADANO de la 
	 * base de datos de Vacuandes, por su cedula
	 * @param pm - El manejador de persistencia
	 * @param cedula - El identificador de la Ciudadano
	 * @return El objeto CIUDADANO que tiene el identificador dado
	 */
	public Ciudadano darCiudadanoPorCedula (PersistenceManager pm, String cedula) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCiudadano() + " WHERE documento ='?' ");
		q.setResultClass(Ciudadano.class);
		q.setParameters(cedula);
		return (Ciudadano) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para actualizar el Id del Punto de Vacunación
	 * @param pm - El manejador de persistencia
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * param cedula - Cédula del ciudadano a modificar
	 * @return El número de tuplas modificadas
	 */
	public long actualizarPuntoVacunacionCiudadano (PersistenceManager pm, long idPunto,String cedula)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaCiudadano() + " SET IDPUNTOVACUNACION=?, ESTADO_ACTUAL=4 WHERE DOCUMENTO = ?");
        q.setParameters(idPunto, cedula);
        return (long) q.executeUnique();
	}
}
