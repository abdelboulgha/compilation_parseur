package Ex2;



public class Test {
	public static void main(String[] args) {
        // Exemple d'utilisation
        String[] tests = {"b", "c", "d", "bc", "bd", "cd", "ccc", "bcb", "db", "", "dc"};

        for (String test : tests) {
            TokenManager tm = new TokenManager(test);
            Parseur parseur = new Parseur(tm);

            try {
                 parseur.parse();
                 System.out.println("Input: '" + test + "' => est valide");
            } catch (RuntimeException e) {
                System.out.println("Input: '" + test + "' => n'est pas valide");
            }
        }
    }
}
