import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class searchTester {
	
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		InvertedIndex index = new InvertedIndex("/Users/Hector/Desktop/index0");
		LinkedList<String> query = new LinkedList<>();
		Scanner sc = new Scanner(System.in);

		String line;
		while(true){
			System.out.print("Enter Search Query: ");
			line = sc.nextLine();
			System.out.println("\n Searching : " + line);
			String[] input = line.split("\\s+");	
			SearchDocuments postings = new SearchDocuments(input, index);
			query.clear();
			System.out.println(postings.getSearchResults());
		}
		//sc.close();
	}

}


