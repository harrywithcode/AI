
public class MainDriver {
	public static void main(String[] args){
		InputGraph inputGraph = new InputGraph();
		Container container = inputGraph.graph();
		
		//PrintMatrix printMatrix = new PrintMatrix();
		//printMatrix.printOut(container);
		AStar a = new AStar();
		a.astar(container);
	}
}
