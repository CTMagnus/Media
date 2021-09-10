package agencia;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import agencia.Producto.*;


public class agencia {
	protected List<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
	public List<Atraccion> listaDeAtracciones = new ArrayList<Atraccion>();
	public List<Promocion> listaDePromociones = new ArrayList<Promocion>();
	public List<String>    nombres = new ArrayList<String>();
	public TreeMap<Integer, PriorityQueue<Promocion>> mapaPromocionesAventuras;
	public TreeMap<Integer, PriorityQueue<Promocion>> mapaPromocionesPaisaje;
	public TreeMap<Integer, PriorityQueue<Promocion>> mapaPromocionesDegustacion;
	private TreeMap<Integer, PriorityQueue<Atraccion>> mapaAtraccionesAventuras;
	private TreeMap<Integer, PriorityQueue<Atraccion>> mapaAtraccionesPaisaje;
	private TreeMap<Integer, PriorityQueue<Atraccion>> mapaAtraccionesDegustacion;
	private HashMap<String, Atraccion> mapaAtraccionesPorNombre;
	private List<Promocion> promocionesAxb = new ArrayList<Promocion>();
	private List<absoluta> promocionesAbsoluta = new ArrayList<absoluta>();
	private List<Porcentual> promocionesPorcentual = new ArrayList<Porcentual>();

	//Diccionario de Atraciones--------------------------------------------------
	
	//Mapas de Promociones  por tipo de Atraccion  Ordenadas por precio
	public void mapaAtraccionPorNombre() {
		
	//Mapa de Promociones  de Aventuras	
		mapaAtraccionesPorNombre = new HashMap<String, Atraccion>();
		
		for (Atraccion a : listaDeAtracciones) {
			
				String key = a.getNombre();
				mapaAtraccionesPorNombre.put(key, a);
				}
	
		}
	

	//Carga de archivos --------------------------------------------------------
	
	@SuppressWarnings("unused")
	private void cargarDatos() throws IOException {
		
		this.LeeYcargaUsuarios("usuarios.txt");
		this.LeeYcargaAtracciones("atracciones.txt");
		this.mapaAtraccionPorNombre();
		this.LeeYcargaPromociones("promociones.txt");
	}
	
	public List<Atraccion> LeeYcargaAtracciones(String archivo) throws IOException {

	//	List<Atraccion> listaDeAtr= new ArrayList<Atraccion>();
		FileReader fr = null; 
		BufferedReader br = null;

		try {
			// instancia un objeto Scanner
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea = br.readLine();

			while (linea != null) {
				// Lee cada línea y la asiga a un array de String usando el espacio como
				// separador
			
				String[] datos = linea.split(",");

				
				// Crea la Atraccion con los datos de la línea de texto
					tipoDeProducto tipoDeP = tipoDeProducto.ATRACCION;   
					String tipoDeAtr = datos[0];
					String nombre = datos[1];
					int precio = Integer.parseInt(datos[2]);
					int cupo = Integer.parseInt(datos[3]);
					double tiempoNecesario = Double.parseDouble(datos[4]);
					tipoDeAtraccion tipoA;
					
				// Se instancia una nueva atraccion y
				// Si no está repetida, se agrega a la lista
					if (tipoDeAtr.equals("aventura")) {
						tipoA = tipoDeAtraccion.AVENTURA;
					} else if (tipoDeAtr.equals("paisaje")) {
						tipoA = tipoDeAtraccion.PAISAJE;
					} else tipoA = tipoDeAtraccion.DEGUSTACION;
					
					
				Atraccion a = new Atraccion(tipoDeP, 
						tipoA,
						nombre,
						precio,
						cupo,
						tiempoNecesario); 
				
				
				if (!listaDeAtracciones.contains(a)  ){
					listaDeAtracciones.add(a);
				}
				linea = br.readLine();
			}

		}
			
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Cierra el archivo
		try {
			if (fr != null) {
				fr.close();
			} 
		} catch (Exception e2) {
				e2.printStackTrace();
			}
		
		
		return listaDeAtracciones;
	}

	public  void LeeYcargaPromociones(String archivo) throws IOException {

		//List<Promocion> listaDePromociones = new ArrayList<Promocion>();
		FileReader fr = null; 
		BufferedReader br = null;

		try {
			// instancia un objeto Scanner
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea = br.readLine();

			while (linea != null) {
				// Lee cada línea y la asiga a un array de String usando el espacio como
				// separador
				
				String[] datos = linea.split(",");
					
			//	System.out.println(linea);
				 	String tipoDesc = datos[0];
				 	TipoDeDescuento tDes;
					tipoDeProducto tipoDeP = tipoDeProducto.PROMOCION;   
					String tipoDeAtr = datos[1];
					String nombre = datos[2];
					tipoDeAtraccion tipoA;
					Double descuento = Double.parseDouble(datos[3]);
					ArrayList<String> nombresAtracciones = new ArrayList<String>();
					ArrayList<Atraccion> atraccionesContenidas = new ArrayList<Atraccion>();
					
				//	System.out.println(tipoDeAtr);
					for (int i = 4; i <datos.length; i++) {
						nombresAtracciones.add(datos[i]);	 
					}
					
					if (tipoDeAtr.equals("AVENTURA") ) {
						tipoA = tipoDeAtraccion.AVENTURA;
					} else if (tipoDeAtr.equals("PAISAJE")) {
						tipoA = tipoDeAtraccion.PAISAJE;
					} else tipoA = tipoDeAtraccion.DEGUSTACION;
					
										
					for(String atr: nombresAtracciones ) {
						
						if (mapaAtraccionesPorNombre.containsKey(atr)) {
							Atraccion a = mapaAtraccionesPorNombre.get(atr);
							atraccionesContenidas.add(a);
						}
					}
					// Se instancia una nueva Promocion y
					if (tipoDesc.equals("axb") ) {
						tDes = TipoDeDescuento.AxB;
						 listaDePromociones.add(new AxB(tDes, tipoDeP, tipoA, nombre, descuento,
								atraccionesContenidas));
						 
					} else if (tipoDesc.equals("absoluta") ) {
						tDes = TipoDeDescuento.ABSOLUTO;
						 listaDePromociones.add(new absoluta(tDes, tipoDeP, tipoA, nombre, descuento,
							atraccionesContenidas));
					
					} else {
						tDes = TipoDeDescuento.PORCENTUAL;
						
						 listaDePromociones.add(new Porcentual(tDes, tipoDeP, tipoA, nombre, descuento,
							atraccionesContenidas));
					}
				//	System.out.println(tDes);
				// Si no está repetida, se agrega a la lista
				
			
				linea = br.readLine();
			}

		}
			
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Cierra el archivo
		try {
			if (fr != null) {
				fr.close();
			} 
		} catch (Exception e2) {
				e2.printStackTrace();
			}
		
	
	}

	public List<Usuario> LeeYcargaUsuarios (String archivo) throws IOException {

		//	List<Atraccion> listaDeAtr= new ArrayList<Atraccion>();
			FileReader fr = null; 
			BufferedReader br = null;

			try {
				// instancia un objeto Scanner
				fr = new FileReader(archivo);
				br = new BufferedReader(fr);
				String linea = br.readLine();

				while (linea != null) {
					// Lee cada línea y la asiga a un array de String usando el espacio como
					// separador
				
					String[] datos = linea.split(",");

					
					// Crea la Atraccion con los datos de la línea de texto
					//
				//	tipoDeAtraccion preferencia)
						String nombre = datos[0];   
						int dinero = Integer.parseInt(datos[1]);
						double tiempoNecesario = Double.parseDouble(datos[2]);
						String tipoDeAtr = datos[3];
						
						tipoDeAtraccion tipoA;
						
					// Se instancia una nueva atraccion y
					// Si no está repetida, se agrega a la lista
						if (tipoDeAtr.equals("aventura")) {
							tipoA = tipoDeAtraccion.AVENTURA;
						} else if (tipoDeAtr.equals("paisaje")) {
							tipoA = tipoDeAtraccion.PAISAJE;
						} else tipoA = tipoDeAtraccion.DEGUSTACION;
						
						
					Usuario u = new Usuario(nombre, 
							dinero,
							tiempoNecesario,
							tipoA); 
					
					
					if (!listaDeUsuarios.contains(u)  ){
						listaDeUsuarios.add(u);
					}
					linea = br.readLine();
				}

			}
				
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// Cierra el archivo
			try {
				if (fr != null) {
					fr.close();
				} 
			} catch (Exception e2) {
					e2.printStackTrace();
				}
			
			
			return listaDeUsuarios;
		}

		
		
	/*
	 * public void cargaAtracciones(String linea) {
	 * 
	 * String name= new String(); int costo = 0; double tiempo = 0; int cupo = 0 ;
	 * int status=0; int index=0;
	 * 
	 * for(int i=0; i < linea.length(); i++) { if(9 == linea.charAt(i)) {
	 * switch(status) { case 0: name=linea.substring(index,i);; break; case 1: costo
	 * = Integer.parseInt(linea.substring(index,i)); break; case 2: tiempo =
	 * Double.parseDouble(linea.substring(index,i)); break; case 3: cupo=
	 * Integer.parseInt(linea.substring(index,i));
	 * 
	 * if(linea.substring(i+1,linea.length()).equals("aventura"))
	 * listaDeAtracciones.add(new
	 * Atraccion(tipoDeProducto.ATRACCION,tipoDeAtraccion.AVENTURA,name,costo,cupo,
	 * tiempo)); else if(linea.substring(i+1,linea.length()).equals("degustacion"))
	 * listaDeAtracciones.add(new
	 * Atraccion(tipoDeProducto.ATRACCION,tipoDeAtraccion.DEGUSTACION,name,costo,
	 * cupo,tiempo)); else listaDeAtracciones.add(new
	 * Atraccion(tipoDeProducto.ATRACCION,tipoDeAtraccion.PAISAJE,name,costo,cupo,
	 * tiempo));
	 * 
	 * i=linea.length(); break; } status++; index=i+1; } } this.guardarNombres();
	 * 
	 * }
	 */
	
	
	//--------------------------------------------------------------------------
	public void mostrarUsers() {
		for (int i = 0; i < listaDeUsuarios.size(); i++) {
			System.out.println(listaDeUsuarios.get(i).toString());
		}
	}
	public void mostrarAtracciones() {
		for (int i = 0; i < listaDeAtracciones.size(); i++) {
			System.out.println(listaDeAtracciones.get(i).toString());
		}
	}
	
	public void mostrarPromos() {
		
			
	
	}
	public void guardarNombres() {
		for (int i = 0; i < listaDeAtracciones.size(); i++) {
			String aca = listaDeAtracciones.get(i).getNombre();
			if(nombres.contains(aca)) continue;
			nombres.add(aca);
		}
	}
	
	public void mostrarNames() {
		for (int i = 0; i < nombres.size(); i++) {
			System.out.println(nombres.get(i));
		}
	}
	
	//---------------------------------------------------------------------------------------
		//Filtros
	
	//Mapas de Promociones  por tipo de Atraccion  Ordenadas por precio
	public void mapasPorAtraccionPorPrecio() {
		
	//Mapa de Promociones  de Aventuras	
		mapaPromocionesAventuras = new TreeMap<Integer, PriorityQueue<Promocion>>();
		
		for (Producto p : listaDePromociones) {
			if (p.tipoDeAtraccion == tipoDeAtraccion.AVENTURA
					
					//Agregar que tenga cupo. Sino que no la ingrese
					) {

				Integer key = (int) p.getPrecio();
				if (mapaPromocionesAventuras.containsKey(key)) {
					mapaPromocionesAventuras.get(key).offer((Promocion) p);
				} else {
					PriorityQueue<Promocion> cola = new PriorityQueue<Promocion>();
					cola.offer((Promocion) p);
					mapaPromocionesAventuras.put(key, cola);
				}

			}
		}
		
		
		//Mapa de  Promociones  de  Paisaje	
				mapaPromocionesPaisaje = new TreeMap<Integer, PriorityQueue<Promocion>>();
				
				for (Producto p : listaDePromociones) {
					if (p.tipoDeAtraccion == tipoDeAtraccion.PAISAJE
							
							//Agregar que tenga cupo. Sino que no la ingrese
							
							
							) {

						Integer key = (int) p.getPrecio();
						if (mapaPromocionesPaisaje.containsKey(key)) {
							mapaPromocionesPaisaje.get(key).offer((Promocion) p);
						} else {
							PriorityQueue<Promocion> cola = new PriorityQueue<Promocion>();
							cola.offer((Promocion) p);
							mapaPromocionesPaisaje.put(key, cola);
						}

					}
				}
		
				//Mapa de Degustacion	
				mapaPromocionesDegustacion = new TreeMap<Integer, PriorityQueue<Promocion>>();
				
				for (Producto p : listaDePromociones) {
					if (p.tipoDeAtraccion == tipoDeAtraccion.DEGUSTACION
							
							
							
							//Agregar que tenga cupo. Sino que no la ingrese
							
							
							) {

						Integer key = (int) p.getPrecio();
						if (mapaPromocionesPaisaje.containsKey(key)) {
							mapaPromocionesPaisaje.get(key).offer((Promocion) p);
						} else {
							PriorityQueue<Promocion> cola = new PriorityQueue<Promocion>();
							cola.offer((Promocion) p);
							mapaPromocionesPaisaje.put(key, cola);
						}

					}
				}
		
	}

	
	//Mapas de Atraccion  Ordenadas por precio
	public void mapasDeAtraccionPorPrecio() {
		
	//Mapa de Aventuras	
		mapaAtraccionesAventuras = new TreeMap<Integer, PriorityQueue<Atraccion>>();
		
		for (Producto p : listaDeAtracciones) {
			if (p.tipoDeAtraccion == tipoDeAtraccion.AVENTURA
					
					//Agregar que tenga cupo. Sino que no la ingrese
					) {

				Integer key = (int) p.getPrecio();
				if (mapaAtraccionesAventuras.containsKey(key)) {
					mapaAtraccionesAventuras.get(key).offer((Atraccion) p);
				} else {
					PriorityQueue<Atraccion> cola = new PriorityQueue<Atraccion>();
					cola.offer((Atraccion) p);
					mapaAtraccionesAventuras.put(key, cola);
				}

			}
		}
		
		
		//Mapa de Paisaje		
				mapaAtraccionesPaisaje = new TreeMap<Integer, PriorityQueue<Atraccion>>();
				
				for (Producto p : listaDeAtracciones) {
					if (p.tipoDeAtraccion == tipoDeAtraccion.PAISAJE
							
							//Agregar que tenga cupo. Sino que no la ingrese
							) {

						Integer key = (int) p.getPrecio();
						if (mapaAtraccionesPaisaje.containsKey(key)) {
							mapaAtraccionesPaisaje.get(key).offer((Atraccion) p);
						} else {
							PriorityQueue<Atraccion> cola = new PriorityQueue<Atraccion>();
							cola.offer((Atraccion) p);
							mapaAtraccionesPaisaje.put(key, cola);
						}

					}
				}

		
				//Mapa de Degustacion	
				mapaAtraccionesDegustacion = new TreeMap<Integer, PriorityQueue<Atraccion>>();
				
				for (Producto p : listaDeAtracciones) {
					if (p.tipoDeAtraccion == tipoDeAtraccion.DEGUSTACION
							
							//Agregar que tenga cupo. Sino que no la ingrese
							) {

						Integer key = (int) p.getPrecio();
						if (mapaAtraccionesDegustacion.containsKey(key)) {
							mapaAtraccionesDegustacion.get(key).offer((Atraccion) p);
						} else {
							PriorityQueue<Atraccion> cola = new PriorityQueue<Atraccion>();
							cola.offer((Atraccion) p);
							mapaAtraccionesDegustacion.put(key, cola);
						}

					}
				}
		
	}
	
	//Recorre lista usuarios
	private void filtroSugerencias() {
		
		for(Usuario u: listaDeUsuarios) {
		
	//Por cada usuario se genera el mapa para que los cupos estén actualizados		
			
		this.mapasPorAtraccionPorPrecio();	
			// Primero entra en el mapa del tipo de atraccion preferida
		
		
		if (u.getPreferencia() == tipoDeAtraccion.AVENTURA) {
			
			//primero ofrece las de su gusto y luego las que no coinciden
			filtroPreferenciaAventura(u);
			filtroPreferenciaDegustacion(u);
			filtroPreferenciPaisaje(u);
			filtroAtraccionAventura(u);
			filtroAtraccionDegustacion(u);
			filtroAtraccionPaisaje(u);
		}
			
						
		if (u.getPreferencia() == tipoDeAtraccion.DEGUSTACION) {
			
			//primero ofrece las de su gusto y luego las que no coinciden
			filtroPreferenciaDegustacion(u);
			filtroPreferenciPaisaje(u);
			filtroPreferenciaAventura(u);
			filtroAtraccionDegustacion(u);
			filtroAtraccionAventura(u);
			filtroAtraccionPaisaje(u);
		}
			
		
		if (u.getPreferencia() == tipoDeAtraccion.PAISAJE) {
			
			//primero ofrece las de su gusto y luego las que no coinciden
			filtroPreferenciPaisaje(u);
			filtroPreferenciaDegustacion(u);
			filtroPreferenciaAventura(u);
			filtroAtraccionPaisaje(u);
			filtroAtraccionAventura(u);
			filtroAtraccionDegustacion(u);
			
		}
			
		System.out.println("muchas gracias " + u.getNombre() + " por tratar con nuestra agencia");
		
	
	}	
		
		
	
	}
	private void filtroPreferenciPaisaje(Usuario u) {
		{
			
			for(Entry <Integer, PriorityQueue<Promocion>> cadaPromoPaisaje: 
				mapaPromocionesPaisaje.entrySet()) {
				
				
		//comprueba que le alcanza el dinero
				if(u.getMonedasDeOro() >= cadaPromoPaisaje.getKey() ) {
					
					PriorityQueue<Promocion> listaPromociones = cadaPromoPaisaje.getValue();	
		//Recorre la lista de promociones de ese costo			
					for(Promocion p : listaPromociones ) {
			
		//Comprueba que le alcance el tiempo				
						if(u.getTiempoDisponible() >= p.getTiempo()) {
		
		// Si cumple con todo, la ofrece
							if (this.ofertar(u)) {
					
		//Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra	
								u.restarDinero((int)p.calcularCosto());
								u.setTiempoDisponible(p.getTiempo());
								u.setSugerenciasDiarias(p);
				
						//Actualiza el cupo de las y atracciones			
								List<Atraccion> atraccionesCompradas =  p.getAtraccionesContenidas();
								for(Atraccion a : atraccionesCompradas) {
									a.reducirCupo();
									
								}
							}
						}
					}
					
				}
					
			}
			
		}
	}
	private void filtroPreferenciaDegustacion(Usuario u) {
		{
			
			for(Entry <Integer, PriorityQueue<Promocion>> cadaPromoDegustacion: 
				mapaPromocionesDegustacion.entrySet()) {
				
				
		//comprueba que le alcanza el dinero
				if(u.getMonedasDeOro() >= cadaPromoDegustacion.getKey() ) {
					
					PriorityQueue<Promocion> listaPromociones = cadaPromoDegustacion.getValue();	
		//Recorre la lista de promociones de ese costo			
					for(Promocion p : listaPromociones ) {
			
		//Comprueba que le alcance el tiempo				
						if(u.getTiempoDisponible() >= p.getTiempo()) {
		
		// Si cumple con todo, la ofrece
							if (this.ofertar(u)) {
					
		//Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra	
								u.restarDinero((int)p.calcularCosto());
								u.setTiempoDisponible(p.getTiempo());
								u.setSugerenciasDiarias(p);
				
						//Actualiza el cupo de las y atracciones			
								List<Atraccion> atraccionesCompradas =  p.getAtraccionesContenidas();
								for(Atraccion a : atraccionesCompradas) {
									a.reducirCupo();
									
								}
							}
						}
					}
					
				}
					
			}
			
		}
	}
	
	
	private void filtroPreferenciaAventura(Usuario u) {
		
			
			for(Entry <Integer, PriorityQueue<Promocion>> cadaPromoAventura: 
				mapaPromocionesAventuras.entrySet()) {
				
				
		//comprueba que le alcanza el dinero
				if(u.getMonedasDeOro() >= cadaPromoAventura.getKey() ) {
					
					PriorityQueue<Promocion> listaPromociones = cadaPromoAventura.getValue();	
		//Recorre la lista de promociones de ese costo			
					for(Promocion p : listaPromociones ) {
			
		//Comprueba que le alcance el tiempo				
						if(u.getTiempoDisponible() >= p.getTiempo()) {
		
		// Si cumple con todo, la ofrece
							if (this.ofertar(u)) {
					
		//Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra	
								u.restarDinero((int)p.calcularCosto());
								u.setTiempoDisponible(p.getTiempo());
								u.setSugerenciasDiarias(p);
				
						//Actualiza el cupo de las y atracciones			
								List<Atraccion> atraccionesCompradas =  p.getAtraccionesContenidas();
								for(Atraccion a : atraccionesCompradas) {
									a.reducirCupo();
									
								}
							}
						}
					}
					
				}
					
			}
			
		
	}
	
	private void filtroAtraccionAventura(Usuario u) {
		
		
		for(Entry <Integer, PriorityQueue<Atraccion>> cadaAtraccionAventura: 
			mapaAtraccionesAventuras.entrySet()) {
			
			
	//comprueba que le alcanza el dinero
			if(u.getMonedasDeOro() >= cadaAtraccionAventura.getKey() ) {
				
				PriorityQueue<Atraccion> listaAtracciones = cadaAtraccionAventura.getValue();	
	//Recorre la lista de promociones de ese costo			
				for(Atraccion a : listaAtracciones ) {
		
	//Comprueba que le alcance el tiempo				
					if(u.getTiempoDisponible() >= a.getTiempo()) {
	
	// Si cumple con todo, la ofrece
						if (this.ofertar(u)) {
				
	//Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra	
							u.restarDinero((int)a.getPrecio());
							u.setTiempoDisponible(a.getTiempo());
							u.setSugerenciasDiarias(a);
			
					//Actualiza el cupo de las y atracciones			
							a.reducirCupo();
								
							}
						}
					}
				}
				
			}
				
		}
		
	
	private void filtroAtraccionPaisaje(Usuario u) {
		
		
		for(Entry <Integer, PriorityQueue<Atraccion>> cadaAtraccionPaisaje: 
			mapaAtraccionesPaisaje.entrySet()) {
			
			
	//comprueba que le alcanza el dinero
			if(u.getMonedasDeOro() >= cadaAtraccionPaisaje.getKey() ) {
				
				PriorityQueue<Atraccion> listaAtracciones = cadaAtraccionPaisaje.getValue();	
	//Recorre la lista de promociones de ese costo			
				for(Atraccion a : listaAtracciones ) {
		
	//Comprueba que le alcance el tiempo				
					if(u.getTiempoDisponible() >= a.getTiempo()) {
	
	// Si cumple con todo, la ofrece
						if (this.ofertar(u)) {
				
	//Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra	
							u.restarDinero((int)a.getPrecio());
							u.setTiempoDisponible(a.getTiempo());
							u.setSugerenciasDiarias(a);
			
					//Actualiza el cupo de las y atracciones			
							a.reducirCupo();
								
							}
						}
					}
				}
				
			}
				
		}
		
	private void filtroAtraccionDegustacion(Usuario u) {
	
	
	for(Entry <Integer, PriorityQueue<Atraccion>> cadaAtraccionDegustacion: 
		mapaAtraccionesDegustacion.entrySet()) {
		
		
//comprueba que le alcanza el dinero
		if(u.getMonedasDeOro() >= cadaAtraccionDegustacion.getKey() ) {
			
			PriorityQueue<Atraccion> listaAtracciones = cadaAtraccionDegustacion.getValue();	
//Recorre la lista de promociones de ese costo			
			for(Atraccion a : listaAtracciones ) {
	
//Comprueba que le alcance el tiempo				
				if(u.getTiempoDisponible() >= a.getTiempo()) {

// Si cumple con todo, la ofrece
					if (this.ofertar(u)) {
			
//Si acepta la compra actualiza dinero y tiempo del Usuario. Guarda la compra	
						u.restarDinero((int)a.getPrecio());
						u.setTiempoDisponible(a.getTiempo());
						u.setSugerenciasDiarias(a);
		
				//Actualiza el cupo de las y atracciones			
						a.reducirCupo();
							
						}
					}
				}
			}
			
		}
			
	}
	
	
	private boolean ofertar(Usuario u) {
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args) throws IOException {
		
		
		agencia a1 = new agencia();
		a1.cargarDatos();
	//	a1.LeeYcargaAtracciones("atracciones.txt");
	//	a1.cargarPromocion("promociones.txt");


		//a1.mostrarNames();
		//a1.mostrarPromos();
		/*
		 * a1.mostrarPromos();
		 */
	//	for(Producto p : a1.listaDePromociones) {
	//		System.out.println(p.toString());
	//	}
		//*
	//	List<Producto> a2 = a1.listaDePromociones.get(0).getAtr();
	//	Producto a3 = a2.get(0);
	//	System.out.println(a3.getNombre());
		//System.out.println(a3.getNombre().equals(a1.listaDeAtracciones.get(0).getNombre()));
		//List<Producto> a2 = a1.listaDePromociones.get(0).getAtr();
		//Producto a3 = a2.get(0);
		//System.out.println(a3.getNombre().equals(a1.listaDeAtracciones.get(0).getNombre()));
		//a1.mostrarUsers();
		//a1.mostrarAtracciones(); 
		//a1.mostrarAtracciones(); 
	//	a1.mapaAtraccionPorNombre();
	//	a1.mapasDeAtraccionPorPrecio();
		//a1.filtroAtraccionAventura(new Usuario ("lucas", 60, 5.4, tipoDeAtraccion.AVENTURA));
	//	a1.LeeYcargaPromociones("promociones.txt");
		//a1.listaDePromociones.toString();
		//System.out.println(a1.listaDePromociones.toString());
		//a1.LeeYcargaAtracciones("atracciones.txt");
	//	System.out.println(a1.listaDeAtracciones.toString());
	//	a1.LeeYcargaUsuarios("usuarios.txt");
	//	System.out.println(a1.listaDeUsuarios.toString());
	}


}
