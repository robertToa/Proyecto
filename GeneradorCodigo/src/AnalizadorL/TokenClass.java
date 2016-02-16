/*
 * ESCUELA POLITECNICA NACIONAL
 * COMPILADORES Y LENGUAJES
 * PROYECTO
 * Nombres: Roberto Toapanta, Bryan Jarrin.
 * GR!
 * Tema: Analizaodr Lexico.
 */
package AnalizadorL;
import java.util.regex.Matcher;
import AnalizadorL.TokenClass;
import java.util.regex.Pattern;
public class TokenClass{
	
	private boolean verificar;
	public String literal;
	public boolean PalabraReservada(String texto){
		Pattern pat = Pattern.compile("int|float|bool|char|string|if|then|else|while|do|input|output|return");
		Matcher mat = pat.matcher(texto);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean Identificador(String texto){
		Pattern pat = Pattern.compile("[a-z][a-zA-Z0-9]*");
		Matcher mat = pat.matcher(texto);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean OperadorCompuesto(String texto){
		Pattern pat = Pattern.compile("==|>=|<=|!=|&&");
		Matcher mat = pat.matcher(texto);
		if (mat.matches()||texto.equals("||")) {
			return true;
		} else {
			return false;
		}
	}
	public boolean CaracterEspecial(String texto){
		Pattern pat = Pattern.compile(",|;|:|-|/|>|<|=|!|&|}|]");
		Matcher mat = pat.matcher(texto);
		if (mat.matches()||texto.equals("+")||texto.equals("(")||texto.equals(")")||texto.equals("*")||texto.equals("$")||texto.equals("{")||texto.equals("[")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean entero(String texto){
		Pattern pat = Pattern.compile("(-)?[0-9]+");
		Matcher mat = pat.matcher(texto);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean Ffloat(String texto){
		Pattern pat = Pattern.compile("[0-9]+(.)[0-9]+");
		Matcher mat = pat.matcher(texto);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean Fbool(String texto){
		Pattern pat = Pattern.compile("true|false");
		Matcher mat = pat.matcher(texto);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean Fchar(String texto){
		Pattern pat = Pattern.compile("'([^s]|s)'");
		Matcher mat = pat.matcher(texto);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean Fstring(String texto){
		Pattern pat = Pattern.compile("“([^s]|s)+”"); 
		Matcher mat = pat.matcher(texto);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}
	public boolean Literales(String texto){
		verificar=false;
		for(int i=0;i<5;i++){
			switch(i){
			case 0:
				if(verificar!=entero(texto)){
					literal="entero";
					i=7;
					verificar=true;
				}
				break;
			case 1:
				if(verificar!=Ffloat(texto)){
					literal="float";
					i=7;
					verificar=true;
				}
				break;
			case 2:
				if(verificar!=Fbool(texto)){
					literal="bool";
					i=7;
					verificar=true;
				}
				break;
			case 3:
				if(verificar!=Fchar(texto)){
					literal="char";
					i=7;
					verificar=true;
				}
				break;
			case 4:
				if(verificar!=Fstring(texto)){
					literal="string";
					i=7;
					verificar=true;
				}
				break;
			}
		}
		return verificar;
	}
}

