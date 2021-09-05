package agencia;

import java.util.*;


public abstract class Promocion extends Producto {
	
	List<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	protected boolean atraccionConCupo = true;
	
	public Promocion(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion) {
		super(tipo, tipoAtraccion);
	}

	public List<Atraccion> getAtraccionesContenidas() {
		return atraccionesContenidas;
	}

	public boolean isAtraccionConCupo() {
		return atraccionConCupo;
	}

	public void setAtraccionConCupo(boolean atraccionConCupo) {
		this.atraccionConCupo = atraccionConCupo;
	}
	
	
	
}
