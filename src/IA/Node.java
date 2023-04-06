package IA;

import java.util.ArrayList;

public abstract class Node<E>{
    public abstract int distanceTo(E elem);
    public abstract boolean compareTo(E elem);
    public abstract ArrayList<E> neighbors();
    public abstract int heuristic(ArrayList<E> targets);
    public abstract boolean isBadState();
}
