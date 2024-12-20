package Ex2;

public class Parseur {
private char tc;
private TokenManager tm; //Gestionnaire de l'entrée

public Parseur(TokenManager tm) {
	this.tm=tm;
	avancer();
}

public void avancer() {
	tc=tm.suivant();
}

public void consommer(char attendu) {
	if (tc==attendu ) {
		avancer();
	}
	else {
		throw new RuntimeException("Erreur : attendu '" + attendu + "' mais '" + tc + "' a été trouvé.");
	}
}
public void parseS() {
	parseB();
	parseC();
	parseD();
	}
public void parseB() {
    if (tc == 'b') {
        consommer('b');
        parseB(); 
    }
    // Si tc n'est pas 'b', cela correspond à B → ε (rien à faire)
}

public void parseC() {
    if (tc == 'c') {
        consommer('c');
        parseC(); 
    }
    // Si tc n'est pas 'c', cela correspond à C → ε (rien à faire)
}

public void parseD() {
    if (tc == 'd') {
        consommer('d');
        parseD();
    }
    // Si tc n'est pas 'd', cela correspond à D → ε (rien à faire)
}

public void parse() {
    parseS(); 
    if (tc != '\0') { 
        throw new RuntimeException("Erreur : caractère inattendu '" + tc + "' trouvé à la fin.");
    }
}
}

