package agencia;

import java.util.*;


public class AxB extends Promocion {
	
	List<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
	protected boolean atraccionConCupo = true;
	
	public AxB(tipoDeProducto tipo, tipoDeAtraccion tipoAtraccion,List lista) {
		super(tipo, tipoAtraccion);
		atraccionesContenidas.addAll(lista);
		
	}
	
	
}
