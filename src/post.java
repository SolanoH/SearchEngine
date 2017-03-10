/**
 * 
 */

/**
 * @author Hector
 *
 */

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;


public class post {
	ConcurrentHashMap<Integer, docData> posts;

	
	public post(){
		posts = new ConcurrentHashMap<Integer, docData>();
	}
	
	public void addPost(docData post){
		posts.put(post.docId, post);
	}
	
	
	// Takes a list of document IDs and returns a list of docData
	public LinkedHashSet<docData> getPostData(LinkedHashSet<Integer> postList){
		LinkedHashSet<docData> postings = new LinkedHashSet<>();
		Iterator<Integer> iterator = postList.iterator();
		while (iterator.hasNext()){
			postings.add(posts.get(iterator.next()));
		}
		return postings;
			
	}
}
	
