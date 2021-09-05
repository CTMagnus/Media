package agencia;


import java.io.*;
import java.util.*;


import agencia.Producto.*;


public class agencia {
	protected List<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
	protected List<Producto> listaDeAtracciones = new ArrayList<Producto>();
	protected List<Producto> listaDePromociones = new ArrayList<Producto>();
	
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
		
	}
	
	@SuppressWarnings("unused")
	private void cargarPromocion(String txt) {
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			archivo = new File(txt);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea ;
			while((linea = br.readLine())!=null) {
				
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

	public static void main(String[] args) {
		agencia a1 = new agencia();
		a1.readData("usuarios.txt");
		a1.readData("atracciones.txt");
		a1.mostrarUsers();
		a1.mostrarAtracciones();
	}

}
