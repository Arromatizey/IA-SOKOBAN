package IA;

import Modele.Coup;
import Modele.Niveau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameState extends Node<GameState>{
    Niveau level; // Level is fixed during a game state. (Need to be a copy)
    Pathing<PositionInLevel> reachable_box_path;
    HashMap<GameState,Path<PositionInLevel>> neighbor_states;
    Path<PositionInLevel> cursor_origin;
    PositionInLevel cursor_current;

    public GameState(Niveau level,Path<PositionInLevel> origin,PositionInLevel current)
    {
        this.level = level;
        this.cursor_origin = origin;
        this.cursor_current = current;
    }

    @Override
    public int distanceTo(GameState elem) {
        Path<PositionInLevel> current;
        if(neighbor_states != null){
            current  = neighbor_states.get(elem);
            if(current == null)
            {
                return 0; // This state should be impossible to get to
            }
            else
            {
                return current.length;
            }
        }

        createNeighbors();

        current  = neighbor_states.get(elem);
        if(current == null)
        {
            return 0; // This state should be impossible to get to
        }
        return current.length;
    }

    @Override
    public ArrayList<GameState> neighbors() {
        ArrayList<GameState> neighbors = new ArrayList<>();
        if(reachable_box_path != null && neighbor_states != null){
            return new ArrayList<GameState>(neighbor_states.keySet());
        }
        ArrayList<PositionInLevel> targets = computeTargets();
        reachable_box_path = new Pathing<PositionInLevel>(new Path<PositionInLevel>(cursor_origin,cursor_current),targets);
        int[] source = new int[2];
        source[0] = level.pousseurL;
        source[1] = level.pousseurC;
        Path<PositionInLevel> origin = new Path<PositionInLevel>(cursor_origin,cursor_current);
        reachable_box_path.Dijkstra();
        createNeighbors();
        return neighbors;
    }

    @Override
    public int heuristic(ArrayList<GameState> targets) {
        // Need to define heuristic (low priority) TODO
        // Probably gonna be the shortest
        return 0;
    }
    @Override
    public boolean isBadState()
    {
        ArrayList<int[]> boxes = new ArrayList<>();
        boxes = level.findBoxes(level);
        Iterator<int[]> ite = boxes.iterator();
        int[] box = new int[2];
        while(!ite.hasNext()){
            box = ite.next();
            if(isBoxBlocked(box[0], box[1])){return true;}
        }
        return false;
    }

    public boolean isBoxBlocked(int l, int c){  //verifie si une box est bloquée en regaredant le mur ou elle bloque
        int[] wallCoord = new int[2];
        wallCoord = blockingWall(l, c);     //recupere ou se trouve le mur qui blocke
        if(wallCoord[0] != l && wallCoord[1] != c){return false;}  //box dans un coin
        int dL = l - wallCoord[0], dC = c - wallCoord[1]; //trouve les directions ou se déplacer, si dL != 0 deplacement vertical
        if(dC != 0){
            int compd = 0, compg = 0;
            while(level.aMur(wallCoord[0] + compd, wallCoord[1]) && level.estOccupable(l + compd, c)){ //verifie si sortie a droite
                compd++;
            }
            while(level.aMur(wallCoord[0] - compg, wallCoord[1]) && level.estOccupable(l - compg, c)){     //verification si sortie gauche
                compg++;
            }
            //verify if the position where the comp has stopped is a way
            if((level.aMur(wallCoord[0] + compd, wallCoord[1]) && level.estOccupable(l + compd, c)) || (level.aMur(wallCoord[0], wallCoord[1] - compg) && level.estOccupable(l, c - compg))){
                return false;
            }

            else{
                return true;
            }
        }
        else if(dL
                != 0){
            int compd = 1, compg = 1;
            while(level.aMur(wallCoord[0], wallCoord[1] + compd) && level.estOccupable(l, c + compd)){ //verifie si sortie a droite
                compd++;
            }
            while(level.aMur(wallCoord[0] - compg, wallCoord[1]) && level.estOccupable(l , c - compg)){     //verification sortie gauche
                compg++;
            }
            //verify if the possition where the comp has stopped is a way
            if((level.aMur(wallCoord[0], wallCoord[1] + compd) && level.estOccupable(l, c + compd)) || (level.aMur(wallCoord[0] - compg, wallCoord[1]) && level.estOccupable(l , c - compg))){
                return false;
            }
            else{
                return true;
            }
        }
        return true;
    }

    public int[] blockingWall(int l, int c){
        int[] wallCoord = new int[2];
        wallCoord[0] = l;
        wallCoord[1] = c;
        if(level.aMur(l+1, c)){
            wallCoord[0] = l+1;
            wallCoord[1] = c;
            return wallCoord;
        }
        if(level.aMur(l-1, c)){
            wallCoord[0] = l-1;
            wallCoord[1] = c;
            return wallCoord;
        }
        if(level.aMur(l, c+1)){
            wallCoord[0] = l;
            wallCoord[1] = c+1;
            return wallCoord;
        }
        if(level.aMur(l, c-1)){
            wallCoord[0] = l;
            wallCoord[1] = c-1;
            return wallCoord;
        }
        return wallCoord;
    }
    @Override
    public boolean compareTo(GameState elem)
    {
        return elem.level.estTermine() && this.level.estTermine();
    }

    public ArrayList<PositionInLevel>computeTargets()
    {
        ArrayList<PositionInLevel> targets = new ArrayList<PositionInLevel>();
        targets = findInterestingPosition();
        return targets;
    }

    public void createNeighbors()
    {
        Niveau neighboring_level;
        PositionInLevel new_pos;
        Coup coup;
        //Create neighbors based on if a box is a move is possible and move a box
        for(Path<PositionInLevel> current : this.reachable_box_path.shortest_paths.values()) {
            coup = level.elaboreCoup(1, 0);
            if (coup != null && coup.caisse() != null) {
                neighboring_level = level.clone();
                neighboring_level.joue(coup);
                new_pos = new PositionInLevel(neighboring_level, current.position.pos[0] + 1, current.position.pos[1]);
                neighbor_states.put(new GameState(neighboring_level, new Path<PositionInLevel>(current, new_pos, 1), new_pos), new Path<PositionInLevel>(current,new_pos));
            }
            coup = level.elaboreCoup(0, 1);
            if (coup != null && coup.caisse() != null) {
                neighboring_level = level.clone();
                neighboring_level.joue(coup);
                new_pos = new PositionInLevel(neighboring_level, current.position.pos[0], current.position.pos[1] + 1);
                neighbor_states.put(new GameState(neighboring_level, new Path<PositionInLevel>(current, new_pos, 1), new_pos), new Path<PositionInLevel>(current,new_pos));
            }
            coup = level.elaboreCoup(-1, 0);
            if (coup != null && coup.caisse() != null) {
                neighboring_level = level.clone();
                neighboring_level.joue(coup);
                new_pos = new PositionInLevel(neighboring_level, current.position.pos[0] - 1, current.position.pos[1]);
                neighbor_states.put(new GameState(neighboring_level, new Path<PositionInLevel>(current, new_pos, 1), new_pos), new Path<PositionInLevel>(current,new_pos));
            }
            coup = level.elaboreCoup(0, -1);
            if (coup != null && coup.caisse() != null) {
                neighboring_level = level.clone();
                neighboring_level.joue(coup);
                new_pos = new PositionInLevel(neighboring_level, current.position.pos[0], current.position.pos[1] - 1);
                neighbor_states.put(new GameState(neighboring_level, new Path<PositionInLevel>(current, new_pos, 1), new_pos), new Path<PositionInLevel>(current,new_pos));
            }
        }
    }
    public ArrayList<PositionInLevel> interestingCaseBox(int x, int y){
        ArrayList<PositionInLevel> positions = new ArrayList<>();
        int[] pos = new int[2];
        for(int i = 0; i<3; i++){
            if(level.aBut(x, y) && !isBoxBlocked(x, y)){
                if(level.aBut(x+1, y)){
                    pos[0] = x + 1;
                    pos[1] = y;
                    positions.add(new PositionInLevel(pos));
                }
                if(level.aBut(x-1, y)){
                    pos[0] = x - 1;
                    pos[1] = y;
                    positions.add(new PositionInLevel(pos));
                }
                if(level.aBut(x, y+1)){
                    pos[0] = x;
                    pos[1] = y + 1;
                    positions.add(new PositionInLevel(pos));
                }
                if (level.aBut(x, y-1)){
                    pos[0] = x;
                    pos[1] = y - 1;
                    positions.add(new PositionInLevel(pos));
                }
            }
            else{
                if(level.estOccupable(x+1, y)){
                    pos[0] = x + 1;
                    pos[1] = y;
                    positions.add(new PositionInLevel(pos));
                }
                if(level.estOccupable(x-1, y)){
                    pos[0] = x - 1;
                    pos[1] = y;
                    positions.add(new PositionInLevel(pos));
                }
                if(level.estOccupable(x, y+1)){
                    pos[0] = x;
                    pos[1] = y + 1;
                    positions.add(new PositionInLevel(pos));
                }
                if(level.estOccupable(x, y-1)){
                    pos[0] = x;
                    pos[1] = y - 1;
                    positions.add(new PositionInLevel(pos));
                }
            }
        }
        return positions;
    }
    public ArrayList<PositionInLevel> findInterestingPosition()
    {
        ArrayList<int[]> boxes = new ArrayList<>();
        boxes = level.findBoxes(level);
        Iterator<int[]> ite = boxes.iterator();     //recupere la position des box

        ArrayList<PositionInLevel> pos = new ArrayList<>();
        ArrayList<PositionInLevel> res = new ArrayList<>();

        int[] boxpos = new int[2];
        while(!ite.hasNext()){
            boxpos = ite.next();
            pos = interestingCaseBox(boxpos[0], boxpos[1]);
            res.addAll(pos);
        }
        return res;
    }
}
