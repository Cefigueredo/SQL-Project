package uniandes.isis2304.parranderos.negocio;

public class Etapa implements VOEtapa {
	/*
	 * Id de la etapa
	 */
	private long id;
	/**
	 * Id de la fase en la que se encuentra la etapa
	 */
	private int fase;
	/**
	 * Descripción de la etapa
	 */
	private String descripcion;
	/**
	 * Edad mínima para estar en la etapa
	 */
	private int edadMinima;
	/**
	 *  Edad Máxima para estar en la etapa
	 */
	private int edadMaxima;
	/**
	 * Id de Vacunacion
	 */
	private long idVacunacion;
	
	public Etapa() {
		this.id = 0;
		this.fase = 0;
		this.edadMinima = 0;
		this.edadMaxima = 0;
		this.idVacunacion = 0;
		this.descripcion = "";
	}

	public Etapa(long id, int fase, String descripcion, int edadMinima, int edadMaxima, long idVacunacion) {
		this.id = id;
		this.fase = fase;
		this.descripcion = descripcion;
		this.edadMinima = edadMinima;
		this.edadMaxima = edadMaxima;
		this.idVacunacion = idVacunacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getFase() {
		return fase;
	}

	public void setFase(int fase) {
		this.fase = fase;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEdadMinima() {
		return edadMinima;
	}

	public void setEdadMinima(int edadMinima) {
		this.edadMinima = edadMinima;
	}

	public int getEdadMaxima() {
		return edadMaxima;
	}

	public void setEdadMaxima(int edadMaxima) {
		this.edadMaxima = edadMaxima;
	}

	public long getIdVacunacion() {
		return idVacunacion;
	}

	public void setIdVacunacion(long idVacunacion) {
		this.idVacunacion = idVacunacion;
	}
}
