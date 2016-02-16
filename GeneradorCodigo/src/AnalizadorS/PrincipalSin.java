package AnalizadorS;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import javax.swing.*;
import AnalizadorS.Sintactico;
import AnalizadorS.TokenClass;

public class PrincipalSin {
	private int contador=0;
	private TokenClass lex=new TokenClass();
	private Sintactico tex=new Sintactico();
	public PrincipalSin() {
		lecturaArchivo();
	}	

	private void lecturaArchivo(){
		try{
			FileReader fileNam=new FileReader("texEntrada.txt");
			BufferedReader br=new BufferedReader(fileNam);
			String cadena,text;
			while(((cadena=br.readLine())!=null) && contador<50){
				StringTokenizer st = new StringTokenizer(cadena);
				contador++;
				text="";
				try{
					while (st.hasMoreTokens()){
						if(lex.PalabraReservada(st.nextToken())){
							text+=st.nextToken();
						}
						else
							text=st.nextToken(";");
						AnalizadorSintactico(text+" ;" ,contador);
					}}catch(Exception e){}
			}
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void AnalizadorSintactico(String texto, int a){
		for(int i=0;i<7;i++){
			switch(i){
			case 0:
				if(tex.Funcion(texto))
					i=7;
				break;
			case 1:
				if(tex.VariablesGlobales(texto))
					i=7;
				break;
			case 2:
				if(tex.ComnadosSimples(texto))
					i=7;
				break;
			case 3:
				if(tex.Expresiones(texto))
					i=7;
				break;
			case 4:
				if(tex.ControlFlujo(texto))
					i=7;
				break;
			case 5:
				//if(tex.BloqueComandos(texto))
					//i=7;
				break;
			case 6:
				System.out.println("Error en la linea: "+a);
				System.out.println(texto);
				break;
			}
		}
	}

	public static void main (String [] args ){
		new PrincipalSin();
	}
}