package IA;

import java.util.ArrayList;

public class Path<E> {

    boolean head;
    public int length;
    public Path suffix;
    public int[] position;  // Add a new field for the position

    public Path(boolean head) {
        this.head = head;
        this.length = 1;
        this.suffix = null;
        this.position = null;
    }

    public Path(boolean head, int[] position) {
        this.head = head;
        this.length = 1;
        this.suffix = null;
        this.position = position;
    }

    public Path(boolean head, Path suffix, int[] position) {  // Modify the constructor to take an additional int[] argument
        this.head = head;
        this.length = suffix.length + 1;
        this.suffix = suffix;
        this.position = position;  // Initialize the position field
    }


    public void addSuffix(Path path)
    {
        this.suffix = path;
        this.length += suffix.length;
    }
}