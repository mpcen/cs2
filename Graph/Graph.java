// Sean Szumlanski  / modified by Michael Gabilondo Spring 2014
// COP 3503, Fall 2013

// Graph.java
// ==========
// This is a framework for initializing a graph from an input file that contains
// an adjacency matrix. The first line of the input file indicates how many
// vertices are in the graph. The following n lines contain the adjacency matrix
// for the (zeros and ones). For example:
//
//    4
//    0 1 0 1
//    1 0 1 1
//    0 1 0 1
//    1 1 1 0
//
// ...which corresponds to the unweighted, undirected graph:
//
//    0---1
//     \ /|
//      X |
//     / \|
//    2---3
//
// These values are read into a boolean matrix, and we perform breadth-first and
// depth-first search traversals starting from an arbitrary vertex.
//
// The comments in this file are sparse in some places because I've already
// explained this code in class. In other places, the comments are unusually
// verbose because I want you to have a written copy of some of the comments I
// made about this code. In your programming assignments, please do not emulate
// the unbalanced commenting style I am employing here. :)


import java.util.*;
import java.io.*;

public class Graph
{
	boolean [][] matrix;

	
	// Initialize 'matrix' from input file
	public Graph(String filename) throws Exception
	{
		Scanner in = new Scanner(new File(filename));
		int N = in.nextInt();
		
		matrix = new boolean[N][N];
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				matrix[i][j] = (in.nextInt() == 1);
	}
	
	// Iterative BFS method.
	public void BFS(int start)
	{
		// Iterative BFS places vertices in a queue. When we pull a vertex out of
		// the queue, we process it (in this case, print it to the screen), then
		// place all its unvisited neighbors in the queue. We mark each vertex as
		// visited as it goes into the queue, because that ensures we never place
		// a vertex into the queue more than once. (That can significantly reduce
		// the space complexity of this algorithm when dealing with a large, dense
		// graph.)
		
		// A queue is an abstract data type. We need to decide what underlying
		// data structure to use to implement it. Java will not allow you to do,
		// e.g.: Queue<Integer> q = new Queue<Integer>();
		Queue<Integer> q = new LinkedList<Integer>();
		boolean [] visited = new boolean[matrix.length];
		
		// Start by adding the start vertex to the queue. It will be the first
		// thing dequeued, at which point we'll print it and add its neighbors
		// to the queue.
		q.add(start);
		System.out.print(start + " ");
		visited[start] = true;
		
		while (!q.isEmpty())
		{
			// Remove a node from the queue and process it. If we were searching
			// for a particular node (a "goal"), this is where we would check
			// whether we had found it. If so, we would terminate the BFS. However,
			// since the goal of this BFS method is simply to print all nodes of a
			// graph in BFS order, we simply print this node and move on.
			int node = q.remove();
			//System.out.println(node);
			
			// Add all neighbors of 'node' to the queue (as long as they haven't
			// been visited already).
			for (int i = 0; i < matrix.length; i++)
				if (matrix[node][i] && !visited[i])
				{
					visited[i] = true;
					q.add(i);
					//System.out.println("Add " + i + " to queue");
					System.out.print(i + " ");
				}
			//System.out.println("Queue: " + q);
		}
	}


	public void DFS_iter(int start)
	{
		Stack<Integer> q = new Stack<Integer>();
		boolean [] visited = new boolean[matrix.length];
		
		q.add(start);
		System.out.print(start + " ");
		visited[start] = true;
		
		while (!q.empty())
		{
			int node = q.pop();
			//System.out.print(node + " ");
			
			for (int i = 0; i < matrix.length; i++)
				if (matrix[node][i] && !visited[i])
				{
					visited[i] = true;
					q.push(i);
					System.out.print(i + " ");
					//System.out.println("Add " + i + " to queue");
					//System.out.print(i + " ");
				}
			//System.out.println("Queue: " + q);
		}
	}


	
	// Wrapper to our recursive DFS method. This one sets up the 'visited' array.
	public void DFS(int start)
	{
		// Recall that I mentioned the Arrays.fill() method here. If we want to
		// fill a boolean array with all 'true' values, we could use, e.g.,
		// Arrays.fill(myArray, true).
		boolean [] visited = new boolean[matrix.length];
		DFS(start, visited);
	}
	
	private void DFS(int node, boolean [] visited)
	{
		// As soon as we encounter a node in our DFS traversal, we mark it as
		// visited. This ensures that we won't get stuck in an infinite loop if
		// our graph has a cycle.
		visited[node] = true;
		
		// If we were searching for a particular vertex (a "goal"), this is where we
		// would check whether we have found the goal. If so, we would terminate the
		// DFS. However, since the purpose of this particular method is simply to
		// print all vertices in DFS order, we just print this vertex and move on.
		System.out.print(node + " ");
		
		// Now call DFS() on all of this node's neighbors that haven't already
		// been visited.
		for (int i = 0; i < matrix.length; i++)
			if (matrix[node][i] && !visited[i])
				DFS(i, visited);
	}


	public static void main(String [] args) throws Exception
	{
		// first sample graph
		Graph g = new Graph("g1.txt");
		System.out.print("BFS(0, 0): "); g.BFS(0);
		System.out.println("");
		//System.out.print("DFS(0, 0): "); g.DFS(0);
		System.out.print("DFS(0, 0): "); g.DFS_iter(0);
		System.out.println("");

		// second sample graph
		g = new Graph("g2.txt");
		System.out.print("BFS(1, 0): "); g.BFS(0);
		System.out.println("");
		//System.out.print("DFS(1, 0): "); g.DFS(0);
		System.out.print("DFS(1, 0): "); g.DFS_iter(0);
		System.out.println("");

		// complete graph
		g = new Graph("g3.txt");
		System.out.print("BFS(2, 0): "); g.BFS(0);
		System.out.println("");
		//System.out.print("DFS(2, 0): "); g.DFS(0);
		System.out.print("DFS(2, 0): "); g.DFS_iter(0);
		System.out.println("");

		// line
		g = new Graph("g4.txt");
		System.out.print("BFS(3, 0): "); g.BFS(0);
		System.out.println("");
		//System.out.print("DFS(3, 0): "); g.DFS(0);
		System.out.print("DFS(3, 0): "); g.DFS_iter(0);
		System.out.println("");

		// cycle
		g = new Graph("g5.txt");
		System.out.print("BFS(4, 0): "); g.BFS(0);
		System.out.println("");
		//System.out.print("DFS(4, 0): "); g.DFS(0);
		System.out.print("DFS(4, 0): "); g.DFS_iter(0);
		System.out.println("");


		// change starting vertices

		// first sample graph
		g = new Graph("g1.txt");
		System.out.print("BFS(5, 1): "); g.BFS(1);
		System.out.println("");
		//System.out.print("DFS(5, 1): "); g.DFS(1);
		System.out.print("DFS(5, 1): "); g.DFS_iter(1);
		System.out.println("");

		// second sample graph
		g = new Graph("g2.txt");
		System.out.print("BFS(6, 1): "); g.BFS(1);
		System.out.println("");
		//System.out.print("DFS(6, 1): "); g.DFS(1);
		System.out.print("DFS(6, 1): "); g.DFS_iter(1);
		System.out.println("");

		// complete graph
		g = new Graph("g3.txt");
		System.out.print("BFS(7, 1): "); g.BFS(1);
		System.out.println("");
		//System.out.print("DFS(7, 1): "); g.DFS(1);
		System.out.print("DFS(7, 1): "); g.DFS_iter(1);
		System.out.println("");

		// line
		g = new Graph("g4.txt");
		System.out.print("BFS(8, 1): "); g.BFS(1);
		System.out.println("");
		//System.out.print("DFS(8, 1): "); g.DFS(1);
		System.out.print("DFS(8, 1): "); g.DFS_iter(1);
		System.out.println("");

		// cycle
		g = new Graph("g5.txt");
		System.out.print("BFS(9, 1): "); g.BFS(1);
		System.out.println("");
		//System.out.print("DFS(9, 1): "); g.DFS(1);
		System.out.print("DFS(9, 1): "); g.DFS_iter(1);
		System.out.println("");

	}
}
