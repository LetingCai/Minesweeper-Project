import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Tile extends JFrame implements MouseListener{
    private boolean flag;
    private boolean bomb;
    private boolean shown;
    private static boolean initialized;
    private static int numBombs;
    private int nearbyBombs;
    private final JLabel label;

    public Tile(){
        ImageIcon tiles = new ImageIcon("tile.png");
        label = new JLabel();
        label.setIcon(new ImageIcon(getScaledImage(tiles.getImage(),30,30)));
        label.setOpaque(true);
        label.addMouseListener(this);
        Grid.board.add(label);

        revalidate();
    }

    public static void setNumBombs(int num) {
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

    public void showTile() {
        if (!flag) {
            if (bomb) {
                System.exit(0);
            } else {
                shown = true;
                label.setText(String.valueOf(nearbyBombs));
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.CENTER);
                label.removeMouseListener(this);
                revalidate();
            }
        }
    }

    public void flagTile() {
        if (!shown && !flag) {
            numBombs--;
            flag = true;
            ImageIcon flag = new ImageIcon("flag.png");
            label.setIcon(new ImageIcon(getScaledImage(flag.getImage(),30,30)));
        } else if (!shown){
            numBombs++;
            flag = false;
            ImageIcon tiles = new ImageIcon("tile.png");
            label.setIcon(new ImageIcon(getScaledImage(tiles.getImage(),30,30)));
        }
        Grid.statusBar.setText(String.valueOf(numBombs));
        revalidate();
    }

    public static void number() {
        for (int row = 0; row < Grid.map.length; row++) {
            for (int col = 0; col < Grid.map[0].length; col++) {
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i >= 0 && i < Grid.map.length && j >= 0 && j < Grid.map[0].length && Grid.map[i][j].bomb) {
                            Grid.map[row][col].nearbyBombs++;
                        }
                    }
                }
            }
        }
    }

    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public static int getNumBombs(){
        return numBombs;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3){ //Right Click
            System.out.println("Right Click?");
            flagTile();
        }
        if (e.getButton() == MouseEvent.BUTTON1){ //Left Click
            if (!initialized){
                initialized = true;
                bomb = false;
            }
            showTile();
            System.out.println("Left Click?");
        }
        if (e.getButton() == MouseEvent.BUTTON2){
            System.out.println("Middle Click?"); //Middle Click
        }
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
