import javax.swing.*;
import java.awt.*;

public class MainGUIWindow extends JFrame{
    public static void main(String[] args){
        new MainGUIWindow();
    }

    public MainGUIWindow(){

        //Setting up the base frame
        setTitle("Totally Functional Minesweeper Game"); //Name the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close frame on exit.
        setVisible(true); //Show frame.
        ImageIcon logo = new ImageIcon("bomb.png"); //Create image icon for the logo
        setIconImage(logo.getImage()); //Change the icon of the frame
        getContentPane().setBackground(new Color(192, 192, 192)); //set frame color.

        //Adding text to base frame for difficulty selections
        JLabel startingScreen = new JLabel("Select Difficulty:");

        startingScreen.setHorizontalAlignment(JLabel.CENTER);
        startingScreen.setVerticalAlignment(JLabel.CENTER);

        //Adding everything to frame.
        add(startingScreen);

    }

}
