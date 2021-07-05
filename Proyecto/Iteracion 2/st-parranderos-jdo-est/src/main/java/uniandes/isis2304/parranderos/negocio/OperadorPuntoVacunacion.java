package uniandes.isis2304.parranderos.negocio;

public class OperadorPuntoVacunacion extends Funcionario implements VOOperadorPuntoVacunacion{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El id del Punto de Vacunación del cual es operador
	 */
	private long id_Punto_Vacunacion;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public OperadorPuntoVacunacion() {
		super();
		this.id_Punto_Vacunacion=0;
	}
	
	/**
     * Constructor con valores
     */
	public OperadorPuntoVacunacion(long id, String correo, long id_Vacunacion, long id_Punto_Vacunacion) {
		super(id, correo, id_Vacunacion);
		this.id_Punto_Vacunacion=id_Punto_Vacunacion;		
	}
	
	/**
	 * @return El id del Punto de Vacunación del cual es operador
	 */
	public long getId_Punto_Vacunacion() {
		return id_Punto_Vacunacion;
	}
	
	/**
	 * @param El id del Punto de Vacunación del cual es operador
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
		return "OperadorPuntoVacunacion [id=" + super.getId() + ", correo="+super.getCorreo()+", idPlanVacunacion="+super.getId_Vacunacion()+", idPuntoVacunacion"+this.id_Punto_Vacunacion +"]";
	}
}
