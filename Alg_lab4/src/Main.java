// 4
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
	static Scanner scan = new Scanner(System.in);
	static ArrayList<Edge> edges = new ArrayList<Edge>();
	
	public static void main(String[] args){
		int ans = 0;
        do{
	          int[][] weight = { { 0, 9, 8, 7, 0, 0 }, { 0, 0, 0, 3, 4, 0 }, { 0, 0, 0, 5, 2, 0 }, { 0, 3, 5, 0, 4, 9 }, { 0, 4, 1, 4, 0, 7 }, { 0, 0, 0, 0, 0, 0 } };
//    		  int[][] weight = { { 0, 9, 12, 0, 0, 0 }, { 0, 0, 0, 6, 11, 0 }, { 0, 0, 0, 12, 3, 0 }, { 0, 6, 12, 0, 0, 6 }, { 0, 11, 3, 0, 0, 8 }, { 0, 0, 0, 0, 0, 0 } };
//	    		int[][] weight = { { 0, 9, 12, 0, 0, 0 }, { 0, 0, 0, 1, 1, 0 }, { 0, 0, 0, 1, 1, 0 }, { 0, 1, 1, 0, 0, 6 }, { 0, 1, 1, 0, 0, 8 }, { 0, 0, 0, 0, 0, 0 } };
    		enterValues(weight);
    		
        	ans = enterInt(1, 3, "Выберите пункт меню:\n1. Найти максимальный поток\n2. Найти минимальный разрез\n3. Выход");
			switch (ans){
			case 1:
				System.out.println("Максимальный поток = " + maxFlow(weight, 0, 5) + "\n");
				break;
			case 2: 
				System.out.println("Минимальный разрез = " + minIncision(weight) + "\n");
				break;
			case 3:
				System.out.println("Программа успешно завершена!");
				break;
			}
		}while(ans != 3);
     }
	 
	 public static int enterInt(int left, int right, String message){
		int res = 0;
		do{
			System.out.println(message);
			try{
				res = Integer.parseInt(scan.next());
			}
			catch(Exception e){
				continue;
			}
		}while(res < left || res > right);
		return res;
	}
	 
	 public static void enterValues(int[][] weight){
		 for (int i = 0; i < weight.length; i++)
			 for (int j = 0; j < weight.length; j++)
				 if (weight[i][j] != 0)
					 edges.add(new Edge(i, j, weight[i][j]));
	 }
	 
	 public static int maxFlow(int[][] cap, int start, int end) {
		for (int flow = 0;;) {
	      int df = findPath(cap, new boolean[cap.length], start, end, Integer.MAX_VALUE);
	      if (df == 0)
	        return flow;
	      flow += df;
		}
	 }
	 
	 public static int findPath(int[][] cap, boolean[] vis, int u, int t, int f) {
	    if (u == t)
	      return f;
	    vis[u] = true;
	    for (int v = 0; v < vis.length; v++)
	    	if (!vis[v] && cap[u][v] > 0) {	//	Если есть ребро
	    		int df = findPath(cap, vis, v, t, Math.min(f, cap[u][v]));
	    		if (df > 0){
	    			cap[u][v] -= df;
	    			cap[v][u] += df;
	    			return df;
	    		}
	    	}
	     return 0;
	 }
	 
	 public static int minIncision(int[][] weight){		// Персонально для данного варианта: проверка множества для двух ребер, трех и 4-ех 
		 int minIncision = 99999999, e1, e2, e3, e4;
		 if ((edges.get(0).weight + edges.get(1).weight) < minIncision)
			 minIncision = edges.get(0).weight + edges.get(1).weight;
		 if ((edges.get(8).weight + edges.get(11).weight) < minIncision)
			 minIncision = edges.get(8).weight + edges.get(11).weight;
		 
		 if ((edges.get(0).weight + edges.get(4).weight + edges.get(5).weight) < minIncision)
			 minIncision = edges.get(0).weight + edges.get(4).weight + edges.get(5).weight;
		 if((edges.get(1).weight + edges.get(2).weight + edges.get(3).weight) < minIncision)
			 minIncision = edges.get(1).weight + edges.get(2).weight + edges.get(3).weight;
		 if((edges.get(2).weight + edges.get(4).weight + edges.get(11).weight) < minIncision)
			 minIncision = edges.get(2).weight + edges.get(4).weight + edges.get(11).weight;
		 if((edges.get(3).weight + edges.get(5).weight + edges.get(8).weight) < minIncision)
			 minIncision = edges.get(3).weight + edges.get(5).weight + edges.get(8).weight;
		 
		 Collections.sort(edges, new Comparator<Edge>(){
			@Override
			public int compare(Edge o1, Edge o2) {
				return Integer.compare(o1.weight, o2.weight);
			}
		 });
		 
		 start:
		 for (e1 = 3; e1 < edges.size(); e1++){
			 for (e2 = 2; e2 < edges.size(); e2++){
				 if (e1 == e2)
					 break;
				 for (e3 = 1; e3 < edges.size(); e3++){
					 if (e3 == e1 || e3 == e2)
						 break;
					 for (e4 = 0; e4 < edges.size(); e4++){
						 if (e4 == e1 || e4 == e2 | e4 == e3)
							 break;
						 if (edges.get(e1).weight + edges.get(e2).weight + edges.get(e3).weight + edges.get(e4).weight > minIncision)
							 break start;
						 else if ((edges.get(e1).weight + edges.get(e2).weight + edges.get(e3).weight + edges.get(e4).weight) < minIncision){
							 int [][] temp_weight = weight;
							 temp_weight[edges.get(e1).u][edges.get(e1).v] = 0;
							 temp_weight[edges.get(e2).u][edges.get(e2).v] = 0;
							 temp_weight[edges.get(e3).u][edges.get(e3).v] = 0;
							 temp_weight[edges.get(e4).u][edges.get(e4).v] = 0;

							 if (maxFlow(temp_weight, 0, 5) == 0){
								 minIncision = edges.get(e1).weight + edges.get(e2).weight + edges.get(e3).weight + edges.get(e4).weight;
							 }
						 }
					 }
				 }
			 }
		 }
		 edges.clear();
		 return minIncision;
	 }
}