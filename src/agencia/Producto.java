package agencia;

public  class Producto {
	protected tipoDeProducto tipoDeProducto;
	protected tipoDeAtraccion tipoDeAtraccion;
	
	public enum tipoDeProducto{
		ATRACCION,PROMOCION
	}
	
	public enum tipoDeAtraccion {
		AVENTURA, PAISAJE, DEGUSTACION
	}
	
	public Producto(tipoDeProducto tipo, tipoDeAtraccion tipoDeAtraccion) {
		this.tipoDeProducto = tipo;
		this.tipoDeAtraccion = tipoDeAtraccion;
	}
	
	//Getters and setters
	
	public tipoDeProducto getTipoDeProducto() {
		return tipoDeProducto;
	}



	public void setTipoDeProducto(tipoDeProducto tipoDeProducto) {
		this.tipoDeProducto = tipoDeProducto;
	}



	public tipoDeAtraccion getTipoDeAtraccion() {
		return tipoDeAtraccion;
	}



	public void setTipoDeAtraccion(tipoDeAtraccion tipoDeAtraccion) {
		this.tipoDeAtraccion = tipoDeAtraccion;
	}



	//Funciones que van a poder usar clases hijas//
	public int getCupo() {return 0;}
	
	public void reducirCupo() {};
	
	public String getNombre() {return null;}
	
	public double calcularCosto() {return 0;};
	
	public boolean getAtraccionConCupo() {return false;}
	





}
