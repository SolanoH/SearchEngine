import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;

/**
 * 
 * @author Hector Solano
 *
 */


/**
 * InvertedIndex class implements an inverted index. The class does not handle exceptions,
 * that is all exception are thrown for the calling program to handle exceptions.
 * The format for the inverted index to be loaded from file must be the following format:
 * Term1, post1, post2, post3, ....., postn
 * Term2, post1, post2, post3, ....., postn
 * Inverted Index uses the class Pair as its value
 */
public class InvertedIndex {
	private ConcurrentHashMap<String,Pair> index;
	private ConcurrentHashMap<String, String> indexList;

	/***
	 * Empty Constructor
	 */
	public InvertedIndex() {
		index = new ConcurrentHashMap<>();
		indexList = new ConcurrentHashMap<>();
	}

	/***
	 * ConstructorLoads inverted index from disk
	 * @param invertedDisk is the filename of the inverted index on disk
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	
	public InvertedIndex(String invertedDisk) throws FileNotFoundException, IOException {
		loadIndexFromDisk(invertedDisk);
	}
	
	/***
	 * addToDictionary adds term to the inverted index, if term is not in the index
	 * a new element is added to the index, if the term already exist, the document
	 * frequency is incremented for that term.
	 * @param docID is the document id that contains the String term
	 * @param term 
	 */
	public void addtoDictonary(int docID, String term){
		
		if( term.isEmpty() )
			return;
			if (index.getOrDefault(term, null) == null){
			Pair element = new Pair(docID);
			index.put(term,element);	
		}
		else{
			index.get(term).addPosting(docID);
			index.get(term).incrementDocFrequency();
		}
	}
	
	/***
	 * getDocufreq returns the number of documents that contain the String term
	 * if term is in the inverted index, returns null the term is not in the index
	 * @param term 
	 * @return
	 */
	public int getDocFreq(String term){
		return index.getOrDefault(term, null).getDocFrequency();
	}
	
	/***
	 * getPostings returns a sorted LinkedHashset with all the documents
	 * that contain the String term in increasing order. 0,1,2,..,n.
	 * @param term
	 * @return Returns null if the String term is not in the inverted index
	 */
	public LinkedHashSet <Integer> getPostings(String term){
		return index.getOrDefault(term, null).getPostings();
	}
	
	/***
	 * writeIndextoDisk writes the inverted index to disk.
	 * @param filename is the name of the inverted index to be written to disk
	 * @throws FileNotFoundException in thrown if the file cannot be opened
	 */
	public void writeIndexToDisk(String filename) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(filename);
		for (Entry<String, Pair> t : index.entrySet()){
			writer.print(t.getKey());
			writer.print("," + index.get(t.getKey()).getDocFrequency());
			Iterator<Integer> iterator = index.get(t.getKey()).getPostings().iterator();
			while (iterator.hasNext()){
				writer.print("," + iterator.next());
			}
			writer.println();
		}
		writer.close();
	}
	
	
	/***
	 * loadIndexFromDisk loads an inverted index from disk
	 * @param filename is the name of the inverted index to be loaded
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void loadIndexFromDisk(String filename) throws FileNotFoundException, IOException{
		index = new ConcurrentHashMap<>();
		try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = buffer.readLine()) != null) {
		       String[] data = line.split(",");
		       for (int i = 1; i < data.length; i++){
		    	   addtoDictonary(Integer.parseInt(data[i]), data[0] );
		       }
		    }
		} 
		
	}

}



