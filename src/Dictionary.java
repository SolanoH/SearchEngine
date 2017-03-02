import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class Dictionary {
	ConcurrentHashMap<String, ConcurrentHashMap<String, LinkedList<Integer>>> index;
	String postings = "Postings";
	String docFreq = "docFreq";

	public Dictionary() {
		index = new ConcurrentHashMap<String, ConcurrentHashMap<String, LinkedList<Integer>>>();
	}
	
	public void addtoDictonary(int docID, String term){
		if (index.getOrDefault(term, null) == null){
			ConcurrentHashMap<String, LinkedList<Integer>> element = new ConcurrentHashMap<String, LinkedList<Integer>>();
			LinkedList<Integer> postList = new LinkedList<Integer>();
			postList.add(docID);
			LinkedList<Integer> freq = new LinkedList<Integer>();;
			freq.add(1);
			element.put(postings, postList);
			element.put(docFreq, freq);
			index.put(term,element);	
		}
		else{
			index.get(term).get(postings).add(docID);
			int value = index.get(term).get(docFreq).getFirst();
			index.get(term).get(docFreq).set(0,value + 1);
		}
	}
	
	public int getDocFreq(String term){
		return index.get(term).get(docFreq).getFirst();
	}
	
	public LinkedList<Integer> getPostings(String term){
		return index.get(term).get(postings);
	}

}
