import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BuildIndex {


	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		LinkedList<String> fileList = searchFileDirectory.getFiles("/Users/Hector/Downloads/WEBPAGES_RAW");
		System.out.println("Start");
		System.out.println(fileList.size());
		Dictionary index = new Dictionary();
		long heapMaxSize = Runtime.getRuntime().maxMemory();
		long heapFreeSize = Runtime.getRuntime().freeMemory();
		long heapSize = Runtime.getRuntime().totalMemory();
		long stop = (long) (heapSize*.25);
		String filename;
		int docID = 0;
		int fileNumber = 0;
		while ((filename = (String) fileList.pollFirst()) != null){
			if (heapFreeSize > stop){
				File fd = new File(filename);
				Parser p = new Parser(fd,docID,"");
				System.out.println("MADE IT PASS NEW PARSER");
				ConcurrentHashMap< String, Integer > d = p.wordFrequency();
				System.out.println(d);
				if( d != null )
				for (Map.Entry<String, Integer> key : d.entrySet()){
					index.addtoDictonary(docID, key.getKey());
				}
				docID++;
			}
			else {
				index.writeIndexToFile("/Users/Hector/Desktop/index" + fileNumber );
				fileNumber++;
				System.gc();
				index = new Dictionary();
			}
			
		}
		
		index.writeIndexToFile("/Users/Hector/Desktop/index" + fileNumber );
		System.out.println("DONE");
		System.out.println(docID);
	}
	
}


