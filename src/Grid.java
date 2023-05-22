import java.util.ArrayList;

public class Grid {
    private Tile[][] map;
    public Grid(int difficulty){
        switch (difficulty) {
            case 1 -> {
                map = new Tile[8][8]; // 8 by 8 grid
                fillGrid();
            }
            case 2 -> {
                map = new Tile[13][15]; // 13 by 15 grid
                fillGrid();
            }
            case 3 -> {
                map = new Tile[16][30]; // 16 by 30 grid
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
