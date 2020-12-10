import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * The type Main menu frame.
 */
public class MainMenuFrame extends JFrame {
    private int playButtonValue = 0;



    Random random;

    /**
     * The constant PANEL_WIDTH.
     */

    static final int PANEL_WIDTH = 600;
    /**
     * The Panel height.
     */
    static final int PANEL_HEIGHT = 600;

    /**
     * The Play button.
     */
    JButton playButton = new JButton("Play");

    /**
     * Instantiates a new Main menu frame.
     */
    MainMenuFrame(){
        makePlayButton();
        setContent();
    }

    /**
     * Play button action.
     *
     * @param e the event when clicked
     */
    public void playButtonAction(ActionEvent e) {
        if(e.getSource() == playButton){
            this.dispose();
            new Frame();
            playButtonValue = 1;

        }
    }

    /**
     * Set content.
     */
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

    /**
     * Make play button.
     */
    public void makePlayButton(){
        playButton.setBounds(300,500,100,50);
        playButton.setVisible(true);
        playButton.addActionListener(this::playButtonAction);
        playButton.setBackground(Color.BLACK);
        playButton.requestFocus();
    }
}
