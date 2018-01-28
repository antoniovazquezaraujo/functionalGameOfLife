
/**
 * Game of life
 * (Using Java 8 Streams and lambdas intensively)
 * Author: antoniovazquezaraujo@gmail.com (Antonio Vazquez Araujo)
 * Ourense
 * Spain.
 * 28/1/2018
 * 
 */
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameOfLife {
	public static final int PAUSE= 300;
	public static final int ROWS = 20;
	public static final int COLS = 120;

	public static void main(String[] args) {
		gameLoop(randomizeCells(createCells()));
	}

	private static void gameLoop( List<Boolean> cells) {
		while(true) {
			pause();
			clearScreen();
			cells= applyRules(cells, getNeighbours(cells), rules);
			showCells(cells);
		}
	}

	private static List<Boolean> createCells() {
		return IntStream
			.range(0, numCells())
			.mapToObj(t -> false)
			.collect(Collectors.<Boolean>toList());
	}

	public static List<Boolean> randomizeCells(List<Boolean> list) {
		Random random = new Random();
		return list.stream()
			.map(t -> random.nextBoolean())
			.collect(Collectors.toList());
	}
	public static Function<Integer, Function<Boolean, Boolean>> rules = (numNeighbours) -> {
		return (t) -> (!t && numNeighbours == 3) || (t &&(numNeighbours == 2 || numNeighbours == 3));	
	};

	public static int countNeighbours(final List<Boolean> cells, int pos) {
		return countNeighbours(cells, colOf(pos), rowOf(pos));
	}

	public static int countNeighbours(final List<Boolean> cells, int paramCol, int paramRow) {
		return IntStream
			.rangeClosed(-1, 1)
	        .mapToObj(col -> 
	        	IntStream.rangeClosed(-1, 1)
	        	.filter(row -> !(col== 0 && row==0)) // no se cuenta a sí mismo
	        	.mapToObj(row -> {return getAt(cells, paramCol+col, paramRow+row);})
	        	.filter(cellValue -> cellValue == true) // solo se cuentan las celdas vivas
	        	.count()
        	)
	        .map(longCountValue -> Math.toIntExact(longCountValue))
	        .reduce(0, Integer::sum);
	}

	public static String simbolOf(Boolean value) {
		if (value ) {
			return "#";
		} else {
			return " ";
		}
	}

	public static List<Boolean> applyRules(final List<Boolean> lives, final List<Integer> neighbours,
			Function<Integer, Function<Boolean, Boolean>> rule) {
		return IntStream
				.range(0, numCells())
				.mapToObj(t -> rule.apply(neighbours.get(t)).apply(lives.get(t)))
				.collect(Collectors.toList());
	}

	private static void showCells(List<Boolean> cells) {
		IntStream.range(0, numCells()).mapToObj(t -> {
			if ((t + 1) % COLS == 0) {
				return String.format("%1.1s%n", simbolOf(cells.get(t)));
			} else {
				return String.format("%1s", simbolOf(cells.get(t)));
			}
		}).forEach(System.out::print);
	}

	private static void pause() {
		try {
			Thread.sleep(PAUSE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static List<Integer> getNeighbours(List<Boolean> cells) {
		return IntStream
				.range(0, cells.size())
				.mapToObj(t -> countNeighbours(cells, t))
				.collect(Collectors.toList());
	}

	public static void clearScreen() {
		for (int n = 0; n < ROWS; n++) {
			System.out.println();
		}
	}

	public static int numCells() {
		return COLS * ROWS;
	}

	public static int rowOf(int pos) {
		return pos / COLS;
	}

	public static int colOf(int pos) {
		return pos % COLS;
	}

	public static int posOf(int col, int row) {
		if (col < 0 || col >= COLS || row < 0 || row >= ROWS) {
			return -1;
		}
		return row * COLS + col;
	}

	public static void setAt(List<Boolean> list, int col, int row, Boolean value) {
		int pos = posOf(col, row);
		if (pos != -1) {
			list.set(pos, value);
		}
	}

	public static Boolean getAt(List<Boolean> list, int col, int row) {
		int pos = posOf(col, row);
		if (pos != -1) {
			return list.get(pos);
		}
		return false;
	}

	public static Boolean getAt(List<Boolean> list, int pos) {
		if (pos < 0 || pos >= numCells()) {
			return false;
		}
		return list.get(pos) ;
	}
}
