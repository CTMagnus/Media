package agencia;

import java.util.*;


public class axb extends Promocion {
	
	List<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	protected boolean atraccionConCupo = true;
	private double costo;
	
	public axb(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,String nombre,List lista) {
		super(tipo, tipoAtraccion,nombre);
		atraccionesContenidas.addAll(lista);
		
	}
	
	
	@Override
	public double calcularCosto() {
		double costo = 0;
		for (int i = 0; i < atraccionesContenidas.size()-1; i++) {
			costo += atraccionesContenidas.get(i).getPrecio();
		}
		return costo;
	}
	
	public void agregarAtraccionesContenidas(List<Atraccion> lista) {
		atraccionesContenidas.addAll(lista);
	}

	public void reducirCupoPromocion(agencia a1) {
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			int posicionDeLaAtraccion = a1.listaDeAtracciones.indexOf(atraccionesContenidas.get(i));
			a1.listaDeAtracciones.get(posicionDeLaAtraccion).reducirCupo();
			if(a1.listaDeAtracciones.get(posicionDeLaAtraccion).getAtraccionConCupo()!=true) this.atraccionConCupo = false;
		}
	}
	@Override
	public String toString() {
		String datos = "Soy axb";
		/*
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			datos += atraccionesContenidas.get(i).getNombre() + " ";
		}
		*/
		return datos;
	}
	
}
