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
		int startNum = 1;
		int endNum = 19;
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
		//record path��¼�������п��ܵ�·�����Լ����ǵĳ��ȡ����ֻ������һ��list�Ľ�β��end node
		//�����list���棬����������list��ͷ��β�͵ľ��볤�ȣ�list�Ŀ�ͷ��һλ��¼�������ֵ
		//================================================================
		
		currentVertices = startNum;//���ʼ����
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
				around.add(i);
				//=============
				List<Integer> tails = new ArrayList<Integer>();
				
				for(int j = 0; j < recordPath.size(); j++){
					int tail = recordPath.get(j).size();//ÿһ�������һλβ��
					
					if(recordPath.get(j).get(tail) == pre){						
						tails.add(j);//tails���������ǰ�������ڵĶ�ά���������
					}
				}
				int addWhichLine = 0;//���i����󵽵���ӵ��ĸ�list�����ˣ����list�ڶ�ά������ı��
				if(tails.size() == 0){//�³��ĵ㣬û��ǰ��
					ArrayList<Integer> newPath = new ArrayList<Integer>();
					newPath.add(gn);//list�ĵ�һλ���list�ĳ���
					newPath.add(pre);
					recordPath.add(newPath);
					addWhichLine = recordPath.size();
				}
				else if(tails.size() == 1){//��һ��ǰ����ֱ������ȥ������
					recordPath.get(tails.get(0)).add(currentVertices);
					recordPath.get(tails.get(0)).set(0,recordPath.get(tails.get(0)).get(0)
							+ gn);//list���ȸ���
					addWhichLine = tails.get(0);
				}				
				else{//���ֵ������������ϵ�ǰ������Ҫ�ж��������Ǵ��ĸ�ǰ����������
					//�����ж������ǱȽ��⼸��ǰ����fn����hn��
					//���˸о���hn
					//15,14,13,5,1,  1����������
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
				System.out.println("Done!");
				break;
			}
		}
	}
}