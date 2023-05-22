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

    private void generateBomb(){

        bomb = true;
    }

}
