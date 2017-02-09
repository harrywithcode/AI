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
		
		Map<Integer, Integer> waitList = new HashMap<Integer, Integer>();
		
		Map<Integer, Integer> fn = new HashMap<Integer, Integer>();
		
		int[] pathValue = new int[20];
		int currentVertices = startNum;
		int pre = startNum;
		
		
		Map<Integer,Integer> recordPre = new HashMap<Integer,Integer>();//key is current node, value is its parent
		ArrayList<ArrayList<Integer>> recordPath = new ArrayList<ArrayList<Integer>>();
		//record path记录了所有有可能的路径，以及他们的长度。最后只可能有一个list的结尾是end node
		//在这个list里面，我先添加这个list从头到尾巴的距离长度，list的开头第一位记录这个长度值
		//================================================================
		
		currentVertices = startNum;//最初始数据
		pre = startNum;
		waitList.put(currentVertices, pathValue[currentVertices] + straight_dis[currentVertices]);
		
		for(int loop = 0; loop < 30; loop++){
			System.out.println("The input current vertice is " + currentVertices);
			//System.out.println("The input previous node is " + pre);
			System.out.println();
			
			waitList.remove(currentVertices);
			visitedNode.add(currentVertices);
			
			//System.out.println("ddddddddddddd");
			ArrayList<Integer> around = new ArrayList<Integer>();
			ArrayList<Integer> hn = new ArrayList<Integer>();
			
			
			for(int i = 0; i < 20 ; i++){
				int gn = matrix[currentVertices][i];
				int skip = 0;//跳过访问过的node和连不上的node
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
				around.add(i);
				//=============
				List<Integer> tails = new ArrayList<Integer>();
				
				for(int j = 0; j < recordPath.size(); j++){
					int tail = recordPath.get(j).size();//每一条的最后一位尾巴
					
					if(recordPath.get(j).get(tail) == pre){						
						tails.add(j);//tails存放着所有前辈的所在的二维数组的行数
					}
				}
				int addWhichLine = 0;//这个i点最后到底添加到哪个list里面了，这个list在二维数组里的编号
				if(tails.size() == 0){//新出的点，没有前辈
					ArrayList<Integer> newPath = new ArrayList<Integer>();
					newPath.add(gn);//list的第一位存放list的长度
					newPath.add(pre);
					recordPath.add(newPath);
					addWhichLine = recordPath.size();
				}
				else if(tails.size() == 1){//有一个前辈，直接连过去就行了
					recordPath.get(tails.get(0)).add(currentVertices);
					recordPath.get(tails.get(0)).set(0,recordPath.get(tails.get(0)).get(0)
							+ gn);//list长度更新
					addWhichLine = tails.get(0);
				}				
				else{//这种点有两个或以上的前辈，就要判断他到底是从哪个前辈那里来的
					//但是判断依据是比较这几个前辈的fn还是hn？
					//个人感觉是hn
					//15,14,13,5,1,  1从哪里来？
					int min = Integer.MAX_VALUE;
					for(int p = 0;p < tails.size();p++){
						if(recordPath.get(tails.get(p)).get(0)  < min){
							min = p;
						}
					}
					recordPath.get(tails.get(min)).add(currentVertices);
					recordPath.get(tails.get(min)).set(0,recordPath.get(tails.get(min)).get(0) 
							+ gn);
					addWhichLine = tails.get(min);
				}
				
				//************Problem under this line. How to compute length of path
				//and how to record path
				//先把所有的路径都写进二维list里，然后把每个路径对应的长度都算出来。
				//更新长度的时候，直接在list里面更新，fn添加的时候直接去list里面找路径长度
				int pathVal = recordPath.get(addWhichLine).get(0);
				fn.put(i, pathVal + straight_dis[i]);//previous path length + current heo path lenth + h(n)
				waitList.put(i, fn.get(i));
				
			//=================================		
				
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
			
//			
			
			if(currentVertices == endNum){
				System.out.println("Done!");
				break;
			}
		}
	}
}