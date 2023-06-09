import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GameBoard extends JFrame implements MouseListener, ActionListener {
    public static void main(String[] args){
        new GameBoard();
    }
    //Image Icons for GUI
    private static final ImageIcon[] img = new ImageIcon[]{new ImageIcon("0.png"), new ImageIcon("1.png"), new ImageIcon("2.png"),new ImageIcon("3.png"), new ImageIcon("4.png"), new ImageIcon("5.png"), new ImageIcon("6.png"), new ImageIcon("7.png"), new ImageIcon("8.png"), new ImageIcon("bomb.png"), new ImageIcon("flag.png"),  new ImageIcon("tile.png")};

    //General Game Board construction
    private JLabel statusBar;
    private final JPanel board;
    private int numBombs;
    private int width;
    private int height;
    private boolean initialized;
    private int numberOfNonBombs;

    //JButtons:
    private final JButton easyMode = new JButton("Beginner");
    private final JButton mediumMode = new JButton("Intermediate");
    private final JButton hardMode = new JButton("Expert");

    //The tiles:
    private final JLabel[][] map;
    private final int[][] shownFlagNeither; // 0 = Neither shown nor flagged; 1 = flagged; 2 = Shown;
    private final int[][] nearbyBombs;


    public GameBoard(){
        //Setting up the base frame
        setTitle("Totally Functional Minesweeper Game"); //Name the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close frame on exit.
        setVisible(true); //Show frame.
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(500,500);
        ImageIcon logo = img[9]; //Create image icon for the logo
        getContentPane().setBackground(new Color(192, 192, 192)); //set frame color.
        setIconImage(logo.getImage()); //Change the icon of the frame

        //Adding text to base frame for difficulty selections
        JLabel startingScreen = new JLabel("Select Difficulty:");
        startingScreen.setFont(new Font("Arial", Font.PLAIN, 30));
        startingScreen.setPreferredSize(new Dimension(100,100));
        startingScreen.setHorizontalAlignment(JLabel.CENTER);
        startingScreen.setVerticalAlignment(JLabel.CENTER);

        //Button Settings
        easyMode.setFocusable(false);
        easyMode.addActionListener(e -> {new GameBoard();dispose();});
        mediumMode.setFocusable(false);
        mediumMode.addActionListener(e -> {new Grid(2);dispose();});
        hardMode.setFocusable(false);
        hardMode.addActionListener(this);

        //JPanel to group all the buttons
        JPanel selectDifficulty = new JPanel();
        selectDifficulty.setLayout(new GridLayout(3,1));
        selectDifficulty.add(easyMode);
        selectDifficulty.add(mediumMode);
        selectDifficulty.add(hardMode);
        selectDifficulty.setSize(100,100);


        //Adding everything to frame.
        add(startingScreen,BorderLayout.NORTH);
        add(selectDifficulty,BorderLayout.CENTER);
        repaint();
        revalidate();

    }

    //---------------------------Active Methods----------------------------//
    private void showTile(int row, int col){
        int nearbyBombs = this.nearbyBombs[row][col];
        if (!initialized){
            initialization(row, col);
        }

        if (shownFlagNeither[row][col] == 0) {
            if (nearbyBombs == 9) {
                System.out.println("BOOM!");
            }

            changeTileImage(map[row][col], nearbyBombs);
            shownFlagNeither[row][col] = 2;
            numberOfNonBombs--;

            if (nearbyBombs == 0) {
                for (int i = row - 1; i < row + 2; i++) {
                    for (int k = col - 1; k < col + 2; k++) {
                        if (i >= 0 && i < height && k >= 0 && k < width && this.nearbyBombs[i][k] != 9) {
                            showTile(i,k);
                        }
                    }
                }
            }

            if (numberOfNonBombs == 0){

                getContentPane().removeAll();
                revalidate();
                repaint();
            }
        }
    }

    public void middleClick(int row, int col){
        if (nearbyBombs[row][col] == check3by3(row,col,shownFlagNeither,1) && shownFlagNeither[row][col] == 2){
            for (int i = row-1; i < row+2; i++){
                for (int k = col-1; k<col+2; k++){
                    if (i >= 0 && i < height && k >= 0 && k < width ){
                        showTile(i,k);
                    }
                }
            }
            System.out.println("Middle Click");
        }
    }

    private void initialization(int row, int col){
        System.out.println("Initialized");
        initialized = true;
        int bombs = check3by3(row, col,nearbyBombs, 9);

        for (int i = row-1; i < row+2; i++){
            for (int k = col-1; k<col+2; k++){
                if (i >= 0 && i < height && k >= 0 && k < width){
                    if (nearbyBombs[i][k] == 9){
                        nearbyBombs[i][k] = 0;
                    }
                    showTile(i,k);
                }
            }
        }

        generateBomb(bombs);

        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                if (nearbyBombs[i][k] != 9){
                    nearbyBombs[i][k] = check3by3(i,k,nearbyBombs,9);
                }
                map[i][k].setText(String.valueOf(nearbyBombs[i][k]));
                if (shownFlagNeither[i][k] == 2){
                    changeTileImage(map[i][k],nearbyBombs[i][k]);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row =0, col = 0;

        for (int i = 0; i < height; i++){
            for (int k = 0; k <width; k++){
                if (map[i][k].hashCode() == e.getSource().hashCode()){
                    row = i;
                    col = k;
                }
            }
        }

        if (e.getButton() == MouseEvent.BUTTON1) { //Left Click; Reveal a single block
            System.out.println("Left Click");
            showTile(row, col);
        }

        if (e.getButton() == MouseEvent.BUTTON2) {//Middle Click; Reveal a 3 by 3 tile
            middleClick(row, col);
        }

        if (initialized){
            if (e.getButton() == MouseEvent.BUTTON3){ //Right Click; Flagging the tile
                System.out.println("Right Click");
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
        }
        System.out.println("Clicked");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easyMode){
            width = 8;
            height = 8;
            numBombs = 10;
        }
        if (e.getSource() == mediumMode){
            width = 15;
            height = 12;
            numBombs = 10;
        }
        if (e.getSource() == hardMode){
            width = 30;
            height = 16;
            numBombs = 99;
        }

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
                    nearbyBombs[row][col] = check3by3(row, col,nearbyBombs,9);
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
            if (nearbyBombs[row][col] != 9 && shownFlagNeither[row][col] == 0) {
                nearbyBombs[row][col] = 9;
                i++;
            }
        }
    }

    private int check3by3(int row, int col, int[][] list, int condition){
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
        setSize(width * 30, height * 30 + 50);

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
