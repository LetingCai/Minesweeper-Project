import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GameBoard extends JFrame implements MouseListener {
    //Image Icons for GUI
    private static final ImageIcon[] img = new ImageIcon[]{new ImageIcon("flag.png"), new ImageIcon("bomb.png"), new ImageIcon("tile.png"), new ImageIcon("empty_tile.png")};

    //General Game Board construction parameters
    private final JLabel[][] map;
    private final boolean[][] bomb;
    private final boolean[][] flag;
    private final boolean[][] shown;
    private final int[][] nearbyBombs;
    private JLabel statusBar;
    private final JPanel board;
    private final int numBombs;

    public GameBoard(int boardWidth, int boardHeight, int numberOfBombs){

        //-------------Generates the Minesweeper Game Frame------------//
        map = new JLabel[boardHeight][boardWidth];
        bomb = new boolean[boardHeight][boardWidth];
        flag = new boolean[boardHeight][boardWidth];
        shown = new boolean[boardHeight][boardWidth];
        nearbyBombs = new int[boardHeight][boardWidth];
        numBombs = numberOfBombs;
        board = new JPanel(new GridLayout(boardHeight,boardWidth));
        fillMap();
        generateFrame();
        //-------------------------------------------------------------//

    }

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




    //----------------------------------Private Helpers-------------------------------//

    private void fillMap(){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[0].length; col++){
                map[row][col] = new JLabel(new ImageIcon(getScaledImage(img[2].getImage(),30,30)));
                board.add(map[row][col]);
            }
        }
    }
    private Image getScaledImage(Image srcImg, int width, int height){
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, width, height, null);
        g2.dispose();

        return resizedImg;
    }
    private void generateFrame() {
        //Frame Setting
        setTitle("Totally Functional Minesweeper Game"); //Name the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close frame on exit.
        setVisible(true); //Show frame.
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(map[0].length * 30, map.length * 30 + 50);
        ImageIcon logo = new ImageIcon("bomb.png"); //Create image icon for the logo
        getContentPane().setBackground(new Color(192, 192, 192)); //set frame color.
        setIconImage(logo.getImage()); //Change the icon of the frame

        //Add a status bar showing the number of flags placed.
        statusBar = new JLabel(String.valueOf(numBombs));
        statusBar.setIcon(new ImageIcon(getScaledImage(img[0].getImage(),40,40)));
        statusBar.setFont(new Font("Arial", Font.PLAIN, 30));
        statusBar.setHorizontalAlignment(JLabel.CENTER);
        add(statusBar, BorderLayout.NORTH);

        board.setBackground(new Color(192, 192, 192));
        add(board, BorderLayout.CENTER);

        revalidate();
    }

}
