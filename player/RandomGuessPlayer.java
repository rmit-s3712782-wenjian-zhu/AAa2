package player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import world.World;
import world.World.Coordinate;
import world.World.ShipLocation;

/**
 * Random guess player (task A). Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class RandomGuessPlayer implements Player {
    
    
    /*
     * javac -cp .:samplePlayer.jar BattleshipMain.java
     * java -cp .:samplePlayer.jar BattleshipMain -v ../config.txt ../loc1.txt ../loc2.txt sample sample
     * 
     * 
     * 
     */

    private World world;

    // arrays for ships and coordinates
    public ArrayList<ShipLocation> shipLocations = new ArrayList<>();
    public ArrayList<Coordinate> shots = new ArrayList<>();
    // public ArrayList<Ship> ships = new Arraylist<>();
    // our random object for random guessing
    private Random random = new Random();

    /**
     * Initialise the player using the world object.
     * 
     * @param world
     *            world object contains the configuration and ship locations
     */
    @Override
    public void initialisePlayer(World world) {
        this.world = world;
        this.shipLocations = world.shipLocations;
        this.shots = world.shots;

    } // end of initialisePlayer()

    /**
     * Answer a guess from the opponent.
     *
     * @param guess
     *            from the opponent.
     * 
     * @return Answer object holding the player's answer.
     */
    @Override
    public Answer getAnswer(Guess guess) {

        // instantiate a new answer object
        Answer a = new Answer();
        // for each ship
        for (ShipLocation s : shipLocations) {
            // check the coordinates to match the guess
            for (Coordinate c : shots) {
                if (c.column == guess.column && c.row == guess.row) {
                    // if hit is true then set isHit for answer object
                    a.isHit = true;
                    // remove the coordinate from ship list
                    s.coordinates.remove(c);
                    // if s contains no more coordinates
                    if (s.coordinates.size() == 0)
                        //set ship status to sunk
                        a.shipSunk = s.ship;
                    break; 
                }
                if (a.isHit){
                    //if ship is unk
                    if(a.shipSunk != null){
                        this.shipLocations.remove(s);
                        System.out.println("ship sunk");
                    }
                    
                }
        }
        }
        return a;
    } // end of getAnswer()

    /**
     * Generate/make a guess.
     *
     * @return Guess object.
     */
    @Override
    public Guess makeGuess() {

        Guess g = new Guess();
        
        g.column = random.nextInt(world.numColumn);
        g.row = random.nextInt(world.numRow);

        return g;
    } // end of makeGuess()

    /**
     * Callback to allow player to process the answer of their guess and
     * possibly update their internal state.
     *
     * @param guess
     *            Guess of this player.
     * @param answer
     *            Answer to the guess from opponent.
     */
    @Override
    public void update(Guess guess, Answer answer) {

        guess.toString();
        answer.toString();

    } // end of update()

    /**
     * Check whether all the ships of this player have been destroyed by the
     * opponent.
     *
     * @return True if there are no ship remaining, i.e., all ships sunk.
     */
    @Override
    public boolean noRemainingShips() {
        for (ShipLocation s : shipLocations) {
            if (s.coordinates == null)

                return true;
        }

        return false;
    } // end of noRemainingShips()

} // end of class RandomGuessPlayer
