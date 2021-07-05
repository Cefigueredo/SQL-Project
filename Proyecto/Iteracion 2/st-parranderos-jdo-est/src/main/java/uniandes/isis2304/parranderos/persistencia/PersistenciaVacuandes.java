package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.AdminEps;
import uniandes.isis2304.parranderos.negocio.AdminPlanVacunacion;
import uniandes.isis2304.parranderos.negocio.AdminPuntoVacunacion;
import uniandes.isis2304.parranderos.negocio.Cita;
import uniandes.isis2304.parranderos.negocio.Ciudadano;
import uniandes.isis2304.parranderos.negocio.EstadoDeSalud;
import uniandes.isis2304.parranderos.negocio.EstadoProceso;
import uniandes.isis2304.parranderos.negocio.Etapa;
import uniandes.isis2304.parranderos.negocio.OficinaEPSRegional;
import uniandes.isis2304.parranderos.negocio.Profesion;
import uniandes.isis2304.parranderos.negocio.PuntoVacunacion;
import uniandes.isis2304.parranderos.negocio.Vacuna;
import uniandes.isis2304.parranderos.negocio.Vacunacion;
import uniandes.isis2304.parranderos.negocio.VencimientoDosis;

public class PersistenciaVacuandes {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaVacuandes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaVacuandes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, vencimientoDosis, vacuna, vacunacion, numeroDosisVacuna, restriccion, etapa,
	 * oficinaEPSRegional, funcionario, profesion, estadoDeSalud, puntoDeVacunacion, 
	 * contrasenaFuncionario, adminPlanVacunacion, adminEPSRegional, adminPuntVacunacion,
	 * operadorPuntoVacunacion, ciudadano, dosis, cita, estadoProceso.
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaVacuandes
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla VACUANDES de la base de datos
	 */
	private SQLVacunacion sqlVacunacion;
	
	/**
	 * Atributo para el acceso a la tabla CIUDADANO de la base de datos
	 */
	private SQLCiudadano sqlCiudadano;
	
	/**
	 * Atributo para el acceso a la tabla ETAPA de la base de datos
	 */
	private SQLEtapa sqlEtapa;
	
	/**
	 * Atributo para el acceso a la tabla PROFESION de la base de datos
	 */
	private SQLProfesion sqlProfesion;
	
	/**
	 * Atributo para el acceso a la tabla ESTADO_DE_SALUD de la base de datos
	 */
	private SQLEstadoDeSalud sqlEstadoDeSalud;
	
	/**
	 * Atributo para el acceso a la tabla OFICINA_EPS_REGIONAL de la base de datos
	 */
	private SQLOficinaEPSRegional sqlOficinaEPSRegional;
	
	/**
	 * Atributo para el acceso a la tabla PUNTO_VACUNACION de la base de datos
	 */
	private SQLPuntoVacunacion sqlPuntoVacunacion;
	
	/**
	 * Atributo para el acceso a la tabla VENCIMIENTO_DOSIS de la base de datos
	 */
	private SQLVencimientoDosis sqlVencimientoDosis;
	
	/**
	 * Atributo para el acceso a la tabla VACUNA de la base de datos
	 */
	private SQLVacuna sqlVacuna;
	
	/**
	 * Atributo para el acceso a la tabla FUNCIONARIOS de la base de datos
	 */
	private SQLFuncionario sqlFuncionario;
	
	/**
	 * Atributo para el acceso a la tabla ADMIN_PLAN_VACUNACION de la base de datos
	 */
	private SQLAdminPlanVacunacion sqlAdminPlanVacunacion;
	
	/**
	 * Atributo para el acceso a la tabla ADMIN_EPS de la base de datos
	 */
	private SQLAdminEps sqlAdminEps;
	
	/**
	 * Atributo para el acceso a la tabla ADMIN_EPS de la base de datos
	 */
	private SQLAdminPuntoVacunacion sqlAdminPuntoVacunacion;

	/**
	 * Atributo para el acceso a la tabla CITA de la base de datos
	 */
	private SQLCita sqlCita;
	
	/**
	 * Atributo para el acceso a la tabla ESTADO_PROCESO de la base de datos
	 */
	private SQLEstadoProceso sqlEstadoProceso;

	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaVacuandes () {
		pmf = JDOHelper.getPersistenceManagerFactory("Vacuandes");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Vacuandes_sequence");
		tablas.add("VENCIMIENTO_DOSIS");
		tablas.add("VACUNA");
		tablas.add ("VACUNACION");
		tablas.add("NUMERO_DOSIS_VACUNA");
		tablas.add("RESTRICCIONES");
		tablas.add("ETAPA");
		tablas.add("OFICINA_EPS_REGIONAL");
		tablas.add("FUNCIONARIOS");
		tablas.add ("PROFESION");
		tablas.add ("ESTADO_DE_SALUD");
		tablas.add ("PUNTO_VACUNACION");
		tablas.add("CONTRASENA_FUNCIONARIOS");
		tablas.add("ADMIN_PLAN_VACUNACION");
		tablas.add("ADMIN_EPS");
		tablas.add("ADMIN_PUNTO_VACUNACION");
		tablas.add("OPERADOR_PUNTO_VACUNACION");
		tablas.add("CIUDADANOS");
		tablas.add("DOSIS");
		tablas.add("CITAS");
		tablas.add ("ESTADO_PROCESO");
		
	}
	
	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaVacuandes (JsonObject tableConfig) {
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}
	
	/**
	 * @return Retorna el único objeto PersistenciaVacuandes existente - Patrón SINGLETON
	 */
	public static PersistenciaVacuandes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaVacuandes ();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaVacuandes existente - Patrón SINGLETON
	 */
	public static PersistenciaVacuandes  getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaVacuandes  (tableConfig);
		}
		return instance;
	}
	
	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlVencimientoDosis=new SQLVencimientoDosis(this);
		sqlVacuna=new SQLVacuna(this);
		sqlVacunacion = new SQLVacunacion(this);
		
		sqlEtapa = new SQLEtapa(this);
		sqlOficinaEPSRegional= new SQLOficinaEPSRegional(this);
		sqlFuncionario=new SQLFuncionario(this);
		sqlProfesion= new SQLProfesion(this);
		sqlEstadoDeSalud= new SQLEstadoDeSalud(this);		
		sqlPuntoVacunacion=new SQLPuntoVacunacion(this);
		
		sqlAdminPlanVacunacion=new SQLAdminPlanVacunacion(this);
		sqlAdminEps=new SQLAdminEps(this);
		sqlAdminPuntoVacunacion=new SQLAdminPuntoVacunacion(this);
		
		sqlCiudadano = new SQLCiudadano(this);
		sqlEstadoProceso=new SQLEstadoProceso(this);
		
		sqlUtil = new SQLUtil(this);
	}
	//TODO Corregir el orden  de tablas
	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de Vacuandes 
	 */
	public String darSeqVacuandes()
	{
		return tablas.get (0);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla VENCIMIENTO_DOSIS de Vacuandes
	 */
	public String darTablaVencimientoDosis()
	{
		return tablas.get (1);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla VACUNA de Vacuandes
	 */
	public String darTablaVacuna()
	{
		return tablas.get (2);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Vacunacion de Vacuandes 
	 */
	public String darTablaVacunacion()
	{
		return tablas.get (3);
	}
	
	//4.Numero de dosis vacuna
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PUNTO_VACUNACION de Vacuandes
	 */
	public String darTablaDosisVacuna()
	{
		return tablas.get (4);
	}
	
	//5.Restricciones
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PUNTO_VACUNACION de Vacuandes
	 */
	public String darTablaRestricciones()
	{
		return tablas.get (5);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Etapa de Vacuandes 
	 */
	public String darTablaEtapa()
	{
		return tablas.get (6);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de OficinaEPSRegional de Vacuandes
	 */
	public String darTablaOficinaEPSRegional()
	{
		return tablas.get (7);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla VACUNA de Vacuandes
	 */
	public String darTablaFuncionario()
	{
		return tablas.get (8);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Profesion de Vacuandes
	 */
	public String darTablaProfesion()
	{
		return tablas.get (9);
	}
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de EstadoDeSalud de Vacuandes
	 */
	public String darTablaEstadoDeSalud()
	{
		return tablas.get (10);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PUNTO_VACUNACION de Vacuandes
	 */
	public String darTablaPuntoVacunacion()
	{
		return tablas.get (11);
	}
	
	//12.Contraseña funcionarios
	/**
	 * @return La cadena de caracteres con el nombre de la tabla PUNTO_VACUNACION de Vacuandes
	 */
	public String darTablaContrasenaFuncionarios()
	{
		return tablas.get (12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla ADMIN_PLAN_VACUNACION de Vacuandes
	 */
	public String darTablaAdminPlan()
	{
		return tablas.get (13);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla ADMIN_PLAN_VACUNACION de Vacuandes
	 */
	public String darTablaAdminEps()
	{
		return tablas.get (14);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla ADMIN_PLAN_VACUNACION de Vacuandes
	 */
	public String darTablaAdminPuntoVacunacion()
	{
		return tablas.get (15);
	}
	
	//16.Operador Punto Vacunación
	/**
	 * @return La cadena de caracteres con el nombre de la tabla ADMIN_PLAN_VACUNACION de Vacuandes
	 */
	public String darTablaOperadorPuntoVacunacion()
	{
		return tablas.get (16);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Ciudadano de Vacuandes
	 */
	public String darTablaCiudadano()
	{
		return tablas.get (18);
	}
	
	//18.Dosis
	/**
	 * 
	 * @return La cadena de caracteres con el nombre de la tabla de Dosis de vacuades
	 */
	public String darTablaDosis()
	{
		return tablas.get(18);
	}
	/**
	 * 
	 * @return La cadena de caracteres con el nombre de la tabla de Cita de vacuandes
	 */
	public String darTablaCita()
	{
		return tablas.get(19);
	}
	
	/**
	 * 
	 * @return La cadena de caracteres con el nombre de la tabla de Cita de vacuandes
	 */
	public String darTablaEstadoProceso()
	{
		return tablas.get(20);
	}
	
	//--------------------------------
	// Set isolation
	//---------------------------------
	public void setIsolation() {
		sqlVacunacion.setIsolation(pmf.getPersistenceManager());
	}
	/**
	 * Transacción para el generador de secuencia de Vacuandes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Vacuandes
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar Vacunacion
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Vacunacion
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Vacunacion adicionado. null si ocurre alguna Excepción
	 */
	public Vacunacion adicionarVacunacion(int dosisTotales, int dosisAplicadas, int dosisPerdidas, long idEtapaActual)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idVacunacion= nextval ();
            long tuplasInsertadas = sqlVacunacion.adicionarVacunacion(pm, idVacunacion, dosisTotales, dosisAplicadas, dosisPerdidas, idEtapaActual);
            tx.commit();
            
            log.trace ("Inserción de Vacunacion: [" +dosisTotales+", "+dosisAplicadas+", "+dosisPerdidas+", "+idEtapaActual+ "] : " + tuplasInsertadas + " tuplas insertadas");
            
            return new Vacunacion (idVacunacion, dosisTotales, dosisAplicadas, dosisPerdidas, idEtapaActual);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
        
    /**
	 * Método que elimina, de manera transaccional, una tupla en la tabla VACUNACION  dado el id
	 * @param idVacunacion - id de Vacunacion
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVacunacionPorId(long idVacunacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlVacunacion.eliminarVacunacionPorId(pm, idVacunacion);           
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//            	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	/**
	 * Método que consulta todas las tuplas en la tabla VACUNACION
	 * @return La lista de objetos VACUNACION, construidos con base en las tuplas de la tabla VACUNACION
	 */
	public List<Vacunacion> darVacunaciones()
	{
		return sqlVacunacion.darVacunaciones(pmf.getPersistenceManager());
	}
    
	/**
	 * Método que consulta todas las tuplas en la tabla VACUNACION que tienen el identificador dado
	 * @param idVacunacion - El identificador del Vacunacion 
	 * @return El objeto VACUNACION, construido con base en la tuplas de la tabla VACUNACION, que tiene el identificador dado
	 */
	public Vacunacion darVacunacionPorId (long idVacunacion) 
	{
		return sqlVacunacion.darVacunacionPorId (pmf.getPersistenceManager(), idVacunacion);
	}
	/* ****************************************************************
	 * 			Métodos para manejar Ciudadano
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Ciudadano
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Ciudadano adicionado. null si ocurre alguna Excepción
	 */
	public Ciudadano adicionarCiudadano(String nombre, String documento, int estadoActual, Date fechaNacimiento, String direccion, String ciudad, String numeroContacto, long idVacunacion, long idOficinaEPSRegional, long idPuntoVacunacion, long idProfesion, long idEstadoSalud, long idEtapa)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idCiudadano = nextval ();
            long tuplasInsertadas = sqlCiudadano.adicionarCiudadano(pm, idCiudadano, nombre, documento, estadoActual, fechaNacimiento, direccion, ciudad, numeroContacto, idVacunacion, idOficinaEPSRegional, idPuntoVacunacion, idProfesion, idEstadoSalud, idEtapa);
            tx.commit();
            
            log.trace ("Inserción de Ciudadano: " +nombre+": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Ciudadano (idCiudadano, nombre, documento, estadoActual, fechaNacimiento, direccion, ciudad, numeroContacto, idVacunacion, idOficinaEPSRegional, idPuntoVacunacion, idProfesion, idEstadoSalud, idEtapa);
        }
        catch (Exception e)
        {
//            	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
            
    /**
	 * Método que elimina, de manera transaccional, una tupla en la tabla CIUDADANO dado el id
	 * @param idCiudadano - identificador del ciudadano
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarCiudadanoPorId(long idCiudadano) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCiudadano.eliminarCiudadanoPorId(pm, idCiudadano);           
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//                	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	/**
	 * Método que consulta todas las tuplas en la tabla CIUDADANO
	 * @return La lista de objetos CIUDADANO, construidos con base en las tuplas de la tabla CIUDADANO
	 */
	public List<Ciudadano> darCiudadanos ()
	{
		return sqlCiudadano.darCiudadanos (pmf.getPersistenceManager());
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla CIUDADANO que tienen el identificador dado
	 * @param idCiudadano - El identificador del Ciudadano 
	 * @return El objeto CIUDADANO, construido con base en la tuplas de la tabla CIUDADANO, que tiene el identificador dado
	 */
	public Ciudadano darCiudadanoPorId (long idCiudadano) 
	{
		return sqlCiudadano.darCiudadanoPorId (pmf.getPersistenceManager(), idCiudadano);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla CIUDADANO que tienen el cedula dado
	 * @param cedula - El identificador del Ciudadano 
	 * @return El objeto CIUDADANO, construido con base en la tuplas de la tabla CIUDADANO, que tiene el cedula dado
	 */
	public Ciudadano darCiudadanoPorCedula (String cedula) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlCiudadano.darCiudadanoPorCedula (pm, cedula);
	}
	
	/**
	 * Método que actualiza el id Punto Vacunación en las tuplas en la tabla CIUDADANO que tienen el cedula dada
	 * @param cedula - El identificador del Ciudadano 
	 * @return El número de tuplas modificadas. Retorna -1 si no pudo completar la transacción
	 */
	public long actualizarPuntoVacunacionCiudadano (long idPunto,String cedula) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCiudadano.actualizarPuntoVacunacionCiudadano(pm, idPunto, cedula);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar Etapa
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Etapa
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Etapa adicionado. null si ocurre alguna Excepción
	 */
	public Etapa adicionarEtapa(int fase, String descripcion, int edadMinima, int edadMaxima, long idVacunacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idEtapa = nextval ();
            long tuplasInsertadas = sqlEtapa.adicionarEtapa(pm, idEtapa, fase, descripcion, edadMinima, edadMaxima, idVacunacion);
            tx.commit();
            
            log.trace ("Inserción de Etapa: " +descripcion+": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Etapa (idEtapa, fase, descripcion, edadMinima, edadMaxima, idVacunacion);
        }
        catch (Exception e)
        {
//            	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
            
    /**
//	 * Método que elimina, de manera transaccional, una tupla en la tabla ETAPA dado el id
	 * @param idEtapa- identificador de la etapa
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarEtapaPorId(long idEtapa) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlEtapa.eliminarEtapaPorId(pm, idEtapa);           
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//                	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	/**
	 * Método que consulta todas las tuplas en la tabla ETAPA
	 * @return La lista de objetos ETAPA, construidos con base en las tuplas de la tabla ETAPA
	 */
	public List<Etapa> darEtapas()
	{
		return sqlEtapa.darEtapas (pmf.getPersistenceManager());
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla ETAPA que tienen el identificador dado
	 * @param idEtapa- El identificador del Etapa 
	 * @return El objeto ETAPA, construido con base en la tuplas de la tabla ETAPA, que tiene el identificador dado
	 */
	public Etapa darEtapaPorId (long idEtapa) 
	{
		return sqlEtapa.darEtapaPorId (pmf.getPersistenceManager(), idEtapa);
	}
	/* ****************************************************************
	 * 			Métodos para manejar Profesion
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Profesion
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Profesion adicionado. null si ocurre alguna Excepción
	 */
	public Profesion adicionarProfesion(String descripcion, long idEtapa)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idProfesion = nextval ();
            long tuplasInsertadas = sqlProfesion.adicionarProfesion(pm, idProfesion, descripcion, idEtapa);
            tx.commit();
            
            log.trace ("Inserción de Profesion: " +descripcion+": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Profesion (idProfesion, descripcion, idEtapa);
        }
        catch (Exception e)
        {
//            	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
            
    /**
//	 * Método que elimina, de manera transaccional, una tupla en la tabla PROFESION dado el id
	 * @param idProfesion - identificador de la Profesion
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarProfesionPorId(long idProfesion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlProfesion.eliminarProfesionPorId(pm, idProfesion);           
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//                	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	/**
	 * Método que consulta todas las tuplas en la tabla PROFESION
	 * @return La lista de objetos PROFESION, construidos con base en las tuplas de la tabla PROFESION
	 */
	public List<Profesion> darProfesiones()
	{
		return sqlProfesion.darProfesiones (pmf.getPersistenceManager());
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla PROFESION que tienen el identificador dado
	 * @param idProfesion- El identificador del Profesion 
	 * @return El objeto PROFESION, construido con base en la tuplas de la tabla PROFESION, que tiene el identificador dado
	 */
	public Profesion darProfesionPorId (long idProfesion) 
	{
		return sqlProfesion.darProfesionPorId (pmf.getPersistenceManager(), idProfesion);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar Estado de Salud
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Estado de Salud
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Estado de Salud adicionado. null si ocurre alguna Excepción
	 */
	public EstadoDeSalud adicionarEstadoDeSalud(String descripcion, long idEtapa)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idEstadoDeSalud = nextval ();
            long tuplasInsertadas = sqlEstadoDeSalud.adicionarEstadoDeSalud(pm, idEstadoDeSalud, descripcion, idEtapa);
            tx.commit();
            
            log.trace ("Inserción de Estado de Salud: " +descripcion+": " + tuplasInsertadas + " tuplas insertadas");
            
            return new EstadoDeSalud (idEstadoDeSalud, descripcion, idEtapa);
        }
        catch (Exception e)
        {
//            	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
            
    /**
	 * Método que elimina, de manera transaccional, una tupla en la tabla ESTADO DE SALUD dado el id
	 * @param idEstadoDeSalud - identificador de la Estado de salud
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarEstadoDeSaludPorId(long idEstadoDeSalud) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlEstadoDeSalud.eliminarEstadoDeSaludPorId(pm, idEstadoDeSalud);           
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//                	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	/**
	 * Método que consulta todas las tuplas en la tabla ESTADO DE SALUD
	 * @return La lista de objetos ESTADO DE SALUD, construidos con base en las tuplas de la tabla ESTADO DE SALUD
	 */
	public List<EstadoDeSalud> darEstadosDeSalud()
	{
		return sqlEstadoDeSalud.darEstadosDeSalud(pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla ESTADO DE SALUD que tienen el identificador dado
	 * @param idEstadoDeSalud- El identificador del EstadoDeSalud 
	 * @return El objeto ESTADO DE SALUD, construido con base en la tuplas de la tabla ESTADO DE SALUD, que tiene el identificador dado
	 */
	public EstadoDeSalud darEstadoDeSaludPorId (long idEstadoDeSalud) 
	{
		return sqlEstadoDeSalud.darEstadoDeSaludPorId (pmf.getPersistenceManager(), idEstadoDeSalud);
	}
	/* ****************************************************************
	 * 			Métodos para manejar Oficina EPS regional
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Oficina EPS Regional
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Oficina EPS Regional adicionado. null si ocurre alguna Excepción
	 */
	public OficinaEPSRegional adicionarOficinaEPSRegional(String nombre, String NIT, String region, String direccion, String numeroContacto, String ciudad, long idVacunacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idOficinaEPSRegional = nextval ();
            long tuplasInsertadas = sqlOficinaEPSRegional.adicionarOficinaEPSRegional(pm, idOficinaEPSRegional, nombre, NIT, region, direccion, numeroContacto, ciudad, idVacunacion);
            tx.commit();
            
            log.trace ("Inserción de Oficina EPS Regional: " +nombre+": " + tuplasInsertadas + " tuplas insertadas");
            
            return new OficinaEPSRegional (idOficinaEPSRegional, nombre, NIT, region, direccion, numeroContacto, ciudad, idVacunacion);
        }
        catch (Exception e)
        {
//            	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
            
    /**
//	 * Método que elimina, de manera transaccional, una tupla en la tabla OFICINA EPS REGIONAL dado el id
	 * @param idOficinaEPSRegional - identificador de la Oficina EPS Regional
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOficinaEPSRegionalPorId (long idOficinaEPSRegional ) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOficinaEPSRegional .eliminarOficinaEPSRegionalPorId (pm, idOficinaEPSRegional );           
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//                	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	/**
//	 * Método que consulta todas las tuplas en la tabla OFICINA EPS REGIONAL
	 * @return La lista de objetos OFICINA EPS REGIONAL, construidos con base en las tuplas de la tabla OFICINA EPS REGIONAL 
	 */
	public List<OficinaEPSRegional > darOficinasEPSRegional ()
	{
		return sqlOficinaEPSRegional .darOficinasEPSRegional (pmf.getPersistenceManager());
	}
	/**
	 * Método que consulta todas las tuplas en la tabla OFICINA EPS REGIONAL que tienen el identificador dado
	 * @param idOficinaEPSRegional- El identificador del OficinaEPSRegional 
	 * @return El objeto OFICINA EPS REGIONAL, construido con base en la tuplas de la tabla OFICINA EPS REGIONAL, que tiene el identificador dado
	 */
	public OficinaEPSRegional darOficinaEPSRegionalPorId (long idOficinaEPSRegional) 
	{
		return sqlOficinaEPSRegional.darOficinaEPSRegionalPorId (pmf.getPersistenceManager(), idOficinaEPSRegional);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PUNTO_VACUNACION
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla PUNTO_VACUNACION
	 * Adiciona entradas al log de la aplicación
	 * @param localización - La localización del Punto de Vacunación
	 * @param cupoMaximo - El máximo de ciudadanos que pueden estar al tiempo en el Punto de Vacunación
	 * @param maxCiudadanosPorDia - La capacidad de atención por día del Punto de Vacunación
	 * @param totalVacunados - El número de ciudadanos que ha vacunado el Punto de Vacunación
	 * @param infraestructura - La infraestructura para almacenar dosis del punto de vacunación
	 * @param capacidadAlmacenamiento - El número de dosis máximo que puede tener el punto
	 * @param idEps - El id de la Oficina de EPS Regional al que pertenece el Punto de Vacunación 
	 * @return El objeto PuntoVacunacion adicionado. null si ocurre alguna Excepción
	 */
	public PuntoVacunacion adicionarPuntoVacunacion (String localizacion, int cupoMaximo,
			int maxCiudadanosPorDia, int totalVacunados, String infraestructura, int capacidadAlmacenamiento, long idEps)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idPtoVacunacion = nextval ();
            long tuplasInsertadas = sqlPuntoVacunacion.adicionarPuntoVacunacion(pm, idPtoVacunacion, localizacion, cupoMaximo, maxCiudadanosPorDia, totalVacunados, infraestructura, capacidadAlmacenamiento, idEps);
            tx.commit();

            log.trace ("Inserción de Punto Vacunacion: " + localizacion + ": " + tuplasInsertadas + " tuplas insertadas");

            return new PuntoVacunacion(idPtoVacunacion, localizacion, cupoMaximo, maxCiudadanosPorDia, totalVacunados, infraestructura, capacidadAlmacenamiento, idEps);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla PUNTO_VACUNACION, dado el identificador del punto
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación 
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPuntoVacunacionPorId (long idPtoVacunacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPuntoVacunacion.eliminarPuntoVacunacionPorId(pm, idPtoVacunacion);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla PUNTO_VACUNACION, dado el identificador de la EPS regional al que pertenece
	 * @param idEps - El identificador de la Oficina de la EPS Regional 
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPuntoVacunacionPorIdEps (long idEps) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPuntoVacunacion.eliminarPuntoVacunacionPorIdEps(pm, idEps);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla PUNTO_VACUNACION
	 * @return La lista de objetos PuntoVacunacion, construidos con base en las tuplas de la tabla PUNTO_VACUNACION
	 */
	public List<PuntoVacunacion> darPuntosVacunacion()
	{
		return sqlPuntoVacunacion.darPuntosVacunacion(pmf.getPersistenceManager());
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla PUNTO_VACUNACION que tienen el identificador dado
	 * @param idPtoVacunacion- El identificador del Punto de vacunación
	 * @return El objeto PuntoVacunacion, construido con base en la tuplas de la tabla PUNTO_VACUNACION, que tiene el identificador dado
	 */
	public PuntoVacunacion darPuntoVacunacionPorId (long idPtoVacunacion) 
	{
		return (PuntoVacunacion) sqlPuntoVacunacion.darPuntoVacunacionPorId(pmf.getPersistenceManager(), idPtoVacunacion);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla PUNTO_VACUNACION que tienen el id de la EPS dado
	 * @param idEps - El identificador de la Oficina de la EPS Regional
	 * @return Los objetos Punto Vacunación, construido con base en la tuplas de la tabla PUNTO_VACUNACION, que tiene el identificador dado
	 */
	public List<PuntoVacunacion> darPuntoVacunacionPorIdEps (long idEps) 
	{
		return (List<PuntoVacunacion>) sqlPuntoVacunacion.darPuntoVacunacionPorIdEps(pmf.getPersistenceManager(), idEps);
	}
	
	/**
	 * Método que actualiza, de manera transaccional, aumentando en 1 el número total de ciudadanos vacunados por un punto de vacunación
	 * @param idPtoVacunacion - El identificador del Punto de Vacunación
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long aumentarTotalVacunados (long idPtoVacunacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPuntoVacunacion.aumentarTotalVacunados(pm, idPtoVacunacion);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar el VENCIMIENTO_DOSIS
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla VENCIMIENTO_DOSIS
	 * Adiciona entradas al log de la aplicación
	 * @param lote, El lote de la dosis a adicionar
	 * @param fechaVencimiento, La fecha de vencimiento de la dosis
	 * @return El objeto PuntoVacunacion adicionado. null si ocurre alguna Excepción
	 */
	public VencimientoDosis adicionarVencimientoDosis (String lote, Date fVenc)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();            
            long tuplasInsertadas = sqlVencimientoDosis.adicionarVencimientoDosis(pm, lote, fVenc);
            tx.commit();

            log.trace ("Inserción de Vencimiento Dosis: " + lote + ": " + tuplasInsertadas + " tuplas insertadas");

            return new VencimientoDosis(lote, fVenc);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla VENCIMIENTO_DOSIS
	 * @return La lista de objetos PuntoVacunacion, construidos con base en las tuplas de la tabla VENCIMIENTO_DOSIS
	 */
	public List<VencimientoDosis> darVencimientosDosis()
	{
		return sqlVencimientoDosis.darVencimientosDosis(pmf.getPersistenceManager());
	}
	
	/**
	 * Método que consulta la tupla en la tabla VENCIMIENTO_DOSIS con un lote en espcífico
	 * @param lote - El lote buscado
	 * @return Un objetos PuntoVacunacion que tiene el lote buscado
	 */
	public VencimientoDosis darVencimientoDosisporLote(String lote)
	{
		return sqlVencimientoDosis.darVencimientoDosisPorLote(pmf.getPersistenceManager(),lote);
	}
	
	/**
	 * Método que actualiza, de manera transaccional, la fecha de vencimiento de un lote
	 * @param lote - El lote de las dosis a modificar
	 * @param nFecha - la nueva fecha de vencimiento
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long modificarVencimientoDosis (String lote, Date nFecha)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlVencimientoDosis.modificarVencimientoDosis(pm, lote, nFecha);
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla VENCIMIENTO_DOSIS, dado el lote
	 * @param lote - El lote de las dosis a modificar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVencimientoDosisPorLote (String lote) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlVencimientoDosis.eliminarVencimientoDosisPorLote(pm, lote);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la VACUNA
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla VACUNA
	 * Adiciona entradas al log de la aplicación
	 * @param nombre, El nombre de la vacuna
	 * @param requerimientos, Los requerimientos de almacenamiento de la vacuna
	 * @return El objeto Vacuna adicionado. null si ocurre alguna Excepción
	 */
	public Vacuna adicionarVacuna (String nombre, String requerimientos)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idVacuna = nextval ();
            long tuplasInsertadas = sqlVacuna.adicionarVacuna(pm, idVacuna, nombre, requerimientos);

            log.trace ("Inserción de Vacuna: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

            return new Vacuna(idVacuna, nombre, requerimientos);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla VACUNA
	 * @return La lista de objetos Vacuna, construidos con base en las tuplas de la tabla VACUNA
	 */
	public List<Vacuna> darVacunas()
	{
		return sqlVacuna.darVacunas(pmf.getPersistenceManager());
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla VACUNA que tienen el identificador dado
	 * @param id- El identificador de la Vacuna
	 * @return El objeto Vacuna, construido con base en la tuplas de la tabla VACUNA, que tiene el identificador dado
	 */
	public Vacuna darVacunaPorId (long id) 
	{
		return (Vacuna) sqlVacuna.darVacunaPorId(pmf.getPersistenceManager(), id);
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla VACUNA, dado el identificador de la vacuna
	 * @param id- El identificador de la Vacuna 
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVacunaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlVacuna.eliminarVacunaPorId(pm, id);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar el Admin del Plan Vacunación
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ADMIN_PUNTO_VACUNACION y FUNCIONARIOS
	 * Adiciona entradas al log de la aplicación
	 * @param 
	 * @param requerimientos, Los requerimientos de almacenamiento de la vacuna
	 * @return El objeto Vacuna adicionado. null si ocurre alguna Excepción
	 */
	public AdminPlanVacunacion adicionarAdminPlanVacunacion (long idPlan, String correo, long idPlanAdmin)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlFuncionario.adicionarFuncionario(pm, id, correo, idPlan);
            tuplasInsertadas+= sqlAdminPlanVacunacion.adicionarAdminPlanVacunacion(pm, id, idPlanAdmin);

            log.trace ("Inserción de Funcionarios y AdminPlan Vacunacion: " + correo  + ": " + tuplasInsertadas + " tuplas insertadas");

            return new AdminPlanVacunacion(id,correo,idPlan,idPlanAdmin);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un ADMIN PLAN VACUNACION de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param correo - El correo del Funcionario
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminPlanVacunacionPorCorreo(String correo) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            AdminPlanVacunacion admin=(AdminPlanVacunacion) sqlFuncionario.darfuncionarioPorCorreo(pm, correo);
            long resp = sqlAdminPlanVacunacion.eliminarAdminPlanVacunacionPorId(pm, admin.getId());
            resp+= sqlFuncionario.eliminarFuncionarioPorCorreo(pm, correo);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla ADMIN_PLAN_VACUNACION que tienen el identificador dado
	 * @param id- El identificador del funcionario
	 * @return El objeto Admin Plan Vacunación, construido con base en la tuplas de la tabla VACUNA, que tiene el identificador dado
	 */
	public AdminPlanVacunacion darAdminPlanVacunacionPorCorreo (String correo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		AdminPlanVacunacion base=(AdminPlanVacunacion) sqlFuncionario.darfuncionarioPorCorreo(pm, correo);
		AdminPlanVacunacion resp=(AdminPlanVacunacion) sqlAdminPlanVacunacion.darAdminPlanVacunacionPorId(pm, base.getId());
		base.setId_Plan_Vacunacion(resp.getId_Plan_Vacunacion());
		return base;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar el Admin de una Oficina de EPS regional
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ADMIN_EPS y FUNCIONARIOS
	 * Adiciona entradas al log de la aplicación
	 * @param 
	 * @param requerimientos, Los requerimientos de almacenamiento de la vacuna
	 * @return El objeto Vacuna adicionado. null si ocurre alguna Excepción
	 */
	public AdminEps adicionarAdminEps (long idPlan, String correo, long idOficinaEps)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlFuncionario.adicionarFuncionario(pm, id, correo, idPlan);
            tuplasInsertadas+= sqlAdminEps.adicionarAdminEPS(pm, id, idOficinaEps);

            log.trace ("Inserción de Funcionarios y AdminPlan Vacunacion: " + correo  + ": " + tuplasInsertadas + " tuplas insertadas");

            return new AdminEps(id,correo,idPlan,idOficinaEps);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un ADMIN_EPS de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param correo - El correo del Funcionario
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminEpsPorCorreo(String correo) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            AdminEps admin=(AdminEps) sqlFuncionario.darfuncionarioPorCorreo(pm, correo);
            long resp = sqlAdminEps.eliminarAdminEpsPorId(pm, admin.getId());
            resp+= sqlFuncionario.eliminarFuncionarioPorCorreo(pm, correo);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla ADMIN_PLAN_VACUNACION que tienen el identificador dado
	 * @param id- El identificador del funcionario
	 * @return El objeto Admin Plan Vacunación, construido con base en la tuplas de la tabla VACUNA, que tiene el identificador dado
	 */
	public AdminEps darAdminEpsPorCorreo (String correo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		AdminEps base=(AdminEps) sqlFuncionario.darfuncionarioPorCorreo(pm, correo);
		AdminEps resp=(AdminEps) sqlAdminEps.darAdminEpsPorId(pm, base.getId());
		base.setId_Oficina_Eps(resp.getId_Oficina_Eps());
		return base;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar el Admin de un Punto de Vacunación
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ADMIN_PUNTO_VACUNACION y FUNCIONARIOS
	 * Adiciona entradas al log de la aplicación
	 * @param 
	 * @param requerimientos, Los requerimientos de almacenamiento de la vacuna
	 * @return El objeto Vacuna adicionado. null si ocurre alguna Excepción
	 */
	public AdminPuntoVacunacion adicionarAdminPuntoVacunacion (long idPlan, String correo, long idPunto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlFuncionario.adicionarFuncionario(pm, id, correo, idPlan);
            tuplasInsertadas+= sqlAdminPlanVacunacion.adicionarAdminPlanVacunacion(pm, id, idPunto);

            log.trace ("Inserción de Funcionarios y Admin Punto Vacunacion: " + correo  + ": " + tuplasInsertadas + " tuplas insertadas");

            return new AdminPuntoVacunacion(id,correo,idPlan,idPunto);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un ADMIN_EPS de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param correo - El correo del Funcionario
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminPuntoVacunacionPorCorreo(String correo) {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            AdminPuntoVacunacion admin=(AdminPuntoVacunacion) sqlFuncionario.darfuncionarioPorCorreo(pm, correo);
            long resp = sqlAdminPuntoVacunacion.eliminarAdminPuntoVacunacionPorId(pm, admin.getId());
            resp+= sqlFuncionario.eliminarFuncionarioPorCorreo(pm, correo);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla ADMIN_PLAN_VACUNACION que tienen el identificador dado
	 * @param id- El identificador del funcionario
	 * @return El objeto Admin Plan Vacunación, construido con base en la tuplas de la tabla VACUNA, que tiene el identificador dado
	 */
	public AdminPuntoVacunacion darAdminPuntoVacunacionPorCorreo (String correo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		AdminPuntoVacunacion base=(AdminPuntoVacunacion) sqlFuncionario.darfuncionarioPorCorreo(pm, correo);
		AdminPuntoVacunacion resp=(AdminPuntoVacunacion) sqlAdminPuntoVacunacion.darAdminPuntoVacunacionPorId(pm, base.getId());
		base.setId_Punto_Vacunacion(resp.getId_Punto_Vacunacion());
		return base;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar CITA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Cita
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Cita adicionado. null si ocurre alguna Excepción
	 */
	public Cita adicionarCita(Timestamp fechaYHora, int duracionCita, String estadoCita,long idCiudadano, long idPuntoVacunacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idCita = nextval ();
            long tuplasInsertadas = sqlCita.adicionarCita(pm, idCita, fechaYHora, duracionCita, estadoCita, idCiudadano, idPuntoVacunacion);
            tx.commit();
            
            log.trace ("Inserción de Cita: " +fechaYHora+": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Cita (idCita, fechaYHora, duracionCita, estadoCita, idCiudadano, idPuntoVacunacion);
        }
        catch (Exception e)
        {
//            	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
            
    /**
//	 * Método que elimina, de manera transaccional, una tupla en la tabla CITA dado el id
	 * @param idCita- identificador de la Cita
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarCitaPorId(long idCita) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlEtapa.eliminarEtapaPorId(pm, idCita);           
            tx.commit();

            return resp;
        }
        catch (Exception e)
        {
//                	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	/**
	 * Método que consulta todas las tuplas en la tabla CITA
	 * @return La lista de objetos CITA, construidos con base en las tuplas de la tabla CITA
	 */
	public List<Cita> darCitas()
	{
		return sqlCita.darCitas(pmf.getPersistenceManager());
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla CITA que tienen el identificador dado
	 * @param idCita- El identificador del Cita
	 * @return El objeto CITA, construido con base en la tuplas de la tabla CITA, que tiene el identificador dado
	 */
	public Cita darCitaPorId (long idCita) 
	{
		return sqlCita.darCitaPorId (pmf.getPersistenceManager(), idCita);
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar el ESTADO_PROCESO
	 *****************************************************************/
	
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ESTADO_PROCESO
	 * Adiciona entradas al log de la aplicación
	 * @param requerimientos, Los requerimientos de almacenamiento de la vacuna
	 * @return El objeto Estado Proceso adicionado. null si ocurre alguna Excepción
	 */
	public EstadoProceso adicionarEstadoProceso (String estado, Date fecha_Cambio,String comentario_descripcion, long idCiudadano)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlEstadoProceso.adicionarEstadoProceso(pm, id, estado, fecha_Cambio, comentario_descripcion, idCiudadano);

            log.trace ("Inserción de Estado Proceso: " + id + ": " + tuplasInsertadas + " tuplas insertadas");

            return new EstadoProceso(id, estado, fecha_Cambio, comentario_descripcion, idCiudadano);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un ESTADO_PROCESO de la base de datos de Vacuandes, por sus identificadores
	 * @param pm - El manejador de persistencia
	 * @param correo - El correo del Funcionario
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstadoProcesoPorId(long id)  {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp=sqlEstadoProceso.eliminarEstadoProcesoPorId(pm, id);
            
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un ESTADO_PROCESO de la base de datos de Vacuandes por el ID del ciudadano
	 * @param pm - El manejador de persistencia
	 * @param id - Id dle ciudadano
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstadoProcesoPorIdCiudadano(long id)  {
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp=sqlEstadoProceso.eliminarEstadoProcesoPorIdCiudadano(pm, id);
            
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla ESTADO_PROCESO que tienen el identificador de ciudadano dado
	 * @param id- El identificador del ciudadano
	 * @return El objeto Estado Proceso, construido con base en la tuplas de la tabla VACUNA, que tiene el identificador dado
	 */
	public List<EstadoProceso> darEstadosProcesoIdPorCiudadano(long id)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		List<EstadoProceso> resp=sqlEstadoProceso.darEstadoProcesoPorIdCiudadano(pm, id);
		return resp;
	}
	
	/* ****************************************************************
	 * 			Método para trabajar con toda la base de datos 
	 *****************************************************************/	
	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Vacuandes
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarVacuandes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarVacuandes (pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	
}
