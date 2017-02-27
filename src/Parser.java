import java.io.File;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 */

/**
 * @author Hector

 *
 */


/* here is the skeleton for the parser, you can use Jsoup to help you parse the html files,
 * feel free to add any member variables and methods you may beed.  You may also modify
 * the skeletons of the methods I added but they have to return the concurrent maps.
 */

public class Parser {
	int docId;
	File htmlFile;
	ConcurrentHashMap<String, Integer> wordFreq;


	@SuppressWarnings("rawtypes")
	public Parser(File file, int id) {
		docId = id;
		htmlFile = file;
			
	}
	
	// Returns a metadata of html file
	public ConcurrentHashMap<String, String>  getMetaData() {
		// TODO Auto-generated constructor stub
		/*
		if meta data exist add the meta data else return empty map.
		ConcurrentHashMap<String, String> metaData; = new ConcurrentHashMap<String, String>();;
		return metaData
		*/
	}
	
	// Returns authors of htlm file
	public ConcurrentHashMap<String, String> getAuthors() {
		// TODO Auto-generated constructor stub
		/*
		ConcurrentHashMap<String, String> zones = new ConcurrentHashMap<String, String>();
		if authors exist put them in map else return empty map
		authors.put("author", "Hector Solano");
		authors.put("author", "Mario  Sanches"); 
		return zones
		*/

	}
	
	// Returns Zones of html files.
	public ConcurrentHashMap<String, String> getZones() {
		// TODO Auto-generated constructor stub
		/*
		ConcurrentHashMap<String, String> zones = new ConcurrentHashMap<String, String>();
		if zones exist add them else return empty map (right now we are only gathering 
			zones.put("title", "Hello Word");
			zones.put("abstract", "None")
			zones.put("h1" "h1 data")
			zones.put("h2" "h2 data")
			zones.put("h3" "h3 data")
			zones.put("h(n)" "h(n) data )
			etc.
		return zones
		*/
	
	public wordFrequency(){
		// TODO Auto-generated constructor stub
	}
	
	

}
