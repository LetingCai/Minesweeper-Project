import java.util.ArrayList;

public class Grid {
    private Tile[][] map;
    public Grid(int difficulty){
        switch (difficulty) {
            case 1 -> {
                map = new Tile[8][8];
                fillGrid();
            }
            case 2 -> {
                map = new Tile[13][15];
                fillGrid();
            }
            case 3 -> {
                map = new Tile[16][30];
                fillGrid();
            }
        }
    }

    private void fillGrid(){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[0].length; row++){
                map[row][col] = new Tile();
            }
        }
    }
}
