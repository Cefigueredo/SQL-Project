package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Vacuandes;
import uniandes.isis2304.parranderos.negocio.VOVacunacion;

/**
 * Clase con los métdos de prueba de funcionalidad sobre VACUNACION
 * @author Grupo 8
 *
 */
public class VacunacionTest {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(VacunacionTest.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
	/**
	 * La clase que se quiere probar
	 */
    private Vacuandes vacuandes;
    

    /* ****************************************************************
	 * 			Métodos de prueba para la tabla Vacunacion - Creación y borrado
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla Vacunacion
	 * 1. Adicionar una vacunacion
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar una vacunacion por su identificador
	 * 4. Borrar una vacunacion por su identificador
	 */
    @Test
	public void CRDVacunacionTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre Vacunacion");
			vacuandes = new Vacuandes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Vacunacion incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Vacunacion incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de Vacuandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los tipos de bebida con la tabla vacía
			List <VOVacunacion> lista = vacuandes.darVOVacunaciones();
			assertEquals ("No debe haber tipos de bebida creados!!", 0, lista.size ());

			// Lectura de los tipos de bebida con un tipo de bebida adicionado
			int dosisTotales = 1000000;
			VOVacunacion vacunacion1 = vacuandes.adicionarVacunacion(dosisTotales, 0, 0, 0);
			lista = vacuandes.darVOVacunaciones();
			assertEquals ("Debe haber una vacunacion creada !!", 1, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", vacunacion1, lista.get (0));

			// Lectura de los tipos de bebida con dos tipos de bebida adicionados
			int dosisTotales2 = 2000000;
			VOVacunacion vacunacion2 = vacuandes.adicionarVacunacion (dosisTotales2, 0, 0, 0);
			lista = vacuandes.darVOVacunaciones();
			assertEquals ("Debe haber dos vacunaciones creadas !!", 2, lista.size ());
			assertTrue ("La primer vacunacion adicionada debe estar en la tabla", vacunacion1.equals (lista.get (0)) || vacunacion1.equals (lista.get (1)));
			assertTrue ("La segunda vacunacion adicionada debe estar en la tabla", vacunacion2.equals (lista.get (0)) || vacunacion2.equals (lista.get (1)));

			// Prueba de eliminación de una vacunacion, dado su identificador
			long vEliminados = vacuandes.eliminarVacunacionPorId (vacunacion1.getId ());
			assertEquals ("Debe haberse eliminado una vacunacion!!", 1, vEliminados);
			lista = vacuandes.darVOVacunaciones();
			assertEquals ("Debe haber una sola vacunacion!!", 1, lista.size ());
			assertFalse ("La primera vacunacion adicionada NO debe estar en la tabla", vacunacion1.equals (lista.get (0)));
			assertTrue ("La segunda vacunacion adicionada debe estar en la tabla", vacunacion2.equals (lista.get (0)));
			
			// Prueba de eliminación de una vacunacion, dado su identificador
			vEliminados = vacuandes.eliminarVacunacionPorId (vacunacion1.getId ());
			assertEquals ("Debe haberse eliminado una vacunacion!!", 1, vEliminados);
			lista = vacuandes.darVOVacunaciones();
			assertEquals ("La tabla debió quedar vacía !!", 0, lista.size ());
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla Vacunacion.\n";
			msg += "Revise el log de vacuandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla Vacunacion");
		}
		finally
		{
			vacuandes.limpiarVacuandes ();
    		vacuandes.cerrarUnidadPersistencia ();    		
		}
	}
    	
    /**
     * Método de prueba de la restricción de unicidad sobre el nombre de Vacunacion
     */
    @Test
    public void unicidadVacunacionTest() 
    {
    	// Probar primero la conexión a la base de datos
    	try
    	{
    		log.info ("Probando la restricción de UNICIDAD del id de vacunacion");
    		vacuandes = new Vacuandes (openConfig (CONFIG_TABLAS_A));
    	}
    	catch (Exception e)
    	{
    		//    			e.printStackTrace();
    		log.info ("Prueba de UNICIDAD de Vacunacion incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
    		log.info ("La causa es: " + e.getCause ().toString ());

    		String msg = "Prueba de UNICIDAD de Vacunacion incompleta. No se pudo conectar a la base de datos !!.\n";
    		msg += "Revise el log de vacuandes y el de datanucleus para conocer el detalle de la excepción";
    		System.out.println (msg);
    		fail (msg);
    	}

    	// Ahora si se pueden probar las operaciones
    	try
    	{
    		// Lectura de los tipos de bebida con la tabla vacía
    		List <VOVacunacion> lista = vacuandes.darVOVacunaciones();
    		assertEquals ("No debe haber Vacunaciones creadas!!", 0, lista.size ());

    		// Lectura de los tipos de bebida con un tipo de bebida adicionado
    		int dosisTotales1 = 1000000;
    		VOVacunacion vacunacion1 = vacuandes.adicionarVacunacion(dosisTotales1, 0, 0, 0);
    		lista = vacuandes.darVOVacunaciones();
    		assertEquals ("Debe haber una Vacunacion creada!!", 1, lista.size ());

    		VOVacunacion vacunacion2 = vacuandes.adicionarVacunacion (dosisTotales1, 0, 0, 0);
    		assertNull ("No puede adicionar vacunaciones con las mismas dosisTotales!!", vacunacion2);
    	}
    	catch (Exception e)
    	{
    		//    			e.printStackTrace();
    		String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla Vacunacion.\n";
    		msg += "Revise el log de vacuandes y el de datanucleus para conocer el detalle de la excepción";
    		System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla Vacunacion");
    	}    				
    	finally
    	{
    		vacuandes.limpiarVacuandes ();
    		vacuandes.cerrarUnidadPersistencia ();    		
    	}
    }

    /* ****************************************************************
     * 			Métodos de configuración
     *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
    	try 
    	{
    		Gson gson = new Gson( );
    		FileReader file = new FileReader (archConfig);
    		JsonReader reader = new JsonReader ( file );
    		config = gson.fromJson(reader, JsonObject.class);
    		log.info ("Se encontró un archivo de configuración de tablas válido");
    	} 
    	catch (Exception e)
    	{
    		//    			e.printStackTrace ();
    		log.info ("NO se encontró un archivo de configuración válido");			
    		JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "VacunacionTest", JOptionPane.ERROR_MESSAGE);
    	}	
    	return config;
    }	

}
