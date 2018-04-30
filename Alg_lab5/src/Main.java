import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {
	static int n, m, A, B, up[], types[], type[][], temp_type, c = 1, SIZE;
	static Double result = 999999999.9;
	static double weight[][];
	
	public static void main(String[] args){
		int ans = 0;
		do{
			ans = enterInt(1, 3, "Выберите пункт меню:\n1. Генерация графа\n2. Нахождение минимальной стоимости\n3. Выход");
			switch(ans){
			case 1: 
				generateGraf();
				System.out.println("Граф успешно сгенерирован. Проверьте файл TOUR.IN!\n");
				break;
			case 2:
				readValues();
				findMinCost();
				printResult();
				System.out.println("Минимальная стоимость успешно посчитана. Проверьте файл TOUR.OUT!\n");
				break;
			case 3:
				System.out.println("Программа успешно завершена!");
				break;
			}
		}while(ans != 3);
	}
	
	static int enterInt(int left, int right, String message){
		Scanner scan = new Scanner(System.in);
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
	static void generateGraf(){
		int n = 0, m = 0, A = 0, B = 0;
		Scanner sc = new Scanner(System.in);
		boolean Error;
		
		 do{
			Error = false;
			System.out.println("Введите количество вершин:");
			try{
				n = Integer.parseInt(sc.next());
			}
			catch(Exception e){
				Error = true;
				System.out.println("Неверный формат!");
			}
		}while(Error || n <= 1 || n > 999);
			
		do{
			Error = false;
			System.out.println("Введите количество дорог:");
			try{
				m = Integer.parseInt(sc.next());
			}
			catch(Exception e){
				Error = true;
				System.out.println("Неверный формат!");
			}
			if (m <= n || m >= n*n || m > 1000)
				System.out.println("Количество рёбер должно быть больше количества вершин и меньше его квадрата!");
		}while(Error || m <= n || m >= n*n || m > 1000);
		
		A = enterInt(0, n-1, "Введите начальный город");
		B = enterInt(0, n-1, "Введите конечный город");
		
		FileWriter writer = null;
		Random rand = new Random();
		int weight[][] = new int[n][n];
		
		try {
			writer = new FileWriter("TOUR.IN");
			writer.write(n + "\n" + m + "\n");
			
			for (int i = 0; i < n-1; i++){
				weight[i][i+1] = 1;
				writer.write(i + " " + (i+1) + " " + rand.nextInt(2) + " " + (double)((int)Math.round(rand.nextDouble()*100000))/100 + "\n");
			}

			int tempN = n-1;
			while(tempN < m){
				int o1 = rand.nextInt(n);
				int o2 = rand.nextInt(n);
				if (weight[o1][o2] == 0 && o1 != o2){
					weight[o1][o2] = 1;
					writer.write(o1 + " " + o2 + " " + rand.nextInt(2) + " " + (double)((int)Math.round(rand.nextDouble()*100000))/100 + "\n");
					tempN++;
				}
			}
			
			writer.write(A + " " + B);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				writer.close();
			} catch (IOException e) {}
		}
	}
	
	static void readValues(){
		File file = new File("/Users/mackyz9/Documents/workspace/Alg_lab5/TOUR.IN");	// Поменять путь
		Scanner sc;
		
		try {
			sc = new Scanner(file);
			sc.useLocale(Locale.US);
			
			n = sc.nextInt();
			m = sc.nextInt();
			weight = new double[n][n];
			type = new int[n][n];
			
			for (int i = 0; i < m; i++){
				int o1 = sc.nextInt();
				int o2 = sc.nextInt();
				type[o1][o2] = sc.nextInt();
				weight[o1][o2] = sc.nextDouble();
			}
			temp_type = type[0][1];
			A = sc.nextInt();
			B = sc.nextInt();
		} catch (FileNotFoundException e) {
			System.out.println("Файл не найден.");
			System.exit(1);
		}
	}
	static void printResult(){
		FileWriter writer = null;
		
		try {
			writer = new FileWriter("TOUR.OUT");
			writer.write("Минимальная стоимость: " + (double)((int)Math.round(result*1000))/1000 + "\n\nКратчайший путь:\n");
			
			for (int i = 0; i < SIZE-1; i++)
				writer.write(up[i] + " - " + types[i+1] + "\n");
			writer.write("" + B);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				writer.close();
			} catch (IOException e) {}
		}
	}
	static void findMinCost(){
		Double way = 0.0;
		up = new int[n];
		types = new int[n];
		findPath(new boolean[n], A, B, way);
	}
	public static int findPath(boolean[] vis, int from, int to, Double cost) {
	    if (from == to){
	    	if (cost < result){
	    		result = cost;
	    		SIZE = c;
	    	}
	    	return to;
	    }
	    vis[from] = true;
	    for (int v = 0; v < vis.length; v++)
	      if (!vis[v] && weight[from][v] > 0) {			//	Если есть ребро
	    	  double temp_cost;
	    	  if (from == A || type[from][v] != temp_type){
	    		  cost += weight[from][v]*1.1;
	    		  temp_cost = weight[from][v]*1.1;
	    	  }
	    	  else{
	    		  cost += weight[from][v];
	    		  temp_cost = weight[from][v];
	    	  }
	    	temp_type = type[from][v];
	    	up[c] = v; 
	    	types[c] = temp_type;
   			c++;
   			
   			if (v == from)
   				types[0] = temp_type;
			findPath(vis, v, to, cost);

   			cost -= temp_cost;
   			c--;
	      }
	    return 0;
	 }
}