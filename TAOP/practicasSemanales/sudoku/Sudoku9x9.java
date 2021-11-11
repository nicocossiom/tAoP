package TAOP.practicasSemanales.sudoku;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Sudoku9x9 {
    public class Cell{
        private int valor;
        private int fila;
        private int columna;
        private Set<Integer> possibleValues;
        public Cell(char valor, int fila, int columna){
            this.valor = (valor != '-')? Character.getNumericValue(valor) : null;
            this.fila = fila;
            this.columna = columna;
            this.possibleValues = new HashSet<Integer>(); 
        }
        public int freedom(Board tablero ){
            int result = 0;
            boolean done = false;
            while (!done){
                
            }
            return result;
        }
    }

    public class Board implements PartialSolutionCost, Iterable<Board>{
        private Cell [][] tabCeldas;
        private Set<Integer> sets [][];
        public Board(String[] input){
           for (int i = 0, j = 0; i<10 && j<10 ; i++, j++){
                this.tabCeldas[i][j] = new Cell(input[i].charAt(j), i, j);
                this.sets [i][j]= new HashSet<Integer>();
           }
        }
        public Board board = new Board(input);
        @Override
        public int compareTo(Object o) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public boolean isValid() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isFinal() {
            // TODO Auto-generated method stub
            return false;
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
        String [] input = {"-2-5-1-9-",
                       "8--2-3--4",
                       "-3--6--7-",
                       "--1---6--",
                       "54-----17",
                       "--2---7--",
                       "-9--3--8-",
                       "2--8-4--5",
                       "-1-9-7-6-"};
        
    }

}

