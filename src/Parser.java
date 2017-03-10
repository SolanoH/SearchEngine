import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
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
	String url;
	int docId;
	ConcurrentHashMap< String, Integer > wordFreq;
	ConcurrentHashMap< String, List< String > > documentInformation;
	Document htmlDocument;

	@SuppressWarnings("rawtypes")
	public Parser( File document, int id, String u )
	{
		url = u;
		docId = id;
		wordFreq = new ConcurrentHashMap< String, Integer >() ;
		documentInformation = new ConcurrentHashMap< String, List< String > >();

		try
		{
			htmlDocument = Jsoup.parse( document, "UTF-8", "" );
			initializeConcurrentHashMap();
		}
		catch( Exception e )
		{
			htmlDocument = null;
		}
	}


	public ConcurrentHashMap< String, Integer > getWordFreq()
	{
		return wordFreq;
	}
	/**
	 * Returns a metadata of html file
	 * @return ConcurrentHashMap containing all metadata
     */
	public ConcurrentHashMap< String, List< String > > initializeConcurrentHashMap()
	{
		documentInformation = new ConcurrentHashMap<>();
		for( Element tag : htmlDocument.getElementsByTag( "meta" ) )
		{
			if( !documentInformation.containsKey( tag.attr("name") ) )
				documentInformation.put( tag.attr( "name" ), Collections.synchronizedList( new ArrayList<>() ) );
			documentInformation.get( tag.attr("name") ).add( tag.attr("content") );
		}

		return documentInformation;
	}

	public ConcurrentHashMap< String, Integer > getAuthors()
	{
		//return documentInformation.get( "authors" );
		return g( "authors" );

	}

	public ConcurrentHashMap< String, Integer > getKeywords()
	{
		return g( "keywords" );
	}

	public ConcurrentHashMap< String, Integer > g( String searching )
	{
		ConcurrentHashMap< String, Integer > s = new ConcurrentHashMap<>();
		List< String > d = documentInformation.get( searching );

		if( d != null )
			for( String line : d )
				for( String word : line.split( "\\s+" ) )
					s.put( word, 1 );
		return s;
	}

	public ConcurrentHashMap< String, Integer > getDescription()
	{
		return g( "description" );
	}

	public ConcurrentHashMap< String, Integer > getAbstract()
	{
		return g( "abstract" );
	}

	public String getTitle()
	{
		return htmlDocument.title();
	}
	public ConcurrentHashMap< String, Integer > getTitle2()
	{
		ConcurrentHashMap< String, Integer > s = new ConcurrentHashMap<>();
		String d = getTitle();

		if( d != null )
			for( String l : d.split( "\\s+") )
					s.put( l, 1 );
		return s;

	}

	public ConcurrentHashMap< String, List< String > > getMetaData()
	{
		return documentInformation;
	}

	public String getUrl()
	{
		return url;
	}

	public ConcurrentHashMap< String, List< String > > getHeaders()
	{
		ConcurrentHashMap< String, List< String > > headers = new ConcurrentHashMap<>();
		for( int value = 1; value < 7; value++ )
		{
			List< String > list = new LinkedList<>();
			for (Element tag : htmlDocument.select( "h" + value ) )
				list.add( tag.ownText() );
			headers.put("h" + value, list );
		}

		return headers;
	}
	
	// Returns Zones of html files.
	public ConcurrentHashMap< String, List< String > > getZones()
	{
		ConcurrentHashMap< String, List< String > > zones = getHeaders();
		List< String > title = new LinkedList<>();
		Collections.addAll( title, getTitle().split( "\\s+" ) );
		zones.put( "title", title );
		return zones;
	}

	public int getDocId()
	{
		return docId;
	}

	public ConcurrentHashMap< String, Integer > wordFrequency()
	{

		if( htmlDocument != null  )
		for( String line : htmlDocument.html().split( "\n" ) )
		{
			//System.out.println( Jsoup.parse( line ).text() );
			for( String word : Jsoup.parse( line ).text().replaceAll( "[^a-zA-Z]+", " " ).split( "\\s+" ) )
			{
				word = word.toLowerCase();
				if( !word.isEmpty() && word.length() > 1 )
				{
					wordFreq.put( word, ( wordFreq.containsKey(word) ? wordFreq.get( word ) + 1 : 1 ) );
				}
			}
		}

		//System.out.println( wordFreq.toString() );
		return wordFreq;
	}

}
