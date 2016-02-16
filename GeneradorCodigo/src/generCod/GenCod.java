package generCod;
import java.io.BufferedReader;
import AnalizadorS.Sintactico;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import AnalizadorS.TokenClass;
public class GenCod{
	private TokenClass lex=new TokenClass();
	private Sintactico sint=new Sintactico();
	private ArrayList<String> lis=new ArrayList<String>();
	private ArrayList<String> eti=new ArrayList<String>();
	private int contEti=0,k=0; 
	String cadena,text,ver;
	FileReader fileNam;
	BufferedReader br;
	public  GenCod(){
			try{
				fileNam=new FileReader("texEntrada.txt");
				br=new BufferedReader(fileNam);
				while(((cadena=br.readLine())!=null)){
					StringTokenizer st = new StringTokenizer(cadena);
					
					try{
						while (st.hasMoreTokens()){
							text="";
							ver=st.nextToken();
							if(lex.PalabraReservada(ver)){
								try{
									while (st.hasMoreTokens()){
										text+=st.nextToken()+" ";}}catch(Exception e){}
								VerificacionPrincipal(ver+" "+text);
							}
							else{
								text=st.nextToken(";");
								VerificacionPrincipal(ver+" "+text+" ;");
							}
						}}catch(Exception e){}
				}
			}
			catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<eti.size();i++){
				StringTokenizer st = new StringTokenizer(eti.get(i));
				try{
					System.out.println("E"+i+": ");
					System.out.println("load r"+lis.indexOf("if"+i)+" => t4");
					while (st.hasMoreTokens()){
						text=st.nextToken(";");
						if(!text.equals(" ")){
							VerificacionPrincipal(text+" ;");
						}
					}System.out.println("jr t4");
					}catch(Exception e){}
			}
	}
	
	void VerificacionPrincipal(String texto){
		for(int i=0;i<5;i++){
			switch(i){
			case 0:
				if(sint.VariablesGlobales(texto)){
					StringTokenizer st = new StringTokenizer(texto);
					String palabra;
					palabra=st.nextToken();
					palabra=st.nextToken();
					lis.add(palabra);
					i=5;
				}
				break;
			case 1:
				if(sint.Declaracion(texto)){
					StringTokenizer st = new StringTokenizer(texto);
					Inicializacion(st.nextToken(";"));
					i=5;
				}					
				break;
			case 2:
				StringTokenizer st = new StringTokenizer(texto);
				if(sint.ControlFlujo(texto)){
					String ver=st.nextToken("}");
					ComandosdeControldeFlujo(ver);
					i=5;
				}
				break;
			case 3:
				if(sint.Atribucion(texto)){
					StringTokenizer st1 = new StringTokenizer(texto);
					if(st1.countTokens()==4)
						Declaracióndevariables(st1.nextToken(";"));
					else
						Expresionesaritméticas(st1.nextToken(";"));
					i=5;
				}
				break;				
			case 4:
				System.out.println("No se encuentra insertado esa sentencia para codig de maquina");
			}
		}
	}
	
	void Inicializacion(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		String palabra;
		palabra=st.nextToken();
		palabra="";
		try{
			while (st.hasMoreTokens()){	
				palabra +=(st.nextToken()+" ");
			}}catch(Exception e){}
		Declaracióndevariables(palabra);
	}
	
	void Declaracióndevariables(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		String palabra[]=new String[3];
		int i=0;
		try{
		while (st.hasMoreTokens()){	
			palabra[i]=st.nextToken();
			i++;
		}}catch(Exception e){}
		if(lis.indexOf(palabra[0])==-1)
			lis.add(palabra[0]);
		letraNum(palabra[2],"a0");
		System.out.println("store a0 => r" + lis.indexOf(palabra[0]));	
	}
	void Expresionesaritméticas(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		String palabra[]=new String[5];
		try{
			for(int i=0;i<5;i++)
				palabra[i]=st.nextToken();			
		}catch(Exception e){}
		letraNum(palabra[2],"t1");
		letraNum(palabra[4],"t2");
		Operaciones(palabra[3],"t2");
		try{
			while (st.hasMoreTokens()){	
				palabra[3]=st.nextToken();
				palabra[4]=st.nextToken();
				letraNum(palabra[4],"t1");
				Operaciones(palabra[3],"t3");
			}
		}catch(Exception e){}
		System.out.println("Load t3 => a0");
		System.out.println("store a0 => r" + lis.indexOf(palabra[0]));
	}
	
	void ComandosdeControldeFlujo(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		String palabra=st.nextToken();
		switch(palabra){
		case "if":
			try{
				palabra="";
				while (st.hasMoreTokens()){	
					palabra+=st.nextToken()+" ";
				}
				Funif(palabra);
			}catch(Exception e){}
			break;
		case "for":
			try{
				palabra="";
				while (st.hasMoreTokens()){	
					palabra+=st.nextToken()+" ";
				}
				//Funfor();
			}catch(Exception e){}
			break;
		case "do":
			try{
				palabra="";
				while (st.hasMoreTokens()){	
					palabra+=st.nextToken()+" ";
				}
				//Fundo();
			}catch(Exception e){}
			break;
		case "while":
			try{
				palabra="";
				while (st.hasMoreTokens()){	
					palabra+=st.nextToken()+" ";
				}
				//Funwhile();
			}catch(Exception e){}
			break;
		}
	}
	
	void letraNum(String tex,String da){
		if(tex.matches("[0-9]*"))
			System.out.println("LoadI "+tex+" => "+da);
		else
			System.out.println("Load r"+lis.indexOf(tex)+" => "+da);
	}
	void Operaciones(String text, String da){
		if(text.equals("+"))
			System.out.println("ADD t1, "+da+" => t3");
		if(text.equals("-"))
			System.out.println("SUB t1, "+da+" => t3");
		if(text.equals("*"))
			System.out.println("MULT t1, "+da+" => t3");
		if(text.equals("/"))
			System.out.println("DIV t1, "+da+" => t3");
	}
	
	void Funif(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		String palabra, ver;
		palabra=st.nextToken();
		palabra=st.nextToken(")");
		ExpresLogica(palabra);
		try{
			palabra=st.nextToken(" ");
			palabra=st.nextToken(); palabra="";
			while (st.hasMoreTokens()){	
				palabra+=st.nextToken()+" ";
			}
		}catch(Exception e){}
		eti.add(palabra);
		lis.add("if"+(eti.size()-1));
	}
	
	void ExpresLogica(String texto){
		StringTokenizer st = new StringTokenizer(texto);
		String palabra[]=new String[3];
		for(int i=0;i<3;i++){
			palabra[i]=st.nextToken();
		}
		letraNum(palabra[0],"t1");
		letraNum(palabra[2],"t2");
		VericaLog(palabra[1]);
	}
	
	void VericaLog(String texto){
		switch(texto){
		case "==": System.out.println("seq $t3, $ti, $t2");
			break;
		case "<=": System.out.println("sle $t3, $ti, $t2");
			break;
		case ">=": System.out.println("sge $t3, $ti, $t2");
			break;
		case "<": System.out.println("slt $t3, $ti, $t2");
			break;
		case ">": System.out.println("sgt $t3, $ti, $t2");
			break;
		case "!=": System.out.println("sne $t3, $ti, $t2");
			break;
		}
		System.out.println("beq $t3, 1, E"+contEti);
		contEti++;
		System.out.println("beq $t3, 0, E"+contEti);
		contEti++;
	}
	public static void main (String [] args ){
		new GenCod();
	}
}