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
    private final boolean[][] flag;
    private final boolean[][] shown;
    private final int[][] nearbyBombs;
    private JLabel statusBar;
    private final JPanel board;
    private final int numBombs;
    private final int width;
    private final int height;
    private final boolean initialized;

    public GameBoard(int boardWidth, int boardHeight, int numberOfBombs){
        width = boardWidth;
        height = boardHeight;

        //-------------Generates the Minesweeper Game Frame------------//
        map = new JLabel[boardHeight][boardWidth];
        flag = new boolean[boardHeight][boardWidth];
        shown = new boolean[boardHeight][boardWidth];
        nearbyBombs = new int[boardHeight][boardWidth];
        numBombs = numberOfBombs;
        board = new JPanel(new GridLayout(boardHeight,boardWidth));
        initialized = false;
        generateBomb(numberOfBombs);
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
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                map[row][col] = new JLabel(new ImageIcon(getScaledImage(img[2].getImage(), 30, 30)));
                board.add(map[row][col]);
                flag[row][col] = false;
                shown[row][col] = false;
                nearbyBombs[row][col] = 0;
            }
        }
    }

    private void generateBomb(int num) {
        int i = 0;
        while (i < num) {
            int row = (int) (Math.random() * height);
            int col = (int) (Math.random() * width);
            if (nearbyBombs[row][col] != 9) {
                nearbyBombs[row][col] = 9;
                i++;
            }
        }
    }

    private int check3by3(int hashcode){
        int row = getRow(hashcode);
        int col = getCol(hashcode);
        int count = 0;
        for (int i = row-1; i < row+2; i++){
            for (int k = col-1; k<col+2; k++){
                if (i >= 0 && i<height && k >= 0 && k < width && nearbyBombs[row][col] == 9){
                    count++;
                }
            }
        }
        return count;
    }

    private int getRow(int hashcode){
        for (int i = 0; i < height; i++){
            for (int k = 0; k <width; k++){
                if (map[i][k].hashCode() == hashcode){
                    return i;
                }
            }
        }
        return -1;
    }

    private int getCol(int hashcode){
        for (int i = 0; i < height; i++){
            for (int k = 0; k <width; k++){
                if (map[i][k].hashCode() == hashcode){
                    return k;
                }
            }
        }
        return -1;
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
        setSize(width * 30, height * 30 + 50);
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
