import java.util.LinkedHashSet;

/**
 * A Pair is a container that holds to an integer and LinkedList<Integer>
 * @param <T>
 */

public class Pair{
	private Integer docFreq;
	private LinkedHashSet<Integer> postings;
	
	/* Empty constructor */
	public Pair() {
		docFreq = 0;
		postings = new LinkedHashSet<>();
	}
	
	public Pair(Integer posting){
		docFreq = 1;
		postings = new LinkedHashSet<>();
		postings.add(posting);
	}
	
	/* Returns left value */
	public Integer getDocFrequency(){
		return docFreq;
	}
	
	/* Returns right value */
	public LinkedHashSet<Integer> getPostings(){
		return postings;
	}
	
	/* Set left Value  */
	public void setLeft(Integer value){
		docFreq = value;
	}
	
	/* Set right Value  */
	public void addPosting(Integer posting){
		postings.add(posting);
		docFreq++;
	}
	
	/* Increment Left Value  */
	public void incrementDocFrequency(){
		docFreq++;
	}
	
	
	

}
