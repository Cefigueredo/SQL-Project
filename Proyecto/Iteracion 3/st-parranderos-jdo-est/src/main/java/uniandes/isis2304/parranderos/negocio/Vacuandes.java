package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.persistencia.PersistenciaVacuandes;

public class Vacuandes {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Vacuandes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaVacuandes pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Vacuandes ()
	{
		pp = PersistenciaVacuandes.getInstance ();
	}
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Vacuandes (JsonObject tableConfig)
	{
		pp = PersistenciaVacuandes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Requerimientos funcionales
	 *****************************************************************/
	/**
	 * RNF1 - Privacidad
	 * Adiciona entradas al log de la aplicación
	 * @param 
	 * @return Un booleano indicando si las credenciales son válidas
	 */
	public int loginUsuario(boolean esCiudadano,String correoAdmin, String contrasena) {
		if(esCiudadano) {
			if (pp.darCiudadanoPorCedula(correoAdmin)!=null) {
				return 1;
			}
		}else {
			if(pp.darAdminPlanVacunacionPorCorreo(correoAdmin)!=null) {
				//Metodo para confirmar contraseña
				return 2;
			}else if(pp.darAdminEpsPorCorreo(correoAdmin)!=null) {
			//TODO Metodo para confirmar contraseña
				return 3;
			}else if(pp.darAdminPuntoVacunacionPorCorreo(correoAdmin)!=null) {
				return 4;
			}
		//TODO else if(pp.darOperadorPuntoVacunacionPorCorreo()!=null
		}
		return -1;
	}
	
	/**
	 * RF1 - REGISTRAR LAS CONDICIONES DE PRIORIZACIÓN PARA LA VACUNACIÓN
	 * Adiciona entradas al log de la aplicación
	 * @param 
	 * @return Un String con la información de la Etapa creada
	 */
	public String registrarCondicionesDePriorizacion(String correo, int fase, String descripcion, int edadMinima, int edadMaxima, long idVacunacion)
	{
		log.info("Creando una etapa "+descripcion+", por el Admin del Plan de Vacunación con correo: "+correo);
		
		Etapa e=this.adicionarEtapa(fase, descripcion, edadMinima, edadMaxima, idVacunacion);
		log.info("La etapa fue creada");
		if(e==null) {
			return "No se pudo crear la nueva etapa";
		}
		return "La etapa "+descripcion+" fue creada por el Admin del Plan de Vacunación con correo: "+correo;
	}
	
	
	/**
	 * RF10 - ASIGNAR CIUDADANO A PUNTO DE VACUNACIÓN
	 * Adiciona entradas al log de la aplicación
	 * @param 
	 * @return El objeto Ciudadano modificado. null si ocurre alguna Excepción
	 */
	public String asignarCiudadanoAPuntoDeVacunacion(String correoAdmin, long idPuntoVacunacion, String documento) {
		log.info("Asignando el ciudadano con documento"+documento+", por el Admin de Oficina Eps Regional con correo: "+correoAdmin);
		String mensaje= "";
		AdminEps admin=pp.darAdminEpsPorCorreo(correoAdmin);
		if(admin==null) {
			return "No existe un Administrador de EPS regional con este correo";
		}
		
		PuntoVacunacion punto=pp.darPuntoVacunacionPorId(idPuntoVacunacion);
		if(punto==null) {
			return "No hay puntos de vacunación con id"+admin.getId_Oficina_Eps();
		}
		
		if(punto.getIdEpsRegional()!=admin.getId_Oficina_Eps()) {
			return "No hay puntos de vacunación asociados a la Oficina de EPS regional con id"+admin.getId_Oficina_Eps();
		}
		
		Ciudadano ciudadano=pp.darCiudadanoPorCedula(documento);
		if(ciudadano==null) {
			mensaje="No hay ciudadanos con cédula"+documento;
			return mensaje;
		}
		
		if(ciudadano.getIdOficinaEPS()!=admin.getId_Oficina_Eps()) {
			mensaje="El ciudadano no pertenece a esta oficina de EPS Regional";
			return mensaje;
		}
		if(ciudadano.getIdPuntoVacunacion()!= -1) {
			mensaje="El ciudadano ya tiene un Punto de Vacunación asignado";
			return mensaje;
		}
		
		//Si llega hasta aquí el usuario es un Administrador de EPS regional, la EPS tiene puntos de vacunación,
		//existe un ciudadano con el documento dado y NO tiene un punto de vacunación asignado.
		
		log.info("Datos del ciuadadno y del punto de vacunación son correctos. Iniciando asignación");
		pp.actualizarPuntoVacunacionCiudadano(idPuntoVacunacion, documento);
		log.info ("Asignación completada");
		mensaje="El administrador de EPS con correo "+correoAdmin+" asignó al ciudadano con cédula "+documento+" al punto de vacunación ubicado en "+punto.getLocalizacion();
		
		//Crea un nuevo Estado en el proceso para el ciuadano, luego de asignarle un punto de vacunacion
		Date hoy= new Date(Calendar.getInstance().getTimeInMillis());
		pp.adicionarEstadoProceso("PUNTO_DE_VACUNACION_ASIGNADO", hoy, mensaje,ciudadano.getId());
		
		return mensaje;
	}
	
	/**
	 * RF11 - ASIGNAR CITA DE VACUNACIÓN A UN CIUDADANO
	 * Esta operación se hace siguiendo la priorización definida en el plan y la capacidad de atención del punto
	 * de vacunación. Esta operación es realizada por los Administradores de los Puntos de vacunación 
	 * @
	 * @return 
	 */
	public String asignarCitaDeVacunacionAUnCiudadano(String correoAdmin, int duracionCita, String documento, Timestamp fechaYHora) {

		log.info("Asignando una cita al ciudadano con documento"+documento+", por el Admin del Punto de Vacunacioncon correo: "+correoAdmin);
		String mensaje= "";
		AdminPuntoVacunacion admin=pp.darAdminPuntoVacunacionPorCorreo(correoAdmin);
		if(admin==null) {
			return "No existe un Administrador de Punto de Vacunación con este correo";
		}
		
		Ciudadano ciudadano=pp.darCiudadanoPorCedula(documento);
		if(ciudadano==null) {
			mensaje="No hay ciudadanos con cédula"+documento;
			return mensaje;
		}
		
		if(ciudadano.getIdPuntoVacunacion()!=admin.getId_Punto_Vacunacion()) {
			mensaje="El ciudadano no pertenece a este Punto de Vacunación";
			return mensaje;
		}
		if(ciudadano.getIdPuntoVacunacion()== -1) {
			mensaje="El ciudadano no tiene un Punto de Vacunación asignado";
			return mensaje;
		}
		
		//Se debe comprobar que el ciudadano sea de la etapa actual o posterior
		Vacunacion vac=pp.darVacunacionPorId(admin.getId_Punto_Vacunacion());
		long etapaActual=vac.getIdEtapaActual();
		if(ciudadano.getIdEtapa()>etapaActual) {
			mensaje="El ciudadano aún no puede ser vacunado en esta etapa";
			return mensaje;
		}
		
		//Se debe comprobar que el Estado Actual del ciudadano sea PUNTO DE VACUNACION ASIGNADO o DOSIS PENDIENTE
		if(ciudadano.getEstadoActual()!=4||ciudadano.getEstadoActual()!=6) {
			mensaje="El ciudadano no tiene un Punto de Vacunación Asignado o ya fue vacunado. Si rechazo la cita debe esperar a que se agende otra";
			return mensaje;
		}
		
		//Si llega hasta aquí el usuario es un Administrador de un Punto de Vacunación,
		//el ciudadano exoste y pertenece al punto de vacunación asignado del Administrador
		
		log.info("Datos del ciuadadno y del punto de vacunación son correctos. Iniciando asignación");
		pp.adicionarCita(fechaYHora, duracionCita, "ASIGNADA", ciudadano.getId(), admin.getId_Punto_Vacunacion());
		
		log.info ("Asignación completada");
		mensaje="El administrador de Punto Vacunación con correo "+correoAdmin+" asignó al ciudadano con cédula "+documento+" una cita el día"+fechaYHora.getDay()+"/"+fechaYHora.getMonth()+"/"+fechaYHora.getYear();
		
		//Crea un nuevo Estado en el proceso para el ciuadano, luego de asignarle un punto de vacunacion
		Date hoy= new Date(Calendar.getInstance().getTimeInMillis());
		pp.adicionarEstadoProceso("CITA_AGENDADA", hoy, mensaje,ciudadano.getId());
		
		return mensaje;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los VACUNACION
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un vacunacion 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del vacunacion
	 * @return El objeto Vacunacion adicionado. null si ocurre alguna Excepción
	 */
	public Vacunacion adicionarVacunacion (int dosisTotales, int dosisAplicadas, int dosisPerdidas, long idEtapaActual)
	{
        log.info ("Adicionando Vacunacion: " + dosisTotales);
        Vacunacion vacunacion = pp.adicionarVacunacion(dosisTotales, dosisAplicadas, dosisPerdidas, idEtapaActual);		
        log.info ("Adicionando Vacunacion: " + vacunacion);
        return vacunacion;
	}
	
	/**
	 * Elimina un vacunacion por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idVacunacion - El id del vacunacion a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVacunacionPorId(long idVacunacion)
	{
		log.info ("Eliminando Vacunacion por id: " + idVacunacion);
        long resp = pp.eliminarVacunacionPorId(idVacunacion);		
        log.info ("Eliminando Vacunacion por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los tipos de ciudadano en Vacuandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Vacunacion con todos los tipos de ciudadano que conoce la aplicación, llenos con su información básica
	 */
	public List<Vacunacion> darVacunaciones ()
	{
		log.info ("Consultando Vacunacion");
        List<Vacunacion> vacunacion = pp.darVacunaciones ();	
        log.info ("Consultando Vacunacion: " + vacunacion.size() + " existentes");
        return vacunacion;
	}
	
	/**
	 * Encuentra un Vacunacion y su información básica, según su identificador
	 * @param idBebedor - El identificador del Vacunacion buscado
	 * @return Un objeto Vacunacion que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un Vacunacion con dicho identificador no existe
	 */
	public Vacunacion darVacunacionPorId (long idVacunacion)
	{
        log.info ("Dar información de una Vacunacion por id: " + idVacunacion);
        Vacunacion vacunacion = pp.darVacunacionPorId (idVacunacion);
        log.info ("Buscando vacunacion por Id: " + vacunacion != null ? vacunacion : "NO EXISTE");
        return vacunacion;
	}

	/**
	 * Encuentra todos los tipos de ciudadano en Vacuandes y los devuelve como una lista de VOVacunacion
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOVacunacion con todos los tipos de ciudadano que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVacunacion> darVOVacunaciones ()
	{
		int cont = 0;
		log.info ("Generando los VO de Vacunacion");        
        List<VOVacunacion> voTipos = new LinkedList<VOVacunacion> ();
        for (Vacunacion tb : pp.darVacunaciones ())
        {
        	voTipos.add (tb);
        	cont++;
        	if(cont>10) {break;}
        }
        log.info ("Generando los VO de Vacunacion: " + voTipos.size() + " existentes");
        return voTipos;
	}


	/* ****************************************************************
	 * 			Métodos para manejar las CIUDADANOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una ciudadano 
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Ciudadano adicionado. null si ocurre alguna Excepción
	 */
	public Ciudadano adicionarCiudadano (String nombre, String documento, int estadoActual, Date fechaNacimiento, String direccion, String ciudad, String numeroContacto, long idVacunacion, long idOficinaEPSRegional, long idPuntoVacunacion, long idProfesion, long idEstadoSalud, long idEtapa)
	{
		log.info ("Adicionando ciudadano " + nombre);
		Ciudadano ciudadano = pp.adicionarCiudadano (nombre, documento, estadoActual, fechaNacimiento, direccion, ciudad, numeroContacto, idVacunacion, idOficinaEPSRegional, idPuntoVacunacion, idProfesion, idEstadoSalud, idEtapa);
        log.info ("Adicionando ciudadano: " + ciudadano);
        return ciudadano;
	}
	
	/**
	 * Elimina una ciudadano por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idCiudadano - El identificador de la ciudadano a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarCiudadanoPorId(long idCiudadano)
	{
        log.info ("Eliminando ciudadano por id: " + idCiudadano);
        long resp = pp.eliminarCiudadanoPorId(idCiudadano);
        log.info ("Eliminando ciudadano por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todas las ciudadano en Vacuandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Ciudadano con todos las ciudadanos que conoce la aplicación, llenos con su información básica
	 */
	public List<Ciudadano> darCiudadanos ()
	{
        log.info ("Consultando Ciudadanos");
        List<Ciudadano> ciudadanos = pp.darCiudadanos ();	
        log.info ("Consultando Ciudadanos: " + ciudadanos.size() + " ciudadanos existentes");
        return ciudadanos;
	}

	/**
	 * Encuentra un Ciudadano y su información básica, según su identificador
	 * @param idCiudadano- El identificador del Ciudadano buscado
	 * @return Un objeto Ciudadano que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un Ciudadano con dicho identificador no existe
	 */
	public Ciudadano darCiudadanoPorId (long idCiudadano)
	{
        log.info ("Dar información de una ciudadano por id: " + idCiudadano);
        Ciudadano ciudadano = pp.darCiudadanoPorId (idCiudadano);
        log.info ("Buscando ciudadano por Id: " + ciudadano != null ? ciudadano : "NO EXISTE");
        return ciudadano;
	}
	
	/**
	 * Encuentra un Ciudadano y su información básica, según su cedula
	 * @param cedula- El cedula del Ciudadano buscado
	 * @return Un objeto Ciudadano que corresponde con el cedula buscado y lleno con su información básica
	 * 			null, si un Ciudadano con dicho cedula no existe
	 */
	public Ciudadano darCiudadanoPorCedula (String cedula)
	{
        log.info ("Dar información de una ciudadano por cedula: " + cedula);
        Ciudadano ciudadano = pp.darCiudadanoPorCedula(cedula);
        log.info ("Buscando ciudadano por cedula: " + ciudadano != null ? ciudadano : "NO EXISTE");
        return ciudadano;
	}

	/**
	 * Encuentra todos los tipos de ciudadano en Vacuandes y los devuelve como una lista de VOVacunacion
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOCiudadano con todos las ciudadanos que conoce la aplicación, llenos con su información básica
	 */
	public List<VOCiudadano> darVOCiudadanos ()
	{
		log.info ("Generando los VO de las ciudadanos");       
        List<VOCiudadano> voCiudadanos = new LinkedList<VOCiudadano> ();
        for (Ciudadano beb : pp.darCiudadanos ())
        {
        	voCiudadanos.add (beb);
        }
        log.info ("Generando los VO de los ciudadanos: " + voCiudadanos.size() + " existentes");
        return voCiudadanos;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los ETAPAS
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un etapa 
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto ETAPA adicionado. null si ocurre alguna Excepción
	 */
	public Etapa adicionarEtapa (int fase, String descripcion, int edadMinima, int edadMaxima, long idVacunacion)
	{
		log.info ("Adicionando etapa: " + descripcion);
        Etapa etapa = pp.adicionarEtapa (fase, descripcion, edadMinima, edadMaxima, idVacunacion);
        log.info ("Adicionando etapa: " + etapa);
        return etapa;
	}

	/**
	 * Elimina un etapa por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idEtapa - El identificador del etapa a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEtapaPorId(long idEtapa)
	{
        log.info ("Eliminando etapa por id: " + idEtapa);
        long resp = pp.eliminarEtapaPorId(idEtapa);
        log.info ("Eliminando etapa por Id: " + resp + " tuplas eliminadas");
        return resp;
	}


	/**
	 * Encuentra todos los etapas en Vacuandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Etapa con todos las etapas que conoce la aplicación, llenos con su información básica
	 */
	public List<Etapa> darEtapas()
	{
        log.info ("Dar información de etapas");
        List<Etapa> etapas = pp.darEtapas();
        log.info ("Dar información de Etapas: " + etapas.size() + " etapas existentes");
        return etapas;
 	}
	
	/**
	 * Encuentra un etapa y su información básica, según su identificador
	 * @param idEtapa- El identificador del etapa buscado
	 * @return Un objeto etapa que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un etapa con dicho identificador no existe
	 */
	public Etapa darEtapaPorId (long idEtapa)
	{
        log.info ("Dar información de una etapa por id: " + idEtapa);
        Etapa etapa = pp.darEtapaPorId (idEtapa);
        log.info ("Buscando etapa por Id: " + etapa != null ? etapa : "NO EXISTE");
        return etapa;
	}

	/**
	 * Encuentra la información básica de los etapas, según su nombre y los devuelve como VO
	 * @param nombre - El nombre de etapa a buscar
	 * @return Una lista de Etapas con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen etapas con ese nombre
	 */
	public List<VOEtapa> darVOEtapas()
	{
        log.info ("Generando VO de etapas");
        List<VOEtapa> voEtapas = new LinkedList<VOEtapa> ();
       for (Etapa bdor : pp.darEtapas())
       {
          	voEtapas.add (bdor);
       }
       log.info ("Generando los VO de Etapas: " + voEtapas.size() + " etapas existentes");
      return voEtapas;
 	}

	/* ****************************************************************
	 * 			Métodos para manejar los PROFESIONES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un profesion 
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto Profesion adicionado. null si ocurre alguna Excepción
	 */
	public Profesion adicionarProfesion (String descripcion, long idEtapa)
	{
        log.info ("Adicionando profesion: " + descripcion);
        Profesion profesion = pp.adicionarProfesion (descripcion, idEtapa);
        log.info ("Adicionando profesion: " + profesion);
        return profesion;
	}
	
	/**
	 * Elimina un etapa por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idProfesion - El identificador del profesion a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProfesionPorId(long idProfesion)
	{
        log.info ("Eliminando profesion por id: " + idProfesion);
        long resp = pp.eliminarProfesionPorId(idProfesion);
        log.info ("Eliminando profesion: " + resp);
        return resp;
	}
	
	/**
	 * Encuentra todos los profesiones en Vacuandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Profesion con todos las profesiones que conoce la aplicación, llenos con su información básica
	 */
	public List<Profesion> darProfesiones()
	{
        log.info ("Listando Profesiones");
        List<Profesion> profesiones = pp.darProfesiones();	
        log.info ("Listando Profesiones: " + profesiones.size() + " profesiones existentes");
        return profesiones;
	}
	/**
	 * Encuentra un profesion y su información básica, según su identificador
	 * @param idProfesion- El identificador del profesion buscado
	 * @return Un objeto profesion que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un profesion con dicho identificador no existe
	 */
	public Profesion darProfesionPorId (long idProfesion)
	{
        log.info ("Dar información de una etapa por id: " + idProfesion);
        Profesion profesion = pp.darProfesionPorId (idProfesion);
        log.info ("Buscando profesion por Id: " + profesion != null ? profesion : "NO EXISTE");
        return profesion;
	}

	/**
	 * Encuentra todos los profesiones en Vacuandes y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Profesion con todos las profesiones que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProfesion> darVOProfesion()
	{
		log.info ("Generando los VO de Profesiones");
		List<VOProfesion> voProfesiones = new LinkedList<VOProfesion> ();
		for (Profesion profesion: pp.darProfesiones())
		{
			voProfesiones.add (profesion);
		}
		log.info ("Generando los VO de Profesiones: " + voProfesiones.size () + " profesiones existentes");
		return voProfesiones;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ESTADOS DE SALUD
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un estado de salud 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del estado de salud
	 * @return El objeto EstadoDeSalud adicionado. null si ocurre alguna Excepción
	 */
	public EstadoDeSalud adicionarEstadoDeSalud (String descripcion, long idEtapa)
	{
        log.info ("Adicionando Tipo de bebida: " + descripcion);
        EstadoDeSalud estadoDeSalud = pp.adicionarEstadoDeSalud (descripcion, idEtapa);		
        log.info ("Adicionando Tipo de bebida: " + estadoDeSalud);
        return estadoDeSalud;
	}
	
	/**
	 * Elimina un estado de salud por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idEstadoDeSalud - El id del estado de salud a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstadoDeSaludPorId(long idEstadoDeSalud)
	{
		log.info ("Eliminando Estado de salud por id: " + idEstadoDeSalud);
        long resp = pp.eliminarEstadoDeSaludPorId(idEstadoDeSalud);		
        log.info ("Eliminando Estado de salud por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los tipos de bebida en Vacuandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos EstadoDeSalud con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<EstadoDeSalud> darEstadosDeSalud()
	{
		log.info ("Consultando Estados de salud");
        List<EstadoDeSalud> estadoDeSalud = pp.darEstadosDeSalud();	
        log.info ("Consultando Estados de salud: " + estadoDeSalud.size() + " existentes");
        return estadoDeSalud;
	}

	/**
	 * Encuentra todos los tipos de bebida en Vacuandes y los devuelve como una lista de VOEstadoDeSalud
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOEstadoDeSalud con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<VOEstadoDeSalud> darVOEstadoDeSalud ()
	{
		log.info ("Generando los VO de Estados de salud");        
        List<VOEstadoDeSalud> voTipos = new LinkedList<VOEstadoDeSalud> ();
        for (EstadoDeSalud tb : pp.darEstadosDeSalud())
        {
        	voTipos.add (tb);
        }
        log.info ("Generando los VO de Estados de salud: " + voTipos.size() + " existentes");
        return voTipos;
	}
	
	/**
	 * Encuentra un EstadoDeSalud y su información básica, según su identificador
	 * @param idEstadoDeSalud- El identificador del EstadoDeSalud buscado
	 * @return Un objeto EstadoDeSalud que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un EstadoDeSalud con dicho identificador no existe
	 */
	public EstadoDeSalud darEstadoDeSaludPorId (long idEstadoDeSalud)
	{
        log.info ("Dar información de una etapa por id: " + idEstadoDeSalud);
        EstadoDeSalud estadoDeSalud = pp.darEstadoDeSaludPorId (idEstadoDeSalud);
        log.info ("Buscando profesion por Id: " + estadoDeSalud != null ? estadoDeSalud : "NO EXISTE");
        return estadoDeSalud;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los OFICINAS EPS REGIONALES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un oficina eps regional 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del oficina eps regional
	 * @return El objeto OficinaEPSRegional adicionado. null si ocurre alguna Excepción
	 */
	public OficinaEPSRegional adicionarOficinaEPSRegional (String nombre, String NIT, String region, String direccion, String numeroContacto, String ciudad, long idVacunacion)
	{
        log.info ("Adicionando Tipo de bebida: " + nombre);
        OficinaEPSRegional oficinaEPSRegional = pp.adicionarOficinaEPSRegional (nombre, NIT, region, direccion, numeroContacto, ciudad, idVacunacion);		
        log.info ("Adicionando Tipo de bebida: " + oficinaEPSRegional);
        return oficinaEPSRegional;
	}
	
	/**
	 * Elimina un oficina eps regional por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idOficinaEPSRegional - El id del oficina eps regional a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarOficinaEPSRegionalPorId(long idOficinaEPSRegional)
	{
		log.info ("Eliminando Oficina EPS Regional por id: " + idOficinaEPSRegional);
        long resp = pp.eliminarOficinaEPSRegionalPorId(idOficinaEPSRegional);		
        log.info ("Eliminando Oficina EPS Regional por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los tipos de bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos OficinaEPSRegional con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<OficinaEPSRegional> darOficinasEPSRegional ()
	{
		log.info ("Consultando Tipos de bebida");
        List<OficinaEPSRegional> OficinaEPSRegional = pp.darOficinasEPSRegional ();	
        log.info ("Consultando Tipos de bebida: " + OficinaEPSRegional.size() + " existentes");
        return OficinaEPSRegional;
	}
	
	/**
	 * Encuentra un OficinaEPSRegional y su información básica, según su identificador
	 * @param idOficinaEPSRegional- El identificador del OficinaEPSRegional buscado
	 * @return Un objeto OficinaEPSRegional que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un OficinaEPSRegional con dicho identificador no existe
	 */
	public OficinaEPSRegional darOficinaEPSRegionalPorId (long idOficinaEPSRegional)
	{
        log.info ("Dar información de una etapa por id: " + idOficinaEPSRegional);
        OficinaEPSRegional oficinaEPSRegional= pp.darOficinaEPSRegionalPorId (idOficinaEPSRegional);
        log.info ("Buscando profesion por Id: " + oficinaEPSRegional != null ? oficinaEPSRegional: "NO EXISTE");
        return oficinaEPSRegional;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOOficinaEPSRegional
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOOficinaEPSRegional con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<VOOficinaEPSRegional> darVOOficinaEPSRegional ()
	{
		log.info ("Generando los VO de Oficina EPS Regional ");        
        List<VOOficinaEPSRegional> voTipos = new LinkedList<VOOficinaEPSRegional> ();
        for (OficinaEPSRegional tb : pp.darOficinasEPSRegional ())
        {
        	voTipos.add (tb);
        }
        log.info ("Generando los VO de Oficina EPS Regional : " + voTipos.size() + " existentes");
        return voTipos;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PUNTO_VACUNACION
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Punto de Vacunacion
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
		log.info ("Adicionando punto vacunacion: " + localizacion);
        PuntoVacunacion punto = pp.adicionarPuntoVacunacion(localizacion, cupoMaximo, maxCiudadanosPorDia, totalVacunados, infraestructura, capacidadAlmacenamiento, idEps);
        log.info ("Adicionando punto vacunacion: " + punto);
        return punto;
	}
	
	/**
	 * Elimina un punto de vacunación por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idPtoVacunacion - El identificador del punto de vacunacion a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPuntoVacunacionPorId (long idPtoVacunacion)
	{
        log.info ("Eliminando punto vacunacion por id: " + idPtoVacunacion);
        long resp = pp.eliminarPuntoVacunacionPorId(idPtoVacunacion);
        log.info ("Eliminando punto vacunacion: " + resp);
        return resp;
	}
	
	/**
	 * Elimina un punto de vacunación por el id de su EPS
	 * Adiciona entradas al log de la aplicación
	 * @param idEps - El identificador de la EPS cuyos puntos de vacunacion se van a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPuntoVacunacionPorIdEps (long idEps)
	{
        log.info ("Eliminando punto vacunacion con idEps: " + idEps);
        long resp = pp.eliminarPuntoVacunacionPorId(idEps);
        log.info ("Eliminando punto vacunacion: " + resp);
        return resp;
	}
	
	/**
	 * Encuentra todos los puntos de vacunacion en VacuAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos PuntoVacunacion que conoce la aplicación, llenos con su información básica
	 */
	public List<PuntoVacunacion> darPuntosVacunacion()
	{
		log.info ("Consultando Puntos de Vacunación");
        List<PuntoVacunacion> puntos= pp.darPuntosVacunacion();
        log.info ("Consultando puntos de vacunacion: " + puntos.size() + " existentes");
        return puntos;
	}

	/**
	 * Encuentra todos los puntos de vacunacion en VacuAndes y los devuelve como una lista de VOPuntoVacunacion
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOPuntoVacunacion con todos los puntos de vacunación que conoce la aplicación, llenos con su información básica
	 */
	public List<VOPuntoVacunacion> darVOPuntosVacunacion ()
	{
		log.info ("Generando los VO de Puntos de Vacunacion");        
        List<VOPuntoVacunacion> voPuntos= new LinkedList<VOPuntoVacunacion> ();
        for (PuntoVacunacion pv : pp.darPuntosVacunacion())
        {
        	voPuntos.add (pv);
        }
        log.info ("Generando los VO de Puntos de Vacunacion: " + voPuntos.size() + " existentes");
        return voPuntos;
	}
	
	/**
	 * Encuentra todos los puntos de vacunación en VacuAndes que tienen el id dado
	 * Adiciona entradas al log de la aplicación
	 * @param idPtoVacunacion - El identificador del punto de vacunacion a buscar
	 * @return Un objeto PuntoVacunacion  
	 */
	public PuntoVacunacion darPuntoVacunacionPorId (long idPtoVacunacion) {
		log.info ("Buscando Punto de Vacunacion por id: " + idPtoVacunacion);
		PuntoVacunacion pv = pp.darPuntoVacunacionPorId(idPtoVacunacion);
        log.info ("Buscando Punto de Vacunacion por Id: " + pv != null ? pv : "NO EXISTE");
		return pv;
	}
	
	/**
	 * Encuentra la información básica de los puntos de vacunacion, según su id EPS regional
	 * @param idEPS - Id dela oficina de la EPS regional
	 * @return Una lista de Puntos de Vacunación con su información básica, donde todos tienen el id EPS buscado.
	 * 	La lista vacía indica que no existen Puntos de Vacunación para esa Oficina de EPS Regional
	 */
	public List<PuntoVacunacion> darPuntoVacunacionPorIdEps (long idEps)
	{
        log.info ("Dar información de puntos de vacunación por id EPS:" + idEps);
        List<PuntoVacunacion> puntos= pp.darPuntoVacunacionPorIdEps(idEps);
        log.info ("Dar información de Puntos de Vacunación por id EPS: " + puntos.size() + " Puntos de Vacunación con ese nombre existentes");
        return puntos;
 	}
	
	/**
	 * Aumenta en 1 el número de personas vacunadas por un Punto de Vacunación
	 * Adiciona entradas al log de la aplicación
	 * @param idPtoVacunacion - El id del Punto de Vacunación a añadir
	 * @return El número de tuplas actualizadas
	 */
	public long aumentarTotalVacunados (long idPtoVacunacion)
	{
        log.info ("Aumentando el número total de vacunados de un punto de vacunación con id: " + idPtoVacunacion);
        long resp = pp.aumentarTotalVacunados(idPtoVacunacion);
        log.info ("Aumentando el número total de vacunados de un punto de vacunación : " + resp + " tuplas actualizadas");
        return resp;
	}
	
	/**
	 * Elimina un vencimiento dosis por su lote
	 * Adiciona entradas al log de la aplicación
	 * @param lote - El lote del vencimiento dosis a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVencimientoDosisPorLote (String lote)
	{
		log.info ("Eliminando punto vacunacion con el lote: " + lote);
        long resp = pp.eliminarVencimientoDosisPorLote(lote);
        log.info ("Eliminando vencimiento dosis: " + resp);
        return resp;
	}
	/* ****************************************************************
	 * 			Métodos para manejar los VENCIMIENTO_DOSIS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Vencimiento Dosis
	 * Adiciona entradas al log de la aplicación
	 * @param lote, El lote de la dosis a adicionar
	 * @param fechaVencimiento, La fecha de vencimiento de la dosis
	 * @return El objeto VencimientoDosis adicionado. null si ocurre alguna Excepción
	 */
	public VencimientoDosis adicionarVencimientoDosis (String lote, Date fVenc)
	{
		log.info ("Adicionando Vencimiento Dosis: " + lote);
        VencimientoDosis vencimiento = pp.adicionarVencimientoDosis(lote, fVenc);
        log.info ("Adicionando vencimiento dosis: " + vencimiento);
        return vencimiento;
	}
	
	/**
	 * Encuentra todos los vencimiento dosis en VacuAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VencimientoDosis que conoce la aplicación, llenos con su información básica
	 */
	public List<VencimientoDosis> darVencimientosDosis()
	{
		log.info ("Consultando Vencimiento Dosis");
        List<VencimientoDosis> venc= pp.darVencimientosDosis();
        log.info ("Consultando vencimientos dosis: " + venc.size() + " existentes");
        return venc;
	}
	
	/**
	 * Encuentra todos los vencimiento dosis en VacuAndes y los devuelve como una lista de VOVencimientoDosis
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOVencimientoDosis con todos los puntos de vacunación que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVencimientoDosis> darVOVencimientoDosis ()
	{
		log.info ("Generando los VO de Vencimiento Dosis");        
        List<VOVencimientoDosis> voVenc= new LinkedList<VOVencimientoDosis> ();
        for (VencimientoDosis vd : pp.darVencimientosDosis())
        {
        	voVenc.add (vd);
        }
        log.info ("Generando los VO de Vencimiento Dosis: " + voVenc.size() + " existentes");
        return voVenc;       
	}
	
	/**
	 * Encuentra todos los vencimiento dosis en VacuAndes que tienen el lote dado
	 * Adiciona entradas al log de la aplicación
	 * @param lote - El lote del vencimiento dosis a buscar
	 * @return Un objeto VencimientoDosis  
	 */
	public VencimientoDosis darVencimientoDosisporLote(String lote)
	{
		log.info ("Dar información de vencimiento dosis por Lote:" + lote);
        VencimientoDosis venc= pp.darVencimientoDosisporLote(lote);
        log.info ("Dar información de Puntos de Vacunación por lote " + lote + ". Elemento encontrado.");
        return venc;
	}
	
	/**
	 * Modifica la fecha de vencimiento de unas dosis con un lote dado
	 * Adiciona entradas al log de la aplicación
	 * @param lote - El lote de las dosis a modificar
	 * @return El número de tuplas actualizadas
	 */
	public long modificarVencimientoDosis (String lote, Date nFecha)
	{
		log.info ("Cambiando la fecha de vencimiento de un vencimiento dosis con lote: " + lote);
        long resp = pp.modificarVencimientoDosis(lote, nFecha);
        log.info ("Modificando la fecha de vencimiento de las dosis con el lote dado: " + resp + " tuplas actualizadas");
        return resp;	
	}
	
	/**
	 * Elimina la fecha de vencimiento de una dosis con lote dado
	 * Adiciona entradas al log de la aplicación
	 * @param lote - El lote de las dosis a modificar
	 * @return El número de tuplas actualizadas
	 */
	public long eliminarVencimientoDosisPorId (String lote)
	{
		log.info ("Eliminando la fecha de vencimiento de un vencimiento dosis con lote: " + lote);
        long resp = pp.eliminarVencimientoDosisPorLote(lote);
        log.info ("Eliminando tuplas de vencimiento dosis: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la VACUNA
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una Vacuna
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - nombre de la vacuna
	 * @param requerimientos - requerimientos de almacenamiento de la vacuna
	 * @return El objeto Vacuna adicionado. null si ocurre alguna Excepción
	 */
	public Vacuna adicionarVacuna (String nombre, String requerimientos)
	{
		log.info ("Adicionando Vacuna: " + nombre);
        Vacuna vacuna= pp.adicionarVacuna(nombre,requerimientos);
        log.info ("Adicionando vacuna: " + vacuna.toString());
        return vacuna;
	}
	
	/**
	 * Encuentra todas las vacunas en VacuAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Vacuna que conoce la aplicación, llenos con su información básica
	 */
	public List<Vacuna> darVacuna()
	{
		log.info ("Consultando vacunas");
        List<Vacuna> venc= pp.darVacunas();
        log.info ("Consultando vacunas: " + venc.size() + " existentes");
        return venc;
	}
	
	/**
	 * Encuentra todas las vacunas en VacuAndes y los devuelve como una lista de VOVacuna
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOVacuna con todos los puntos de vacunación que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVacuna> darVOVacunas()
	{
		log.info ("Generando los VO de Vacunas");        
        List<VOVacuna> voVenc= new LinkedList<VOVacuna> ();
        for (Vacuna vd : pp.darVacunas())
        {
        	voVenc.add (vd);
        }
        log.info ("Generando los VO de Vencimiento Dosis: " + voVenc.size() + " existentes");
        return voVenc;
	}
	
	/**
	 * Encuentra todos los objetos vacuna en VacuAndes que tienen el lote dado
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id de la vacuna
	 * @return Un objeto Vacuna
	 */
	public Vacuna darVacunaPorId(long id)
	{
		log.info ("Dar información de vacuna por id:" + id);
        Vacuna venc= pp.darVacunaPorId(id);
        log.info ("Dar información de vacunas por id" + id + ". Elemento encontrado.");
        return venc;
	}
	
	/**
	 * Elimina la vacuna con id dado
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id de la vacuna a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVacunasPorId (long id)
	{
		log.info ("Eliminando la vacuna con id: " + id);
        long resp = pp.eliminarVacunaPorId(id);
        log.info ("Eliminando tuplas de vacuna: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la ADMIN_PLAN_VACUNACION
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Admin Plan Vacunacion
	 * Adiciona entradas al log de la aplicación
	 * @param idPlan - id del plan de vacunación
	 * @param correo - correo del funcionario
	 * @param idPlanAdmin - id del Plan de Vacunación que administra
	 * @return El objeto AdminPlanVacunacion adicionado. null si ocurre alguna Excepción
	 */
	public AdminPlanVacunacion adicionarAdminPlanVacunacion (long idPlan, String correo, long idPlanAdmin)
	{
		log.info ("Adicionando AdminPlanVacunacion: " + correo);
        AdminPlanVacunacion admin = pp.adicionarAdminPlanVacunacion(idPlan, correo, idPlanAdmin);
        log.info ("Adicionando AdminPlanVacunacion: " + admin.toString());
        return admin;
	}
	
	/**
	 * Encuentra todos los objetos AdminPlanVacunacion en VacuAndes que tienen el correo dado
	 * Adiciona entradas al log de la aplicación
	 * @param correo - El correo de la Vacuna
	 * @return Un objeto Vacuna
	 */
	public AdminPlanVacunacion darAdminPlanVacunacionPorCorreo (String correo) 
	{
		log.info ("Dar información de un AdminPlanVacunacion por correo:" + correo);
        AdminPlanVacunacion admin= pp.darAdminPlanVacunacionPorCorreo(correo);
        log.info ("Dar información de AdminPlanVacunacion con correo" +  correo + ". Elemento encontrado.");
        return admin;
	}
	
	/**
	 * Elimina la vacuna con id dado
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id de la vacuna a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminPlanVacunacionPorCorreo(String correo)
	{
		log.info ("Eliminando al AdminPlanVacunacion con correo: " + correo);
        long resp = pp.eliminarAdminPlanVacunacionPorCorreo(correo);
        log.info ("Eliminando tuplas de AdminPlanVacunacion: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la ADMIN_EPS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Admin Eps
	 * Adiciona entradas al log de la aplicación
	 * @param idPlan - id del plan de vacunación
	 * @param correo - correo del funcionario
	 * @param idEps - id de la oficina de EPS regional que administra
	 * @return El objeto AdminEps adicionado. null si ocurre alguna Excepción
	 */
	public AdminEps adicionarAdminEps (long idPlan, String correo, long idEps)
	{
		log.info ("Adicionando AdminEps: " + correo);
        AdminEps admin = pp.adicionarAdminEps(idPlan, correo, idEps);
        log.info ("Adicionando AdminEps: " + admin.toString());
        return admin;
	}
	
	/**
	 * Encuentra todos los objetos AdminPlanVacunacion en VacuAndes que tienen el correo dado
	 * Adiciona entradas al log de la aplicación
	 * @param correo - El correo de la Vacuna
	 * @return Un objeto Vacuna
	 */
	public AdminEps darAdminEpsPorCorreo (String correo) 
	{
		log.info ("Dar información de un AdminEps por correo:" + correo);
        AdminEps admin= pp.darAdminEpsPorCorreo(correo);
        log.info ("Dar información de AdminEps con correo" +  correo + ". Elemento encontrado.");
        return admin;
	}
	
	/**
	 * Elimina el AdminEps con correo dado
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id de la vacuna a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminEpsPorCorreo(String correo)
	{
		log.info ("Eliminando el AdminEps con correo: " + correo);
        long resp = pp.eliminarAdminPlanVacunacionPorCorreo(correo);
        log.info ("Eliminando tuplas de AdminEps: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la ADMIN_PUNTO_VACUNACION
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Admin Eps
	 * Adiciona entradas al log de la aplicación
	 * @param idPlan - id del plan de vacunación
	 * @param correo - correo del funcionario
	 * @param idPunto - id de la oficina de EPS regional que administra
	 * @return El objeto AdminEps adicionado. null si ocurre alguna Excepción
	 */
	public AdminPuntoVacunacion adicionarAdminPuntoVacunacion (long idPlan, String correo, long idPunto)
	{
		log.info ("Adicionando AdminPuntoVacunacion: " + correo);
        AdminPuntoVacunacion admin = pp.adicionarAdminPuntoVacunacion(idPlan, correo, idPunto);
        log.info ("Adicionando AdminEps: " + admin.toString());
        return admin;
	}
	
	/**
	 * Encuentra todos los objetos AdminPlanVacunacion en VacuAndes que tienen el correo dado
	 * Adiciona entradas al log de la aplicación
	 * @param correo - El correo de la Vacuna
	 * @return Un objeto Vacuna
	 */
	public AdminPuntoVacunacion darAdminPuntoVacunacionPorCorreo (String correo) 
	{
		log.info ("Dar información de un AdminPuntoVacunacion por correo:" + correo);
        AdminPuntoVacunacion admin= pp.darAdminPuntoVacunacionPorCorreo(correo);
        log.info ("Dar información de AdminPuntoVacunacion con correo" +  correo + ". Elemento encontrado.");
        return admin;
	}
	
	/**
	 * Elimina el AdminEps con correo dado
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id de la vacuna a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAdminPuntoVacunacionPorCorreo(String correo)
	{
		log.info ("Eliminando el AdminPuntoVacunacion con correo: " + correo);
        long resp = pp.eliminarAdminPuntoVacunacionPorCorreo(correo);
        log.info ("Eliminando tuplas de AdminPuntoVacunacion: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los CITAS
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un etapa 
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto CITA adicionado. null si ocurre alguna Excepción
	 */
	public Cita adicionarCita (long idCita, Timestamp fechaYHora, int duracionCita, String estadoCita,long idCiudadano, long idPuntoVacunacion)
	{
        log.info ("Adicionando cita: " + fechaYHora);
        Cita cita = pp.adicionarCita (fechaYHora, duracionCita, estadoCita, idCiudadano, idPuntoVacunacion);
        log.info ("Adicionando etapa: " + cita);
        return cita;
	}

	/**
	 * Elimina un Cita por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idCita - El identificador del Cita a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarCitaPorId(long idCita)
	{
        log.info ("Eliminando Cita por id: " + idCita);
        long resp = pp.eliminarCitaPorId(idCita);
        log.info ("Eliminando Cit por Id: " + resp + " tuplas eliminadas");
        return resp;
	}


	/**
	 * Encuentra todos los Citas en Vacuandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Cita con todos las Citas que conoce la aplicación, llenos con su información básica
	 */
	public List<Cita> darCitas()
	{
        log.info ("Dar información de Citas");
        List<Cita> citas= pp.darCitas();
        log.info ("Dar información de Citas: " + citas.size() + " Citas existentes");
        return citas;
 	}
	
	/**
	 * Encuentra un Cita y su información básica, según su identificador
	 * @param idCita - El identificador del Cita buscado
	 * @return Un objeto Cita que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un Cita con dicho identificador no existe
	 */
	public Cita darCitaPorId (long idCita)
	{
        log.info ("Dar información de una etapa por id: " + idCita);
        Cita cita= pp.darCitaPorId (idCita);
        log.info ("Buscando Cita por Id: " + cita!= null ? cita: "NO EXISTE");
        return cita;
	}

	/**
	 * Encuentra la información básica de los Citas y los devuelve como VO
	 * @return Una lista de Citas con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen etapas con ese nombre
	 */
	public List<VOCita> darVOCitas()
	{
        log.info ("Generando VO de Citas");
        List<VOCita> voCitas = new LinkedList<VOCita> ();
       for (Cita cita: pp.darCitas())
       {
          	voCitas.add (cita);
       }
       log.info ("Generando los VO de Citas: " + voCitas.size() + " Citas existentes");
      return voCitas;
 	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ESTADO_PROCESO
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un Estado Proceso
	 * Adiciona entradas al log de la aplicación
	 * @return El objeto AdminEps adicionado. null si ocurre alguna Excepción
	 */
	public EstadoProceso adicionarEstadoProceso (String estado, Date fecha_Cambio,String comentario_descripcion, long idCiudadano)
	{
		log.info ("Adicionando EstadoProceso: " + estado);
        EstadoProceso result = pp.adicionarEstadoProceso(estado, fecha_Cambio, comentario_descripcion, idCiudadano);
        log.info ("Adicionando AdminEps: " + result.toString());
        return result;
	}
	
	/**
	 * Elimina el EstadoProcesp con un id dado
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id del estado proceso
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstadoProcesoPorId(long id)
	{
		log.info ("Eliminando el EstadoProceso con id: " + id);
        long resp = pp.eliminarEstadoProcesoPorId(id);
        log.info ("Eliminando tuplas de EstadoProceso: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina el EstadoProcesp con un id de ciudadano dado
	 * Adiciona entradas al log de la aplicación
	 * @param id - El id del ciuadano
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstadoProcesoPorIdCiudadano(long id)
	{
		log.info ("Eliminando el EstadoProceso con id: " + id);
        long resp = pp.eliminarEstadoProcesoPorIdCiudadano(id);
        log.info ("Eliminando tuplas de EstadoProceso: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los objetos EstadoProceso en VacuAndes que tienen el correo dado
	 * Adiciona entradas al log de la aplicación
	 * @param correo - El correo de la Vacuna
	 * @return Un objeto Vacuna
	 */
	public List<EstadoProceso> darEstadosProcesoIdPorCiudadano(long id)
	{
		log.info ("Dar información de un los EstadosProceso del ciudadano con id:" + id);
        List<EstadoProceso> resp= pp.darEstadosProcesoIdPorCiudadano(id);
        log.info ("Dar estado proceso para el id ciudadano." +  resp.size() + " elementos encontrados.");
        return resp;
	}
	
	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/
//TODO Poner las tablas en orden
	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Vacuandes
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas 
	 */
	public long [] limpiarVacuandes ()
	{
        log.info ("Limpiando la BD de Vacuandes");
        long [] borrrados = pp.limpiarVacuandes();	
        log.info ("Limpiando la BD de Vacuandes: Listo!");
        return borrrados;
	}
}
