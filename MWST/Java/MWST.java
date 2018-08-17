package mwstTest;


import java.util.*;
import java.util.Scanner;
import java.io.File;


class Edge implements Comparable<Edge>{
  public int id;
  public int y;
  public int x;
  public Integer weight;
  
  public Edge(int ID, int X, int Y, int W){
	this.id = ID;
    this.y = Y;
    this.x = X;
    this.weight = W;
  }
  
	@Override
	public int compareTo(Edge other) {
		return weight.compareTo(other.weight);
	}
}




public class MWST{
	/* mwst(G)
		Given an adjacency matrix for graph G, return the total weight
		of all Edges in a minimum weight spanning tree.
		
		If G[i][j] == 0, there is no Edge between vertex i and vertex j
		If G[i][j] > 0, there is an Edge between vertices i and j, and the
		value of G[i][j] gives the weight of the Edge.
		No entries of G will be negative.
	*/
	private static int[] parent;
	
	
	

	public static int find(int[] parent, int i) {
		if(parent[i] == -1)
			return i;
		return find(parent, parent[i]);
	}
	public static void union(int[] parent, int x, int y) {
		int xset = find(parent, x);
		int yset = find(parent, y);
		parent[xset] = yset;
	}
	static int mwst(int[][] G){
		parent = new int[G.length]; //initialize parent array
		for(int i = 0; i < parent.length; i++) {
			parent[i] = -1; 
		}
		List<Edge> EdgeList = new LinkedList<Edge>();
		int size = 0;
	
		/* Find a minimum weight spanning tree by any method */
		/* (You may add extra functions if necessary) */
		
		//add the Edges to the list
		for(int y = 0; y < G.length; y++){
			for(int x = y; x < G[0].length; x++){ //y = x --> only half the matrix is read
				if(x != y){ //cannot be an Edge to itself
					if(G[x][y]!=0){ //only perform if there exists an Edge
						Edge E = new Edge(size, x, y, G[x][y]);
						EdgeList.add(E);
						size++;
						
					}
				}
			}	
		}
		Collections.sort(EdgeList);
		
		/* Add the weight of each Edge in the minimum weight spanning tree
		   to totalWeight, which will store the total weight of the tree.
		*/
		int totalWeight = 0;
		int[][] G2 = new int[G.length][G[0].length];//G2 is used to check for cycles in the MWST
		
		//Constructing the MWST
		List<Edge> mwstList = new LinkedList<Edge>();
		for(Edge E : EdgeList){
			//if the Edge does not create a cycle then add it to the total weight
			int x = find(parent, E.x);
			int y = find(parent, E.y);
			if((x!=y)){ 
				union(parent, x, y);
				totalWeight = totalWeight + E.weight;
				mwstList.add(E);
				G2[E.x][E.y] = E.weight;
				G2[E.y][E.x] = E.weight;
				if(mwstList.size() > (G.length-2)) { //the mwst graph is formed if #edges = #nodes-1
					break;
				}
			}
		}

		System.out.println("Number of Nodes in Graph: "+G.length);
		System.out.println("Is the Minimum Spanning Tree Graph Connected: "+isConnected(G2));
		return totalWeight;
	}

	

	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		int graphNum = 0;
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(!s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				G[i] = new int[n];
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			if (!isConnected(G)){
				System.out.printf("Graph %d is not connected (no spanning trees exist...)\n",graphNum);
				continue;
			}
			long startTime = System.currentTimeMillis();
			int totalWeight = mwst(G);
			long endTime = System.currentTimeMillis();
			long timeTaken = (endTime-startTime);
			System.out.println("Total Time: "+timeTaken+" Milliseconds");
			System.out.printf("Graph %d: Total weight is %d\n",graphNum,totalWeight);
				
		}
	}

	/* isConnectedDFS(G, covered, v)
	   Used by the isConnected function below.
	   You may modify this, but nothing in this function will be marked.
	*/
	static void isConnectedDFS(int[][] G, boolean[] covered, int v){
		covered[v] = true;
		for (int i = 0; i < G.length; i++)
			if (G[v][i] > 0 && !covered[i])
				isConnectedDFS(G,covered,i);
	}
	   
	/* isConnected(G)
	   Test whether G is connected.
	   You may modify this, but nothing in this function will be marked.
	*/
	static boolean isConnected(int[][] G){
		boolean[] covered = new boolean[G.length];
		for (int i = 0; i < covered.length; i++)
			covered[i] = false;
		isConnectedDFS(G,covered,0);
		for (int i = 0; i < covered.length; i++)
			if (!covered[i])
				return false;
		return true;
	}

}