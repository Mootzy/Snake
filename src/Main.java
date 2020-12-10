import java.io.PrintStream;

public class Main {
    static final int MAX_WIDTH = 550, MAX_HEIGHT = 500;

    public static void main(String[] args) {
        new MainMenuFrame();

    }
}



/*

- Tyler Wallace December 9th final project -

Where to begin... I experienced a multitude of issues/problems with this program. most noteably-
1- collision detection
2- creating snake
3- detecting how many game units were available on the JPanel
4- compensation for strange movement patterns i.e turning 180/360 degrees.
5- score keeping
6- gathering input from users keyboard
7- random generation of 'apple' on screen

Overall as my git commit's will reveal i'd still like to add a pause function when the player hit's space,
as well as the ability to live track the players score in game.

since the presentation i've added a "reset" and "quit" button when the player loses
as well as a bright red "game over" with the players score. I've also left the alert dialog as i feel
placing the buttons on the jPanel its self may provide a more aesthetically pleasing look it will ultimately
take more time.

Thanks for the class.


 */