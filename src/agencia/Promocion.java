package agencia;

import java.util.*;


public class Promocion extends Producto {
	
	List<Producto> atraccionesContenidas = new ArrayList<Producto>();
	public boolean atraccionConCupo = true;
	String nombre;
	
	public Promocion(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,String nombre) {
		super(tipo, tipoAtraccion);
		this.nombre = nombre;
	}

	public List<Producto> getAtraccionesContenidas() {
		return atraccionesContenidas;
	}

	public boolean isAtraccionConCupo() {
		return atraccionConCupo;
	}

	public void setAtraccionConCupo(boolean atraccionConCupo) {
		this.atraccionConCupo = atraccionConCupo;
	}
	@Override
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public String toString() {
		String datos = "";
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			datos += atraccionesContenidas.get(i).getNombre() + " ";
		}
		
		return datos;
	}
	
	
	
}
