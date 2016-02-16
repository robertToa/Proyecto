/*
 * ESCUELA POLITECNICA NACIONAL
 * COMPILADORES Y LENGUAJES
 * PROYECTO
 * Nombres: Roberto Toapanta, Bryan Jarrin.
 * GR1
 * Tema: Analizador Sintactico.
 */
package AnalizadorS;
import java.util.StringTokenizer;

import AnalizadorS.TokenClass;

public class Sintactico{
	TokenClass patron=new TokenClass();

	public boolean VariablesGlobales(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		boolean verificar=false;
		int i=0;
		try{
		while (st.hasMoreTokens()){	
			if(i==6){
				verificar=false;
				break;
			}
			for(i=0 ; i<6  ;i++){
				String palabra=st.nextToken();
				if(i==2 && palabra.equals(";")){
					i=5;
				}
				switch(i){
				case 0:
					if(!patron.PalabrasReservada1(palabra))
						i=6;
					break;
				case 1:
					if(!patron.Identificador(palabra))
						i=6;
					break;
				case 2:
					if(!palabra.equals("["))
						i=6;
					break;
				case 3:
					if(!patron.entero(palabra))
						i=6;
					break;
				case 4:
					if(!palabra.equals("]"))
						i=6;
					break;
				case 5:
					if(palabra.equals(";"))
						verificar=true;
					break;
				}
			}

		}}catch(Exception e){}return verificar;
	}	
	
	public boolean Declaracion(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		boolean verificar=false;
		int i=0;
		try{
		while (st.hasMoreTokens()){	
			for(i=0 ; i<5  ;i++){
				String palabra=st.nextToken();
				switch(i){
				case 0:
					if(!patron.PalabrasReservada1(palabra))
						i=5;
					break;
				case 1:
					if(!patron.Identificador(palabra))
						i=5;
					break;
				case 2:
					if(!palabra.equals("="))
						i=5;
					break;
				case 3:
					if(!patron.entero(palabra) && !patron.Identificador(palabra) )
						i=5;
					break;
				case 4:
					if(palabra.equals(";"))
						verificar=true;
					break;
				}
			}

		}}catch(Exception e){}return verificar;
	}
	public boolean Funcion(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		boolean verificar=false;
		int i=0;
		try{
		while (st.hasMoreTokens()){	
			if(i==5){
				verificar=false;
				break;
			}
			for(i=0 ; i<5  ;i++){
				String palabra=st.nextToken();
				if(i==3 && palabra.equals(")")){
					i=4;
				}
				switch(i){
				case 0:
					if(!patron.PalabrasReservada1(palabra))
						i=5;
					break;
				case 1:
					if(!patron.Identificador(palabra))
						i=5;
					break;
				case 2:
					if(!palabra.equals("("))
						i=5;
					break;
				case 3:
					boolean salir=false;
					do{
						salir=false;
						for(int j=0 ; j<2  ;j++){
							if(j!=0)
								palabra=st.nextToken();
							switch(j){
							case 0:
								if(!patron.PalabrasReservada1(palabra)){
									j=2; i=5;}
								break;
							case 1:
								if(!patron.Identificador(palabra)){
									j=2; i=5;}
								break;
							}
						}
						palabra=st.nextToken();
						if(palabra.equals(","))
						{salir=true;palabra=st.nextToken();}
						else i=4;	
					}while(salir==true);
				case 4:
					if(palabra.equals(")"))
						verificar=true;
					break;
				}
			}
		}}catch(Exception e){}return verificar;
	}	
	
	public boolean Expresiones(String texto){
		boolean verificar=false;
		for(int i=0;i<2;i++){
			switch(i){
			case 0:
				if(ExpAritmetica(texto)){
					i=2;verificar=true;}
				break;
			case 1:
				if(ExpLogica(texto))
					verificar=true;
				break;
			}
		}return verificar;
	}
	
	private boolean ExpAritmetica(String texto){
		int i=0;
		boolean verificar=false;
		StringTokenizer st = new StringTokenizer(texto);
		try{
		while(st.hasMoreTokens() && i<2){
		for(i=0;i<3;i++){
			String palabra=st.nextToken();
			if(palabra.equals(";") && i==1){
				i=2;
			}
			switch(i){
			case 0:
				if(!patron.entero(palabra) && !patron.Identificador(palabra))
					i=2;
				break;
			case 1:
				if(patron.OperadoresAsignacion(palabra))
					i=0;
				break;
			case 2:
				verificar=true;
			}
		}
		}
		}catch(Exception e){}
		return verificar;
	}
	
	public boolean Atribucion(String texto){
		boolean verificar=false;
		StringTokenizer st = new StringTokenizer(texto);
		int i=0;
		try{
		while (st.hasMoreTokens()){	
			if(i==6){
				verificar=false;
				break;
			}
			for(i=0 ; i<6 ;i++){
				String palabra=st.nextToken(" ");
				if(i==1 && palabra.equals("=")){
					i=4;
				}
				switch(i){
				case 0:
					if(!patron.Identificador(palabra))
						i=6;
					break;
				case 1:
					if(!palabra.equals("["))
						i=6;
					break;
				case 2:
					palabra+=st.nextToken("]");
					if(!Expresiones(palabra))
						i=6;
					break;
				case 3:
					if(!palabra.equals("]"))
						i=6;
					break;
				case 4:
					if(!palabra.equals("="))
						i=6;
					break;
				case 5:
					while(st.hasMoreTokens()){
						palabra+=" "+st.nextToken();
					}
					if(Expresiones(palabra))
						verificar=true;
					break;
				}
			}
		}}catch(Exception e){}
		return verificar;
	}
	
	private boolean ExpLogica(String texto){
		boolean verificar=false;
		StringTokenizer st = new StringTokenizer(texto);
		int i=0;
		try{
		while (st.hasMoreTokens()){	
			if(i==3){
				verificar=false;
				break;
			}
			for(i=0 ; i<3  ;i++){
				String palabra=st.nextToken();
				switch(i){
				case 0:
					if(!patron.Identificador(palabra) && !patron.Literales(palabra))
						i=3;
					break;
				case 1:
					if(!patron.OperadorCompuesto(palabra))
						i=3;
					break;
				case 2:
					if(patron.Identificador(palabra) || patron.Literales(palabra))
						verificar=true;
					break;
				}
			}

		}}catch(Exception e){}
		return verificar;
	}
	
	private boolean CFlujo1(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		boolean verificar=false;
		int i=0;
		try{
		while (st.hasMoreTokens()){
			if(i==6)
				verificar=false;
			for(i=0 ; i<6 ;i++){
				String palabra=st.nextToken(" ");
				switch(i){
				case 0:
					if(!palabra.equals("if"))
						i=6;
					break;
				case 1:
					if(!palabra.equals("("))
						{i=6;
					break;}
					else i=2;
				case 2:
					palabra=st.nextToken(")");
					if(!Expresiones(palabra)){
						i=6;
					}
					break;
				case 3:
					if(!palabra.equals(")"))
						i=6;
					break;
				case 4:
					if(!palabra.equals("{"))
						i=6;
					else{
						palabra=st.nextToken("}");
					}
					break;
				case 5:
					if(palabra.equals("}"))
						verificar=true;
					break;
				}
			}
		}}catch(Exception e){}
		return verificar;
	}
	
	private boolean CFlujo2(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		boolean verificar=false;
		String veri="";
		int i=0;
		try{
		while (st.hasMoreTokens()){
			if(i==5)
				verificar=false;
			for(i=0 ; i<5 ;i++){
				String palabra=st.nextToken(" ");
				switch(i){
				case 0:
					while(!veri.equals("else")){
						palabra+=" "+veri;
						veri=st.nextToken();
					}
					if(!CFlujo1(palabra))
						{i=5;break; }
					else i=1;
				case 1:
					if(!veri.equals("else"))
						i=5;
					break;
				case 2:
					if(!palabra.equals("{"))
						i=5;
					break;
				case 3:
					palabra=st.nextToken("}");
					break;
				case 4:
					if(palabra.equals("}"))
						verificar=true;
				}
			}
		}}catch(Exception e){}
		return verificar;
	}

	private boolean CFlujo3(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		boolean verificar=false;
		int i=0;
		try{
		while (st.hasMoreTokens()){
			if(i==6)
				verificar=false;
			for(i=0 ; i<6 ;i++){
				String palabra=st.nextToken(" ");
				switch(i){
				case 0:
					if(!palabra.equals("while"))
						i=6;
					break;
				case 1:
					if(!palabra.equals("("))
						{i=6;
					break;}
					else i=2;
				case 2:
					palabra=st.nextToken(")");
					if(!Expresiones(palabra))
						i=6;
					break;
				case 3:
					if(!palabra.equals(")"))
						i=6;
					break;
				case 4:
					if(!palabra.equals("do"))
						i=6;
					break;
				case 5:
					//if(Comando(palabra))
						verificar=true;
					break;
				}
			}
		}}catch(Exception e){}
		return verificar;
	}
	
	private boolean CFlujo4(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		boolean verificar=false;
		int i=0;
		try{
		while (st.hasMoreTokens()){
			if(i==6)
				verificar=false;
			for(i=0 ; i<6 ;i++){
				String palabra=st.nextToken(" ");
				switch(i){
				case 0:
					if(!palabra.equals("do"))
						i=6;
					break;
				case 1:
					//
					break;
				case 2:
					if(!palabra.equals("while"))
						i=6;
					break;
				case 3:
					if(!palabra.equals("("))
						{i=6;
					break;}
					else i=4;
				case 4:
					palabra=st.nextToken(")");
					if(!Expresiones(palabra))
						i=6;
					break;
				case 5:
					if(palabra.equals(")"))
						verificar=true;
					break;
				}
			}
		}}catch(Exception e){}
		return verificar;
	}

	public boolean ControlFlujo(String texto){
		boolean verificar=false;
		for(int i=0;i<4;i++){
			switch(i){
			case 0:
				if(CFlujo2(texto)){				
					i=4;verificar=true;}
				break;
			case 1:
				if(CFlujo1(texto)){
					i=4;verificar=true;}
				break;
			case 2:
				if(CFlujo3(texto)){
					i=4;verificar=true;}
				break;
			case 3:
				if(CFlujo4(texto)){
					verificar=true;
				}
				break;
			}
		}return verificar;
	}

	private boolean LLamadaFunciones(String texto){
		boolean verificar=false;
		StringTokenizer st = new StringTokenizer(texto);
		int i=0;
		try{
		while (st.hasMoreTokens()){	
			if(i==4){
				verificar=false;
				break;
			}
			for(i=0 ; i<4  ;i++){
				String palabra=st.nextToken();
				switch(i){
				case 0:
					if(!patron.Identificador(palabra))
						i=4;
					break;
				case 1:
					if(!palabra.equals("("))
						i=4;
					break;
				case 2:
					while(true){
					if(!patron.Literales(palabra) && !patron.Identificador(palabra))
						i=4;
					palabra=st.nextToken();
					if(!palabra.equals(",") || i==4) 
						break;
					palabra=st.nextToken();
					}
				case 3:
					i++;
					if(palabra.equals(")")&& i==3)
						verificar=true;
					break;
				}
			}

		}}catch(Exception e){}
		return verificar;
	}

	private boolean OperESR(String texto){
		boolean verificar=false;
		StringTokenizer st = new StringTokenizer(texto);
		int i=0;
		try{
		while (st.hasMoreTokens()){
			if(i==2)
				verificar=false;
			for(i=0 ; i<2  ;i++){
				String palabra=st.nextToken();
				switch(i){
				case 0:
					if(!patron.PalabrasReservadas2(palabra))
						i=4;
					break;
				case 1:
					if(patron.Identificador(palabra))
						verificar=true;
					break;
				}
			}

		}}catch(Exception e){}
		return verificar;
	}
	
	public boolean ComnadosSimples(String texto){
		boolean verificar=false;
		for(int i=0;i<6;i++){
			switch(i){
			case 0:
				if(ControlFlujo(texto)){
					i=6;verificar=true;}
				break;
			case 1:
				if(Atribucion(texto)){
					i=6;verificar=true;}
				break;
			case 2:
				if(LLamadaFunciones(texto)){
					i=6;verificar=true;}
				break;
			case 3:
				if(OperESR(texto)){
					i=6;verificar=true;}
				break;
			case 4:
				if(Declaracion(texto)){
					i=6;verificar=true;}				
			case 5:
				if(texto.equals(""))
					verificar=true;
				break;
			}
		}return verificar;
	}

	public boolean BloqueComandos(String texto){
		return false;
	}
	
}