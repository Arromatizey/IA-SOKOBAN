package IA;
import Modele.Niveau;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;


public class PositionInLevel extends Node{
    Niveau level; // Level is tied to the GameState level. Just reference it.
    int[] pos;

    PositionInLevel(int[] pos)
    {
        this.pos = pos;
    }

    @Override
    public int distanceTo(Node elem) {
        PositionInLevel compare = (PositionInLevel) elem;
        return abs(this.pos[0] - compare.pos[0]) + abs(this.pos[1] - compare.pos[1]);
    }

    @Override
    public Node[] neighbors() {
        return new Node[0];
    }


    @Override
    public int heuristic(ArrayList<Node> targets) {
        ArrayList<Integer> distance = new ArrayList<Integer>();
        for (Node elem :  targets) {
            PositionInLevel pos = (PositionInLevel) elem;
            distance.add(this.distanceTo(elem));
        }
        return Collections.min(distance);
    }

    @Override
    public boolean isBadState()
    {
        return false;
    }
}
