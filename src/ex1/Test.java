package ex1;

public class Test {
	public static void main(String[] args) {
        // Exemple d'utilisation
        String[] tests = {"b", "c", "d", "+bc", "-/bc*cd", "+-*/bcbcd", "bc", "+b", "*bcd", ""};

        for (String test : tests) {
            TokenManager tm = new TokenManager(test);
            Parseur parseur = new Parseur(tm);

            try {
                boolean result = parseur.parse();
                System.out.println("Input: '" + test + "' => " + (result ? "Accepted" : "Rejected"));
            } catch (RuntimeException e) {
                System.out.println("Input: '" + test + "' => Error: " + e.getMessage());
            }
        }
    }
}
