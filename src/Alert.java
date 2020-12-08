import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Alert extends JFrame implements ActionListener  {

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    //text to be displayed to frame
    JTextArea gameOver = new JTextArea("GAME OVER");

    //needed to use String.valueOf as opposed to typeCast to properly get playerScore
    JTextArea score = new JTextArea();
    JButton reset = new JButton("restart");
    JButton quit = new JButton("quit");

    Alert (){

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.setTitle("Play Again?");
        this.setBounds(550,300,420,420);
        this.setResizable(false);
        this.setContentPane(new Container());
        this.add(gameOver);
        this.add(score);
        this.add(reset);
        this.add(quit);
    }

    public void generateContent(){
        //text to be displayed to frame
        JTextArea gameOver = new JTextArea("GAME OVER");

        //needed to use String.valueOf as opposed to typeCast to properly get playerScore
        JTextArea score = new JTextArea();

        //Score tracker constraints
        score.setText("hi");
        score.setFont(new Font(null, Font.BOLD, 50));
        score.setVisible(true);

        score.setBounds(190,200,200,50);

        score.setFont(new Font(null, Font.CENTER_BASELINE,25));

        //game over text area constraints
        gameOver.setVisible(true);

        gameOver.setText("GAME OVER");
        gameOver.setBounds(120,100,200,50);

        gameOver.setFont(new Font(null, Font.CENTER_BASELINE,25));


    }
}
