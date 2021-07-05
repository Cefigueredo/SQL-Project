package uniandes.isis2304.parranderos.negocio;

public class NumeroDosisVacuna implements VONumeroDosisVacuna{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador ÚNICO de la vacuna
	 */
	private long idVacuna;
	
	/**
	 * @return El número de dosis que requiere la vacuna
	 */
	private int numeroDosis;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public NumeroDosisVacuna() {
		this.idVacuna=0;
		this.numeroDosis=0;
	}
	
	/**
     * Constructor con valores
     */
	public NumeroDosisVacuna(Long idVacuna, int numeroDosis) {
		this.idVacuna=idVacuna;
		this.numeroDosis=numeroDosis;
	}
	
	/**
	 * @return El id de la vacuna
	 */
	public long getIdVacuna() {
		return idVacuna;
	}
	/**
	 * @param El id de la vacuna
	 */
	public void setIdVacuna(long idVacuna) {
		this.idVacuna = idVacuna;
	}
	/**
	 * @return El número de dosis de la vacuna
	 */
	public int getNumeroDosis() {
		return numeroDosis;
	}
	/**
	 * @param El número de dosis de la vacuna
	 */
	public void setNumeroDosis(int numeroDosis) {
		this.numeroDosis = numeroDosis;
	}
	
	
}
