import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
	private static int count_compare = 0;
	private static int count_words = 0;
	private static int count_collision = 0;
	private static int count_input_collision = 0;
	private static int size = 506;			// Размер хэш-таблицы
	private static PhoneBook [] mas = new PhoneBook[size];
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException{
		fillArray();

		String str = null;
		int ans = 0;
		
		do{
			ans = enterInt();
			
			if (ans == 1){
				System.out.println("Введите слово");
				str = sc.next();
				
				if (str.length() < 3){
					System.out.println("Слово должно быть длинее трех символов\n");
					continue;
				}
					
				
				PhoneBook book = null;
				
				try{
					count_words++;
					book = searchElem(str.toLowerCase());
				}
				catch(NullPointerException e){}
				if(book != null)
					System.out.println("Элемент найден. Телефон: " + book.getTel() + "\n");
				else
					System.out.println("Элемент не найден\n");
			}
		}while (ans != 2);
		
		if (count_words != 0)
			System.out.println("Среднее число обращений: " + count_compare/count_words);
		else
			System.out.println("Среднее число обращений: 0");
		System.out.println("Среднее число коллизий: " + count_input_collision/count_collision + "\n");
		
		System.out.println("Программа успешно завершена!");
	}
	
	public static void fillArray() throws IOException{
		String FILENAME = "input.txt";
		List<String> lines = null;
		
		lines = Files.readAllLines(Paths.get(FILENAME), StandardCharsets.UTF_8);

		String temp1 = null, temp2 = null;
		for (String line: lines){
			int pos_space = line.indexOf('\t');
			
			temp1 = line.substring(0, pos_space);
			line = line.substring(pos_space + 1, line.length());
			temp2 = line.substring(0, line.length());

			PhoneBook book = new PhoneBook();
			book.setLogin(temp1.toLowerCase());
			book.setTel(temp2.toLowerCase());
			
			if (addToMap(book) == 1)
				return;
		}
	}
	
	public static int Hash(String word){
		char[] first_letters = new char[3];
		word.getChars(0, 2, first_letters,  0);
		first_letters[2] = first_letters[0];
		word.getChars(word.length()-1, word.length(), first_letters,  0);
		return (int)first_letters[0] + (int)first_letters[1] + (int)first_letters[2];
	}
	public static int addToMap(PhoneBook book){
		int i = 1, hash;
		String str1 = book.getLogin();
		
			hash = Hash(str1);

			if (mas[hash] == null){
				mas[hash] = book;
			}
			else{
				int hash_temp = hash;

				hash++;
				count_collision++;
				while (mas[hash] != null){
					i++;
					if (hash_temp == hash){
						System.out.println("Ошибка размещения идентификатора с индексом: " + i + ". Таблица заполнена. Свободных ячеек нет");
						return 1;
					}					
					hash++;
					hash = hash % size;
					count_input_collision++;
				}
				mas[hash] = book;
				i = 1;
			}
			return 0;
	}
	public static PhoneBook searchElem(String str){
		int hash = Hash(str), hash_temp;
		
		count_compare++;
		if (mas[hash] == null)
			return null;
		else if(mas[hash].getLogin().equals(str))
			return mas[hash];
		else{
			hash_temp = hash;
			hash++;
			while(!mas[hash].getLogin().equals(str)){
				count_compare++;
				if (hash_temp == hash)
					return null;
				hash++;
				hash = hash % size;
			}
		}
		
		if (hash_temp == hash)
			return null;
		else
			return mas[hash];
	}
	public static int enterInt(){
		int ans = 0;
		sc = new Scanner(System.in);
		
		do{
			System.out.println("1. Найти элемент\n2. Выход");
			try{
				ans = Integer.parseInt(sc.next());
			}
			catch(Exception e){
				System.out.println("Выберите действие\n");
				continue;
			}
			if (ans < 1 || ans > 2)
				System.out.println("Выберите действие\n");
			
		}while(ans != 1 && ans != 2);
		return ans;
	}
}