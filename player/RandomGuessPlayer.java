package player;

import java.util.ArrayList;
import world.*;
import world.World.*;
import java.util.Random;

/**
 * Random guess player (task A). Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class RandomGuessPlayer implements Player {
	ArrayList<Coordinate> shots = new ArrayList<>();
	Random rand = new Random();
	int x;
	int y;
	World world;

	@Override
	public void initialisePlayer(World world) {
		// To be implemented.
		this.world = world;
		x = world.numRow;
		y = world.numColumn;
		// recprd the health of its ships.

	} // end of initialisePlayer()

	@Override
	public Answer getAnswer(Guess guess) {
		// To be implemented.
		Answer answer = new Answer();
		// Get the coordinate of guess.
		Coordinate cdn = world.new Coordinate();
		cdn.row = guess.row;
		cdn.column = guess.column;
		// Check if ShipLocation contains the coordinate.
		for (ShipLocation ship : world.shipLocations) {
			if (world.shots.contains(cdn))
				answer.isHit = true;
		}
		// dummy return
		return answer;
	} // end of getAnswer()

	@Override
	public Guess makeGuess() {
		// To be implemented.
		Guess guess = new Guess();
		Coordinate cdn = world.new Coordinate();
		do {
			guess.row = rand.nextInt(x);
			guess.column = rand.nextInt(y);
			cdn.row = guess.row;
			cdn.column = guess.column;
		} while (world.shots.contains(cdn));
		// dummy return
		return guess;
	} // end of makeGuess()

	@Override
	public void update(Guess guess, Answer answer) {
		guess.toString();
		answer.toString();
	} // end of update()

	@Override
	public boolean noRemainingShips() {
		// To be implemented.

		// dummy return
		return false;
	} // end of noRemainingShips()

} // end of class RandomGuessPlayer
