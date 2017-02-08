import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AStar {
	public void astar(Container container){
		/*如果这个点被选作current点，他就要被移除fn，以后再也不访问它
		 * 可以利用waitlist实现，目前程序里面没用到waitlist
		 * 换句话说，只要这个node成为了current node，在他成为的一瞬间，他就被移除waitlist了
		 * 所以，不用判断这个node被访问了几次，我的visit数组用的不对，不应该存放visit次数
		 * 
		 * 选children是按照fn选，还是按照gn选？
		 * Pseudo code:

		OPEN //the set of nodes to be evaluated
		CLOSED //the set of nodes already evaluated
		add the start node to OPEN
		
		loop
		    current = node in OPEN with the lowest f_cost
		    remove current from OPEN
		    add current to CLOSED
		
		    if current is the target node //path has been found
		        return
		
		    foreach neighbour of the current node
		        if neighbour is not traversable or neighbour is in CLOSED
		            skip to the next neighbour
		
		        if new path to neighbour is shorter OR neighbour is not in OPEN
		            set f_cost of neighbour
		            set parent of neighbour to current 
		            if neighbour is not in OPEN
		                add neighbour to OPEN
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		System.out.println("============================");
		
		int[][] matrix = container.getMatrix();
		String[] vertices = container.getVertices();
		int[] straight_dis = container.getStraight_dis();
		int startNum = 1;
		int endNum = 19;
		//=======================================
		
		ArrayList<Integer> visitedNode = new ArrayList<Integer>();
			//list里存的数就是已经访问过的node的编号，index没有任何意义
		int currentVertices = startNum;
		Map<Integer, Integer> waitList = new HashMap<Integer, Integer>();
		
		Map<Integer, Integer> fn = new HashMap<Integer, Integer>();
		
		int[] pathValue = new int[20];
		
		int pre = startNum;
		//================================================================
		
		currentVertices = 1;//最初始数据
		pre = 1;
		waitList.put(currentVertices, pathValue[currentVertices] + straight_dis[currentVertices]);
		
		for(int loop = 0; loop < 30; loop++){
			System.out.println("The input current vertice is " + currentVertices);
			System.out.println("The input previous node is " + pre);
			System.out.println();
			
			waitList.remove(currentVertices);
			visitedNode.add(currentVertices);
			
			//System.out.println("ddddddddddddd");
			ArrayList<Integer> around = new ArrayList<Integer>();
			ArrayList<Integer> hn = new ArrayList<Integer>();
			
			
			//find children of current vertex
			for(int i = 0; i < 20 ; i++){
				int gn = matrix[currentVertices][i];
				int skip = 0;
				for(int j = 0;j < visitedNode.size(); j++){
					if(i == visitedNode.get(j)){
						skip = 1;
					}
					if(gn == 0){
						skip = 1;
					}
				}
				
				if(skip == 1){
					continue;
				}
				
				//System.out.println("++++++++++++++" + matrix[17][18]);
				//System.out.println("the children is " + i + " with the value " + matrix[currentVertices][i]);
				if(gn != 0){
					around.add(i);
					pathValue[i] = pathValue[pre] + gn;
					fn.put(i, pathValue[i] + straight_dis[i]);//previous path length + current heo path lenth + h(n)
					waitList.put(i, fn.get(i));
				}
			}
			
			int count = 0;
			for(int i = 0; i < 20; i++){
				if(matrix[currentVertices][i] != 0){
					count++;
				}
			}
			if(count == 0){
				waitList.remove(currentVertices);
			}
				
			
			
			int minimumFn = Integer.MAX_VALUE;
			int chooseNode = startNum;
			for(int j :waitList.keySet()){
				if(waitList.get(j) < minimumFn){						
					minimumFn = waitList.get(j);
					chooseNode = j;
				}
			}
			pre = currentVertices;
			currentVertices = chooseNode;
			
			if(currentVertices == 19){
				System.out.println("Done!");
				break;
			}
			//System.out.println("++++++++++++++++++++++++++++++++" + currentVertices);
			
			
//			System.out.println("currentVertices " + currentVertices);
//			System.out.println("pre "+pre);
//			
//			
//			for(int k:fn.keySet()){
//				System.out.println("fn " + k + " --- " + fn.get(k));
//			}
//			
//			for(int k:waitList.keySet()){
//				System.out.println("wait list " + k + " --- " + waitList.get(k));
//			}
//			System.out.println("the " + loop + " loop is finished  =============================================");
//			System.out.println();
			//=========================================
			//judge whether we reach the goal
			//if(currentVertices == endNum){
				//travelToGoal = true;
			//}
			
			
		}	
		
		//return path;
	}
}