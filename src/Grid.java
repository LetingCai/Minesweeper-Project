import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Grid extends JFrame{
    public static Tile[][] map;
    public static JLabel statusBar;
    public static JPanel board = new JPanel();
    public Grid(int difficulty){
        switch (difficulty) {
            case 1 -> {
                map = new Tile[8][8];
                board.setLayout(new GridLayout(8,8));
                Tile.setNumBombs(10);
            }// 8 by 8 grid
            case 2 -> {
                map = new Tile[13][15];
                board.setLayout(new GridLayout(13,15));
                Tile.setNumBombs(40);
            } // 13 by 15 grid
            case 3 -> {
                map = new Tile[16][30];
                board.setLayout(new GridLayout(16,30));
                Tile.setNumBombs(99);
            } // 16 by 30 grid
        }
        fillGrid();
        Tile.number();
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
        setSize(map[0].length*30,map.length*30 + 50);
        ImageIcon logo = new ImageIcon("bomb.png"); //Create image icon for the logo
        getContentPane().setBackground(new Color(192, 192, 192)); //set frame color.
        setIconImage(logo.getImage()); //Change the icon of the frame

        //Add a status bar showing the number of flags placed.
        statusBar = new JLabel(String.valueOf(Tile.getNumBombs()));
        ImageIcon img = new ImageIcon("flag.png");
        statusBar.setIcon(new ImageIcon(getScaledImage(img.getImage())));
        statusBar.setFont(new Font("Arial", Font.PLAIN, 30));
        statusBar.setHorizontalAlignment(JLabel.CENTER);
        add(statusBar,BorderLayout.NORTH);

        board.setBackground(new Color(192, 192, 192));
        add(board,BorderLayout.CENTER);

        revalidate();
    }

    private Image getScaledImage(Image srcImg){
        BufferedImage resizedImg = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, 40, 40, null);
        g2.dispose();

        return resizedImg;
    }

}
