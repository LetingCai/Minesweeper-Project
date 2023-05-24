import javax.swing.*;

public class Tile extends JButton{
    private boolean flag;
    private boolean bomb;
    private boolean shown;
    private static int numBombs;
    private int nearbyBombs;
    public Tile(){
        add(new JButton());
        
    }

    public void setNumBombs(int num) {
        numBombs = num;
    }

    public static void generateBomb() {
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
                System.exit(0);
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

    public void number() {
        for (int row = 0; row < Grid.map.length; row++) {
            for (int col = 0; col < Grid.map[0].length; col++) {
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i > 0 && i < Grid.map.length - 1 && j > 0 && j < Grid.map[0].length - 1 && Grid.map[i][j].bomb) {
                            Grid.map[row][col].nearbyBombs++;
                        }
                    }
                }
            }
        }
    }
}
