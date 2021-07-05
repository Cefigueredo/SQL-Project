package uniandes.isis2304.parranderos.negocio;

import java.sql.Date;

/**
 * Clase para modelar el concepto VencimientoDosis del negocio de los Parranderos
 *
 * @author Juan Manuel Rivera
 */
public class VencimientoDosis implements VOVencimientoDosis{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El lote de las dosis correspondientes
	 */
	private String lote;
	
	/**
	 * La fecha de vencimiento de la dosis
	 */
	private Date fechaVencimiento;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public VencimientoDosis() {
		this.lote="";
		this.fechaVencimiento=new Date(1);
	}
	
	/**
	 * Constructor con valores
	 */
	public VencimientoDosis(String lote, Date fechaVencimiento) {
		this.lote=lote;
		this.fechaVencimiento=fechaVencimiento;
	}
	
	/**
	 * @return El lote de las dosis correspondientes
	 */
	public String getLote() {
		return lote;
	}

	/**
	 * @param El lote de las dosis correspondientes
	 */
	public void setLote(String lote) {
		this.lote = lote;
	}
	
	/**
	 * @return La fecha de vencimiento de la dosis
	 */
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param La fecha de vencimiento de la dosis
	 */
	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la fecha de vencimiento de la dosis
	 */
	public String toString() 
	{
		return "VencimientoDosis [lote=" + lote + ", fechaVencimiento=" + fechaVencimiento +"]";
	}

}
