public class Tile {
    private boolean flag;
    private boolean bomb;
    private boolean shown;
    private static int numBombs;
    public Tile(){

    }

    public void setNumBombs(int num){
        numBombs = num;
    }

    public void generateBomb(){
        int i = 0;
        while (i < numBombs) {
            int row = (int) (Math.random() * Grid.map.length);
            int col = (int) (Math.random() * Grid.map[0].length);
            if (!Grid.map[row][col].bomb) {
                Grid.map[row][col].bomb = true;
                i++;
            }
        }
    }

    public void show() {
        if (!flag) {
            if (bomb) {

            } else {
                shown = true;
            }
        }
    }

    public void flag() {
        if (!shown && !flag) {
            numBombs--;
            flag = true;
        }
    }



}
