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
        private JPanel board;
        private int numBombs;
        private int width;
        private int height;
        private boolean initialized;

        //JButtons:
        private final JButton easyMode = new JButton("Beginner");
        private final JButton mediumMode = new JButton("Intermediate");
        private final JButton hardMode = new JButton("Expert");
        private final JButton restart = new JButton("Restart");

        //The tiles:
        private JLabel[][] map;
        private int[][] shownFlagNeither; // 0 = Neither shown nor flagged; 1 = flagged; 2 = Shown;
        private int[][] nearbyBombs;


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
            easyMode.addActionListener(this);
            mediumMode.setFocusable(false);
            mediumMode.addActionListener(this);
            hardMode.setFocusable(false);
            hardMode.addActionListener(this);
            restart.setFocusable(false);
            restart.addActionListener(this);

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
                System.out.println("Initialized");
                initialized = true;
                int bombs = check3by3(row, col,this.nearbyBombs, 9);

                for (int i = row-1; i < row+2; i++){
                    for (int k = col-1; k<col+2; k++){
                        if (i >= 0 && i < height && k >= 0 && k < width){
                            if (this.nearbyBombs[i][k] == 9){
                                this.nearbyBombs[i][k] = 0;
                            }
                            shownFlagNeither[i][k] = 2;
                        }
                    }
                }

                generateBomb(bombs);

                for (int i = 0; i < height; i++) {
                    for (int k = 0; k < width; k++) {
                        if (this.nearbyBombs[i][k] != 9){
                            this.nearbyBombs[i][k] = check3by3(i,k,this.nearbyBombs,9);
                        }
                        map[i][k].setText(String.valueOf(this.nearbyBombs[i][k]));
                        if (shownFlagNeither[i][k] == 2){
                            changeTileImage(map[i][k],this.nearbyBombs[i][k]);
                        }
                    }
                }

                for (int i = 0; i < height; i++) {
                    for (int k = 0; k < width; k++) {
                        if(shownFlagNeither[i][k] == 2 && this.nearbyBombs[i][k] == 0){
                            middleClick(i,k);
                        }
                    }
                }

            }

            if (shownFlagNeither[row][col] == 0) {
                if (nearbyBombs == 9) {
                    System.out.println("BOOM!");
                    getContentPane().removeAll();
                    JLabel lose = new JLabel("You Lost!");
                    restart.setText("Retry");
                    add(lose, BorderLayout.CENTER);
                    add(restart, BorderLayout.NORTH);
                    lose.setVerticalTextPosition(JLabel.CENTER);
                    lose.setVerticalTextPosition(JLabel.CENTER);
                    lose.setHorizontalAlignment(JLabel.CENTER);

                    revalidate();
                    repaint();
                }
                changeTileImage(map[row][col], nearbyBombs);
                shownFlagNeither[row][col] = 2;
                if (nearbyBombs == 0) {
                    middleClick(row,col);
                }
            }
            if (win()){
                getContentPane().removeAll();
                JLabel win = new JLabel("You Won!");
                add(win, BorderLayout.CENTER);
                restart.setText("Play Again");
                add(restart, BorderLayout.NORTH);
                win.setHorizontalTextPosition(JLabel.CENTER);
                win.setVerticalTextPosition(JLabel.CENTER);
                win.setHorizontalAlignment(JLabel.CENTER);
                revalidate();
                repaint();
            }
        }

        private void middleClick(int row, int col){
            if (nearbyBombs[row][col] == check3by3(row,col,shownFlagNeither,1) && shownFlagNeither[row][col] == 2){
                for (int i = row-1; i < row+2; i++){
                    for (int k = col-1; k<col+2; k++){
                        if (i >= 0 && i < height && k >= 0 && k < width && shownFlagNeither[i][k] == 0){
                            showTile(i,k);
                        }
                    }
                }
                System.out.println("Middle Click");
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

                if (e.getButton() == MouseEvent.BUTTON2) {//Middle Click; Reveal a 3 by 3 tile
                    middleClick(row, col);
                }
            }
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
                numBombs = 40;

            }
            if (e.getSource() == hardMode){
                width = 30;
                height = 16;
                numBombs = 99;
            }
            if (e.getSource() == restart){
                new GameBoard();
                dispose();
            }
            getContentPane().removeAll();
            initialized = false;
            map = new JLabel[height][width];
            shownFlagNeither = new int[height][width];
            nearbyBombs = new int[height][width];
            board = new JPanel(new GridLayout(height, width));
            generateBomb(numBombs);
            fillMap();
            generateFrame();
        }

        //----------------------------------Private Helpers-------------------------------//
        private boolean win() {
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    if (shownFlagNeither[r][c] == 0 || (shownFlagNeither[r][c] == 1 && nearbyBombs[r][c] != 9)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private void changeTileImage(JLabel label, int positionOfImage){
            label.setIcon( new ImageIcon(getScaledImage(img[positionOfImage].getImage(),30,30)));
        }

        private void fillMap(){
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    map[row][col] = new JLabel(new ImageIcon(getScaledImage(img[11].getImage(), 30, 30)));
                    map[row][col].addMouseListener(this);
                    board.add(map[row][col]);
                    if (nearbyBombs[row][col]!=9) {
                        nearbyBombs[row][col] = check3by3(row,col,nearbyBombs,9);
                    }
                    map[row][col].setHorizontalTextPosition(JLabel.CENTER);
                    map[row][col].setVerticalTextPosition(JLabel.CENTER);

                    //map[row][col].setText(String.valueOf(this.nearbyBombs[row][col]));
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

            repaint();
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
