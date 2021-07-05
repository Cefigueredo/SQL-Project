package uniandes.isis2304.parranderos.negocio;

public class Profesion implements VOProfesion {
	
	/* ****************************************************************
	 * 			Atributos 
	 *****************************************************************/
	
	/**
	 * Id de la profesión
	 */
	private long id;
	/**
	 * Descripción de la profesión
	 */
	private String descripcion;
	/**
	 * Id de la etapa en la que se prioriza la profesión
	 */
	private long idEtapa;
	
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	public Profesion() {
		this.id = 0;
		this.descripcion = "";
		this.idEtapa = 0;
	}

	public Profesion(long id, String descripcion, long idEtapa) {
		this.id = id;
		this.descripcion = descripcion;
		this.idEtapa = idEtapa;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(long idEtapa) {
		this.idEtapa = idEtapa;
	}
	
	
	
	
}
