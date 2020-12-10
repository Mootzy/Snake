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
 * The type Game panel.
 *
 * @author Tyler Wallace
 */
public class GamePanel extends JPanel implements ActionListener {

    /**
     * The constant PANEL_WIDTH.
     */
//variables for panel dimensions
    static final int PANEL_WIDTH = 600;
    /**
     * The Panel height.
     */
    static final int PANEL_HEIGHT = 600;

    /**
     * The constant UNIT_SIZE.
     */
//size of apple, snake, etc.
    static final int UNIT_SIZE = 25;

    /**
     * The constant GAME_UNITS.
     */
//grid units of panel
    static final int GAME_UNITS = (PANEL_WIDTH * PANEL_HEIGHT) / UNIT_SIZE;

    /**
     * The constant DELAY i.e speed at which snake runs
     */
//speed that the game is running at.. would like to make this variable and progressively get faster as game progresses.
    static final int DELAY = 75;

    /**
     * The X and Y coordinates of the board/frame
     */
//
    final int[] x = new int[GAME_UNITS];
    /**
     * The Y.
     */
    final int[] y = new int[GAME_UNITS];

    /**
     * The Body parts.
     */
    int bodyParts = 1;
    /**
     * The Apples eaten.
     */
    int applesEaten = 0;
    /**
     * The Apple x location.
     */
    int appleXLocation = 0;
    /**
     * The Apple y location.
     */
    int appleYLocation = 0;

    /**
     * The Player score.
     */
    public Integer playerScore = applesEaten * 100;

    /**
     * Reflects users input from keyboard and direction of snake head
     */

    char direction = 'R';

    /**
     * Decides whether game is stopped or going.
     */
    boolean running = false;
    boolean paused = false;

    /**
     * used to generate apple randomly
     */
    Random random;

    /**
     * The Timer.
     */
//used to adjust game speed of snakes movement.
    Timer timer;
    /**
     * The Alert.
     */
    JFrame alert;

    /**
     * The Score tracker.
     */
    JTextArea scoreTracker;

    /**
     * The Game over.
     */
    JTextArea gameOver = new JTextArea("GAME OVER");
    /**
     * The Score.
     */
//needed to use String.valueOf as opposed to typeCast to properly get playerScore
    JTextArea score = new JTextArea();

    /**
     * The Reset.
     */
    JButton reset = new JButton("reset");
    /**
     * The Quit.
     */
    JButton quit = new JButton("quit");

    /**
     * Create panel to add to frame.
     * Hold all important data to display
     */
    GamePanel(){

        random = new Random();
        buildPanel();
        startGame();

    }

    /**
     * Make all adjustments to allow game to run
     */
    public void startGame(){
        newApple();
        running = true;
        //NEED TO USE JAVAX.SWING TIMER TO ACCESS START METHOD
        timer = new Timer(DELAY,this);
        timer.start();
    }

    /**
     * used to actually Draw Snake on board. Calls draw(g)
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //uncomment to display overlaying grid of panel
        //drawGrid(g);
        draw(g);
        drawPlayerScore(g, playerScore.toString());
        if(running == false){
            gameOverPaint(g);
        }
    }

    /**
     * Method to illustrate grid like nature of the board
     * in relation to unit size i.e apple or snake head...
     *
     * @param g the g
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
     *
     * @param g the g
     */
    public void draw(Graphics g) {

        if (running) {
            //Apple color, coordinates and size.
            g.setColor(Color.RED);
            g.fillRect(appleXLocation, appleYLocation, UNIT_SIZE, UNIT_SIZE);
            playerScorePaint(g);
            //loop to keep track and add to snake array this maintains the head color/spawn/size
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

                //this maintains the body same conditions as head.
                else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
    }

    /**
     * create apple at random location
     */
    public void newApple(){

        //multiply by unit_size at end to get evenly in grid.
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
        if (paused){

        }
    }

    /**
     * Check apple.
     */
    public void checkApple(){

        if(x[0] == appleXLocation && y[0] == appleYLocation){
            bodyParts++;
            applesEaten ++;
            playerScore = applesEaten * 100;
            newApple();

        }
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
            gameOver();
        }
       
    }

    /**
     * Game over.
     */
    public void gameOver(){
        timer.stop();
        createAlertDialog();
        running = false;
    }

    /**
     * Create alert dialog.
     */
    public void createAlertDialog(){

        //parent container
        alert = new JFrame();

        /*JButton reset = new JButton("reset");
        JButton quit = new JButton("quit");*/

        buildAlertContent();
        addAlertContent();
        //constraints for JFrame

    }

    /**
     * Display score j text area.
     *
     * @return the j text area
     */
    public JTextArea displayScore(){
        scoreTracker = new JTextArea(String.valueOf(playerScore));
        scoreTracker.update(scoreTracker.getGraphics());
        scoreTracker.setBackground(Color.DARK_GRAY);
        scoreTracker.setLocation(25, 0 );

        return scoreTracker;
    }

    /**
     * Get player score int.
     *
     * @return the int
     */
    public int getPlayerScore(){
        return playerScore;
    }

    /**
     * Draw player score.
     *
     * @param g                 the graphics
     * @param playerScoreString the player score string
     */
    public void drawPlayerScore(Graphics g, String playerScoreString){
        g.setFont(new Font(null, Font.BOLD,25));
        g.setColor(Color.white);
        g.drawString(playerScoreString, 25,0);

    }

    /**
     * The type My key adapter.
     */
//SubClass used for global keyAdapter
    public class MyKeyAdapter extends KeyAdapter{

        @SuppressWarnings("AlibabaSwitchStatement")
        @Override
        public void keyPressed(KeyEvent e) {

            //dont want user to be able to turn 180 degrees. Limit to 90 degree turns.
            switch (e.getKeyCode()){

                case KeyEvent.VK_LEFT  :
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
            //duplicate to allow player to used WASD as opposed to arrows
            switch (e.getKeyChar()) {

                case 'a':
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case 'd':
                    if (direction != 'L') {

                        direction = 'R';
                    }
                    break;

                case 'w':
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case 's':
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case ' ':
                    paused = !paused;
                    System.out.println(paused);
                    System.out.println("paused");
                    if (paused) {
                        pauseGame();
                    }
                    if (!paused){
                        resumeGame();
                    }
            }
        }
    }

    /**
     * Resume game.
     * resumes timer
     */
    public void resumeGame(){
    paused = false;
    timer.start();
}

    /**
     * Pause game.
     * stops timer
     */
    public void pauseGame(){
    timer.stop();
    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    /**
     * Reset button action.
     *
     * @param e the event(click)
     */
    public void resetButtonAction(ActionEvent e) {
        //COME BACK TO THIS TO USE FOR NEVERMORE TO CLOSE THAT JPANEL AS WELL NICE JOB DUDE HELL YESSSSSSSSS
        if(e.getSource() == reset){
            this.setVisible(false);
            this.getRootPane().getTopLevelAncestor().setVisible(false);
            new Frame();
            alert.dispose();
        }
    }

    /**
     * Quit button action.
     * closes game
     * @param e the e
     */
    public void quitButtonAction(ActionEvent e){
        if (e.getSource() == quit){
            exitGame();

        }
    }

    /**
     * Game over paint.
     * paints red game over and shows player score
     * @param g the g
     */
    public void gameOverPaint(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+ applesEaten * 100, (PANEL_WIDTH - metrics1.stringWidth("Score: "+ applesEaten * 100))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (PANEL_WIDTH - metrics2.stringWidth("Game Over"))/2, PANEL_HEIGHT/2);
    }

    public void playerScorePaint(Graphics g){
        g.setColor(Color.white);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+ applesEaten * 100, (PANEL_WIDTH - metrics1.stringWidth("Score: "+ applesEaten * 100))/2, g.getFont().getSize());

    }

    /**
     * Build alert content.
     * set all dimensions bounds colors etc
     */
    public void buildAlertContent(){
        //Score tracker constraints
        score.setText("Score: "+String.valueOf(playerScore));
        score.setFont(new Font(null, Font.BOLD, 50));
        score.setVisible(true);
        score.setLocation(alert.getLocation());
        score.setBounds(135,200,200,50);
        score.setBackground(new Color(alert.getBackground().getRGB(), true));
        score.setFont(new Font(null, Font.CENTER_BASELINE,25));

        //game over text area constraints
        gameOver.setVisible(true);
        gameOver.setLocation(alert.getLocation());
        gameOver.setText("GAME OVER");
        gameOver.setBounds(135,100,200,50);
        gameOver.setBackground(new Color(alert.getBackground().getRGB(), true));
        gameOver.setFont(new Font(null, Font.CENTER_BASELINE,25));

        //Create reset button for game
        reset.setVisible(true);
        reset.setText("reset");
        reset.setBounds(120,250,75,25);
        reset.setBackground(new Color(alert.getBackground().getRGB(), true));
        reset.addActionListener(this::resetButtonAction);
        reset.setLayout(null);

        //Create quit button for game
        quit.setVisible(true);
        quit.setText("quit");
        quit.setBounds(195,250,75,25);
        quit.setBackground(new Color(alert.getBackground().getRGB(), true));
        quit.addActionListener(this::quitButtonAction);
        quit.setLayout(null);
    }

    /**
     * Add alert content.
     * add all previously built content to the jpanel
     */
    public void addAlertContent(){
        //constraints for JFrame
        alert.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        alert.setVisible(true);
        alert.setLocationRelativeTo(null);
        alert.setFocusable(true);
        alert.setTitle("Play Again?");
        alert.setBounds(550,300,420,420);
        alert.setResizable(false);
        alert.setContentPane(new Container());
        alert.add(gameOver);
        alert.add(score);
        alert.add(reset);
        alert.add(quit);
    }

    /**
     * Exit game.
     */
    public void exitGame(){
        System.exit(-1);
    }

    /**
     * Build panel.
     */
    public void buildPanel(){
        this.requestFocus();
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

    }

}
