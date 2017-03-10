import java.io.File;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 */

/**
 * @author Hector
 *
 */
public class SearchDocuments {
	
	LinkedHashSet<Integer> postings;

	/**
	 * 
	 */
	public SearchDocuments() {
		postings = new LinkedHashSet<>();
	}
	
	
	public SearchDocuments(LinkedList<String> query, InvertedIndex index ) {
		postings = new LinkedHashSet<>();
		searchQuery(query, index);
	}
	
	
	public SearchDocuments(String[] query, InvertedIndex index ) {
		postings = new LinkedHashSet<>();
		searchQuery(query, index);
	}

	
	public void searchQuery(LinkedList<String> query, InvertedIndex index){
		String term = null;
		LinkedHashSet<Integer> post;
		while ((term = (String) query.pollFirst()) != null){
			if ((post = index.getPostings(term.toLowerCase())) != null){
				if (postings.isEmpty()){
					postings.addAll(post);
				}
				else
					postings.retainAll(post);
			}
			
		}
	}
	
	public void searchQuery(String[] query, InvertedIndex index){
		LinkedHashSet<Integer> post;
		for (int i = 0; i < query.length; i++){
			if ((post = index.getPostings(query[i].toLowerCase())) != null){
				if (postings.isEmpty()){
					postings.addAll(post);
				}
				else
					postings.retainAll(post);
			}
		}	
	}
	
	
	
	// returns postings from the search query
	public LinkedHashSet<Integer> getSearchResults(){
		return postings;
	}

}
