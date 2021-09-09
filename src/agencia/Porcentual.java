package agencia;

import java.util.ArrayList;
import java.util.List;

public class Porcentual extends Promocion {
	
	List<Producto> atraccionesContenidas = new ArrayList<Producto>();
	protected boolean atraccionConCupo = true;
	private double interesDeLaoferta;
	private double tiempo;


	public Porcentual(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,String nombre,
			ArrayList<Atraccion> lista, double interesDeOferta) {
		super(tipo, tipoAtraccion,nombre);
		this.atraccionesContenidas = lista;
		this.interesDeLaoferta = interesDeOferta/100;
	}
	
	
public void setTiempo() {
		
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			this.tiempo += atraccionesContenidas.get(i).getTiempo();
		}
		
	}
	
	public double getTiempo() {
		return this.tiempo;
	}
	
	
	
	
	@Override
	public double calcularCosto() {
		double costo = 0;
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			costo += atraccionesContenidas.get(i).getPrecio();
		}
		costo = costo * (1-this.getInteresDeLaoferta());
		return costo;
	}
	
	//Getters and setters
	public List<Producto> getAtraccionesContenidas() {
		return atraccionesContenidas;
	}
	

	@Override
	public String toString() {
		String datos = "";
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			datos += atraccionesContenidas.get(i).getNombre()+ " ";
		}
		return datos;
	}

	public void setAtraccionesContenidas(ArrayList<Atraccion> atraccionesContenidas) {
		this.atraccionesContenidas = atraccionesContenidas;
	}

	public double getInteresDeLaoferta() {
		return interesDeLaoferta;
	}

	//public void setInteresDeLaoferta(double interesDeLaoferta) {
	//	this.interesDeLaoferta = interesDeLaoferta;
	// }

	public boolean getAtraccionConCupo() {
		return atraccionConCupo;
	}
	
	public void reducirCupoPromocion(agencia a1) {
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			int posicionDeLaAtraccion = a1.listaDeAtracciones.indexOf(atraccionesContenidas.get(i));
			a1.listaDeAtracciones.get(posicionDeLaAtraccion).reducirCupo();
			if(a1.listaDeAtracciones.get(posicionDeLaAtraccion).getAtraccionConCupo()!=true) this.atraccionConCupo = false;
		}
	}
	
	
}
