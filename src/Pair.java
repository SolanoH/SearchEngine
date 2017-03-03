import java.util.LinkedList;

/**
 * A Pair is a container that holds to an integer and LinkedList<Integer>
 * @param <T>
 */

public class Pair{
	private Integer left;
	private LinkedList<Integer> list;
	
	/* Empty constructor */
	public Pair() {
		list = new LinkedList<>();
	}
	
	public Pair(Integer leftValue, Integer rightValue){
		left = leftValue;
		list = new LinkedList<>();
		list.add(rightValue);
	}
	
	/* Returns left value */
	public Integer getLeft(){
		return left;
	}
	
	/* Returns right value */
	public LinkedList<Integer> getRight(){
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
