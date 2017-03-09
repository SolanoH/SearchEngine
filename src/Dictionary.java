import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class Dictionary implements Serializable {
	ConcurrentHashMap<String,Pair> index;
	private static final long serialVersionUID = 1L;

	public Dictionary() {
		index = new ConcurrentHashMap<>();
	}
	
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
	
	public int getDocFreq(String term){
		return index.get(term).getDocFrequency();
	}
	
	public LinkedHashSet <Integer> getPostings(String term){
		return index.get(term).getPostings();
	}
	
	/* write Class Dictionary to file */
	public void writeIndexToFile(String filename) throws FileNotFoundException{
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
	
	
	/* load Class Dictionary from file */
	public void loadIndexFromFile(String filename){
		try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = buffer.readLine()) != null) {
		       String[] data = line.split(",");
		       for (int i = 1; i < data.length; i++){
		    	   addtoDictonary(Integer.parseInt(data[i]), data[0] );
		       }
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}



