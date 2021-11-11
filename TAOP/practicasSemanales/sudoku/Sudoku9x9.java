package TAOP.practicasSemanales.sudoku;

import java.io.*;
import java.util.*;
import com.google.common.collect.Sets;

public class Sudoku9x9 {
    public class Cell{
        private int valor;
        private int fila;
        private int columna;
        private Set<Integer> possibleValues;
        private int cuadrante;

        public Cell(int fila, int columna){
            this.fila = fila;
            this.columna = columna;
            this.possibleValues = new HashSet<Integer>();
            this.cuadrante = calcCuadrado();
        }
        public int freedom( Board tablero ){
            int result = 0;
            boolean done = false;
            return result;
        }
        public boolean add ( Board tablero, int valAProbar){
            //Si se consiguen añadir a las 3 es que es un valor posible 
            return (tablero.setCuadrantes[this.cuadrante].add(valAProbar) && 
                    tablero.setsColumnas[this.columna].add(valAProbar) && 
                    tablero.setsFilas[this.fila].add(valAProbar));
        }
        public void valores (){
            
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

    public class Board implements PartialSolutionCost, Iterable<Board>{
        private Set<Integer> setsFilas [];
        private Set<Integer> setsColumnas[];
        private Set<Integer> setCuadrantes [];
        private Cell current;
        private List<Cell> cellList;
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
                        cellList.add(new Cell(i,j));
                    }
               }
                
           }
        }
        public Board(Board board){
            
        }
        @Override
        public int compareTo(Object o) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public boolean isValid() {
            return (this.setCuadrantes[current.cuadrante].add(current.valAProbar) && 
                    this.setsColumnas[this.columna].add(valAProbar) && 
                    this.setsFilas[this.fila].add(valAProbar));
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
            // TODO Auto-generated method stub
            return null;
        } 
    }

    public void main (String[] args){
        
    }

}

