package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Administrador de una
 * Oficina de EPS regional del negocio de la aplicación VacuAndes
 * @author Juan Manuel Rivera
 */
public class AdminEps extends Funcionario implements VOAdminEps{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El id de la Oficina de EPS Regional del cual es administrador
	 */
	private long id_Oficina_Eps;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public AdminEps() {
		super();
		this.id_Oficina_Eps=0;
	}
	/**
     * Constructor con valores
     */
	public AdminEps(long id, String correo, long id_Vacunacion, long id_EPS) {
		super(id, correo, id_Vacunacion);
		this.id_Oficina_Eps=id_EPS;		
	}
	/**
	 * @return El id de la Oficina de EPS Regional del cual es administrador
	 */
	public long getId_Oficina_Eps() {
		return id_Oficina_Eps;
	}
	/**
	 * @param El id de la Oficina de EPS Regional del cual es administrador
	 */
	public void setId_Oficina_Eps(long id_Oficina_Eps) {
		this.id_Oficina_Eps = id_Oficina_Eps;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del admin EPS Regional
	 */
	public String toString() 
	{
		return "AdminEpsRegional [id=" + super.getId() + ", correo="+super.getCorreo()+", idPlanVacunacion="+super.getId_Vacunacion()+", idOficinaEps"+this.getId_Oficina_Eps() +"]";
	}
}
