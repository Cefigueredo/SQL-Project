package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.VOEtapa;
import uniandes.isis2304.parranderos.negocio.VOVacunacion;
import uniandes.isis2304.parranderos.negocio.Vacuandes;
import uniandes.isis2304.parranderos.negocio.Vacunacion;

@SuppressWarnings("serial")
public class InterfazVacuandesApp extends JFrame implements ActionListener {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazVacuandesApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private Vacuandes vacuandes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;
    
    /**
     * Correo del usuario o documento del ciudadano que hace login
     */
    private String correo;
    
    /**
     * Booleano que indica si se ingresa como ciudadano
     */
    private boolean esCiudadano;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazVacuandesApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        vacuandes = new Vacuandes (tableConfig);
        
        int tipoUsuario=loginUsuario();
        if (guiConfig != null) 	   
        {
     	   if(tipoUsuario==1) {
     		   crearMenu( guiConfig.getAsJsonArray("menuBarCiudadano") );
     	   }else if (tipoUsuario==2){
     		   crearMenu( guiConfig.getAsJsonArray("menuBarAdminPlan") );
     	   }else {
     		   //TODO Menu por default mientras se arregla el login
     		  crearMenu( guiConfig.getAsJsonArray("menuBarAdminPlan") );
     	   }
        }
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Vacuandes App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Vacuandes APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }

    /* ****************************************************************
	 * 			Métodos para que un usuario haga login
	 *****************************************************************/
    public int loginUsuario() {
    	try 
    	{
    		esCiudadano= (JOptionPane.showConfirmDialog(this, "¿Desea ingresar como un ciudadano?")==JOptionPane.YES_OPTION);
    		String usuario="";
    		String contrasena="";
    		if(esCiudadano) {
    			usuario= JOptionPane.showInputDialog (this, "Ingrese su número de documento:", "Login", JOptionPane.QUESTION_MESSAGE);
    		}else if(!esCiudadano) {
    			usuario= JOptionPane.showInputDialog (this, "Ingrese su correo:", "Login", JOptionPane.QUESTION_MESSAGE);
        		contrasena=JOptionPane.showInputDialog (this, "Ingrese su contraseña:", "Login", JOptionPane.QUESTION_MESSAGE);
    		}
    		
    		if (usuario !="")
    		{
    			correo = usuario;
        		int tipousuario=vacuandes.loginUsuario(esCiudadano, usuario, contrasena);
        		if (tipousuario == -1)
        		{
        			JOptionPane.showMessageDialog (this, "El correo o documento ingresado no es valido", "Error", JOptionPane.ERROR_MESSAGE);
        		}
        		return tipousuario;
    		}
    		else
    		{
    			JOptionPane.showMessageDialog (this, "Debe ingresarse un correo o un número de documento", "Error", JOptionPane.ERROR_MESSAGE);
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
//			panelDatos.actualizarInterfaz(resultado);
		}
    	return -1;
    }
    
    
    /* ****************************************************************
	 * 			Métodos para Admin Plan Vacunación
	 *****************************************************************/
    public void registrarCondicionesDePriorizacion()
	{
    	try 
    	{
    		int fase = -1;
    		int edadMinima=-1;
    		int edadMaxima=-1;
    		fase= Integer.parseInt(JOptionPane.showInputDialog (this, "Fase a la que pertenece la Etapa:", "Adicionar fase", JOptionPane.QUESTION_MESSAGE));
    		String descripcion=JOptionPane.showInputDialog (this, "Descripción de la etapa:", "Adicionar fase", JOptionPane.QUESTION_MESSAGE);
    		edadMinima= Integer.parseInt(JOptionPane.showInputDialog (this, "Edad mínima para la etapa:", "Adicionar fase", JOptionPane.QUESTION_MESSAGE));
    		edadMaxima= Integer.parseInt(JOptionPane.showInputDialog (this, "Edad máxima para la etapa:", "Adicionar fase", JOptionPane.QUESTION_MESSAGE));
    		
    		if (fase>=0 && edadMinima>=0 && edadMaxima>=0 && descripcion!=null)
    		{
        		String msg = vacuandes.registrarCondicionesDePriorizacion(correo, fase, descripcion, edadMinima, edadMaxima, 1);
        		if (msg == null)
        		{
        			throw new Exception ("No se pudo crear una etapa con los datos dados");
        		}
    			panelDatos.actualizarInterfaz(msg);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
    
    
	/* ****************************************************************
	 * 			CRUD de TipoBebida
	 *****************************************************************/
    /**
     * Adiciona una vacunacioncon la información dada por el usuario
     * Se crea una nueva tupla de vacunacion en la base de datos
     */
    public void adicionarVacunacion( )
    {
    	try 
    	{
    		String nombre= JOptionPane.showInputDialog (this, "Números de vacunación?", "Adicionar vacunación", JOptionPane.QUESTION_MESSAGE);
    		if (nombre != null)
    		{
        		VOVacunacion vac = vacuandes.adicionarVacunacion(30000000, 400000, 1234, 1);
        		if (vac == null)
        		{
        			throw new Exception ("No se pudo crear un tipo de bebida con nombre: " + nombre);
        		}
        		String resultado = "En adicionarVacunacion\n\n";
        		resultado += "Vacunación adicionada exitosamente: " + vac;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Consulta en la base de datos las vacunaciones existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarVacunacion( )
    {
    	try 
    	{
			List <VOVacunacion> lista = vacuandes.darVOVacunaciones();

			String resultado = "En listarVacunacion";
			resultado +=  "\n" + listarVacunaciones(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Borra de la base de datos la vacunacion con el identificador dado po el usuario
     * Cuando dicho tipo de bebida no existe, se indica que se borraron 0 registros de la base de datos
     */
    public void eliminarVacunacionPorId( )
    {
    	try 
    	{
    		String idVacStr = JOptionPane.showInputDialog (this, "Id de la vacunacion?", "Borrar vacunacion por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idVacStr != null)
    		{
    			long idVac = Long.valueOf (idVacStr);
    			long tbEliminados = vacuandes.eliminarVacunacionPorId(idVac);

    			String resultado = "En eliminar Vacunacion\n\n";
    			resultado += tbEliminados + "Vacunacion eliminados\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /**
     * Busca la vacunacion con el id indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarVacunacionPorID( )
    {
    	try 
    	{
    		String idVacStr = JOptionPane.showInputDialog (this, "Id de la vacunacion?", "Borrar vacunacion por Id", JOptionPane.QUESTION_MESSAGE);
    		if (idVacStr != null)
    		{
    			long idVac = Long.valueOf (idVacStr);
    			Vacunacion vacunacion= vacuandes.darVacunacionPorId(idVac);
    			String resultado = "En buscar Tipo Bebida por nombre\n\n";
    			if (vacunacion != null)
    			{
        			resultado += "El tipo de bebida es: " + vacunacion;
    			}
    			else
    			{
        			resultado += "Una vacunacion con id: " + idVacStr + " NO EXISTE\n";    				
    			}
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }


	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Vacuandes
	 */
	public void mostrarLogVacuandes ()
	{
		mostrarArchivo ("vacuandes.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de vacuandes
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogVacuandes ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("vacuandes.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de vacuandes ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de vacuandes
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = vacuandes.limpiarVacuandes();
			int n = 0; //Poner el número correcto en vez de variable
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [n] + " Vacunaciones eliminados\n";
			resultado += eliminados [n] + " Ciudadanos eliminados\n";
			resultado += eliminados [n] + " Etapas eliminados\n";
			resultado += eliminados [n] + " Estados de salud eliminadas\n";
			resultado += eliminados [n] + " Oficinas EPS regionales eliminados\n";
			resultado += eliminados [n] + " Bebedores eliminados\n";
			resultado += eliminados [n] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-VacuandesJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Vacuandes
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Vacuandes.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Vacuandes
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Vacuandes.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaVacuandes.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Vacuandes
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Vacuandes Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Carlos Figueredo y Juan Rivera\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
    /**
     * Genera una cadena de caracteres con la lista de los vacunaciones recibida: una línea por cada vacunacion
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada vacunacion recibido
     */
    private String listarVacunaciones(List<VOVacunacion> lista) 
    {
    	String resp = "Las vacunaciones existentes son:\n";
    	int i = 1;
        for (VOVacunacion tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y vacuandes.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazVacuandesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazVacuandesApp interfaz = new InterfazVacuandesApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }

}
