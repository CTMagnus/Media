package agencia;

import java.util.ArrayList;
import java.util.List;

public class absoluta extends Promocion {
	
	List<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	private double costo;
	protected boolean atraccionConCupo = true;
	
	public absoluta(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,List lista,int costo) {
		super(tipo, tipoAtraccion);
		atraccionesContenidas.addAll(lista);
	}
	
	@Override
	public double calcularCosto() {
		return costo;
	}
}
