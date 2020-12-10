import javax.swing.*;

public class Frame extends JFrame {


    Frame(){

        startGamePanel();
        addContent();

    }

    /**
     *
     */
     public void startGamePanel(){
        this.add(new GamePanel());
        this.requestFocus();
     }

    public void addContent(){

        this.setLocationRelativeTo(null);this.setTitle("Tyler's Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

    }











}
