package player;

import java.util.Scanner;
import world.World;

// User implemented libries.
import random;

/**
 * Random guess player (task A).
 * Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class RandomGuessPlayer implements Player{
    public ArrayList<Coordinate> shots = new ArrayList<>();
    private World world;
    // Initialise random class.
    Random rand = new Random();

    @Override
    public void initialisePlayer(World world) {
        // To be implemented.
        this.world = world;
    } // end of initialisePlayer()

    @Override
    public Answer getAnswer(Guess guess) {
        // To be implemented.

        // dummy return
        return null;
    } // end of getAnswer()


    @Override
    public Guess makeGuess() {
        // To be implemented.
        do{
        random
        int x = 0;
        int y = 0;
        makeGuess(x,y);
        }
        while (coord != in shots);
        // dummy return
        return null;
    } // end of makeGuess()


    @Override
    public void update(Guess guess, Answer answer) {
        // To be implemented.
    } // end of update()


    @Override
    public boolean noRemainingShips() {
        // To be implemented.

        // dummy return
        return true;
    } // end of noRemainingShips()

} // end of class RandomGuessPlayer
