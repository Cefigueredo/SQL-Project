/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Vacuandes Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Vacuandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
{
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
	public SQLUtil (PersistenciaVacuandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqVacuandes () + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 19 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarVacuandes (PersistenceManager pm)
	{
		
		Query qVencimientoDosis = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVencimientoDosis());
		Query qVacuna = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVacuna());
        Query qVacunacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVacunacion());
        Query qNumeroDosis = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDosisVacuna());
        Query qRestricciones = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaRestricciones());
        Query qEtapa = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEtapa ());  
        Query qOficinaEPSRegional = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOficinaEPSRegional());
        Query qFuncionarios = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFuncionario());
        Query qProfesion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProfesion ());
        Query qEstadoDeSalud = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstadoDeSalud ());
        Query qPuntoVacunacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPuntoVacunacion());
        Query qContrasenaFuncionarios = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContrasenaFuncionarios());
        Query qAdminPlanVacunacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAdminPlan());
        Query qAdminEPS = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAdminEps());
        Query qAdminPuntoVacunacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAdminPuntoVacunacion());
        Query qOperadorPuntoVacunacion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperadorPuntoVacunacion());
        Query qCiudadano = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCiudadano ());
        Query qDosis = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDosis());
        Query qCita = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCita());
        Query qEstadoProceso = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstadoProceso());
        

        long vencimientoDosis = (long) qVencimientoDosis.executeUnique();
        long vacunaEliminadas = (long) qVacuna.executeUnique();
        long vacunacionEliminados = (long) qVacunacion.executeUnique ();
        long numeroDosisEliminados = (long) qNumeroDosis.executeUnique();
        long restriccionEliminados = (long) qRestricciones.executeUnique();
        long etapaEliminadas = (long) qEtapa.executeUnique();
        long oficinaEPSRegioalEliminadas = (long) qOficinaEPSRegional.executeUnique ();
        long funcionarioEliminados= (long) qFuncionarios.executeUnique();
        long profesionEliminadas = (long) qProfesion.executeUnique ();
        long estadoDeSaludEliminados = (long) qEstadoDeSalud.executeUnique ();
        long puntosVacunacionEliminados = (long) qPuntoVacunacion.executeUnique();
        long contrasenaFuncionarioEliminados = (long) qContrasenaFuncionarios.executeUnique();
        long adminPlanEliminados = (long) qAdminPlanVacunacion.executeUnique();
        long adminEPSEliminados = (long) qAdminEPS.executeUnique();
        long adminPuntoEliminados = (long) qAdminPuntoVacunacion.executeUnique();
        long operadorPuntoEliminados = (long) qOperadorPuntoVacunacion.executeResultUnique();
        long ciudadanoEliminados = (long) qCiudadano.executeUnique ();
        long dosisEliminadas = (long) qDosis.executeUnique();
        long citaEliminadas = (long) qCita.executeUnique();
        long estadoProcesoEliminados = (long) qEstadoProceso.executeUnique();
        
        return new long[] {vencimientoDosis, vacunaEliminadas, vacunacionEliminados, numeroDosisEliminados,
        		restriccionEliminados, etapaEliminadas, oficinaEPSRegioalEliminadas, funcionarioEliminados,
        		profesionEliminadas, estadoDeSaludEliminados, puntosVacunacionEliminados, contrasenaFuncionarioEliminados,
        		adminPlanEliminados, adminEPSEliminados, adminPuntoEliminados, operadorPuntoEliminados, ciudadanoEliminados,
        		dosisEliminadas, citaEliminadas, estadoProcesoEliminados};
	}

}
