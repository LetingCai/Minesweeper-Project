import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Grid extends JFrame implements MouseListener {
    public static Tile[][] map;
    public Grid(int difficulty){
        switch (difficulty) {
            case 1 -> map = new Tile[8][8]; // 8 by 8 grid
            case 2 -> map = new Tile[13][15]; // 13 by 15 grid
            case 3 -> map = new Tile[16][30]; // 16 by 30 grid
        }
        fillGrid();
        generateFrame();
    }

    private void fillGrid(){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[0].length; col++){
                map[row][col] = new Tile();
            }
        }
        Tile.generateBomb();
    }





    //GUI Stuff for the game page
    private void generateFrame(){
        //Frame Setting
        setTitle("Totally Functional Minesweeper Game"); //Name the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close frame on exit.
        setVisible(true); //Show frame.
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(500,500);
        ImageIcon logo = new ImageIcon("bomb.png"); //Create image icon for the logo
        getContentPane().setBackground(new Color(192, 192, 192)); //set frame color.
        setIconImage(logo.getImage()); //Change the icon of the frame
    }
    //Mouse Action Key Listeners:
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
