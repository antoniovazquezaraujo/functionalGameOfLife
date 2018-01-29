
/**
 * Game of life
 * (Using Java 8 Streams and lambdas intensively)
 * Author: antoniovazquezaraujo@gmail.com (Antonio Vazquez Araujo)
 * Ourense
 * Spain.
 * 28/1/2018
 * 
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Cell<T>{
	public List<List<T>> map;
	public int col;
	public int row;
	public Cell(List<List<T>> cells, int col, int row) {
		this.map = cells;
		this.col = col;
		this.row = row;
	}
}
public class GameOfLife {
	public static final int PAUSE= 300;
	public static final int ROWS = 20;
	public static final int COLS = 80;

	public static void main(String[] args) {
		Random random = new Random();
		List<List<Boolean>> cells;
		cells = new ArrayList<List<Boolean>>();
		cells = applyToAll(cells, (t) -> false); 
		cells = applyToAll(cells, (t) -> random.nextBoolean()); 
		while (true) {
			cells = applyToAll(cells, rules); 
			applyToAll(cells, (t) -> {viewCell(t); return 0;});
			pause();
			clearScreen();
		}
	}

	static int countNeighbours(List<List<Boolean>> list, int col, int row){
		return IntStream
			.rangeClosed(row-1, row+1)
			.mapToObj(rangeRow ->
				IntStream
				.rangeClosed(col-1, col+1)
				.filter(rangeCol -> !(rangeCol == col && rangeRow == row))
				.mapToObj(rangeCol ->
					GameOfLife.getAt(list, rangeCol, rangeRow).orElse(false)
				)
				.filter(cellValue -> cellValue == true)
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

	public static String selectFormat  (Integer t) {
		if((t+1)% COLS == 0){
			return "%1.1s%n";
		}else {
			return "%1s";
		}
	};

	public static void pause() {
		try {
			Thread.sleep(PAUSE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static  void viewCell(Cell<Boolean>  cell) {
			System.out.print(String.format(
					selectFormat(cell.col),
					simbolOf(getAt(cell.map ,cell.col,cell.row).orElse(false))));
	}
	static Function<Cell<Boolean>, Boolean>  rules = (cell) ->{
		Boolean value = getAt(cell.map, cell.col, cell.row).orElse(false);
		int numNeighbours = countNeighbours(cell.map, cell.col, cell.row);
		return ((value == false) && numNeighbours == 3) || ((value==true) &&(numNeighbours == 2 || numNeighbours == 3));	
	};
	public static <E, V> List<List<V>> applyToAll(List<List<E>> cells, Function<Cell<E>,V> cellFunction) {
		return IntStream
			.range(0, ROWS)
			.mapToObj(row -> 
				IntStream
				.range(0, COLS)
				.mapToObj(col ->
					cellFunction.apply(new Cell<E>(cells, col, row))
				)
			.collect(Collectors.<V>toList())
			)
			.collect(Collectors.<List<V>>toList());
	}

	public static void clearScreen() {
		for (int n = 0; n < ROWS; n++) {
			System.out.println();
		}
	}

	public static <T> void setAt(List<List<T>> list, int col, int row, T value) {
		if(col<0 || col >= COLS) return ;
		if(row<0 || row >= ROWS) return ;
		list.get(row).set(col, value);
	}

	public static <T> Optional<T> getAt(List<List<T>> list, int col, int row) {
		if(col<0 || col >= COLS) return Optional.empty();
		if(row<0 || row >= ROWS) return Optional.empty();
		return Optional.of(list.get(row).get(col));
	}
}
