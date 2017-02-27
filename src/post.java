/**
 * 
 */

/**
 * @author Hector
 *
 */

import java.util.concurrent.ConcurrentHashMap;


public class post {
	int docId;
	int termIdCount;
	String termId;
	ConcurrentHashMap<String, String> metaData;
	ConcurrentHashMap<String, String> zones;
	

	
	public post(int id, int count, String tId){
		termIdCount = count;
		docId = id;
		termId = tId;		
	}
	
	public int getTermCount(){
		return termIdCount;
	}
	
	public int getDocId(){
		return docId;
	}
	
	public String getTermId(){
		return termId;
	}
	
}
