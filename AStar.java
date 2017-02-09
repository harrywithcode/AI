import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AStar {
	public void astar(Container container){
		/*�������㱻ѡ��current�㣬����Ҫ���Ƴ�fn���Ժ���Ҳ��������
		 * ��������waitlistʵ�֣�Ŀǰ��������û�õ�waitlist
		 * ���仰˵��ֻҪ���node��Ϊ��current node��������Ϊ��һ˲�䣬���ͱ��Ƴ�waitlist��
		 * ���ԣ������ж����node�������˼��Σ��ҵ�visit�����õĲ��ԣ���Ӧ�ô��visit����
		 * 
		 * ѡchildren�ǰ���fnѡ�����ǰ���gnѡ��
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
		int startNum = 19;
		int endNum = 1;
		//=======================================
		
		ArrayList<Integer> visitedNode = new ArrayList<Integer>();
			//list�����������Ѿ����ʹ���node�ı�ţ�indexû���κ�����
		
		Map<Integer, Integer> waitList = new HashMap<Integer, Integer>();
		
		Map<Integer, Integer> fn = new HashMap<Integer, Integer>();
		
		int[] pathValue = new int[20];
		int currentVertices = startNum;
		int pre = startNum;
		
		
		Map<Integer,Integer> recordPre = new HashMap<Integer,Integer>();//key is current node, value is its parent
		ArrayList<ArrayList<Integer>> recordPath = new ArrayList<ArrayList<Integer>>();
//		ArrayList<Integer> start = new ArrayList<Integer>();
//		start.add(0);
//		start.add(1);
//		recordPath.add(start);
		//record path��¼�������п��ܵ�·�����Լ����ǵĳ��ȡ����ֻ������һ��list�Ľ�β��end node
		//�����list���棬����������list��ͷ��β�͵ľ��볤�ȣ�list�Ŀ�ͷ��һλ��¼�������ֵ
		//================================================================
		
		waitList.put(currentVertices, 0 + straight_dis[currentVertices]);
		
		for(int loop = 0; loop < 30; loop++){
			System.out.println("The input current vertice is " + currentVertices);
			//System.out.println("The input previous node is " + pre);
			System.out.println();
			
			waitList.remove(currentVertices);
			visitedNode.add(currentVertices);
			
			
			
			for(int i = 0; i < 20 ; i++){
				int gn = matrix[currentVertices][i];
				int skip = 0;//�������ʹ���node�������ϵ�node
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
				
				
				int truePre = currentVertices;//��ʵ��ͼ���ǰ���Ͷ��ӣ�����λ�ù�ϵ�����Ǳ���˳���ϵ
				int children = i;
				
				//=============
				List<Integer> tails = new ArrayList<Integer>();
				
				for(int j = 0; j < recordPath.size(); j++){
					int tail = recordPath.get(j).size() - 1;//ÿһ�������һλβ��
					
					if(recordPath.get(j).get(tail) == truePre){	//�����¼��pre��ʵ�ʵ�ͼ�ϵ�pre�������߼�˳���ϵ�pre					
						tails.add(j);//tails���������ǰ�������ڵĶ�ά���������
					}
				}
				int addWhichLine = 0;//���i����󵽵���ӵ��ĸ�list�����ˣ����list�ڶ�ά������ı��
				if(tails.size() == 0){//�³��ĵ㣬û��ǰ��
					ArrayList<Integer> newPath = new ArrayList<Integer>();
					boolean realNew = true;
					//========= final problem
					realNew = copyOrCreate(recordPath, truePre);
					
					if(realNew == false){//��ǰ��������
						ArrayList copyLine = new ArrayList<Integer>();
						copyLine = copyWhichLine(recordPath,truePre);
						recordPath = copy(recordPath, copyLine, currentVertices, children, matrix);
						addWhichLine  = recordPath.size() - 1;
					}
					//=========
					if(realNew == true){//ǰ���������άlist����һ��û�г��ֹ�
						newPath.add(gn);//list�ĵ�һλ���list�ĳ���
						newPath.add(truePre);
						newPath.add(children);
						recordPath.add(newPath);
						addWhichLine = recordPath.size() - 1;
					}					
				}
				else if(tails.size() == 1){//��һ��ǰ����ֱ������ȥ�����ˣ�ͬʱ��Ҫ�鿴�Ƿ���Ҫcopy
					//�����Ѿ���19-0-15-5��19-12-15�������14��ʱ�򲻽�Ҫ��15������ӣ�ҲҪcopyǰ���Ǹ�list
					ArrayList<Integer> duplicate = new ArrayList<Integer>();
					for(int t = 0; t < recordPath.get(tails.get(0)).size(); t++){
						duplicate.add(recordPath.get(tails.get(0)).get(t));
					}
					
					recordPath.get(tails.get(0)).add(children);
					recordPath.get(tails.get(0)).set(0,recordPath.get(tails.get(0)).get(0) + gn);//list���ȸ���
					recordPath.add(duplicate);
					//tails.add(recordPath.size()-1);
					addWhichLine = tails.get(0);
					
//					for(int p = 0;p < tails.size();p++){
//						System.out.println("tails updated " + tails.get(p));
//					}
					
				}				
				else{//���ֵ������������ϵ�ǰ������Ҫ�ж��������Ǵ��ĸ�ǰ����������
					//�����ж������ǱȽ��⼸��ǰ����fn����hn��
					//���˸о���hn
					//15,14,13,5,1,  1����������
					int min = Integer.MAX_VALUE;
					for(int p = 0;p < tails.size();p++){
						System.out.println("tails " + tails.get(p));
					}
					for(int p = 0;p < tails.size();p++){
						if(recordPath.get(tails.get(p)).get(0)  < min){
							min = tails.get(p);
							System.out.println("recordPath.get(tails.get(p)).get(0) "+recordPath.get(tails.get(p)).get(0));
							System.out.println("mmmm " + min);
						}
					}
					System.out.println("Min is " + min);
					ArrayList<Integer> duplicate = new ArrayList<Integer>();
					for(int t = 0; t < recordPath.get(tails.get(min)).size(); t++){
						duplicate.add(recordPath.get(tails.get(min)).get(t));
					}
					recordPath.get(tails.get(min)).add(children);
					recordPath.get(tails.get(min)).set(0,recordPath.get(tails.get(min)).get(0) 
							+ gn);
					recordPath.add(duplicate);
					//tails.add(recordPath.size()-1);
					addWhichLine = tails.get(min);
					
//					for(int p = 0;p < tails.size();p++){
//						System.out.println("tails updated " + tails.get(p));
//					}
					
				}
				
				//*******Debug**********
				for(int d = 0; d < recordPath.size(); d++){
					for(int e = 0; e < recordPath.get(d).size(); e++){
						System.out.print(recordPath.get(d).get(e) + " - ");
					}
					System.out.println();
				}
				System.out.println("====");
				//*****************
				
				//************Problem under this line. How to compute length of path
				//and how to record path
				//�Ȱ����е�·����д����άlist�Ȼ���ÿ��·����Ӧ�ĳ��ȶ��������
				//���³��ȵ�ʱ��ֱ����list������£�fn��ӵ�ʱ��ֱ��ȥlist������·������
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
				System.out.println("We reach the goal!! The path from start to goal is shown below:");
				int minFn =Integer.MAX_VALUE;
				int pathNum = 0;
				for(int q = 0; q < recordPath.size(); q++){
					ArrayList<Integer> finalPath = new ArrayList<Integer>();
					finalPath = recordPath.get(q);				
					if(finalPath.get(finalPath.size()-1) == endNum){
						if(finalPath.get(0) < minFn){
							minFn = finalPath.get(0);
							pathNum = q;
						}
					}
				}
				for(int t = 1; t < recordPath.get(pathNum).size() - 1; t++){
					System.out.print(vertices[recordPath.get(pathNum).get(t)] + " --> ");
				}
				System.out.println(vertices[recordPath.get(pathNum).get(recordPath.get(pathNum).size() - 1)]);
				System.out.println("The total path length is " + recordPath.get(pathNum).get(0));
				break;
			}
		}
	}
//=====================================	
	public boolean copyOrCreate(ArrayList<ArrayList<Integer>> recordPath, int truePre){
		boolean realNew = true;
		for(int i = 0; i < recordPath.size(); i++){
			int secondLast = recordPath.get(i).size() - 2;
			if(recordPath.get(i).get(secondLast) == truePre){
				realNew = false;
			}
		}
		return realNew;
	}
	//copy���е�paths
	public ArrayList copyWhichLine(ArrayList<ArrayList<Integer>> recordPath, int truePre){
		ArrayList result = new ArrayList<Integer>();//copy from which line? or which lines?
		for(int i = 0; i < recordPath.size(); i++){
			int secondLast = recordPath.get(i).size() - 2;
			if(recordPath.get(i).get(secondLast) == truePre){
				result.add(i);
			}
		}
		return result;
	}
	public ArrayList<ArrayList<Integer>> copy(ArrayList<ArrayList<Integer>> recordPath, 
			ArrayList<Integer> copyLines, int currentVertices, int children, int[][] matrix){
		for(int k = 0; k < copyLines.size(); k++){
			int copyLine = copyLines.get(k);
			ArrayList<Integer> ori = new ArrayList<Integer>();
			int lastOne = recordPath.get(copyLine).size()-1;
			int pathLength = recordPath.get(copyLine).get(0);
			//int last = recordPath.get(copyLine).get()
			int newPathLength = pathLength - matrix[recordPath.get(copyLine).get(lastOne)][recordPath.get(copyLine).get(lastOne-1)]
					+ matrix[currentVertices][children];
			ori.add(newPathLength);
			for(int i = 1; i < recordPath.get(copyLine).size() - 1; i++){
				ori.add(recordPath.get(copyLine).get(i));
			}
			ori.add(children);
			recordPath.add(ori);

		}
				
		return recordPath;
	}
	
}