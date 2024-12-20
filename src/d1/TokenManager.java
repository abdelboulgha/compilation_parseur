package d1;

public class TokenManager {
    private String[] tokens;
    private int positionCourante;

    public TokenManager(String chaine) {
        this.tokens = chaine.toLowerCase().replaceAll(",", "").split(" ");
        this.positionCourante = 0;
    }

    public String suivant() {
        if (positionCourante < tokens.length) {
            return tokens[positionCourante++];
        }
        return "";
    }
}