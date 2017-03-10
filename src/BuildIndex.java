import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class BuildIndex {


	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		LinkedList<String> fileList = searchFileDirectory.getFiles("/Users/Hector/Downloads/WEBPAGES_RAW");
		System.out.println("Start");
		System.out.println(fileList.size());
		InvertedIndex index = new InvertedIndex();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		long heapSize = Runtime.getRuntime().totalMemory();
		long stop = (long) (heapSize*.25);
		String filename;
		int docID = 0;
		int fileNumber = 0;
		ConcurrentHashMap<String, String> urls = urlMap.buildUrlMap("/Users/Hector/Desktop/bookkeeping.txt");
		String urlKey;
		post posts = new post();
		docData data;
		for( String k : urls.keySet() )
			//System.out.println( k );
		while ((filename = (String) fileList.pollFirst()) != null){
			if (heapFreeSize > stop){
				File fd = new File(filename);
				//System.out.println(filename);
				urlKey = filename.substring(37);
				//System.out.println(urlKey);
				Parser p = new Parser(fd,docID, urls.get(urlKey) );
				ConcurrentHashMap< String, Integer > d = p.wordFrequency();
				if( d != null ){
				for (Map.Entry<String, Integer> key : d.entrySet()){
					index.addtoDictonary(docID, key.getKey());
				}
				
				data = new docData(p);
				posts.addPost(data);
				}
				docID++;
			}
			else {
				index.writeIndexToDisk("/Users/Hector/Desktop/index" + fileNumber );
				fileNumber++;
				System.gc();
				index = new InvertedIndex();
			}
			
		}
		
		//index.writeIndexToFile("/Users/Hector/Desktop/index" + fileNumber );
		System.out.println("DONE");
		LinkedList<String> query = new LinkedList<>();
		Scanner sc = new Scanner(System.in);
		
		String line;
		while(true){
			System.out.print("Enter Search Query: ");
			line = sc.nextLine();
			System.out.println("\n Searching : " + line);
			String[] input = line.split("\\s+");	
			LinkedList< String > d = new LinkedList<>();
			for( String a : input )
				d.add( a );
			SearchDocuments postings = new SearchDocuments(input, index);
			//System.out.println(postings.getSearchResults());
			Ranker ranker = new Ranker(d, posts.getPostData(postings.getSearchResults()));
			System.out.println(ranker.rankURL());
			
			query.clear();
		}
	
	}
	
}


