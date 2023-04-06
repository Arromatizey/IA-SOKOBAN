package IA;

import java.util.*;

public class Pathing<E extends Node> {
    boolean computed;
    Path<E> paths;
    FAP<Path<E>> fap;
    ArrayList<Node> targets;

    HashMap<E,Path<E>> shortest_paths;

    public Pathing(ArrayList<Node> targets)
    {
        this.targets = targets;
        this.computed = false;
    }

    public void Dijkstra(E source)
    {
        shortest_paths = new HashMap<>();
        Path<E> current;
        E[] neighbors;
        paths = new Path<E>(null,source);
        fap.insert(paths,0);
        while(!fap.isEmpty()) {
            current = fap.pluck();
            if(this.isEndState())
            {
                this.computed = true;
                return;
            }
            neighbors = (E[]) current.position.neighbors();
            for (E node : neighbors ) {
                if(!node.isBadState()) {
                    Path<E> new_path = new Path<E>(current, node, current.position.distanceTo(node));
                    fap.insert(new_path, current.length + current.position.distanceTo(node));
                    // Need a function to check if the state is the same not that the IDs are identical TODO
                    if (targets.contains(new_path.position)) {
                        shortest_paths.put(current.position, new_path);
                    }
                }
            }
        }
        this.computed = true;

    }
    public void A_star(E source,ArrayList<Node> targets)
    {

        Path<E> current;
        E[] neighbors;
        paths = new Path<E>(null,source);
        fap.insert(paths,0);
        while(!fap.isEmpty()) {
            current = fap.pluck();
            neighbors = (E[]) current.position.neighbors();
            for (E node : neighbors ) {
                Path<E> new_path = new Path<E>(current,node,current.position.distanceTo(node));
                fap.insert(new_path, current.length + current.position.distanceTo(node) + current.position.heuristic(targets));
            }
        }

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
