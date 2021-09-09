package agencia;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import agencia.Producto.*;

public class agencia {
	protected List<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
	public List<Atraccion> listaDeAtracciones = new ArrayList<Atraccion>();
	public List<Producto> listaDePromociones = new ArrayList<Producto>();
	public List<String>    nombres = new ArrayList<String>();
	public TreeMap<Integer, PriorityQueue<Promocion>> mapaPromocionesAventuras;
	public TreeMap<Integer, PriorityQueue<Promocion>> mapaPromocionesPaisaje;
	public TreeMap<Integer, PriorityQueue<Promocion>> mapaPromocionesDegustacion;
	//Carga de archivos --------------------------------------------------------
	@SuppressWarnings("Unused")
	private void readData(String txt) {
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			
			archivo = new File(txt);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea ;
			while((linea= br.readLine())!=null) {
				if(txt == "atracciones.txt")
					this.cargaAtracciones(linea);
				else
					this.cargarUsuarios(linea);
			}			
			
		} catch (NullPointerException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fr != null) {
					fr.close();
				}
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	private void cargarUsuarios(String linea) {
		String name= new String();
		int presupuesto = 0;
		double tiempo = 0;
		
		int status=0;
		int index=0;
		
		for(int i=0; i < linea.length(); i++) {
			if(9 == linea.charAt(i)) {
				switch(status) {
				case 0:
					name=linea.substring(index,i);;
					break;
				case 1:
					presupuesto = Integer.parseInt(linea.substring(index,i));
					break;
				case 2:
					tiempo = Double.parseDouble(linea.substring(index,i));
					if(linea.substring(i+1,linea.length()).equals("aventura"))
						listaDeUsuarios.add(new Usuario(name, presupuesto, tiempo, tipoDeAtraccion.AVENTURA));
					else
						if(linea.substring(i+1,linea.length()).equals("degustacion"))
							listaDeUsuarios.add(new Usuario(name, presupuesto, tiempo, tipoDeAtraccion.DEGUSTACION));
						else
							listaDeUsuarios.add(new Usuario(name, presupuesto, tiempo, tipoDeAtraccion.PAISAJE));
					
					i=linea.length();
					break;
				}
				status++;
				index=i+1;
			}
		}
		
		
	}
	public void cargaAtracciones(String linea) {
		
		String name= new String();
		int costo = 0;
		double tiempo = 0;
		int cupo = 0 ;
		int status=0;
		int index=0;
		
		for(int i=0; i < linea.length(); i++) {
			if(9 == linea.charAt(i)) {
				switch(status) {
				case 0:
					name=linea.substring(index,i);;
					break;
				case 1:
					costo = Integer.parseInt(linea.substring(index,i));
					break;
				case 2:
					tiempo = Double.parseDouble(linea.substring(index,i));
					break;
				case 3:
					cupo= Integer.parseInt(linea.substring(index,i));
					
					if(linea.substring(i+1,linea.length()).equals("aventura"))
						listaDeAtracciones.add(new Atraccion(tipoDeProducto.ATRACCION,tipoDeAtraccion.AVENTURA,name,costo,cupo,tiempo));
					else
						if(linea.substring(i+1,linea.length()).equals("degustacion"))
							listaDeAtracciones.add(new Atraccion(tipoDeProducto.ATRACCION,tipoDeAtraccion.DEGUSTACION,name,costo,cupo,tiempo));
						else
							listaDeAtracciones.add(new Atraccion(tipoDeProducto.ATRACCION,tipoDeAtraccion.PAISAJE,name,costo,cupo,tiempo));
					
					i=linea.length();
					break;
				}
				status++;
				index=i+1;
			}
		}
		this.guardarNombres();
		
	}
	
	
	
	@SuppressWarnings("unused")
	private void cargarPromocion(String txt	) {
		
		List<String> nombreDe = new ArrayList<String>();
		
		
		Scanner sc = null;
		try {
			sc = new Scanner(new File(txt));
			
			while(sc.hasNext()) {
				
				String linea = sc.nextLine();
				String datos[] = linea.split(",");
				tipoDeProducto constante = tipoDeProducto.PROMOCION;
				tipoDeAtraccion tipoAtracc = null;
				String nombre = null;
				ArrayList<Atraccion> atraccionesAcontener = new ArrayList<Atraccion>();
				double costoOInteres = 0;
				double interes = 0;
				int caso = 0 ;
				//Tipo- Si es absoluta,axb o porcentual
				String tipoDePromocion = datos[0];
				//Tipo de atracciones contenidas
				tipoAtracc = tipoDeAtraccion.valueOf(datos[1].toUpperCase());
				//nombre
				nombre = datos[2];
				//Caso de absoluta o porcentual guarda costo o interes respectivamente
				if(datos.length==5) costoOInteres = Double.parseDouble(datos[4]);
				//extraigo nombres de las atracciones contenidas
				String atraccionesDeFichero[] = datos[3].split("-");
				/*
				//Comprobaciones 
				System.out.println(tipoDePromocion.toString());
				System.out.println(tipoAtracc.name());
				System.out.println(nombre);
				System.out.println(costoOInteres);
				for(String s: atraccionesDeFichero) {
					System.out.println(s.toString()); 
				} */
				
				for(String s: atraccionesDeFichero) {
					int index = nombres.indexOf(s);
					atraccionesAcontener.add(listaDeAtracciones.get(index));
					//System.out.println(listaDeAtracciones.get(index).getNombre());
					//System.out.println(nombres.get(index).toString());
				}
				/*
				for(Producto p : atraccionesAcontener) {
					System.out.println(p.getNombre());
				}*/
				
				if(tipoDePromocion.toString().equals("absoluta")) {
					this.listaDePromociones.add(new absoluta(constante,tipoAtracc,nombre,atraccionesAcontener,costoOInteres));
				}
				if(tipoDePromocion.toString().equals("axb")) {
					this.listaDePromociones.add(new AxB(constante,tipoAtracc,nombre,atraccionesAcontener));
				}
				if(tipoDePromocion.toString().equals("porcentual")) {
					this.listaDePromociones.add(new Porcentual(constante,tipoAtracc,nombre,atraccionesAcontener,costoOInteres));
				}
			}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				sc.close();
			}
			
		
	} 
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
		
	//Mapa de Aventuras	
		mapaPromocionesAventuras = new TreeMap<Integer, PriorityQueue<Promocion>>();
		
		for (Producto p : listaDePromociones) {
			if (p.tipoDeAtraccion == tipoDeAtraccion.AVENTURA) {

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
		//System.out.println(mapaPromocionesAventuras);
		
		//Mapa de Paisaje	
				mapaPromocionesPaisaje = new TreeMap<Integer, PriorityQueue<Promocion>>();
				
				for (Producto p : listaDePromociones) {
					if (p.tipoDeAtraccion == tipoDeAtraccion.PAISAJE) {

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
					if (p.tipoDeAtraccion == tipoDeAtraccion.DEGUSTACION) {

						Integer key = (int) p.getPrecio();
						if (mapaPromocionesDegustacion.containsKey(key)) {
							mapaPromocionesDegustacion.get(key).offer((Promocion) p);
						} else {
							PriorityQueue<Promocion> cola = new PriorityQueue<Promocion>();
							cola.offer((Promocion) p);
							mapaPromocionesDegustacion.put(key, cola);
						}

					}
				}
		
	}

	
	
	//Recorre lista usuarios
	private void filtroSugerencias() {
		
		for(Usuario u: listaDeUsuarios) {
			
			// Primero entra en el mapa del tipo de atraccion preferida
			if (u.getPreferencia() == tipoDeAtraccion.AVENTURA) {
				
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
			
			
		
		
		
		// Primero entra en el mapa del tipo de atraccion preferida
		if (u.getPreferencia() == tipoDeAtraccion.DEGUSTACION) {
			
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
		
		
	
	}
	
	
	
	private boolean ofertar(Usuario u) {
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args) {
		
		
		agencia a1 = new agencia();
		a1.readData("usuarios.txt");
		a1.readData("atracciones.txt");
		a1.cargarPromocion("promociones.txt");


		//a1.mostrarNames();
		//a1.mostrarPromos();
		a1.mostrarPromos();
		for(Producto p : a1.listaDePromociones) {
			System.out.println(p.toString());
		}
		List<Producto> a2 = a1.listaDePromociones.get(0).getAtr();
	//	Producto a3 = a2.get(0);
	//	System.out.println(a3.getNombre());
		//System.out.println(a3.getNombre().equals(a1.listaDeAtracciones.get(0).getNombre()));
		//List<Producto> a2 = a1.listaDePromociones.get(0).getAtr();
		//Producto a3 = a2.get(0);
		//System.out.println(a3.getNombre().equals(a1.listaDeAtracciones.get(0).getNombre()));
		//a1.mostrarUsers();
		//a1.mostrarAtracciones(); 
		//a1.mostrarAtracciones(); 
		a1.mapasPorAtraccionPorPrecio();
	}


}
