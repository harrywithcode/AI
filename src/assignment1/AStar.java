package assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AStar {
	public void astar(Container container){
		
		System.out.println("============================");
		
		int[][] matrix = container.getMatrix();
		String[] vertices = container.getVertices();
		
		/*******The problem is here. I didn't use straight_distance in my program.
		 * Try to re-learn A* Algorithm
		 */
		int[] straight_dis = container.getStraight_dis();
		
		int[] visit_time = new int[20];
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
		
		//key is current node, value is the path length from start point to this current node
		int[] pathValue = new int[20];
		
		int pre = startNum;
		/*
		 * pre is not a node, but should be a list of nodes*/
		//================================================================
		

		
		currentVertices = 1;
		pre = 1;
		
		for(int loop = 0; loop < 10; loop++){
			System.out.println("The input current vertice is " + currentVertices);
			System.out.println("The input previous node is " + pre);
			System.out.println();
			int savePre = pre;
			if(currentVertices != startNum ){
				visit_time[currentVertices]++;
				System.out.println("the node " + currentVertices + " has been visited " + visit_time[currentVertices]);
			}
			if(currentVertices == startNum && pre != startNum && matrix[pre][currentVertices] != 0){
				visit_time[pre]++;
				System.out.println("the node " + pre + " has been visited " + visit_time[pre]);
			}
			if(pre != startNum && matrix[pre][currentVertices] == 0){
				visit_time[pre]++;
				System.out.println("the node " + pre + " has been visited " + visit_time[pre]);
			}
			//System.out.println("ddddddddddddd");
			ArrayList<Integer> around = new ArrayList<Integer>();
			ArrayList<Integer> hn = new ArrayList<Integer>();
			
			
			//find children of current vertex
			for(int i = 0; i < 20 ; i++){
				if(i ==  pre || visit_time[i] >= 2){
					continue;
				}
				int gn = matrix[currentVertices][i];
				//System.out.println("++++++++++++++" + matrix[17][18]);
				//System.out.println("the children is " + i + " with the value " + matrix[currentVertices][i]);
				if(gn != 0){
					around.add(i);
					pathValue[i] = pathValue[pre] + gn;
					fn.put(i, pathValue[i] + straight_dis[i]);//previous path length + current heo path lenth + h(n)
					waitList.put(i, fn.get(i));
					//System.out.println("++++++++++++++++++++++++++++++++" + currentVertices);
					waitList.remove(currentVertices);
					pre = currentVertices;
				}
			}
			map.put(currentVertices, around);
//			for(int k : fn.keySet()){
//				System.out.println("the key of fn is " + k + " with the value " + fn.get(k));
//			}
//			
			//find the shortest path from children of current vertex
			int minimumFn = Integer.MAX_VALUE;
//			for(int j = 0; j < around.size(); j++){
//				if(fn.get(around.get(j)) < minimumFn){						
//					minimumFn = fn.get(around.get(j));
//					currentVertices = around.get(j);
//				}
//			}
			for(int j: fn.keySet()){
				if(fn.get(j) < minimumFn && fn.get(j) != 0){
					minimumFn = fn.get(j);
					currentVertices = j;
				}
			}
			
			//use the selected children to compare with node in waiting list
			//so that the "global" shortest path will be found
			for(int k : waitList.keySet()){
				if(fn.get(currentVertices) > waitList.get(k)){
					currentVertices = k;
				}
			}
//			if(currentVertices == pre){
//				
//			}
			//System.out.println("++++++++++++++++++++++++++++++++" + currentVertices);
			waitList.remove(currentVertices);
			path.add(currentVertices);
//	/		if(currentVertices == savePre){
//				pathValue[currentVertices] -= matrix[currentVertices][pre];
//			}
			
			System.out.println("currentVertices " + currentVertices);
			System.out.println("pre "+pre);
			
			
			for(int k:fn.keySet()){
				System.out.println("fn " + k + " --- " + fn.get(k));
			}
			
			for(int k:waitList.keySet()){
				System.out.println("wait list " + k + " --- " + waitList.get(k));
			}
			System.out.println("the " + loop + " loop is finished  =============================================");
			System.out.println();
			//=========================================
			//judge whether we reach the goal
			//if(currentVertices == endNum){
				travelToGoal = true;
			//}
			
			for(int i = 0; i < path.size(); i++){
				//System.out.println(path.get(i));
			}
		}	
		
		//return path;
	}
}
