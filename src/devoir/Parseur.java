package devoir;

public class Parseur {
    private String tokenCourant;
    private TokenManager tm;
    private boolean estPluriel; // Indique si le sujet est au pluriel

    public Parseur(TokenManager tm) {
        this.tm = tm;
        avancer();
    }

    // Avance au token suivant
    public void avancer() {
        tokenCourant = tm.suivant();
    }

    // Consomme le token courant s'il correspond au token attendu
    public void consommer(String attendu) {
        if (tokenCourant.equals(attendu)) {
            avancer();
        } else {
            throw new RuntimeException("Erreur : attendu '" + attendu + "' mais trouvé '" + tokenCourant + "'");
        }
    }

    // Règle de l'axiome : S → Sujet Verbe Complément
    public void axiome() {
        sujet();

        // Verbe optionnel
        if (tokenCourant.equals("mange") || tokenCourant.equals("mangent")) {
            verbe();
        }

        // Complément optionnel
        if (!tokenCourant.isEmpty()) {
            complement();
        }
    }

    // Règle : Sujet → Article Nom | Nom
    public void sujet() {
        if (tokenCourant.equals("le") || tokenCourant.equals("la") || tokenCourant.equals("les")
                || tokenCourant.equals("une") || tokenCourant.equals("un") || tokenCourant.equals("des")) {
            article();
            nom();
        } else if (tokenCourant.equals("souris") || tokenCourant.equals("fromage")) {
            nom();
        } else {
            throw new RuntimeException("Erreur : sujet invalide '" + tokenCourant + "'");
        }
    }

    // Règle : Verbe → "mange" | "mangent"
    public void verbe() {
        if (estPluriel && tokenCourant.equals("mangent")) {
            consommer("mangent");
        } else if (!estPluriel && tokenCourant.equals("mange")) {
            consommer("mange");
        } else {
            throw new RuntimeException("Erreur : verbe invalide ou désaccord avec le sujet '" + tokenCourant + "'");
        }
    }

    // Règle : Complément → Article Nom | Nom
    public void complement() {
        if (tokenCourant.equals("le") || tokenCourant.equals("la") || tokenCourant.equals("les")
                || tokenCourant.equals("une") || tokenCourant.equals("un") || tokenCourant.equals("des")) {
            article();
            nom();
        } else if (tokenCourant.equals("souris") || tokenCourant.equals("fromage")) {
            nom();
        } else {
            throw new RuntimeException("Erreur : complément invalide '" + tokenCourant + "'");
        }
    }

    // Règle : Article → "le" | "la" | "les" | "une" | "un" | "des"
    public void article() {
        if (tokenCourant.equals("le") || tokenCourant.equals("la") || tokenCourant.equals("les")
                || tokenCourant.equals("une") || tokenCourant.equals("un") || tokenCourant.equals("des")) {
            // Déterminer si l'article indique un pluriel
            estPluriel = tokenCourant.equals("les") || tokenCourant.equals("des");
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : article invalide '" + tokenCourant + "'");
        }
    }

    // Règle : Nom → "souris" | "fromage"
    public void nom() {
        if (tokenCourant.equals("souris") || tokenCourant.equals("fromage")) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : nom invalide '" + tokenCourant + "'");
        }
    }

    // Parse l'entrée
    public void parse() {
        axiome();
        if (!tokenCourant.isEmpty()) {
            throw new RuntimeException("Erreur : entrée restante '" + tokenCourant + "'");
        }
    }
}
