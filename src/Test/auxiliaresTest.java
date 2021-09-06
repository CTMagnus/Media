package Test;

import static org.junit.Assert.*;

import agencia.Atraccion;
import agencia.Producto.tipoDeAtraccion;
import agencia.Producto.tipoDeProducto;
import agencia.agencia;

import org.junit.Test;

public class auxiliaresTest {

	@Test
	public void test() {
		String soy = "aventura";
		soy = soy.toUpperCase();
		
		tipoDeAtraccion soyEnum =  tipoDeAtraccion.valueOf(soy);
		
		assertEquals(soyEnum, tipoDeAtraccion.AVENTURA);
	}

}
