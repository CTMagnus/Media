package agencia;

import java.util.ArrayList;
import java.util.List;

public class Porcentual extends Promocion {
	
	List<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	protected boolean atraccionConCupo = true;
	private double interesDeLaoferta;

	public Porcentual(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,List lista,double interesDeOferta) {
		super(tipo, tipoAtraccion);
		atraccionesContenidas.addAll(lista);
		this.interesDeLaoferta = interesDeOferta/100;
	}
	
	
	
	
	
	
	@Override
	public double calcularCosto() {
		double costo = 0;
		for (int i = 0; i < atraccionesContenidas.size(); i++) {
			costo += atraccionesContenidas.get(i).getPrecio();
		}
		costo = costo * this.getInteresDeLaoferta();
		return costo;
	}
	
	//Getters and setters
	public List<Atraccion> getAtraccionesContenidas() {
		return atraccionesContenidas;
	}

	public void setAtraccionesContenidas(List<Atraccion> atraccionesContenidas) {
		this.atraccionesContenidas = atraccionesContenidas;
	}

	public double getInteresDeLaoferta() {
		return interesDeLaoferta;
	}

	public void setInteresDeLaoferta(double interesDeLaoferta) {
		this.interesDeLaoferta = interesDeLaoferta;
	}

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
