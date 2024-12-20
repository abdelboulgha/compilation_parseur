package ex1;

public class Parseur {
    private char tokenCourant; // Le caractère en cours d'analyse
    private TokenManager tm; // Gestionnaire de l'entrée

    public Parseur(TokenManager tm) {
        this.tm = tm;
        avancer(); // Initialiser le premier caractère
    }

    private void avancer() {
        // Passe au caractère suivant
        tokenCourant = tm.suivant();
    }

    private void consommer(char attendu) {
        // Vérifie si le caractère courant correspond à celui attendu
        if (tokenCourant == attendu) {
            avancer();
        } else {
            throw new RuntimeException("Erreur : attendu '" + attendu + "' mais '" + tokenCourant + "' a été trouvé.");
        }
    }

    private boolean parseE() {
        // Analyse récursive suivant les règles
        if (tokenCourant == '+') {
            consommer('+');
            return parseE() && parseE();
        } else if (tokenCourant == '-') {
            consommer('-');
            return parseE() && parseE();
        } else if (tokenCourant == '*') {
            consommer('*');
            return parseE() && parseE();
        } else if (tokenCourant == '/') {
            consommer('/');
            return parseE() && parseE();
        } else if (tokenCourant == 'b' || tokenCourant == 'c' || tokenCourant == 'd') {
            avancer(); // Consomme 'b', 'c', ou 'd'
            return true;
        } else if (tokenCourant == '\0') {
            // Fin de l'entrée (λ est valide)
            return true;
        }

        // Si aucun des cas ne correspond
        return false;
    }

    public boolean parse() {
        // Démarre l'analyse et vérifie que toute l'entrée est consommée
        return parseE() && tokenCourant == '\0';
    }

}
