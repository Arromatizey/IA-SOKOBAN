package IA;

import Modele.Niveau;

import java.util.ArrayList;
import java.util.HashMap;

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
    @Override
    public boolean isBadState()
    {

        //Compute if we are in a blocked state if blocked -> false else -> true TODO
        return true;
    }


    //Compute reachable movable boxes TODO
}
