/*
 * ESCUELA POLITECNICA NACIONAL
 * COMPILADORES Y LENGUAJES
 * PROYECTO
 * Nombres: Roberto Toapanta, Bryan Jarrin.
 * GR!
 * Tema: Analizaodr Lexico.
 */
package AnalizadorL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import AnalizadorL.TokenClass;
import java.io.*;
import javax.swing.JFrame;

public class analizadorLex{	
	TokenClass patron=new TokenClass();
	private boolean verificacion;
	private String palabra;	
	public static ArrayList <String> cadena =new ArrayList<String>();
	private boolean ControlLinea,ControlParrafo;
	public static int contador=0;
	private int a=0;

	public analizadorLex(){
		lecturaArchivo();
	}

	private void lecturaArchivo(){
		try{
			FileReader fileName=new FileReader("texEntrada.txt");
			BufferedReader br=new BufferedReader(fileName);
			String cadena;
			ControlParrafo=false;
			while(((cadena=br.readLine())!=null) && contador<50){
				contador++;
				ControlLinea=false;
				for(int i=0;i<(cadena.length()-1) && ControlParrafo==true ;i++){//para encontrar el fin de los comenatrios multiples
					if(cadena.substring(i, i+2).equals("*/") ){
						ControlParrafo=false;
						cadena=cadena.substring(i+2 , cadena.length()-1);
						i=cadena.length();
					}
				}
				StringTokenizer st = new StringTokenizer(cadena);
				while (st.hasMoreTokens() && ControlLinea==false && ControlParrafo==false){
					palabra=st.nextToken();
					for(int i=0;i<(palabra.length()-1) && palabra.length()>2;i++){//para controlar de los comentarios
						if(palabra.substring(i, i+2).equals("//")){//para comentarios de una linea
							palabra=palabra.substring(0, i);
							i=palabra.length()-1;
							ControlLinea=true;
						}else{
							if(palabra.substring(i, i+2).equals("/*")){ //para comentarios de varias lineas
								palabra=palabra.substring(0, i);
								i=palabra.length()-1;
								ControlParrafo=true;
							}}
					}
					if(!palabra.equals("")){
						VerificarTokenClass(palabra);
					}
				}
			}
			
			CrearArchivoSalida();
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void CrearArchivoSalida(){
		try{
			FileWriter fileNa=new FileWriter("salidaT"+".csv");
			BufferedWriter br=new BufferedWriter(fileNa);
			PrintWriter wr=new PrintWriter(br);
			wr.write("TOKEN CLASS;PALABRA;LINEA DE ERROR\n");
			for(int i=0;i<cadena.size();i++){
				wr.write(cadena.get(i));
				wr.write("\n");
			}
			wr.close();
			br.close();
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void VerificarTokenClass(String texto){
		verificacion=false;
		for(int i=0 ; i<6 ;i++){
			switch(i){
			case 0:
				if(verificacion!=patron.PalabraReservada(texto)){
					cadena.add("Palabra reservada;"+texto+";"+contador);
					i=7;
				}
				break;
			case 1:
				if(verificacion!=patron.Literales(texto)){
					cadena.add("Literales "+patron.literal+";"+texto+";"+contador);
					i=7;
				}
				break;

			case 2:
				if(verificacion!=patron.OperadorCompuesto(texto)){
					cadena.add("Operador Compuesto;"+texto+";"+contador);
					i=7;
				}
				break;
			case 3:
				if(verificacion!=patron.CaracterEspecial(texto)){
					cadena.add("Caracter Especial;"+texto+";"+contador);
					i=7;
				}
				break;
			case 4:
				if(verificacion!=patron.Identificador(texto)){
					cadena.add("Identificador;"+texto+";"+contador);
					i=7;
				}
				break;
			default:
				cadena.add("No Token Class;"+texto+";"+contador);
				break;
			}
		}
	}

	public static void main (String [] args ){
		new analizadorLex();
	}

}

