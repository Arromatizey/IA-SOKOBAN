package IA;


import Modele.Niveau;

public class IA {
    Niveau level;
    Pathing<GameState> AI_pathing;
    Path<GameState> shortest_path;

    public IA(Niveau level)
    {
        this.level = level;
    }

    public void solve()
    {

        AI_pathing = new Pathing<GameState>();
        AI_pathing.Dijkstra(new GameState(level));
        GameState solved_state = new GameState(new Niveau());
        AI_pathing.shortest_paths.get(solved_state);

    }


    // Put the cursor moving code here TODO

}
