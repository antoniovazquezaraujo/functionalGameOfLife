
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameOfLifeTest {
	private static final Logger LOG = Logger.getLogger(GameOfLifeTest.class.getName());
	List<Boolean> testCells;

	@BeforeEach
	void init() {
		testCells = new ArrayList<>();
		for (int n = 0; n < GameOfLife.numCells(); n++) {
			testCells.add(false);
		}
	}

	@AfterEach
	void tearDown() {
		testCells = null;
	}

	@Test
	void testRowOf() {
		for (int n = 0; n < GameOfLife.numCells(); n++) {
			assert ((GameOfLife.rowOf(n) == ((n / GameOfLife.COLS))));
		}
	}

	@Test
	void testColOf() {
		for (int n = 0; n < GameOfLife.numCells(); n++) {
			assert ((GameOfLife.colOf(n) == (n % GameOfLife.COLS)));
		}
	}

	@Test
	void testgetAt() {
		List<Boolean> b = Arrays.asList(false, true, false);
		assert (GameOfLife.getAt(b, 0) == (false));
		assert (GameOfLife.getAt(b, 1) == (true));
		assert (GameOfLife.getAt(b, 2) == (false));
	}

	@Test
	void testPosOf() {
		assert (GameOfLife.posOf(0, 0) == 0);
		assert (GameOfLife.posOf(GameOfLife.COLS - 1, 0) == (0 * (GameOfLife.COLS - 1) + (GameOfLife.COLS - 1)));
	}

	@Test
	void testCountNeighbours() {
		for (int n = 0; n < GameOfLife.numCells(); n++) {
			assert((GameOfLife.countNeighbours(testCells, n)) == 0);
		}
		GameOfLife.setAt(testCells, 0, 0, true);
		assert((GameOfLife.countNeighbours(testCells, 0,0 )) == 0);
		assert((GameOfLife.countNeighbours(testCells, 0,1 )) == 1);
		assert((GameOfLife.countNeighbours(testCells, 1,0 )) == 1);
		assert((GameOfLife.countNeighbours(testCells, 1,1 )) == 1);

		GameOfLife.setAt(testCells, 0, 0, true);
		assert (GameOfLife.countNeighbours(testCells, 1, 1) == 1);
		assert (GameOfLife.countNeighbours(testCells, 0, 1) == 1);
		assert (GameOfLife.countNeighbours(testCells, 1, 0) == 1);
		GameOfLife.setAt(testCells, 1, 1, true);
		assert (GameOfLife.countNeighbours(testCells, 0, 1) == 2);
		assert (GameOfLife.countNeighbours(testCells, 1, 0) == 2);
		GameOfLife.setAt(testCells, 1, 0, true);
		assert (GameOfLife.countNeighbours(testCells, 0, 1) == 3);
		GameOfLife.setAt(testCells, 1, 2, true);
		assert (GameOfLife.countNeighbours(testCells, 0, 1) == 4);
		GameOfLife.setAt(testCells, 0, 2, true);
		assert (GameOfLife.countNeighbours(testCells, 0, 1) == 5);	
	}
}
