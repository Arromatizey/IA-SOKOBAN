package IA;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Pathing<E extends Node> {
    boolean computed;
    boolean targets_reached;
    Path<E> paths;
    FAP<Path<E>> fap;
    Path<E> source;
    ArrayList<E> targets;

    HashMap<E,Path<E>> shortest_paths;

    public Pathing(Path<E> source,ArrayList<E> targets)
    {
        this.source = source;
        this.targets = targets;
        this.computed = false;
        this.targets_reached = false;
    }

    public void Dijkstra()
    {
        shortest_paths = new HashMap<>();
        fap = new FAP<Path<E>>();
        Path<E> current;
        ArrayList<E> neighbors;
        paths = source;
        fap.insert(paths,0);
        while(!fap.isEmpty()) {
            current = fap.pluck();
            if(this.isEndState())
            {
                this.computed = true;
                return;
            }
            neighbors = current.position.neighbors();
            for (E node : neighbors ) {
                if(!node.isBadState()) {
                    Path<E> new_path = new Path<E>(current, node, current.position.distanceTo(node));
                    fap.insert(new_path, current.length + current.position.distanceTo(node));
                    for(E elem:targets) {
                        if (new_path.position.compareTo(elem)) {
                            shortest_paths.put(current.position, new_path);
                        }
                    }
                }
            }
        }
        this.computed = true;
    }
    public void A_star(Path<E> source,ArrayList<E> targets)
    {
        shortest_paths = new HashMap<>();
        Path<E> current;
        ArrayList<E> neighbors;
        paths = source;
        fap.insert(paths,0);
        while(!fap.isEmpty()) {
            current = fap.pluck();
            if(this.isEndState())
            {
                this.computed = true;
                return;
            }
            neighbors = current.position.neighbors();
            for (E node : neighbors ) {
                if(!node.isBadState()) {
                    Path<E> new_path = new Path<E>(current, node, current.position.distanceTo(node));
                    fap.insert(new_path, current.length + current.position.distanceTo(node)+current.position.heuristic(targets));
                    for(E elem:targets) {
                        if (new_path.position.compareTo(elem)) {
                            shortest_paths.put(current.position, new_path);
                        }
                    }
                }
            }
        }
        this.computed = true;

    }

    public boolean isEndState()
    {
        if(targets.size() == shortest_paths.size())
        {
            return true;
        }
        return false;
    }

}
