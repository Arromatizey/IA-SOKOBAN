package IA;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.*;

import Modele.Coup;
import Modele.IA;
import Modele.Niveau;
import Modele.Jeu;

import java.util.ArrayList;


public class IA_reachable extends IA {
    public static Niveau level;
    public int[] cursor;
    public int[][][] boxes;
    public Path[] reachable_blocks;
    public ArrayList<Path> paths = new ArrayList<Path>();


    public void automaticCoup(Path path, Jeu j) {

        while (path.suffix != null) {
            int pousseurL = j.lignePousseur();
            int pousseurC = j.colonnePousseur();
            Coup coup;

            int[] newcase = path.position;
            int newLigneP = newcase[0];
            int newColonneP = newcase[1];

            // On calcule le déplacement horizontal et vertical
            int dx = newColonneP - pousseurL;
            int dy = newLigneP - pousseurC;

            coup = j.elaboreCoup(dx, dy);
            j.joue(coup);

            path = path.suffix;  // On passe au préfixe suivant
        }
    }
}