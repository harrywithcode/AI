
public class MainDriver {
	public static void main(String[] args){
		InputGraph inputGraph = new InputGraph();
		Container container = inputGraph.graph();
		
		//PrintMatrix printMatrix = new PrintMatrix();
		//printMatrix.printOut(container);
		AStar a = new AStar();
		String part1 = "1";//any number except 0.25 and 0.75
		a.astar(container, part1);
		System.out.println("=========");
		
		String b = "0.25";
		a.astar(container, b);
		System.out.println("=========");
		
		String c = "0.75";
		a.astar(container, c);
		
		System.out.println("=========");
		System.out.println("The time complexity of worst case is O(N*N). The worst case is traverse all nodes and calculate all their children. It will spend exponential time complexity");
	}
}
