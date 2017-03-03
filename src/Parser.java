import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/* here is the skeleton for the parser, you can use Jsoup to help you parse the html files,
 * feel free to add any member variables and methods you may need.  You may also modify
 * the skeletons of the methods I added but they have to return the concurrent maps.
 */


public class Parser
{
	int docId;
	Document htmlDocument;
	ConcurrentHashMap< String, Integer > wordFreq;
	//final private ConcurrentHashMap< String, List< String > > documentInformation = new ConcurrentHashMap<>();
	private ConcurrentHashMap< String, List< String > > documentInformation;


	@SuppressWarnings("rawtypes")
	public Parser( File document, int id )
	{
		docId = id;
		try {
			htmlDocument = Jsoup.parse( document, "UTF-8", "" );
		} catch (IOException e) {
			e.printStackTrace();
		}
		//wordFreq = new ConcurrentHashMap<>();
		//Table< String ,Integer,LinkedList<Integer>> twoDimensionalFileMap = HashBasedTable.create();


		//initializeConcurrentHashMap();
	}

	/**
	 * Returns a metadata of html file
	 * @return ConcurrentHashMap containing all metadata
     */
	public ConcurrentHashMap< String, List< String > > initializeConcurrentHashMap()
	{
//		synchronized ( documentInformation )
//		{
//			for( Element tag : htmlDocument.getElementsByTag( "meta" ) )
//			{
//				if( !documentInformation.containsKey( tag.attr("name") ) )
//					documentInformation.put( tag.attr( "name" ), Collections.synchronizedList( new ArrayList<>() ) );
//				documentInformation.get( tag.attr("name") ).add( tag.attr("content") );
//			}
//		}

		documentInformation = new ConcurrentHashMap<>();
		for( Element tag : htmlDocument.getElementsByTag( "meta" ) )
		{
			if( !documentInformation.containsKey( tag.attr("name") ) )
				documentInformation.put( tag.attr( "name" ), Collections.synchronizedList( new ArrayList<>() ) );
			documentInformation.get( tag.attr("name") ).add( tag.attr("content") );
		}

		return documentInformation;
	}

	/**
	 * Returns authors of htlm file
	 */
	public List< String > getAuthors()
	{
//		ConcurrentHashMap< String, String > authors = new ConcurrentHashMap<>();
//		for( Element tag : htmlDocument.getElementsByTag( "meta" ) )
//				if( tag.attr( "name" ).equals( "author" ) )
//					authors.put( tag.attr( "content" ), tag.attr( "content" ) );
		return documentInformation.get( "authors" );
	}
	
	// Returns Zones of html files.
	public ConcurrentHashMap<String, List< String > > getZones()
	{

		ConcurrentHashMap< String, List< String > > zones = new ConcurrentHashMap<>();
//		zones.put( "title", htmlDocument.title() );
//
//		for( int value = 1; value < 7; value++ )
//			for( Element tag : htmlDocument.select( "h" + value ) )
//				zones.put( "h"+value, tag.ownText() );
//
//		for( Map.Entry< String, String > v : zones.entrySet() )
//		 	System.out.println( v.toString() );


		return zones;



		 /*
		 ConcurrentHashMap<String, String> zones = new ConcurrentHashMap<String, String>();
		 if zones exist add them else return empty map (right now we are only gathering
		 	zones.put("title", "Hello Word");
		 	zones.put("abstract", "None")
		 	zones.put("h1" "h1 data")
		 	zones.put("h2" "h2 data")
		 	zones.put("h3" "h3 data")


		 	h3 -> sunny


		 	zones.put("h(n)" "h(n) data )
		 	etc.
		 return zones
		 */
	}

	public static ConcurrentHashMap< String, Integer > wordFrequency(File html)
	{
		Document htmlDocument = null;
		try {
			htmlDocument = Jsoup.parse( html, "UTF-8", "" );
		} catch ( Exception e) {
			//e.printStackTrace();

			return null;
		}
		ConcurrentHashMap< String, Integer > wordFreq = new ConcurrentHashMap< String, Integer >();
		for( String line : htmlDocument.html().split( "\n" ) )
			for( String word : Jsoup.parse( line ).text().replaceAll( "[^a-zA-Z0-9]+", " " ).split( "\\s+" ) )
				if( !word.isEmpty() )
					wordFreq.put( word, ( wordFreq.containsKey( word ) ? wordFreq.get( word ) + 1 : 1 ) );
		return wordFreq;
	}

}
