
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Sudoku {
    private static class Pair<X, Y> {
        public final X x;
        public final Y y;

        public Pair(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    private Board board;
    private int square_size;

    private final Integer[] comparision_array;

    public Sudoku(int dimension, Scanner scanner) {
        this.square_size = (int) Math.sqrt(dimension);
        comparision_array = new Integer[dimension];
        this.board = new Board(dimension);
        for (int i = 0; i < comparision_array.length; i++) {
            comparision_array[i] = i + 1;
        }
        int i = 0; // row counter
        int j = 0; // column counter

        while (i < dimension) {
            String line = scanner.nextLine();
            for (char valor : line.toCharArray()) {
                this.board.tablero.get(i)
                        .add(new Cell(board, valor == '-' ? 0 : Character.getNumericValue(valor), i, j));
                j = j == dimension - 1 ? 0 : j + 1;
            }
            i++;
        }
        System.out.println(this);

    }

    private static PriorityQueue<Pair<Integer, Cell>> calculatePriorities(Sudoku sudoku){
        PriorityQueue<Sudoku.Pair<Integer, Sudoku.Cell>> freedomQueue = new PriorityQueue<Pair<Integer, Cell>>(new intCellComparator());
        for (List<Cell> lista : sudoku.board.tablero) {
            for (Cell celda : lista) {
                if (celda.value == 0) {
                    int freedomDegree = 0;
                    try {
                        freedomDegree = celda.freedom();
                    } catch (Exception e) {
                        return null;
                    }
                    Pair<Integer, Cell> pareja = new Pair<Integer, Cell>(freedomDegree, celda);
                    freedomQueue.add(pareja);
                }
            }
        }
        return freedomQueue;
    }
    private static class intCellComparator implements Comparator<Pair<Integer, Cell>> {

        @Override
        public int compare(Sudoku.Pair<Integer, Sudoku.Cell> o1, Sudoku.Pair<Integer, Sudoku.Cell> o2) {
            return o1.x.compareTo(o2.x);
        }

    }

    /**
     * Actual solving recursive function
     * 
     * @param prioridades
     * @return boolean
     */
    public static Pair<Boolean, Sudoku> solve(Sudoku sudoku, PriorityQueue<Pair<Integer, Cell>> prioridades) {
        Boolean condition = false;
        Pair<Boolean, Sudoku> result = new Pair<Boolean,Sudoku>(false, sudoku);
        int i = 0;
        while (!condition) {
            Pair<Integer, Cell> pair = prioridades.poll();
            if (pair == null) {
                return new Pair<Boolean, Sudoku>(true, sudoku);
            }
            Cell celda = pair.y;
            celda.value = celda.freedomValues[i];
            PriorityQueue<Sudoku.Pair<Integer, Sudoku.Cell>> newQueue = calculatePriorities(sudoku);
            if (newQueue == null) {
                break;
            }
            result = Sudoku.solve(sudoku, newQueue);
            condition = result.x;
        }
        return result;
    }

    /**
     * Sudoku solve function wrapper
     * 
     * @param sudoku
     * @return boolean
     */
    public static boolean solve(Sudoku sudoku) {
        Pair<Boolean, Sudoku> result = Sudoku.solve(sudoku, (PriorityQueue<Sudoku.Pair<Integer, Sudoku.Cell>>) calculatePriorities(sudoku));
        return result.x;
    }

    @Override
    public String toString() {
        return this.board.toString();
    }

    /**
     * @param celda
     * @return Integer[]
     */
    public Integer[] calculateFreedomValues(Cell celda) {
        Set<Integer> freedomSet = new HashSet<Integer>(Arrays.asList(comparision_array));
        freedomSet.removeAll(getColumnNumbers(celda));
        freedomSet.removeAll(getRowNumbers(celda));
        freedomSet.removeAll(getSquareNumbers(celda));
        return freedomSet.toArray(new Integer[0]);
    }

    /**
     * @param celda
     * @return Set<Integer>
     */
    private Set<Integer> getSquareNumbers(Cell celda) {
        int row = celda.row;
        int column = celda.column;
        int localBoxRow = row - row % square_size;
        int localBoxColumn = column - column % square_size;
        List<Integer> result = new ArrayList<Integer>();
        for (int i = localBoxRow; i < localBoxRow + square_size; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + square_size; j++) {
                result.add(this.board.tablero.get(i).get(j).value);
            }
        }
        return new HashSet<Integer>(result);
    }

    /**
     * @param celda
     * @return Set<Integer>
     */
    private Set<Integer> getRowNumbers(Cell celda) {
        List<Integer> result = new ArrayList<Integer>();
        for (Cell celdaFila : board.tablero.get(celda.row)) {
            result.add(celdaFila.value);
        }
        return new HashSet<Integer>(result);
    }

    /**
     * @param celda
     * @return Set<Integer>
     */
    private Set<Integer> getColumnNumbers(Cell celda) {
        List<Integer> columna = new ArrayList<Integer>();
        for (List<Cell> lista : this.board.tablero) {
            columna.add(lista.get(celda.column).value);
        }
        return new HashSet<Integer>(columna);
    }

    public class Cell {

        private Integer[] freedomValues;
        private int value;
        private int row;
        private int column;
        Board tablero;

        public Cell(Board board, int initialValue, int row, int column) {
            this.value = initialValue;
            this.tablero = board;
            this.row = row;
            this.column = column;
        }

        public int freedom() throws Exception {
            this.freedomValues = calculateFreedomValues(this);
            if (freedomValues.length == 0) {
                String errorString = "La celda " + this.toString() + "abortando arbol";
                throw new Exception(errorString);
            } else {
                return freedomValues.length;
            }
        }

        @Override
        public String toString() {
            return "(" + this.row + ", " + this.column + ", Valor = " + String.valueOf(this.value) + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof Cell) {
                Cell aComparar = (Cell) obj;
                return this.value == aComparar.value;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return ((Integer) (value * row - column)).hashCode();
        }
    }

    public class Board {

        private List<List<Cell>> tablero;
        private int size;

        public Board(int size) {
            this.size = size;
            this.tablero = new ArrayList<List<Cell>>();
            for (int i = 0; i < size; i++) {
                this.tablero.add(new ArrayList<Cell>());
            }

        }

        @Override
        public String toString() {
            String result = "";
            for (int i = 0; i < tablero.size(); i++) {
                for (int j = 0; j < tablero.get(i).size(); j++) {
                    result += tablero.get(i).get(j).value;
                }
                result += "\n";
            }
            return result;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        Sudoku sudoku = new Sudoku(9, scanner);
        Sudoku.solve(sudoku);
        System.out.println(sudoku);

    }

}