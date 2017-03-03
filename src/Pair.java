import java.util.LinkedHashSet;

/**
 * A Pair is a container that holds to an integer and LinkedList<Integer>
 * @param <T>
 */

public class Pair{
	private Integer left;
	private LinkedHashSet<Integer> list;
	
	/* Empty constructor */
	public Pair() {
		list = new LinkedHashSet<>();
	}
	
	public Pair(Integer leftValue, Integer rightValue){
		left = leftValue;
		list = new LinkedHashSet<>();
		list.add(rightValue);
	}
	
	/* Returns left value */
	public Integer getLeft(){
		return left;
	}
	
	/* Returns right value */
	public LinkedHashSet<Integer> getRight(){
		return list;
	}
	
	/* Set left Value  */
	public void setLeft(Integer value){
		left = value;
	}
	
	/* Set right Value  */
	public void addElement(Integer element){
		list.add(element);
	}
	
	/* Increment Left Value  */
	public void incrementLeft(){
		left++;
	}
	
	
	

}
