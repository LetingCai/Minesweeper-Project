import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUIWindow extends JFrame implements ActionListener {
    public static void main(String[] args){
        new MainGUIWindow();
    }

    public MainGUIWindow(){

        //Setting up the base frame
        setTitle("Totally Functional Minesweeper Game"); //Name the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close frame on exit.
        setVisible(true); //Show frame.
        setResizable(true);
        setSize(500,500);
        ImageIcon logo = new ImageIcon("bomb.png"); //Create image icon for the logo
        setIconImage(logo.getImage()); //Change the icon of the frame
        getContentPane().setBackground(new Color(192, 192, 192)); //set frame color.

        //Adding text to base frame for difficulty selections
        JLabel startingScreen = new JLabel("Select Difficulty:");
        startingScreen.setHorizontalAlignment(JLabel.CENTER);
        startingScreen.setVerticalAlignment(JLabel.CENTER);

        //Difficulty Buttons
        JButton easyMode = new JButton("Easy");
        JButton mediumMode = new JButton("Medium");
        JButton hardMode = new JButton("Hard");

        //Button Settings
        easyMode.setBounds(JButton.BOTTOM,JButton.BOTTOM,100, 100);
        easyMode.setFocusable(false);
        mediumMode.setBounds(JButton.TOP,200,100,100);
        hardMode.setBounds(100,JButton.TOP,100,100);



        //Adding everything to frame.
        add(startingScreen);
        add(easyMode);
        add(mediumMode);
        add(hardMode);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
