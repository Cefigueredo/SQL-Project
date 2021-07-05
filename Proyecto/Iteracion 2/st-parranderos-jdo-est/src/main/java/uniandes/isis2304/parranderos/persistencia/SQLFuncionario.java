package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Funcionario;
import uniandes.isis2304.parranderos.negocio.PuntoVacunacion;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto FUNCIONARIOS de VacuAndes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Juan Manuel Rivera
 */
class SQLFuncionario {
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
	public SQLFuncionario (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un FUNCIONARIO a la base de datos de VacuAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del funcionario
	 * @param correo - El correo del funcionario
	 * @param idPlanVacunacion - El id del plan de vacunacion al que pertenece
	 * @return El número de tuplas insertadas
	 */
	public long adicionarFuncionario (PersistenceManager pm, long id, String correo, long idPlan)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaFuncionario () + "(ID, CORREO, ID_VACUNACION) values (?, ?, ?)");
        q.setParameters(id, correo, idPlan);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un FUNCIONARIO de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param correo - El correo del Funcionario
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarFuncionarioPorCorreo(PersistenceManager pm, String correo) 
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFuncionario () + " WHERE correo = ?");
        q.setParameters(correo);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de un FUNCIONARIO de la 
	 * base de datos de Vacuandes, por su correo
	 * @param pm - El manejador de persistencia
	 * @param correo - El correo del funcionario a buscar
	 * @return El objeto FUNCIONARIO que tiene el correo dado
	 */
	public Funcionario darfuncionarioPorCorreo(PersistenceManager pm, String correo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFuncionario() + " WHERE correo = ?");
		q.setResultClass(Funcionario.class);
		q.setParameters(correo);
		return (Funcionario) q.executeUnique();
	}
}
