public ArrayList<int []> convert(){
        Path<GameState> temp = this.shortest_path;
        ArrayList<int []> list = null;
        int[] pos;
        while (temp.prefix != null){
            Path<PositionInLevel> nom = temp.position.reachable_box_path.shortest_paths.
            while(nom.prefix != null){
                pos = nom.position;
                list.add(pos);
                nom = nom.prefix;
            }
            temp = temp.prefix;
        }
        return list;
    }
