package assignment1;


public class MainDriver {
	public static void main(String[] args){
		InputGraph inputGraph = new InputGraph();
		Container container = inputGraph.graph();
//		String[] array = container.getVertices();
//		for(int i = 0; i < array.length; i++){
//			System.out.println(array[i]);
//		}
		//System.out.println(container.getVertices());
//		PrintMatrix printMatrix = new PrintMatrix();
//		printMatrix.printOut(container);
		AStar a = new AStar();
		a.astar(container);
	}
}
