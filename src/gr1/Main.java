package gr1;

public class Main {

	public static void main(String[] args) {
		String ch="bbcde";
		//lexeur
		
		TokenManager tm= new TokenManager(ch);
		System.out.println(tm.suivant());
		System.out.println(tm.suivant());
		System.out.println(tm.suivant());
		System.out.println(tm.suivant());
		System.out.println(tm.suivant());
		System.out.println(tm.suivant());
		System.out.println(tm.suivant());
		System.out.println(tm.suivant());
		//analyseur syntaxique
		ParseurG1 parseur = new ParseurG1(tm);
		try {
		parseur.parse();
System.out.println(ch + " est valide");
	}
catch (RuntimeException exp) {
	System.out.println(ch + " n'est pas valide");
}
}
}