
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

 
class GameOfLifeTest {
	private static final Logger LOG = Logger.getLogger(GameOfLifeTest.class.getName());
	List<List<Boolean>> testCells;

	@BeforeEach
	void init() {
		testCells = new ArrayList<List<Boolean>>();
	}

	@AfterEach
	void tearDown() {
		testCells = null;
	}
	
	@Test
	void testAllFalse() {
		testCells = GameOfLife.applyToAll(testCells, (t) -> false); 
		GameOfLife.applyToAll(testCells, (t) -> {assert(GameOfLife.getAt(t.map, t.col, t.row).orElse(false)== false); return t;}); 
	}
	@Test
	void testNumNeighbours() {
		testCells = GameOfLife.applyToAll(testCells, (t) -> false); 
		GameOfLife.applyToAll(testCells, (t) -> {assert(GameOfLife.countNeighbours(t.map, t.col, t.row)== 0); return t;});
		testCells = GameOfLife.applyToAll(testCells, (t) -> true); 
		GameOfLife.applyToAll(testCells, (t) -> {assert(GameOfLife.countNeighbours(t.map, t.col, t.row)>= 3); return t;});
	}
}
