import java.io.*;
import java.util.*;

public class Sudoku9x9 {
    
    public class Board implements PartialSolutionCost, Iterable<Board>{
        private Set<Integer> setsFilas []; //array of sets, each set contains all numbers in a row
        private Set<Integer> setsColumnas[]; //array of sets, each set contains all numbers in a column
        private Set<Integer> setCuadrantes [];  //array of sets, each set contains all numbers of a square 
        private List<Cell> cellList; //a cell is a to be determined number 
        //Initial board maker given input, only used at start 
        public Board(String[] input){
            cellList = new ArrayList<Cell>();
            for (int i = 0; i<=8; i++){
                for (int j = 0; j<=8; j++){
                    char val = (input[i].charAt(j));
                    Integer valor = (val != '-')? Character.getNumericValue(val) : null;
                    if (valor != null) { 
                        this.setsColumnas[i] = new HashSet<Integer>();
                        this.setsFilas[i] = new HashSet<Integer>();
                        this.setCuadrantes[i] = new HashSet<Integer>();
                    }
                    else { 
                        cellList.add(new Cell(i,j, this));
                    }
               }
                
           }
        }
        private Board board;
        public Board(Board board){
           this.setsFilas = board.setsFilas;
           this.setsColumnas = board.setsColumnas;
           this.cellList = board.cellList;
        }
        
        @Override
        public boolean isValid() {
            return true;  
        }

        @Override
        public boolean isFinal() {
            return this.cellList.isEmpty();
        }

        @Override
        public List<PartialSolutionCost> successors() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Iterator<Board> iterator() {
            return new BoardIterator(this);
        }
        public void print() {
            System.out.println("Board [cellList=" + cellList + "setCuadrantes="
                    + Arrays.toString(setCuadrantes) + ", setsColumnas=" + Arrays.toString(setsColumnas)
                    + ", setsFilas=" + Arrays.toString(setsFilas) + "]");
        } 
    }
    class BoardIterator implements Iterator<Board>{
        Board board;
         BoardIterator(Board board){
            this.board = board;
            
         }
        @Override
        public boolean hasNext() {
            return false;
        }
        @Override
        public Sudoku9x9.Board next() {

            return null;
        }
    }
    public class Cell{
        private int fila;
        private int columna;
        private Set<Integer> possibleValues;
        private int cuadrado;

        public Cell(int fila, int columna, Board tablero){
            this.fila = fila;
            this.columna = columna;
            this.possibleValues = calculateValues(tablero);
            this.cuadrado = calcCuadrado();
        }
        //Calculates the set of possible values that said Cell can contain        
        public Set<Integer> calculateValues( Board tablero ){
            Set<Integer> comp = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
            comp.removeAll(tablero.setCuadrantes[this.cuadrado]);
            comp.removeAll(tablero.setsColumnas[this.columna]);
            comp.removeAll(tablero.setsFilas[this.fila]);
            return comp;
        }

        public int freedom( Board tablero ){
           return this.calculateValues(tablero).size();
        }
        
        public boolean add ( Board tablero, int valAProbar){
            //Si se consiguen añadir a las 3 es que es un valor posible 
            return (tablero.setCuadrantes[this.cuadrado].add(valAProbar) && 
                    tablero.setsColumnas[this.columna].add(valAProbar) && 
                    tablero.setsFilas[this.fila].add(valAProbar));
        }
        private int calcCuadrado(){
            // +-----+---+---+---+---+---+---+---+---+---+
            // | i\j | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |
            // +-----+---+---+---+---+---+---+---+---+---+
            // |  0  |     0     |     1     |     2     |
            // +-----+           |           |           |
            // |  1  |           |           |           |
            // +-----+           |           |           |
            // |  2  |           |           |           |
            // +-----+-----------+-----------+-----------+
            // |  3  |     3     |     4     |     5     |
            // +-----+           |           |           |
            // |  4  |           |           |           |
            // +-----+           |           |           |
            // |  5  |           |           |           |
            // +-----+-----------+-----------+-----------+
            // |  6  |     6     |     7     |     8     |
            // +-----+           |           |           |
            // |  7  |           |           |           |
            // +-----+           |           |           |
            // |  8  |           |           |           |
            // +-----+-----------+-----------+-----------+
            int f = this.fila;
            int c = this.columna;
            if ( f < 3 ){
                if (c < 3 ) return 0;
                if ( c < 6 ) return 1;
                else return 2; 
            }
            if ( f < 6 ){
                if (c < 3 ) return 3;
                if ( c < 6 ) return 4;
                else return 5; 
            }
            else { 
                if (c < 3 ) return 6;
                if ( c < 6 ) return 7;
                else return 8; 
            }
        } 
    }

     public static PartialSolutionCost firstSolution(PartialSolutionCost problem) {
        // we store the frontier of the search tree in a priority queue,
        // according to cost
        PriorityQueue<PartialSolutionCost> frontier = new PriorityQueue<PartialSolutionCost>();
        // the initial frontier only contains the problem given
        frontier.add(problem);

        // tree traversal
        PartialSolutionCost current;
        // the exit condition is that the frontier is empty
        while (!frontier.isEmpty()) {
            // we know that there is some element
            // we look at the front element
            // this is, by definition, the best one, i.e.,
            // that with the lesser cost
            current = frontier.poll();
            // if it is not valid, just remove it (do nothing)
            if (current.isValid()) {
                if (current.isFinal()) {
                    // if it is valid and final, it is a solution
                    // return it
                    return current;
                } 
                else {
                    // if it is not final, expand it
                    List<PartialSolutionCost> nextones = current.successors();
                    // and add the successors to the frontier
                    Iterator<PartialSolutionCost> cursor = nextones.iterator();
                    while (cursor.hasNext()) {
                        frontier.add(cursor.next());
                        cursor.remove(); // necessary?
                    }
                }
            }
        }
        // this should throw an exception...
        return(null);
    } // END firstSolution 
    public void main (String[] args){
      
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int i = 0; //line counter
        List<String> sudoku = new ArrayList<String>(); 
        while (scanner.hasNextLine()){
            String line = scanner.nextLine(); 
            if (line == "\n") {
               Board tablero = new Board(sudoku.stream().toArray(String[] :: new));
               Board solution = (Board) Sudoku9x9.firstSolution(tablero);
               solution.print();
               i = 0;
            }
            sudoku.add(line);
            i++;
        }
    }
}

