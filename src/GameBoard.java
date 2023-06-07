import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GameBoard extends JFrame implements MouseListener {
    //Image Icons for GUI
    private static final ImageIcon[] img = new ImageIcon[]{new ImageIcon("0.png"), new ImageIcon("1.png"), new ImageIcon("2.png"),new ImageIcon("3.png"), new ImageIcon("4.png"), new ImageIcon("5.png"), new ImageIcon("6.png"), new ImageIcon("7.png"), new ImageIcon("8.png"), new ImageIcon("bomb.png"), new ImageIcon("flag.png"),  new ImageIcon("tile.png")};

    //General Game Board construction parameters
    private final JLabel[][] map;
    private final int[][] shownFlagNeither; // 0 = Neither shown nor flagged; 1 = flagged; 2 = Shown;
    private final int[][] nearbyBombs;
    private JLabel statusBar;
    private final JPanel board;
    private int numBombs;
    private final int width;
    private final int height;
    private boolean initialized;

    public GameBoard(int boardWidth, int boardHeight, int numberOfBombs){
        width = boardWidth;
        height = boardHeight;

        //-------------Generates the Minesweeper Game Frame------------//
        map = new JLabel[boardHeight][boardWidth];
        shownFlagNeither = new int[boardHeight][boardWidth];
        nearbyBombs = new int[boardHeight][boardWidth];
        numBombs = numberOfBombs;
        board = new JPanel(new GridLayout(boardHeight,boardWidth));
        initialized = false;
        generateBomb(numberOfBombs);
        fillMap();
        generateFrame();
        //-------------------------------------------------------------//

    }

    //---------------------------Active Methods----------------------------//
    private void showTile(int hashcode){
        int row = getRow(hashcode);
        int col = getCol(hashcode);
        int nearbyBombs = this.nearbyBombs[row][col];
        if (!initialized){
            System.out.println("Initialized");
            initialized = true;
        }

        if (shownFlagNeither[row][col] == 0) {
            if (nearbyBombs == 9) {
                System.out.println("BOOM!");
            }
            changeTileImage(map[row][col], nearbyBombs);
            shownFlagNeither[row][col] = 2;

            if (nearbyBombs == 0) {
                for (int i = row - 1; i < row + 2; i++) {
                    for (int k = col - 1; k < col + 2; k++) {
                        if (i >= 0 && i < height && k >= 0 && k < width && this.nearbyBombs[i][k] != 9) {
                            showTile(map[i][k].hashCode());
                        }
                    }
                }
            }
        }
    }

    private void flagTile(int hashcode){
            int row = getRow(hashcode);
            int col = getCol(hashcode);
        switch (shownFlagNeither[row][col]) {
            case 1 -> {
                changeTileImage(map[row][col], 11);
                shownFlagNeither[row][col] = 0;
                numBombs++;
                statusBar.setText(String.valueOf(numBombs));
            }
            case 0 -> {
                changeTileImage(map[row][col], 10);
                shownFlagNeither[row][col] = 1;
                numBombs--;
                statusBar.setText(String.valueOf(numBombs));
            }
        }
    }

    public void middleClick(int hashcode){
        int row = getRow(hashcode);
        int col = getCol(hashcode);
        if (nearbyBombs[row][col] == check3by3(hashcode,shownFlagNeither,1) && shownFlagNeither[row][col] == 2){
            for (int i = row-1; i < row+2; i++){
                for (int k = col-1; k<col+2; k++){
                    if (i >= 0 && i < height && k >= 0 && k < width ){
                        showTile(map[i][k].hashCode());
                    }
                }
            }
            System.out.println("Middle Click");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) { //Left Click
            System.out.println("Left Click");
            showTile(e.getSource().hashCode());
        }

        if (initialized){
            if (e.getButton() == MouseEvent.BUTTON3){ //Right Click
                System.out.println("Right Click");
                flagTile(e.getSource().hashCode());
            }

            if (e.getButton() == MouseEvent.BUTTON2) {//Middle Click
                middleClick(e.getSource().hashCode());
            }
        }
        System.out.println("Clicked");
    }

    //----------------------------------Private Helpers-------------------------------//

    private void changeTileImage(JLabel label, int positionOfImage){
        label.setIcon( new ImageIcon(getScaledImage(img[positionOfImage].getImage(),30,30)));
    }

    private void fillMap(){
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                map[row][col] = new JLabel(new ImageIcon(getScaledImage(img[11].getImage(), 30, 30)));
                map[row][col].addMouseListener(this);
                board.add(map[row][col]);
                shownFlagNeither[row][col] = 0;
                if (nearbyBombs[row][col]!=9) {
                    nearbyBombs[row][col] = check3by3(map[row][col].hashCode(),nearbyBombs,9);
                }
                map[row][col].setText(String.valueOf(nearbyBombs[row][col]));
                map[row][col].setHorizontalTextPosition(JLabel.CENTER);
                map[row][col].setVerticalTextPosition(JLabel.CENTER);
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

    private int check3by3(int hashcode, int[][] list, int condition){
        int row = getRow(hashcode);
        int col = getCol(hashcode);
        int count = 0;
        for (int i = row-1; i < row+2; i++){
            for (int k = col-1; k<col+2; k++){
                if (i >= 0 && i < height && k >= 0 && k < width && list[i][k] == condition){
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
        statusBar.setIcon(new ImageIcon(getScaledImage(img[10].getImage(),40,40)));
        statusBar.setFont(new Font("Arial", Font.PLAIN, 30));
        statusBar.setHorizontalAlignment(JLabel.CENTER);
        add(statusBar, BorderLayout.NORTH);

        board.setBackground(new Color(192, 192, 192));
        add(board, BorderLayout.CENTER);

        revalidate();
    }



    //-------------------------Useless Junk (yet)-------------------//
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
