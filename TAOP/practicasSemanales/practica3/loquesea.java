// Shortest route problems using heuristic search methods
// Best first search
// ShortestRoute2.java
//
// Julio Mariño - MMXX

import java.util.List;
import java.util.ArrayList;

public class ShortestRoute2 {

    // first, we define a class to model graph search
    public static class GraphState implements PartialSolutionCost {

	// we need a (directed) graph
	// for the moment, an array will suffice
	public int[][] graph;

        // the following are sets of vertices
	public boolean[] visited;

	// start and destination of our trip
	public int start, end;

	// distance travelled
	public int distance;

	// path so far
	public ArrayList<Integer> route;

	// constructor
	public GraphState(int[][] graph, int start, int end) {
	    this.graph = graph; // just a ref -- graph is read-only
	    this.start = start;
	    this.end   = end;
	    this.route = new ArrayList<Integer>();
	    // route is never empty -- it always contains the starting point
	    // do this only the first time:
	    if (this.route.size() == 0) {
		this.route.add(start);
		// the starting point has been "visited"
		this.visited = new boolean[this.graph.length];
		this.visited[start] = true; // the others should be false
	    }
	}

	// PartialSolution interface
	// all routes are valid
	public boolean isValid() {
	    return true;
	}

	// a route is final when it reaches the endpoint
	public boolean isFinal() {
	    int size = this.route.size();
	    return this.end == this.route.get(size-1);
	}

	// we extend a route by adding neighbours
	// of the last node visited
	public List<PartialSolutionCost> successors() {
	    int routesize = this.route.size();
	    int lastnode = this.route.get(routesize-1);

	    // the successors will be stored in nextones
	    ArrayList nextones = new ArrayList<GraphState>();
	    
	    // we obtain the number of nodes
	    int graphsize = this.graph.length;
	    // arcs are nonnegative positions in the graph matrix

	    // we look for neighbors of the lastnode that have
	    // not been visited before
	    for (int i = 0; i < graphsize; i++) {
		if (this.graph[lastnode][i] > 0 &&
		    !this.visited[i]) {
		    GraphState next = new GraphState(this.graph,this.start,this.end);
		    // overwrite next's route :(
		    next.route = new ArrayList<Integer>();
		    // we copy our current route to the new route...
		    for (int j = 0; j < routesize; j++) {
			next.route.add(this.route.get(j));
		    }
		    // ...and add the new visited
		    next.route.add(i);

		    // we copy our visited set to the new state...
		    for (int j = 0; j < graphsize; j++) {
			next.visited[j] = this.visited[j];
		    }
		    // ...and we add the new node visited
		    next.visited[i] = true;
		    
		    // we deliver the new state
		    nextones.add(next);
		}
	    }	    
	    
	    // return successors
	    return nextones;
	}

	// Comparable interface
	// the cost is the length of the current route
	private int cost() {
            // initialize length to 0
	    int length = 0;

	    // the route is never empty
	    // it contains at least the starting point
	    int size = this.route.size();
	    for (int i = 0; i < size-1; i++) {
		length += this.graph[route.get(i)][route.get(i+1)];
	    }
	    return length;
	}

	// we compare according to cost
	public int compareTo(Object o) {
	    // o must be another GraphState
	    int mine = this.cost();
	    int his  = ((GraphState)o).cost();
	    
	    if (mine < his) {
		return -1;
	    } else if (mine > his) {
		return 1;
	    } else {
		return 0;
	    }
	}

	// Showable??
	public void show() {
	    // print starting point
	    System.out.print(ShortestRoute2.pretty(this.start));
	    int routesize = this.route.size();

	    // print the rest of the nodes
	    for (int i = 1; i < routesize; i++) {
		System.out.print(" - " + (ShortestRoute2.pretty(this.route.get(i))));
	    }

	    // print length
	    int length = 0;
	    for (int i = 0; i < routesize - 1; i++) {
		length += this.graph[this.route.get(i)][this.route.get(i+1)];
	    }
	    System.out.printf(" ( %d )", length);
	}
    }

    // dirtyprinting
    public static String pretty(int index) {
	String[] letters =
	    new String[] {"A","B","C","D","E","F","G"};
	return letters[index];
    }

    // let us run some tests
    public static void main (String[] args) {
	// the graph in the slides
	int[][] penta = new int[5][5];

        // unconnected nodes are assigned a negative distance
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		penta[i][j] = -1;
	    }
	}

	// symbolic names for nodes
	final int A = 0;
	final int B = 1;
	final int C = 2;
	final int D = 3;
	final int E = 4;

	// the actual distances
	penta[A][B] = 6; penta[B][A] = penta[A][B];
	penta[A][D] = 1; penta[D][A] = penta[A][D];
	penta[B][C] = 5; penta[C][B] = penta[B][C];
	penta[B][D] = 2; penta[D][B] = penta[B][D];
	penta[B][E] = 2; penta[E][B] = penta[B][E];
	penta[C][E] = 5; penta[E][C] = penta[C][E];
	penta[D][E] = 1; penta[E][D] = penta[D][E];

	// the problem
	GraphState query = new GraphState(penta,C,A);


	// 2nd approach: choose always the shortest path so far
	// and output the first solution you find
	PartialSolutionCost route = BestFirst.firstSolution(query);

	((GraphState)route).show();
	System.out.print("\n");
    }
}