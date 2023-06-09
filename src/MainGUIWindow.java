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
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(500,500);
        ImageIcon logo = new ImageIcon("bomb.png"); //Create image icon for the logo
        getContentPane().setBackground(new Color(192, 192, 192)); //set frame color.
        setIconImage(logo.getImage()); //Change the icon of the frame

        //Adding text to base frame for difficulty selections
        JLabel startingScreen = new JLabel("Select Difficulty:");
        startingScreen.setFont(new Font("Arial", Font.PLAIN, 30));
        startingScreen.setPreferredSize(new Dimension(100,100));
        startingScreen.setHorizontalAlignment(JLabel.CENTER);
        startingScreen.setVerticalAlignment(JLabel.CENTER);

        //Difficulty Buttons
        JButton easyMode = new JButton("Easy");
        JButton mediumMode = new JButton("Medium");
        JButton hardMode = new JButton("Hard");

        //Button Settings
        easyMode.setFocusable(false);
        easyMode.setSize(50,100);
        easyMode.addActionListener(e -> {new Grid(1);dispose();});
        mediumMode.setFocusable(false);
        mediumMode.setSize(50,100);
        mediumMode.addActionListener(e -> {new Grid(2);dispose();});
        hardMode.setFocusable(false);
        hardMode.setSize(50,100);
        hardMode.addActionListener(e -> {new Grid(3);dispose();});

        //JPanel to group all the buttons
        JPanel selectDifficulty = new JPanel();
        selectDifficulty.setLayout(new GridLayout(3,1));
        selectDifficulty.add(easyMode);
        selectDifficulty.add(mediumMode);
        selectDifficulty.add(hardMode);


        //Adding everything to frame.
        add(startingScreen,BorderLayout.NORTH);
        add(selectDifficulty,BorderLayout.CENTER);
        revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
