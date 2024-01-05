import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

import javax.print.CancelablePrintJob;
import javax.security.auth.kerberos.KerberosKey;
import javax.swing.JEditorPane;

public class CourseAlterne {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		System.out.println("Exercice 3 : simulateur de course de trot attelé");
		System.out.println("Combien de chevaux participent ? (entre 12 et 20)");
		String numberHorsesString = scn.nextLine();
		//vérifier la saisie utilisateur
		if (verifInt(numberHorsesString) && (Integer.parseInt(numberHorsesString) >= 12 && Integer.parseInt(numberHorsesString) <= 20)) {
			//création des chevaux
			int numberHorses = Integer.parseInt(numberHorsesString), horses[][] = createHorses(numberHorses), distTotal = 0, tour = 0, score = 0;
			do {
				for (int i=0; i<horses.length;i++) {
					score = lanceDices();
					horses[i][1] += distance(horses[i][1], score);
					horses[i][2] += speedCalcul(horses[i][2], score);
					if (horses[i][2] > 6) {
						horses[i][3] = 0;
					}
				}
				distTotal = triDist(horses);
				tour ++;
				System.out.println("Au tour: "+tour+" voici les résultat:");
				System.out.println(viewHorses(horses));
			} while (distTotal < 2400);
			System.out.println("Le résultat final est:\n"+finishPicture(horses));
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
		int horses[][] = new int[numberHorses][4];
		for (int i=0;i<horses.length;i++) {
			horses[i][0]= i+1;//numero
			horses[i][1] = 0;//distance
			horses[i][2] = 0;//vitesse actuelle
			horses[i][3] = 1;//qualifié=1, disqualifié = 0;
		}
		return horses;
	}
	//afficher les chevaux
	private static String viewHorses(int[][] horses) {
		String viewString = "";
		for (int i=0;i<horses.length;i++) {
			if (i < horses.length -1) {
				viewString += "cheval numéro: "+Integer.toString(horses[i][0])+" ,à parcourue: "+Integer.toString(horses[i][1])+" mètres, sa vitesse actuelle est de: "+horses[i][2]+".\n";
			} else {
				viewString += "cheval numéro: "+Integer.toString(horses[i][0])+" ,à parcourue: "+Integer.toString(horses[i][1])+" mètres, sa vitesse actuelle est de: "+horses[i][2]+".";
			}
		}
		return viewString;
	}
	//tri distance parcouru
	private static int triDist(int[][] horses) {
		int distance = horses[0][1];
		for (int i=1;i<horses.length;i++) {
			if (distance<horses[i][1]) {
				distance = horses[i][1];
			}
		}
		return distance;
	}
	//affiche le résultat final
	private static String finishPicture(int[][] horses) {
		int[][] orderVictory = new int[horses.length][4];
		int[] max5 = new int[horses.length];
		String viewVictory = "";
		for (int i=0;i<max5.length;i++) {
			max5[i] = horses[i][1]; 
		}
		//tri a bulle des 5 meilleurs qualifiés
		for(int i=0;i<max5.length;i++) {
			for(int j=1;j<(max5.length-i);j++) {
				int tmp = max5[j-1];
				max5[j-1]=max5[j];
				max5[j]=tmp;
			}
		}
		//parcourir la longueur du tableau pour incrémenter order victory du plus haut vers le plus bas.
		for(int i=0;i<horses.length;i++) {
			//je vais parcourir max 5
			for(int j=max5.length;j>=0;j--) {
				if(horses[i][1] == max5[j]) {
					orderVictory[i][0] = horses[i][0];
					orderVictory[i][1] = horses[i][1];
					orderVictory[i][2] = horses[i][2];
					orderVictory[i][3] = horses[i][3];
				}
			}
		}
		//Parcourir le tableau des victorieux en éliminant les dysqualifiés
		for (int i=0; i<orderVictory.length;i++) {
			if(orderVictory[i][3]!=0) {
				viewVictory += "Le cheval numero: "+orderVictory[i][0]+" à parcouru "+orderVictory[i][1]+" mètres.";
			}
		}
		return viewVictory;
	}
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