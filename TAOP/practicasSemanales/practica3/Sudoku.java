
import java.io.BufferedInputStream;
import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.management.relation.RoleResult;

public class Sudoku {

    private Board board;
    private int SQUARESIZE;
    public Sudoku(int filas , int columnas, Scanner scanner){
        this.board = new Board(filas, columnas);
        this.SQUARESIZE = (int)Math.sqrt(filas);
        int i = 0; //row counter
        int j = 0; //column counter
        while (i < columnas){
            String line = scanner.nextLine(); 
            for ( char valor : line.toCharArray()){
                this.board.tablero.get(i).add(new Cell( board, valor== '-'? 0 : Character.getNumericValue(valor), i, j));
                j = j == 8 ? 0 : j+1; 
            }
            i++;
        }
    }

    public static String solve(Sudoku sudoku){
        String result = "";
        for (List<Cell> celda : sudoku.board.tablero) {
            for List<ce
        }
        return result;
    }


    @Override
    public String toString() {
        return this.board.toString();
    }


    public Integer[] calculateFreedomValues(Cell celda) {
        Set<Integer> freedomSet = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        freedomSet.removeAll(getColumnNumbers(celda));
        freedomSet.removeAll(getRowNumbers(celda));
        freedomSet.removeAll(getSquareNumbers(celda));
        return freedomSet.toArray(new Integer[0]);
    }

    private Set<Integer> getSquareNumbers(Cell celda) {
        int row = celda.row;
        int column = celda.column;
        int localBoxRow = row - row % SQUARESIZE;
        int localBoxColumn = column - column % SQUARESIZE;
        List<Integer> result = new ArrayList<Integer>(); 
        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
          for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
            result.add(this.board.tablero.get(i).get(j).value);
          }
        }
        return new HashSet<Integer>(result);
    }

    private Set<Integer> getRowNumbers(Cell celda) {
        List<Integer> result = new ArrayList<Integer>(); 
        for (Cell celdaFila : board.tablero.get(celda.row)) {
            result.add(celdaFila.value);
        }
        return new HashSet<Integer>(result);
    }

    private Set<Integer> getColumnNumbers(Cell celda) {
        List<Integer> columna = new ArrayList<Integer>(); 
        for (List<Cell> lista : this.board.tablero) {
            columna.add(lista.get(celda.column).value);
        }
        return new HashSet<Integer>(columna);
    }

    public class Cell {
            
        private int [] freedomValues;
        private int value;
        private int row;
        private int column;
        Board tablero;
        public Cell (Board board, int initialValue, int row, int column){
            this.value = initialValue;
            this.tablero = board;
            this.row = row;
            this.column = column;
        }

        // public int freedom(){
        //     this.freedomValues = calculateFreedomValues(this);
        //     return freedomValues.length;
        // }

        @Override
        public String toString() {
            return String.valueOf(this.value);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof Cell){
                Cell aComparar = (Cell) obj;
                return this.value == aComparar.value;
            }
            else{
                return false;
            }
        }

        @Override
        public int hashCode() {
            return ((Integer)(value*row-column)).hashCode();
        }
    }

    public class Board {
        
        private List<List<Cell>> tablero;
        private int columnas;
        private int filas;
        public Board (int rows, int columns){
            this.columnas = columns;
            this.filas = rows;
            this.tablero=new ArrayList<List<Cell>>();
            for(int i = 0; i < rows; i++)  {
                this.tablero.add(new ArrayList<Cell>());
            }

        }

        @Override
        public String toString() {
            String result = "";
            for (int i = 0; i < tablero.size(); i++) {
                for (int j = 0; j < tablero.get(i).size(); j++) {
                    result += tablero.get(i).get(j).toString();
                }
                result += "\n";
            }
            return result;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));        
        Sudoku sudoku = new Sudoku(9,9, scanner);
        System.out.println(sudoku);
        Sudoku.solve(sudoku);
        // Sudoku.solve(sudoku);
    }

   
}