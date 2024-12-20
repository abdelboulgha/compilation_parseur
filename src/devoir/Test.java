package devoir;

public class Test {
    public static void main(String[] args) {
        String[] tests = {
        		"fromage",
                "le fromage", 
                "une souris mange", 
                "Je charge mon téléphone", 
                "Chaque matin, le téléphone sonne à 6 heures"
        };

        for (String test : tests) {
            TokenManager tm = new TokenManager(test);
            Parseur parseur = new Parseur(tm);

            try {
                parseur.parse();
                System.out.println("Entrée : \"" + test + "\" => valide");
            } catch (RuntimeException e) {
                System.out.println("Entrée : \"" + test + "\" => invalide (" + e.getMessage() + ")");
            }
        }
    }
}
