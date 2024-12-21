package d1;
import java.util.Arrays;
import java.util.List;

public class Parseur {
    private String tokenCourant;
    private TokenManager tm;
    private boolean estPluriel;

    public Parseur(TokenManager tm) {
        this.tm = tm;
        avancer();
    }

    
    public void avancer() {
        tokenCourant = tm.suivant().toLowerCase(); 
    }

    
    public void consommer(String attendu) {
        if (tokenCourant.equals(attendu)) {
            avancer();
        } else {
            throw new RuntimeException("Erreur : attendu '" + attendu + "' mais trouvé '" + tokenCourant + "'");
        }
    }

    
    public void axiome() {
        if (estAdverbeCirc(tokenCourant)) {
            adverbeCirc();  
        }
        sujet();
        verbe();
        if (!tokenCourant.isEmpty() && !estVirgule(tokenCourant)) {  
            complement();
        }
    }

    public void sujet() {
        if (estPronom(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            if (estArticle(tokenCourant) || estDeterminant(tokenCourant) || tokenCourant.equals("l'")) {
                
                article();
                nom();
            } else if (estNom(tokenCourant)) {
                nom();
            } else if (estAdverbeCirc(tokenCourant)) {
                adverbeCirc();
                nom();
            } else {
                throw new RuntimeException("Erreur : sujet invalide '" + tokenCourant + "'");
            }
            if (estAdjectif(tokenCourant)) {
                adjectif();
            }
        }
    }

    
    public void verbe() {
        if (estVerbe(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : verbe invalide '" + tokenCourant + "'");
        }
    }

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
                
                return;  
            }
            if (estAdjectif(tokenCourant)) {
                adjectif();
            }
        }

        
        if (estConjonction(tokenCourant)) {
            consommer(tokenCourant);
            complement();
        }
    }


    public void article() {
        if (estArticle(tokenCourant) || estDeterminant(tokenCourant) || tokenCourant.equals("l'")) {
            estPluriel = tokenCourant.equals("les") || tokenCourant.equals("des");
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : article invalide '" + tokenCourant + "'");
        }
    }

   
    public void nom() {
        if (estNom(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : nom invalide '" + tokenCourant + "'");
        }
    }

    
    public void adjectif() {
        if (estAdjectif(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : adjectif invalide '" + tokenCourant + "'");
        }
    }

    
    public void adverbeCirc() {
        if (estAdverbeCirc(tokenCourant)) {
            consommer(tokenCourant);
        } else {
            throw new RuntimeException("Erreur : adverbe circonstanciel invalide '" + tokenCourant + "'");
        }
    }

    
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

   
    public void parse() {
        axiome();
        if (!tokenCourant.isEmpty()) {
            throw new RuntimeException("Erreur : entrée restante '" + tokenCourant + "'");
        }
    }
}
