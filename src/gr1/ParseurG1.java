package gr1;

public class ParseurG1 {
private TokenManager tm;
private char tc; //token currant

private void avancer () {
	tc=tm.suivant();
}
private void consommer(char attendu) {
	if (tc==attendu)
		avancer();
	else throw new RuntimeException("Erreur : attendu " +attendu +"mais "+ tc + " a ete trouvé");
}
	public ParseurG1(TokenManager tm) {
		this.tm=tm;
		avancer();
	}
//s-> BD
	private void S() {
		B();
		D();
	}
	private void B() {
		if (tc == 'b') {
			consommer('b');
			B();
		}
		else if (tc=='c')
			consommer ('c');
		else 
			throw new RuntimeException("Erreur :"+ tc );
	}
	public void D() {
		consommer('d');
		consommer('e');
	}
	public void parse() {
		S();
		if(tc!='#') {
			throw new RuntimeException("Erreur :"+ tc );
		}
	}

}
