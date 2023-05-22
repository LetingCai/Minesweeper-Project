public class Tile {
    private boolean flag;
    private boolean bomb;
    private boolean shown;
    private static int numBombs;
    public Tile(){
        bomb = false;
        shown = false;
        flag = false;
    }

    public void setNumBombs(int num){
        numBombs = num;
    }

    public void generateBomb(){

        bomb = true;
    }

}
