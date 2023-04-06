package IA;

import Modele.Niveau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameState extends Node{
    Niveau level; // Level is fixed during a game state. (Need to be a copy)
    Pathing<PositionInLevel> reachable_box_path;
    HashMap<GameState,Integer> neighbor_states;

    public GameState(Niveau level)
    {
        this.level = level;
    }

    @Override
    public int distanceTo(Node elem) {
        if(neighbor_states != null){
            Integer length  = neighbor_states.get((GameState)elem);
            if(length == null)
            {
                return 0; // This state should be impossible to get to
            }
            else
            {
                return length;
            }
        }

        //Compute neighbor states before  TODO

        Integer length  = neighbor_states.get((GameState)elem);
        if(length == null)
        {
            return 0; // This state should be impossible to get to
        }
        return length;
    }

    @Override
    public Node[] neighbors() {
        if(reachable_box_path != null){
            return (Node[]) reachable_box_path.shortest_paths.keySet().toArray();
        }
        //Compute box target location TODO
        ArrayList<PositionInLevel> targets = new ArrayList<PositionInLevel>();
        reachable_box_path = new Pathing<PositionInLevel>((ArrayList<Node>) targets);
        int[] source = new int[2];
        source[0] = level.pousseurL;
        source[1] = level.pousseurC;
        reachable_box_path.Dijkstra(new PositionInLevel(source));
        // neighbors = reachable_box_path.shortest_paths.keySet(); need to turn into array TODO
        //Create the new states here TODO

        GameState[] neighbor_states=  new GameState[]{null};
        return neighbor_states;
    }

    @Override
    public int heuristic(ArrayList<Node> targets) {
        // Need to define heuristic (low priority) TODO
        // Probably gonna be the shortest
        return 0;
    }
    public int[] blockingWall(int l, int c){    //ou est le mur touchant la box
        int[] wallCoord = new int[2];              //cas ou tunel TODO
        wallCoord[0] = l;
        wallCoord[1] = c;
        if(level.aMur(l+1, c)){
            wallCoord[0] = l+1;
            wallCoord[1] = c;
            return wallCoord;
        }
        if(level.aMur(l-1, c)){
            wallCoord[0] = l-1;
            wallCoord[1] = c;
            return wallCoord;
        }
        if(level.aMur(l, c+1)){
            wallCoord[0] = l;
            wallCoord[1] = c+1;
            return wallCoord;
        }
        if(level.aMur(l, c-1)){
            wallCoord[0] = l;
            wallCoord[1] = c-1;
            return wallCoord;
        }
        return wallCoord;
    }

    public boolean isBoxBlocked(int l, int c){  //verifie si une box est bloquée en regaredant le mur ou elle bloque
        int[] wallCoord = new int[2];
        wallCoord = blockingWall(l, c);     //recupere ou se trouve le mur qui blocke
        if(wallCoord[0] != l && wallCoord[1] != c){return false;}  //box dans un coin
        int dL = l - wallCoord[0], dC = c - wallCoord[1]; //trouve les directions ou se déplacer, si dL != 0 deplacement vertical
        if(dC != 0){
            int compd = 0, compg = 0;
            while(level.aMur(wallCoord[0] + compd, wallCoord[1]) && level.estOccupable(l + compd, c)){ //verifie si sortie a droite
                compd++;
            }
            while(level.aMur(wallCoord[0] - compg, wallCoord[1]) && level.estOccupable(l - compg, c)){     //verification si sortie gauche
                compg++;
            }
            //verify if the position where the comp has stopped is a way
            if((level.aMur(wallCoord[0] + compd, wallCoord[1]) && level.estOccupable(l + compd, c)) || (level.aMur(wallCoord[0], wallCoord[1] - compg) && level.estOccupable(l, c - compg))){
                return false;
            }
            else{
                return true;
            }
        }
        else if(dC != 0){
            int compd = 1, compg = 1;
            while(level.aMur(wallCoord[0], wallCoord[1] + compd) && level.estOccupable(l, c + compd)){ //verifie si sortie a droite
                compd++;
            }
            while(level.aMur(wallCoord[0] - compg, wallCoord[1]) && level.estOccupable(l , c - compg)){     //verification sortie gauche
                compg++;
            }
            //verify if the possition where the comp has stopped is a way
            if((level.aMur(wallCoord[0], wallCoord[1] + compd) && level.estOccupable(l, c + compd)) || (level.aMur(wallCoord[0] - compg, wallCoord[1]) && level.estOccupable(l , c - compg))){
                return false;
            }
            else{
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean isBadState()
    {
        //Compute if we are in a blocked state if blocked -> false else -> true TODO
        //compare for every box on the map
        ArrayList<int[]> boxes = new ArrayList<>();
        boxes = level.findBoxes(level);
        Iterator<int[]> ite = boxes.iterator();
        int[] box = new int[2];
        while(!ite.hasNext()){
            box = ite.next();
            if(isBoxBlocked(box[0], box[1])){return true;}
        }
        return false;
    }

    //Compute reachable movable boxes TODO
    public boolean CompareLevel()
}
