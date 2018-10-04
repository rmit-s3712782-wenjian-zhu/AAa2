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
	World world;
	int x;
	int y;
	//ArrayList<Coordinate> shots = world.shots;

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
			if (ship.coordinates.contains(cdn)){
				answer.isHit = true;
				for (Coordinate c: ship.coordinates){
					// return answer immediately if not all ship's coordinates are hit.
					if (!shots.contains(c)){
						return answer;
					}
				} // end of for loop;
				// return answer after assigning ship to shipSunk if all ship's
				// coordinates are hit.
				answer.shipSunk = ship.ship;
				return answer;
			}
		}
		// dummy return
		return answer;
	} // end of getAnswer()

	@Override
	public Guess makeGuess() {
		// To be implemented.
		Guess guess = new Guess();
		// Get the coordinate of guess.
		Coordinate cdn = world.new Coordinate();
		do {
			cdn.row = rand.nextInt(x);
			cdn.column = rand.nextInt(y);
		} while (shots.contains(cdn));
		shots.add(cdn);
		guess.row = cdn.row;
		guess.column = cdn.column;
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
		// check if ship lcoations all exist in shots
		for (ShipLocation ship : world.shipLocations) {
			for(Coordinate c: ship.coordinates){
				if (!world.shots.contains(c))
					return false;
			}
		}
		// dummy return
		return true;
	} // end of noRemainingShips()



} // end of class RandomGuessPlayer
