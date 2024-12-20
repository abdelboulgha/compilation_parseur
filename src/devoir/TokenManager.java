package devoir;

public class TokenManager {
    private String[] tokens; // Les mots ou tokens de l'entrée
    private int positionCourante; // La position actuelle dans la liste de tokens

    public TokenManager(String chaine) {
        // Diviser la chaîne d'entrée en tokens séparés par des espaces
        this.tokens = chaine.split(" ");
        this.positionCourante = 0;
    }

    // Méthode pour obtenir le prochain token
    public String suivant() {
        if (positionCourante < tokens.length) {
            return tokens[positionCourante++];
        }
        return ""; // Retourne une chaîne vide lorsqu'il n'y a plus de tokens
    }
}
