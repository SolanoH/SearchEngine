import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;

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
			Pair element = new Pair(1,docID);
			index.put(term,element);	
		}
		else{
			index.get(term).addElement(docID);
			index.get(term).incrementLeft();
		}
	}
	
	public int getDocFreq(String term){
		return index.get(term).getLeft();
	}
	
	public LinkedList<Integer> getPostings(String term){
		return index.get(term).getRight();
	}
	
	/* write Class Dictionary to file */
	public void writeClassToFile(String filename){
		
	}
	
	
	/* load Class Dictionary from file */
	public void loadClassFromFile(String filename){
		
	}

}
