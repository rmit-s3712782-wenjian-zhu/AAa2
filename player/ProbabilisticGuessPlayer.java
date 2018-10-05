package player;

import java.util.ArrayList;
import world.*;
import world.World.*;
import java.util.Random;
import ship.*;

/**
 * Probabilistic guess player (task C). Please implement this class.
 *
 * @author Youhan Xia, Jeffrey Chan
 */
public class ProbabilisticGuessPlayer implements Player {
	World world;
	ArrayList<Coordinate> shots = new ArrayList<>();
	ArrayList<Coordinate> hits = new ArrayList<>();
	ArrayList<Coordinate> targetList = new ArrayList<>();
	Random rand = new Random();
	// Institanse a score board to record the sum of the count of each ship's
	// configuration possible position.
	int[][] prob;
	boolean isHunt = true;
	ArrayList<Ship> ships = new ArrayList<>();

	/*
	 * static final String dirs = "NWSENESW";
	 *
	 */
	// For ship configuration on a coordinate, there can be 4 combinations of
	// directions for the one width ship, i.e. the first four.
	// For ship configuration on a coordinate, there can be 8 combinations of
	// diretions for the two width ship, i.e. the entire eight.
	String allDirs = "NWSENESW";
	String dirs[] = { "NW", "WS", "SE", "EN", "NE", "WN", "SW", "ES" };
	int rowDeltas[] = { 1, 0, -1, 0 };
	int clnDeltas[] = { 0, -1, 0, 1 };
	// Coordinate cdn = world.new Coordinate();

	@Override
	public void initialisePlayer(World world) {
		// To be implemented.
		this.world = world;
    // Assuming opponent have the same ship as mine.
		for (ShipLocation s : world.shipLocations) {
			ships.add(s.ship);
		}
		prob = new int[world.numRow][world.numColumn];
    // updating the grid
    refresh();
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
			if (ship.coordinates.contains(cdn)) {
				answer.isHit = true;
				for (Coordinate c : ship.coordinates) {
					// return answer immediately if not all ship's coordinates are hit.
					if (!world.shots.contains(c)) {
						return answer;
					}
				} // end of for loop;
					// return answer after assigning ship to shipSunk if all ship's
					// coordinates are hit.
				answer.shipSunk = ship.ship;
        world.shipLocations.remove(ship);
				return answer;
			}
		}
		// dummy return
		return answer;
	} // end of getAnswer()

	@Override
	public Guess makeGuess() {
		// To be implemented.
		ArrayList<Coordinate> high = getCount();
		Guess guess = new Guess();
		Coordinate cdn = world.new Coordinate();
		/*
		 * if(isHunt){ }
		 */
		// Get the coordinate of guess.
		// Can be potential deadlock.
		do {
			cdn = high.get(rand.nextInt(high.size()));
		} while (shots.contains(cdn));
		shots.add(cdn);
		guess.row = cdn.row;
		guess.column = cdn.column;
		// dummy return
		return guess;
		/*
		 * do { cdn.row = rand.nextInt(world.numRow); cdn.column =
		 * rand.nextInt(world.numColumn); } while (shots.contains(cdn)); shots.add(cdn);
		 * guess.row = cdn.row; guess.column = cdn.column; // dummy return return guess;
		 */
	} // end of makeGuess()

	@Override
	public void update(Guess guess, Answer answer) {
		// To be implemented.
		guess.toString();
		answer.toString();
		// If the other player gives the answer of hitting the ship,
		// that is, my previous guess was a hit.
		if (answer.isHit) {
			hits.add(shots.get(shots.size() - 1));
			isHunt = false;
			if (answer.shipSunk != null) {
				// Taking out sunk ship from implementing possible configuration
				ships.remove(answer.shipSunk);
				isHunt = true;
        // Refresh the target list after changing to hunting mode.
        targetList = new ArrayList<>();
			} // end of if()
		} // end of if ()
		refresh();
		for (int i = prob.length - 1; i >= 0; i--) {
			for (int j = 0; j < prob.length; j++) {
				System.out.print(prob[i][j] + ".");
			}
			System.out.print("\n");
		}
		System.out.println("=====================");
	} // end of update()

	@Override
	public boolean noRemainingShips() {
    /*
		// Check if all ships' coordinates have been hit.
		for (ShipLocation ship : world.shipLocations) {
			for (Coordinate c : ship.coordinates) {
				// If one coordinate is detected which hasnt been hit in the past,
				// return false.
				if (!world.shots.contains(c))
					return false;
			}
		}
    */
    return (world.shipLocations.isEmpty());
		// return true by default
		// return true;
	} // end of noRemainingShips()

	// Check if certain configuration is able to fit into the grid given
	// the ship type, the starting coordinate and both the primary and secondary
	// directions.
	public boolean ifFit(Ship ship, Coordinate c, int pd, int sd) {
		// dirs = "NWSENESW", it stands for 0-3 in rowDeltas[] and clnDeltas[].
		// Try to place particlar ship onto the grid.
		for (int i = 0; i < ship.len(); i++) {
			for (int j = 0; j < ship.width(); j++) {
				Coordinate cdn = world.new Coordinate();
				cdn.row = c.row + i * rowDeltas[pd] + j * rowDeltas[sd];
				cdn.column = c.column + i * clnDeltas[pd] + j * clnDeltas[sd];
				// Check if coordinate is out of boundary.
				if (!isIn(cdn)) {
					return false;
					// Check if ship's coordinate sits on hitted cell.
				} // end of if()
				else if (shots.contains(cdn) && !hits.contains(cdn)) {
					return false;
				} // end of else if()
			}
		}
		return true;
	} // end of ifFit()

	// Add 1 to the counter of specific coordinate.
	/*
	 * public void addCount(Coordinate cdn){ // if in?
	 * prob[cdn.numRow][cdn.numColumn]++; }
	 */

	// Update the possible ship configuration count grid within the
	// new infomation.
	public void refresh() {
		int comb;
		int pd;
		int sd;
		// Using a new count table to track the changes
		prob = new int[world.numRow][world.numColumn];
		for (Ship s : ships) {
			// Ship with 1 width;
			if (s.width() == 1) {
				comb = 4;
			}
			// Ship with 2 width;
			else {
				comb = 8;
			}
			for (int x = 0; x < comb; x++) {
				pd = allDirs.indexOf(dirs[x].charAt(0));
				sd = allDirs.indexOf(dirs[x].charAt(1));
				for (int i = 0; i < prob.length; i++) {
					for (int j = 0; j < prob[i].length; j++) {
						Coordinate c = world.new Coordinate();
						c.row = i;
						c.column = j;
						if (ifFit(s, c, pd, sd)) {
							if (!shots.contains(c)) {
								prob[i][j]++;
							}
						} // end if()
					} // end of for()
				} // end of for()
			} // end of for()
		} // end of for()
	} // end refresh()

	public ArrayList<Coordinate> getCount() {
		ArrayList<Coordinate> grid = new ArrayList<>();
		// Sort the highest count coordinates from the grid.
		int highestCount = 0;
		if (isHunt) {
			for (int i = 0; i < prob.length; i++) {
				for (int j = 0; j < prob[i].length; j++) {
					// if there is a higher count, chuck old arraylist and store the
					// coordinate of highest counts;
					if (prob[i][j] > highestCount) {
						highestCount = prob[i][j];
						grid = new ArrayList<>();
						Coordinate cdn = world.new Coordinate();
						cdn.row = i;
						cdn.column = j;
						grid.add(cdn);
            // critcal error
					} else if (prob[i][j] == highestCount
          && highestCount != 0) {
						Coordinate cdn = world.new Coordinate();
						cdn.row = i;
						cdn.column = j;
						grid.add(cdn);
					}
				}
			}
      System.out.println("hunting mode loop test");
		} else {
			int count = 0;
			do {
				Coordinate hit = hits.get(hits.size() - 1);
				Coordinate target = null;
				if (!targetList.contains(hit)) {
					// Targeting hit cells and record them to list.
					targetList.add(hit);
          count = 0;
				}
					// Back trace history in target mode.
					// Making this step recurisve to back trace all target cells from
					// history.
// Potential deadlock!
        target = targetList.get(targetList.size() - 1 - count);
				// Get the 4 around coordinates of the hit cell.
				int row[] = { 0, 1, 0, -1 };
				int column[] = { 1, 0, -1, 0 };
				for (int k = 0; k < 4; k++) {
					Coordinate cdn = world.new Coordinate();
					cdn.row = target.row + row[k];
					cdn.column = target.column + column[k];
					// Making sure if coordinate is in the boundary to avoid outofarray
					// index exception.
					if (isIn(cdn)) {
						if (prob[cdn.row][cdn.column] > highestCount) {
							highestCount = prob[cdn.row][cdn.column];
							grid = new ArrayList<>();
							grid.add(cdn);
						} else if (prob[cdn.row][cdn.column] == highestCount
            && highestCount != 0) {
							grid.add(cdn);
						}
					}
				}
        if (grid.isEmpty()){
        //  && count < targetList.size()-1;
				count++; // counter for back trace the history
      }
      System.out.println(grid);
        System.out.println("targeting mode loop test" + count);
			} while (grid.isEmpty());
		}
		return grid;
	}

	// Check if coordinate is in the boundary.
	boolean isIn(Coordinate cdn) {
		return cdn.row >= 0 && cdn.row < world.numRow && cdn.column >= 0 && cdn.column < world.numColumn;
	}

} // end of class ProbabilisticGuessPlayer
