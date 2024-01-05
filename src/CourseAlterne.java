import java.util.ArrayList;
import java.util.Scanner;

import javax.print.CancelablePrintJob;

public class CourseAlterne {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		System.out.println("Exercice 3 : simulateur de course de trot attelé");
		System.out.println("Combien de chevaux participent ? (entre 12 et 20)");
		String numberHorsesString = scn.nextLine();
		//vérifier la saisie utilisateur
		if (verifInt(numberHorsesString) && (Integer.parseInt(numberHorsesString) >= 12 && Integer.parseInt(numberHorsesString) <= 20)) {
			//création des chevaux
			int numberHorses = Integer.parseInt(numberHorsesString), horses[][] = createHorses(numberHorses), distRest = 2400, tour = 0, score = 0;
			do {
				score = lanceDices();
				System.out.println("Au tour: "+tour+" voici les résultat:");
				System.out.println(viewHorses(horses));
			} while (distRest > 0);
		//saisie non conforme
		} else {
			System.out.println("Merci de saisir une valeur en chiffre entre 12 et 20");
		}
	}
	//vérification de chiffres saisie
	private static boolean verifInt(String x) {
		Scanner scn = new Scanner(x);
		boolean isCorrect = false;
		if (scn.hasNextInt()) {
			isCorrect = true;
		}
		return isCorrect;
	}
	//lancé de dés
	private static int lanceDices() {
		return (int) (Math.random() * (6))+1;
	}
	//calcul de la distance
	private static int distance(int distTtoal, int score) {
		int distanceAdd = score * 23;
		return distTtoal + distanceAdd;
	}
	//calcul de la vitesse
	private static int speedCalcul(int currentSpeed, int score) {
		//initialise les vitesses
		int[][] speed = {
				{0,1,1,1,2,2},//0
				{0,0,1,1,1,2},//1
				{0,0,1,1,1,2},//2
				{-1,0,0,1,1,1},//3
				{-1,0,0,0,1,1},//4
				{-2,-1,0,0,0,1},//5
				{-2,-1,0,0,0,1}//6
		};
		return speed[currentSpeed][score-1];
	}
	//créer les chevaux
	private static int[][] createHorses(int numberHorses) {
		int horses[][] = new int[numberHorses][2];
		for (int i=0;i<horses.length;i++) {
			horses[i][0]= i+1;
			horses [i][1] = 0;
		}
		return horses;
	}
	//afficher les chevaux
	private static String viewHorses(int[][] horses) {
		String viewString = "";
		for (int i=0;i<horses.length;i++) {
			if (i < horses.length -1) {
				viewString += "cheval numéro: "+horses[i][0]+" ,à parcourue: "+horses[1]+" mètres.\n";
			} else {
				viewString += "cheval numéro: "+horses[i][0]+" ,à parcourue: "+horses[1]+" mètres.";
			}
		}
		return viewString;
	}
	//avancer les chevaux
}
/*
Une course de trot attelé2 rassemble 12 à 20 chevaux, chacun tractant un sulky, et
étant mené par un driver. Elle peut faire l’objet d’un tiercé, d’un quarté, ou d’un
quinté. La course est supposée se dérouler sur un hippodrome rectiligne (chaque cheval disposant de
son propre couloir), d’une longueur de 2 400 m. Il est à noter que chaque cheval doit respecter
l’allure du trot de bout en bout, le passage au galop entrainant sa disqualification. L’utilisateur saisit
au démarrage le nombre de chevaux et le type de la course.
La course se déroule à la manière d’un « jeu de plateau » : à chaque tour de jeu, chaque cheval fait
l’objet d’un jet de dé (à 6 faces), qui décide d’une altération possible de sa vitesse (augmentation,
stabilisation, diminution). La nouvelle vitesse détermine alors la distance dont il avance. Chaque tour
de jeu représente 10 secondes du déroulement de la course, mais le temps ne sera pas rendu dans le
programme. C’est l’utilisateur qui fera avancer la course de tour en tour, à la suite d’un message du
programme l’y invitant.
*/