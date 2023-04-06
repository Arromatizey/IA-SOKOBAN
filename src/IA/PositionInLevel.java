package IA;
import Modele.Niveau;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.abs;


public class PositionInLevel extends Node<PositionInLevel>{
    Niveau level; // Level is tied to the GameState level. Just reference it.
    int[] pos;

    PositionInLevel(int[] pos)
    {
        this.pos = pos;
    }
    PositionInLevel(Niveau level ,int posx, int posy)
    {
        this.level = level;
        this.pos = new int[2];
        this.pos[0] = posx;
        this.pos[1] = posy;
    }

    @Override
    public int distanceTo(PositionInLevel elem) {
        PositionInLevel compare = elem;
        return abs(this.pos[0] - compare.pos[0]) + abs(this.pos[1] - compare.pos[1]);
    }

    @Override
    public ArrayList<PositionInLevel> neighbors()
    {
        ArrayList<PositionInLevel> neighbors = new ArrayList<>();
        if(level.estOccupable(this.pos[0]+1,this.pos[1]))
        {
            neighbors.add(new PositionInLevel(this.level,this.pos[0]+1,this.pos[1]));
        }
        if(level.estOccupable(this.pos[0]-1,this.pos[1]))
        {
            neighbors.add(new PositionInLevel(this.level,this.pos[0]-1,this.pos[1]));
        }
        if(level.estOccupable(this.pos[0],this.pos[1]+1))
        {
            neighbors.add(new PositionInLevel(this.level,this.pos[0],this.pos[1]+1));
        }
        if(level.estOccupable(this.pos[0],this.pos[1]-1))
        {
            neighbors.add(new PositionInLevel(this.level,this.pos[0],this.pos[1]-1));
        }
        return neighbors;
    }


    @Override
    public int heuristic(ArrayList<PositionInLevel> targets) {
        ArrayList<Integer> distance = new ArrayList<Integer>();
        for (PositionInLevel elem :  targets) {
            PositionInLevel pos = elem;
            distance.add(this.distanceTo(elem));
        }
        return Collections.min(distance);
    }

    @Override
    public boolean isBadState()
    {
        return false;
    }
    @Override
    public boolean compareTo(PositionInLevel elem)
    {
        return elem.pos[0] == this.pos[0] && elem.pos[1] == this.pos[1];
    }
}
