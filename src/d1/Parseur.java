package d1;
import java.util.Arrays;
import java.util.List;

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
        tokenCourant = tm.suivant().toLowerCase();  // Convertir en minuscules
    }

    // Consomme le token courant s'il correspond au token attendu
    public void consommer(String attendu) {
        if (tokenCourant.equals(attendu)) {
            avancer();
        } else {
            throw new RuntimeException("Erreur : attendu '" + attendu + "' mais trouvé '" + tokenCourant + "'");
        }
    }

    // Règle de l'axiome : S → Sujet Verbe [Complément]
    public void axiome() {
        if (estAdverbeCirc(tokenCourant)) {
            adverbeCirc();  // Gérer les adverbes circonstanciels au début
        }
        sujet();
        verbe();
        if (!tokenCourant.isEmpty() && !estVirgule(tokenCourant)) {  // Ignorer les virgules
            complement();
        }
    }

    // Règle : Sujet → Article Nom [Adjectif] | Pronom | Complément circonstanciel
    public void sujet() {
        if (estPronom(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            if (estArticle(tokenCourant) || estDeterminant(tokenCourant) || tokenCourant.equals("l'")) {
                // Gérer l'apostrophe pour "l'"
                article();
                nom();
            } else if (estNom(tokenCourant)) {
                nom();
            } else if (estAdverbeCirc(tokenCourant)) {
                adverbeCirc();
                nom(); // Exemple : "chaque matin le téléphone"
            } else {
                throw new RuntimeException("Erreur : sujet invalide '" + tokenCourant + "'");
            }
            if (estAdjectif(tokenCourant)) {
                adjectif();
            }
        }
    }

    // Règle : Verbe → Liste de verbes connus
    public void verbe() {
        if (estVerbe(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : verbe invalide '" + tokenCourant + "'");
        }
    }

    // Règle : Complément → Article Nom [Adjectif] | Nom [Adjectif] | Complément circonstanciel | [Conjonction + Complément]
    public void complement() {
        if (estComplementCirc(tokenCourant)) {
            while (estComplementCirc(tokenCourant)) {
                consommer(tokenCourant);
            }
        } else {
            if (estArticle(tokenCourant) || estDeterminant(tokenCourant)) {
                article();
                nom();
            } else if (estNom(tokenCourant)) {
                nom();
            } else {
                // Complément est optionnel après le verbe
                return;  // Aucune erreur si le complément est manquant
            }
            if (estAdjectif(tokenCourant)) {
                adjectif();
            }
        }

        // Gestion des conjonctions pour des compléments complexes
        if (estConjonction(tokenCourant)) {
            consommer(tokenCourant);
            complement();
        }
    }

    // Règle : Article → Liste d'articles connus
    public void article() {
        if (estArticle(tokenCourant) || estDeterminant(tokenCourant) || tokenCourant.equals("l'")) {
            estPluriel = tokenCourant.equals("les") || tokenCourant.equals("des");
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : article invalide '" + tokenCourant + "'");
        }
    }

    // Règle : Nom → Liste de noms connus
    public void nom() {
        if (estNom(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : nom invalide '" + tokenCourant + "'");
        }
    }

    // Règle : Adjectif → Liste d'adjectifs connus
    public void adjectif() {
        if (estAdjectif(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : adjectif invalide '" + tokenCourant + "'");
        }
    }

    // Règle : Adverbe circonstanciel → Liste d'adverbes circonstanciels
    public void adverbeCirc() {
        if (estAdverbeCirc(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : adverbe circonstanciel invalide '" + tokenCourant + "'");
        }
    }

    // Méthodes utilitaires pour vérifier les catégories
    private boolean estArticle(String token) {
        return List.of("le", "la", "les", "une", "un", "des").contains(token);
    }

    private boolean estNom(String token) {
        return List.of("souris", "fromage", "chat", "chien", "téléphone", "matin", "ordinateur", "livre", "enfant", "jardin", "chiens", "enfants", "pomme", "ordinateurs", "morceau").contains(token);
    }

    private boolean estAdjectif(String token) {
        return List.of("petit", "grand", "rouge", "joli", "ancien", "bleu", "intelligent", "ancien").contains(token);
    }

    private boolean estVerbe(String token) {
        return List.of("mange", "mangent", "vois", "voit", "voient", "charge", "chargez", "sonne", "lit", "aime", "jouent", "joue", "aimons").contains(token);
    }

    private boolean estPronom(String token) {
        return List.of("je", "tu", "il", "elle", "nous", "vous", "ils", "elles").contains(token);
    }

    private boolean estDeterminant(String token) {
        return List.of("mon", "ton", "son", "notre", "votre", "leur").contains(token);
    }

    private boolean estComplementCirc(String token) {
        return List.of("chaque", "matin", "soir", "à", "6", "heures", "hier", "aujourd'hui", "demain", "dans", "le", "jardin","au").contains(token);
    }

    private boolean estAdverbeCirc(String token) {
        return List.of("aujourd'hui", "demain", "hier", "chaque", "chaque matin").contains(token);
    }

    private boolean estConjonction(String token) {
        return List.of("avec", "et").contains(token);
    }

    private boolean estVirgule(String token) {
        return token.equals(",");
    }

    // Parse l'entrée
    public void parse() {
        axiome();
        if (!tokenCourant.isEmpty()) {
            throw new RuntimeException("Erreur : entrée restante '" + tokenCourant + "'");
        }
    }
}
