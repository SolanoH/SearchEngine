/**
 * 
 */

/**
 * @author Hector
 *
 */

import java.util.concurrent.ConcurrentHashMap;

public class docData {
	int docId;
	ConcurrentHashMap<String, String> metaData;
	ConcurrentHashMap<String, String> zones;
	ConcurrentHashMap<String, String> authors;

	/**
	 * 
	 */
	public docData(int id,ConcurrentHashMap<String, String> data, ConcurrentHashMap<String, String> zone,
	ConcurrentHashMap<String, String> author){
		docId = id;
		metaData = data;
		zones = zone;
		authors = author;
	}
		
	// Returns true if the parameter name is one of the documents authors.
	public boolean termIdisAuthor(String  author){
		return authors.getOrDefault(author, null) == author;
	}
	
	// needs to be implemented
	public String termIdisTitle(){
		return zones.getOrDefault("title", null);
	}

	
	// needs to be implemented
	public boolean termIdinAbstract(String termId){
		return zones.get("abstract") == termId;
	}
	
	// returns date of document creation
	public String dateOfCreation(String date){
		return metaData.getOrDefault("dateOfCreation", null);
	}
	
	// return document type
	public String docType(){
		return metaData.getOrDefault("docType", null);
	}
	
	
	
		/*
		metaData = new ConcurrentHashMap<String, String>();
		metaData.put("authors", "None");
		metaData.put("dateOfCreation", "None");
		metaData.put("title", "None");
		metaData.put("docType", "None");
	
		zones.put("author", "None");
		zones.put("title", "None");
		zones.put("abstract", "None");	
		*/	
}
