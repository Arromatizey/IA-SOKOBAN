package IA;

import Modele.*;
import Global.*;
import Structures.*;
import java.util.ArrayList;
import java.util.Iterator;

public class myAI extends Modele.IA{
    Niveau level;
    Pathing<GameState> AI_pathing;
    Path<GameState> shortest_path;
    Path<PositionInLevel> pathing_tree;
    Path<GameState> state_tree;
    Path<PositionInLevel> end_position;
    boolean solved;
    public myAI(Niveau level)
    {
        this.solved = false;
        this.level = level;
    }

    public void solve()
    {
        //Create the PositionInLevel corresponding to the cursor at the beginning of the level
        PositionInLevel cursor = new PositionInLevel(level,level.pousseurL,level.pousseurC);
        this.pathing_tree = new Path<PositionInLevel>(null,cursor,0);
        //Create the GameState that symbolise the level at the start.
        GameState start_state = new GameState(level,pathing_tree,cursor);
        this.state_tree = new Path<GameState>(null,start_state,0);
        //Create the target state the AI want to reach
        GameState solved_state = new GameState(this.level.clone(),null,cursor );
        ArrayList<GameState> end_state = new ArrayList<GameState>();
        for (int i = 0;i<solved_state.level.lignes();i++) {
            for(int j =0;j<solved_state.level.colonnes();j++)
            {
                if(solved_state.level.cases[i][j] == '$'){
                    solved_state.level.cases[i][j] = ' ';
                } else if(solved_state.level.cases[i][j] == '.'){
                    solved_state.level.cases[i][j] = ' ';
                }
            }
        }
        solved_state.level.nbCaissesSurBut = solved_state.level.nbButs;
        end_state.add(solved_state);
        //Create the pathing AI solver with the beginning and wished end state
        AI_pathing = new Pathing<GameState>(state_tree,end_state);
        AI_pathing.Dijkstra();
        if(!AI_pathing.targets_reached)
        {
            this.solved = false;
            this.shortest_path = null;
            this.end_position = null;
            System.out.print("AI couldn't find a path to the desired state\n");
            return;
        }
        this.solved = true;

        if(AI_pathing.shortest_paths.values().size() >0)
        {
            shortest_path = (Path<GameState>) AI_pathing.shortest_paths.values().toArray()[0];
            end_position = shortest_path.position.cursor_origin;
        }
    }

    public ArrayList<int []> convert(){
        Path<PositionInLevel> current = this.end_position;
        ArrayList<int []> list = new ArrayList<>();
        int[] pos;
        while (current.prefix != null){
            list.add(current.position.pos);
            current = current.prefix;
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
            coup = level.elaboreCoup(dx, dy);
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

}
