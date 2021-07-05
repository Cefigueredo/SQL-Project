package uniandes.isis2304.parranderos.negocio;
/**
 * Clase para modelar el concepto Administrador de un Punto de Vacunación del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class AdminPuntoVacunacion extends Funcionario implements VOAdminPuntoVacunacion{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El id del Punto de Vacunación del cual es administrador
	 */
	private long id_Punto_Vacunacion;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public AdminPuntoVacunacion() {
		super();
		this.id_Punto_Vacunacion=0;
	}
	/**
     * Constructor con valores
     */
	public AdminPuntoVacunacion(long id, String correo, long id_Vacunacion, long id_Punto_Vacunacion) {
		super(id, correo, id_Vacunacion);
		this.id_Punto_Vacunacion=id_Punto_Vacunacion;		
	}
	
	/**
	 * @return El id del Punto de Vacunación del cual es administrador
	 */
	public long getId_Punto_Vacunacion() {
		return id_Punto_Vacunacion;
	}
	
	/**
	 * @param El id del Punto de Vacunación del cual es administrador
	 */
	public void setId_Punto_Vacunacion(long id_Punto_Vacunacion) {
		this.id_Punto_Vacunacion = id_Punto_Vacunacion;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del admin EPS Regional
	 */
	public String toString() 
	{
		return "AdminPuntoVacunacion [id=" + super.getId() + ", correo="+super.getCorreo()+", idPlanVacunacion="+super.getId_Vacunacion()+", idPuntoVacunacion"+this.getId_Punto_Vacunacion() +"]";
	}
}
