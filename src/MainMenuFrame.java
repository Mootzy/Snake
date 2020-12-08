import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class MainMenuFrame extends JFrame {
    private int playButtonValue = 0;


    Random random;

    //variables for panel dimensions
    static final int PANEL_WIDTH = 600;
    static final int PANEL_HEIGHT = 600;

    JButton playButton = new JButton("Play");


    MainMenuFrame(){
        makePlayButton();
        setContent();
    }

    public void playButtonAction(ActionEvent e) {
        if(e.getSource() == playButton){
            this.dispose();
            new Frame();
            playButtonValue = 1;

        }
    }
    public int getPlayButtonValue(){
        return playButtonValue;
    }
    public void setContent(){

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setTitle("Tyler's Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(playButton);
        this.pack();
        this.setVisible(true);
    }
    public void makePlayButton(){
        playButton.setBounds(300,500,100,50);
        playButton.setVisible(true);
        playButton.addActionListener(this::playButtonAction);
        playButton.setBackground(Color.BLACK);
        playButton.requestFocus();
    }
}
