import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

//WRONG TIMER NEED TO USE JAVAX.SWING TIMER TO ACCESS START METHOD
import javax.swing.Timer;

/**
 * @author Tyler Wallace
 */
public class Panel extends JPanel implements ActionListener {

    static final int PANEL_WIDTH = 600;
    static final int PANEL_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (PANEL_WIDTH * PANEL_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    int bodyParts = 6;
    int applesEaten = 0;
    int appleXLocation = 0;
    int appleYLocation = 0;
    char direction = 'R';
    boolean running = false;


    Random random;
    Timer timer;


    Panel(){
        random = new Random();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_WIDTH));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;

        //NEED TO USE JAVAX.SWING TIMER TO ACCESS START METHOD
        timer = new Timer(DELAY,this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGrid(g);
        draw(g);
    }

    public void paintSnake(Graphics g){

    }

    /**
     * Method to illustrate grid like nature of the board
     * in relation to unit size i.e apple or snake head...
     * @param g
     */
    public void drawGrid(Graphics g){

        for ( int i = 0; i < PANEL_HEIGHT/UNIT_SIZE;i++){
            g.setColor(Color.white);
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,PANEL_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, PANEL_WIDTH,i*UNIT_SIZE);
        }

    }

    /**
     * Method to draw head and body of snake.
     * Randomly places apple.
     * Paints Snake
     * @param g
     */
    public void draw(Graphics g){

        //Apple color, coordinates and size.
        g.setColor(Color.RED);
        g.fillRect(appleXLocation, appleYLocation,UNIT_SIZE, UNIT_SIZE);

        //loop to keep track and add to snake array this maintains the head color/spawn/size
        for (int i = 0; i< bodyParts; i++){
            if (i==0){
                g.setColor(Color.green);
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }

            //this maintains the body same conditions as head.
            else{
                g.setColor(new Color(45,180,0));
                g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
            }
        }
    }

    /**
     * create apple at random location
     */
    public void newApple(){

        //multiple by unit_size at end to get evenly in grid.
        appleXLocation = random.nextInt((int)PANEL_WIDTH/UNIT_SIZE) * UNIT_SIZE;
        appleYLocation = random.nextInt((int)PANEL_HEIGHT/UNIT_SIZE) * UNIT_SIZE;

    }

    /**
     * move snake
     * Uses switch-case to change direction based on char var "direction"
     * Represented by: 'U' for up, 'D' for down, etc.
     */
    public void move(){
        
        for (int i = bodyParts; i > 0 ; i--){

           //shifting all coordinates for array over by 1
            x[i] = x[i-1];
            y[i] = y[i-1];

        }

        //Switch statement to change direction of snake's movement. indicative of direction i.e up.
        //Changes value of char direction variable.
        switch (direction){

            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;

            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;

            case 'L':
                x[0] = x[0]- UNIT_SIZE;
                break;

            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    public void checkApple(){

    }

    /**
     * check whether head, or any body part, collides with wall
     * x[0] && y[0] indicative of head of snake
     * iterates through entire snake.
     */
    public void checkCollision(){
        //checks if head collides with body
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i] && y[0] == y[i])){
                //trigger game over method
                running = false;
            }
        }
        //check if head collides with left wall
        if (x[0] < 0) {
            running = false;
            System.out.println("game over");
        }
        //check collision with right wall
        if (x[0] > PANEL_WIDTH){
            running = false;
            System.out.println("game over");
        }
        //check collision with top wall
        if (y[0] < 0) {
            running = false;
            System.out.println("game over");
        }
        //check collision with bottom wall
        if (y[0] > PANEL_WIDTH){
            running = false;
            System.out.println("game over");
        }
        if(!running){
            timer.stop();
        }
       
    }

    public void gameOver(Graphics g){

    }
   //SubClass used for global keyAdapter
    public class MyKeyAdapter extends KeyAdapter{

        @SuppressWarnings("AlibabaSwitchStatement")
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()){
                //dont want user to be able to turn 180 degrees. Limit to 90 degree turns.
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;

                //prevent 180
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){

                        direction = 'R';
                    }
                        break;

                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;

            }


        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }



}
