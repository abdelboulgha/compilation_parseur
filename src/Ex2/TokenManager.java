package Ex2;

public class TokenManager {
 private String chaineEntree;
 private int positionCourante;
 
 public TokenManager(String chaine) {
	 this.chaineEntree=chaine;
	 suivant();
 }
 
 public char suivant() {
	 if (positionCourante < chaineEntree.length()) {
		 return chaineEntree.charAt(positionCourante++);
	 }
		 return '\0';
 }
}
