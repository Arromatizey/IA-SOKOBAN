package IA;

import java.util.ArrayList;

public class Path<E> {

    public int length;
    public Path prefix;
    public int[] position;  // Add a new field for the position

    public ArrayList<Path> childs;

    public Path() {
        this.length = 0;
        this.prefix = null;
        this.childs = null;
    }

    public Path(int[] position) {  // Modify the constructor to take an additional int[] argument
        this.prefix = null;
        this.position = position;  // Initialize the position field
        this.childs = null;
    }

    public Path(Path prefix, int[] position) {  // Modify the constructor to take an additional int[] argument
        if (prefix != null) {
            this.length = prefix.length + 1;
            this.prefix = prefix;
        } else {
            this.length = 1;
            this.prefix = null;
        }
        this.position = position;  // Initialize the position field
        this.childs = null;
    }

    public void addPrefix(Path prefix){
        this.prefix = prefix;
    }

    public void addChild(Path path)
    {
        if(childs == null)
        {
            this.childs = new ArrayList<Path>();
        }
        this.childs.add(path);
    }
}
