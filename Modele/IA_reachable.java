package Modele;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.*;
import java.util.Iterator;

import Modele.Coup;
import IA.*;
import Modele.Niveau;
import Modele.Jeu;

import java.util.ArrayList;


public class IA_reachable extends IA {
    public static Niveau level;
    public int[] cursor;
    public int[][][] boxes;
    public Path[] reachable_blocks;
    public ArrayList<Path> paths = new ArrayList<Path>();

    public IA_reachable(){}

    public ArrayList<Coup> createCoups(ArrayList<int[]> liste){       //create a list of coups
        ArrayList<Coup> res = new ArrayList<Coup>();
        Iterator<int[]> ite = liste.iterator();
        int[] currPos = new int[2];
        int[] nextPos = new int[2];
        int dx, dy;
        Coup coup;
        while(ite.hasNext()){
            currPos = ite.next();
            nextPos = ite.next();
            dx = nextPos[1] - currPos[1];
            dy = nextPos[0] - currPos[0];
            coup = niveau.elaboreCoup(dx, dy);
            res.add(coup);
        }
        return res;
    }
    public static void automaticCoup(ArrayList<Path> paths, Jeu j) {
        Iterator<Path> ite = paths.iterator();
        Path prefix = paths.get(0);
        if(prefix.childs != null){
            Iterator<Path> childIterator= prefix.childs.iterator();
        }
        while (prefix != null) {
            if (prefix.position != null) {  // on se déplace selon le prefix
                int ligneP = j.lignePousseur();
                int colonneP = j.colonnePousseur();
                int[] newcase = prefix.position;
                int newLigneP = newcase[0];
                int newColonneP = newcase[1];

                // On calcule le déplacement horizontal et vertical
                int dx = newColonneP - colonneP;
                int dy = newLigneP - ligneP;

                // On construit le coup correspondant et on le joue
                Coup coup = j.elaboreCoup(dx, dy);
                if(coup != null){
                    j.joue(coup);
                }

                prefix = prefix.prefix;  // On passe au préfixe suivant

            }
//            else {  // on passe aux path fils
//                if (childIterator.hasNext()) {
//                    prefix = childIterator.next();
//                    childIterator = prefix.childs.iterator();
//                } else {
//                    prefix = null;
//                }
//            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


//    public void compute_step() {
//        FAP<Path> fap = new FAP<>();
//
//        // Add all reachable blocks to fap
//        for (Path path : reachable_blocks) {
//            int[] pos = path.get(path.length - 1);
//            compute_neighbor(pos, reachable_blocks, fap);
//        }
//
//        // Find next reachable blocks
//        int nb_ajouts = 0;
//        while (!fap.isEmpty()) {
//            Path path = fap.delete_min();
//            int[] pos = path.get(path.length - 1);
//
//            // Check if block has already been added
//            boolean already_added = false;
//            for (Path p : paths) {
//                int[] last_pos = p.get(p.length - 1);
//                if (last_pos[0] == pos[0] && last_pos[1] == pos[1]) {
//                    already_added = true;
//                    break;
//                }
//            }
//
//            if (!already_added) {
//                // Add new block to paths list
//                paths.add(path);
//                nb_ajouts++;
//
//                // Update reachable_blocks array
//                if (reachable_blocks[pos[0]][pos[1]] == null) {
//                    reachable_blocks[pos[0]][pos[1]] = path;
//                } else {
//                    reachable_blocks[pos[0]][pos[1]] = Path.min(path, reachable_blocks[pos[0]][pos[1]]);
//                }
//
//                // Compute neighbors and add to fap
//                compute_neighbor(pos, reachable_blocks, fap);
//            }
//        }
//    }
//        // Find the intersection of all reachable blocks for each box
//        if (!paths.isEmpty()) {
//            Path intersection = paths.get(0);
//            for (int i = 1; i < paths.size(); i++) {
//                intersection = intersection.intersect(paths.get(i));
//            }
//            reachable_blocks = intersection.toArray();
//        }
//    }

//    int[] getMovable()
//    {
//        return new int[0];
//    }



}
