package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Administrador del Plan de Vacunación del negocio de la aplicación VacuAndes
 *
 * @author Juan Manuel Rivera
 */
public class AdminPlanVacunacion extends Funcionario implements VOFuncionario, VOAdminPlanVacunacion{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El id del Plan de Vacunación del cual es administrador
	 */
	private long id_Plan_Vacunacion;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public AdminPlanVacunacion() {		
		super();
		this.id_Plan_Vacunacion=0;
	}
	
	/**
     * Constructor con valores
     */
	public AdminPlanVacunacion(long id, String correo, long id_Vacunacion, long id_Plan_Vacunacion) {		
		super(id, correo, id_Vacunacion);
		this.id_Plan_Vacunacion=id_Plan_Vacunacion;
	}
	
	/**
	 * @return El id del Plan de Vacunación del cual es administrador
	 */
	public long getId_Plan_Vacunacion() {
		return id_Plan_Vacunacion;
	}
	/**
	 * @param El id del Plan de Vacunación del cual es administrador
	 */
	public void setId_Plan_Vacunacion(long id_Plan_Vacunacion) {
		this.id_Plan_Vacunacion = id_Plan_Vacunacion;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del admin EPS Regional
	 */
	public String toString() 
	{
		return "AdminPlanVacunacion [id=" + super.getId() + ", correo="+super.getCorreo()+", idPlanVacunacion="+super.getId_Vacunacion()+", idOficinaEps"+this.getId_Plan_Vacunacion() +"]";
	}
}
