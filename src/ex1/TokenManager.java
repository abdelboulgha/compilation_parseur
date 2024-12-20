package ex1;

public class TokenManager {
    private String chaineEntree; // L'entrée à analyser
    private int positionCourante; // Position actuelle dans l'entrée

    public TokenManager(String chaine) {
        this.chaineEntree = chaine;
        this.positionCourante = 0; // Initialisation au début de la chaîne
    }

    public char suivant() {
        // Retourne le prochain caractère ou un marqueur de fin
        if (positionCourante < chaineEntree.length()) {
            return chaineEntree.charAt(positionCourante++);
        }
        return '\0'; // Caractère spécial pour indiquer la fin de l'entrée
    }
}
