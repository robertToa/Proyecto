package Principal;

import AnalizadorL.analizadorLex;
import AnalizadorS.PrincipalSin;
import generCod.GenCod;
public class VenPrincipal {
	public VenPrincipal() {
		analizadorLex lexico=new analizadorLex();
		PrincipalSin sintactico=new PrincipalSin();
		GenCod generadorCodio=new GenCod(); 
	}	
	
	public static void main (String [] args ){
		new VenPrincipal();
	}
}