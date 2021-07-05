package uniandes.isis2304.parranderos.negocio;
import java.sql.Date;

/**
 * Interfaz para los métodos get de BAR.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Juan Manuel Rivera
 */
public interface VOVencimientoDosis {
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
     /**
	 * @return El lote de las dosis correspondientes
	 */
	public String getLote();
	
	/**
	 * @return La fecha de vencimiento de la dosis
	 */
	public Date getFechaVencimiento();
}
