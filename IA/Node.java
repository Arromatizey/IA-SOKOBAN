package IA;

import java.util.ArrayList;

public abstract class Node{
    public abstract int distanceTo(Node elem);
    public abstract Node[] neighbors();
    public abstract int heuristic(ArrayList<Node> targets);
    public abstract boolean isBadState();
}
