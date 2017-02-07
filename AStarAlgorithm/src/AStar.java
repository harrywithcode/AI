import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AStar {
	public List astar(Container container){
		int[][] matrix = container.getMatrix();
		String[] vertices = container.getVertices();
		
		/*******The problem is here. I didn't use straight_distance in my program.
		 * Try to re-learn A* Algorithm
		 */
		int[] straight_dis = container.getStraight_dis();
		
		
		boolean travelToGoal = false;
		//ArrayList<Integer> waitList = new ArrayList<Integer>();
		
		ArrayList<Integer> path = new ArrayList<Integer>();//may use HashSet instead of ArrayList later
		
		int startNum = 1;
		int endNum = 19;
		
		//ArrayList<Integer> gn = new ArrayList<Integer>();
		int currentVertices = startNum;
		
		//key is node, value is children
		Map<Integer, ArrayList> map = new HashMap<Integer, ArrayList>();
		//key is node, value is f(n) of this node
		Map<Integer, Integer> fn = new HashMap<Integer, Integer>();
		for(int i = 0; i < 20; i++){
			fn.put(i, 0);
		}
		//key is node which in the waiting list, value is f(n) of this node 
		Map<Integer, Integer> waitList = new HashMap<Integer, Integer>();
		
		while(travelToGoal == false){
			ArrayList<Integer> around = new ArrayList<Integer>();
			ArrayList<Integer> hn = new ArrayList<Integer>();
			
			//find children of current vertex
			for(int i = 0; i < 20; i++){
				int gn = matrix[currentVertices][i];
				if(gn != 0){
					around.add(i);
					fn.put(i, fn.get(i) + gn);
					waitList.put(i, fn.get(i));
				}
			}
			map.put(currentVertices, around);
			
			//find the shortest path from children of current vertex
			int minimumFn = Integer.MAX_VALUE;
			for(int j = 0; j < around.size(); j++){
				if(fn.get(around.get(j)) < minimumFn){						
					minimumFn = fn.get(around.get(j));
					currentVertices = around.get(j);
				}
			}
			
			//use the selected children to compare with node in waiting list
			//so that the "global" shortest path will be found
			for(int k : waitList.keySet()){
				if(fn.get(currentVertices) > waitList.get(k)){
					currentVertices = k;
				}
			}
			waitList.remove(currentVertices);
			path.add(currentVertices);
			//judge whether we reach the goal
			if(currentVertices == endNum){
				travelToGoal = true;
			}
						
		}
		for(int i = 0; i < path.size(); i++){
			System.out.println(path.get(i));
		}
		return path;
	}
}
