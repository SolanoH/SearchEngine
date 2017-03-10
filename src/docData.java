/**
 * 
 */

/**
 * @author Hector
 *
 */

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class docData
{

	int docId;
	String url;
	ConcurrentHashMap<String, Integer> description;
	ConcurrentHashMap<String, Integer> keywords;
	ConcurrentHashMap<String, Integer> authors;
	ConcurrentHashMap<String, Integer> title;
	ConcurrentHashMap<String, Integer> wordFreq;

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ConcurrentHashMap<String, Integer> getDescription() {
		return description;
	}

	public void setDescription(ConcurrentHashMap<String, Integer> description) {
		this.description = description;
	}

	public ConcurrentHashMap<String, Integer> getKeywords() {
		return keywords;
	}

	public void setKeywords(ConcurrentHashMap<String, Integer> keywords) {
		this.keywords = keywords;
	}

	public ConcurrentHashMap<String, Integer> getAuthors() {
		return authors;
	}

	public void setAuthors(ConcurrentHashMap<String, Integer> authors) {
		this.authors = authors;
	}

	public ConcurrentHashMap<String, Integer> getTitle() {
		return title;
	}

	public void setTitle(ConcurrentHashMap<String, Integer> title) {
		this.title = title;
	}

	public ConcurrentHashMap<String, Integer> getWordFreq() {
		return wordFreq;
	}

	public void setWordFreq(ConcurrentHashMap<String, Integer> wordFreq) {
		this.wordFreq = wordFreq;
	}

	/**
	 * 
	 */
	public docData( Parser parser )
	{
		docId = parser.getDocId();
		//System.out.println("Passed docID");
		url = parser.getUrl();
		//System.out.println("Passed getUrl");
		description = parser.getDescription();
		//System.out.println("Passed GetDescription");
		keywords = parser.getKeywords();
		//System.out.println("Passed GetKeywords ");
		authors = parser.getAuthors();
		//System.out.println("Passed getAuthors");
		//title = parser.getTitle2();
		//System.out.println("Passed getTitle2");
		ConcurrentHashMap< String, Integer > d = parser.wordFrequency();
		if( d != null ){
			wordFreq = d;
		}
		//System.out.println("Passed getWordFreq");
	}

	public boolean test( List< String > query, ConcurrentHashMap<String, Integer> map )
	{
		if (map != null)
		for( String name : query )
			if( map.containsKey( name ) )
				return true;
		return false;
	}

	public boolean hasTitle( List< String > query )
	{
		return test( query, title );
	}

	public boolean hasAuthor( List< String > query )
	{
		return test( query, authors );
	}

	public boolean hasKeyWords( List< String > query )
	{
		return test( query, keywords );
	}

	public boolean hasDescription( List< String > query )
	{
		return test( query, description );
	}

	public boolean hasWordFreq( List< String > query )
	{
		return test( query, wordFreq );
	}






		
//	// Returns true if the parameter name is one of the documents authors.
//	public boolean termIdisAuthor(String  author){
//		return authors.getOrDefault(author, null) == author;
//	}
//
//	// needs to be implemented
//	public String termIdisTitle(){
//		return zones.getOrDefault("title", null);
//	}
//
//
//	// needs to be implemented
//	public boolean termIdinAbstract(String termId){
//		return zones.get("abstract") == termId;
//	}
//
//	// returns date of document creation
//	public String dateOfCreation(String date){
//		return metaData.getOrDefault("dateOfCreation", null);
//	}
//
//	// return document type
//	public String docType(){
//		return metaData.getOrDefault("docType", null);
//	}
	

}
