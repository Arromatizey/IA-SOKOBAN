package IA;


import Global.Configuration;
import Modele.Niveau;
import java.util.ArrayList;
import java.util.Iterator;
import Structures.Sequence;
import Modele.Coup;

public class IA {
    Niveau level;
    Pathing<GameState> AI_pathing;
    Path<GameState> shortest_path;

    public IA(Niveau level)
    {
        this.level = level;
    }

    public void solve()
    {

        AI_pathing = new Pathing<GameState>();
        AI_pathing.Dijkstra(new GameState(level));
        GameState solved_state = new GameState(new Niveau());
        AI_pathing.shortest_paths.get(solved_state);

    }

    public ArrayList<int []> convert(){
        Path<GameState> temp = this.shortest_path;
        ArrayList<int []> list = null;
        int[] pos;
        while (temp.prefix != null){
            Path<PositionInLevel> nom = temp.position.reachable_box_path.shortest_paths.
            while(nom.prefix != null){
                pos = nom.position;
                list.add(pos);
                nom = nom.prefix;
            }
            temp = temp.prefix;
        }
        return list;
    }

    public Sequence<Coup> joue(){       //create a list of coups
        Sequence<Coup> res = Configuration.nouvelleSequence();
        ArrayList<int[]> path = new ArrayList<>();
        path = convert();
        Iterator<int[]> ite = path.iterator();
        int[] currPos = new int[2];
        int[] nextPos = new int[2];
        int dx, dy;
        Coup coup;

        int nouveauL = 0;
        int nouveauC = 0;
        int pousseurL = level.lignePousseur();
        int pousseurC = level.colonnePousseur();

        currPos = ite.next();   //recupere la premiere position

        while(ite.hasNext()){
            nextPos = ite.next();
            dx = nextPos[1] - currPos[1];
            dy = nextPos[0] - currPos[0];
            coup = level.deplace(dx, dy);
            nouveauL = pousseurL + dx;
            nouveauC = pousseurC + dy;
            if (coup == null) {
                if (level.aMur(nouveauL, nouveauC))
                    Configuration.info("Tentative de déplacement (" + dx + ", " + dy + ") heurte un mur");
                else if (level.aCaisse(nouveauL, nouveauC))
                    Configuration.info("Tentative de déplacement (" + dx + ", " + dy + ") heurte une caisse non déplaçable");
                else
                    Configuration.erreur("Tentative de déplacement (" + dx + ", " + dy + "), erreur inconnue");
                currPos = nextPos;
            }
            res.insereQueue(coup);
        }
        return res;
    }
    // Put the cursor moving code here TODO
}
