package IA;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.*;

import Modele.Coup;
import Modele.Niveau;
import Modele.Jeu;

import java.util.ArrayList;


public class IA_reachable {
    public static Niveau level;
    public int[] cursor;
    public int[][][] boxes;
    public Path[] reachable_blocks;
    public ArrayList<Path> paths = new ArrayList<Path>();

    public void compute()
        {
        // Initialize data structures
        FAP<Path> fap = new FAP();
        Path[][] reachable = new Path[level.lignes()][level.lignes()];
        reachable[cursor[0]][cursor[1]] = new Path();
        int[][] cost = new int[level.lignes()][level.lignes()];
        int[][][] prev = new int[level.lignes()][level.lignes()][2];
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> cost[a[0]][a[1]]));

        // Add start block to queue
        queue.add(cursor);
        cost[cursor[0]][cursor[1]] = 0;

        // Search for optimal path to goal
        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            if (level.aBut(current[0], current[1])) {
                // Found goal block, return optimal path
                Path path = new Path();
                while (current != cursor) {
                    path = new Path(path, current);
                    current = prev[current[0]][current[1]];
                }
                reachable_blocks = paths.toArray(new Path[paths.size()]);
                break;
            }

            // Process neighbors
            int[][] neighbors = {{current[0] + 1, current[1]}, {current[0] - 1, current[1]}, {current[0], current[1] + 1}, {current[0], current[1] - 1}};
            for (int[] neighbor : neighbors) {
                if (level.estOccupable(neighbor[0], neighbor[1])) {
                    int tentativeCost = cost[current[0]][current[1]] + 1;
                    if (tentativeCost < cost[neighbor[0]][neighbor[1]]) {
                        cost[neighbor[0]][neighbor[1]] = tentativeCost;
                        prev[neighbor[0]][neighbor[1]] = current;
                        queue.add(neighbor);
                    }
                }
            }
        }
    }

    private void compute_neighbor(int[] pos, Path[][] reachable, FAP<Path> fap) {
        if (reachable[pos[0]][pos[1]] == null) {
            return;
        }
        int prio = reachable[pos[0]][pos[1]].length + 1;
        process_neighbor(pos[0]+1, pos[1], reachable[pos[0]][pos[1]], reachable, fap, prio);
        process_neighbor(pos[0]-1, pos[1], reachable[pos[0]][pos[1]], reachable, fap, prio);
        process_neighbor(pos[0], pos[1]+1, reachable[pos[0]][pos[1]], reachable, fap, prio);
        process_neighbor(pos[0], pos[1]-1, reachable[pos[0]][pos[1]], reachable, fap, prio);
    }

    private void process_neighbor(int x, int y, Path prefix, Path[][] reachable, FAP<Path> fap, int prio) {
        if (reachable[x][y] == null) {
            if (level.estOccupable(x, y)) {
                int[] newPos = {x, y};  // Create un nouveau int[] pour la nouvelle position
                Path newPath = new Path(prefix, newPos);  // Create un nouveau object Path
                newPath.length = prefix.length + 1;
                fap.insert(newPath, prio);
            }
        }
    }


    public void automaticCoup(ArrayList<Path> paths, Jeu j) {
        Iterator<Path> ite = paths.iterator();
        Path prefix = paths.get(0);
        Iterator<Path> childIterator= prefix.childs.iterator();

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
                Coup coup;
                if (dx == 1) {
                    coup = new Coup(Coup.DROITE);
                } else if (dx == -1) {
                    coup = new Coup(Coup.GAUCHE);
                } else if (dy == 1) {
                    coup = new Coup(Coup.BAS);
                } else if (dy == -1) {
                    coup = new Coup(Coup.HAUT);
                } else {
                    throw new RuntimeException("Mouvement invalide");
                }
                j.joue(coup);
                prefix = prefix.prefix;  // On passe au préfixe suivant

            } else {  // on passe aux path fils
                if (childIterator.hasNext()) {
                    prefix = childIterator.next();
                    childIterator = prefix.childs.iterator();
                } else {
                    prefix = null;
                }
            }
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
