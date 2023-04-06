package IA;

import java.util.ArrayList;

public class Path<E> {
    E position;
    public int length;
    public Path prefix;

    public ArrayList<Path> childs;

    public Path()
    {
        this.length = 0;
        this.prefix = null;
        this.childs = null;
    }

    public Path(Path prefix, E pos)
    {
        if(prefix != null)
        {
            this.length = prefix.length+1;
            this.prefix = prefix;
            this.prefix.addChild(this);
        }
        else{
            this.length = 0;
            this.prefix = null;
        }
        this.position = pos;

        this.childs = null;
    }

    public Path(Path prefix, E pos,int distance)
    {
        if(prefix != null)
        {
            this.prefix = prefix;
            this.prefix.addChild(this);
            this.length = prefix.length + distance;
        } else
        {
            this.length = distance;
        }
        this.position = pos;
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
