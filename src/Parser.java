import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class Parser
{
	Document htmlDocument;
	ConcurrentHashMap< String, Integer > wordFreq;
	ConcurrentHashMap< String, List< String > > documentMetaInformation;

	public Parser( File document )
	{
		try
		{
			htmlDocument = Jsoup.parse( document, "UTF-8", "" );
			wordFrequency();
			documentMetaInformation();
		}
		catch( Exception e ) { e.getMessage(); }
	}

	public boolean badURL() { return wordFreq == null; }

	public String getTitle()
	{
		return htmlDocument.title();
	}

	public ConcurrentHashMap< String, Integer > getWordFreq()
	{
		return wordFreq;
	}

	public ConcurrentHashMap< String, Integer > getAuthors()
	{
		return getData( "authors" );
	}

	public ConcurrentHashMap< String, Integer > getKeywords()
	{
		return getData( "keywords" );
	}

	public ConcurrentHashMap< String, Integer > getAbstract()
	{
		return getData( "abstract" );
	}

	public ConcurrentHashMap< String, Integer > getDescription()
	{
		return getData( "description" );
	}

	public ConcurrentHashMap< String, List< String > > getHeaders()
	{
		ConcurrentHashMap< String, List< String > > headers = new ConcurrentHashMap<>();
		for( int value = 1; value < 7; value++ )
		{
			List< String > list = new LinkedList<>();
			for( Element tag : htmlDocument.select( "h" + value ) )
				list.add( tag.ownText() );
			headers.put( "h" + value, list );
		}

		return headers;
	}

	private void wordFrequency()
	{
		wordFreq = new ConcurrentHashMap<>();
		for( String line : htmlDocument.html().split( "\n" ) )
			for( String word : Jsoup.parse( line.toLowerCase() ).text().replaceAll( "[^a-zA-Z]+", " " ).split( "\\s+" ) )
				if( !word.isEmpty() && word.length() > 1 )
					wordFreq.put( word, ( wordFreq.containsKey(word) ? wordFreq.get( word ) + 1 : 1 ) );
	}

	private void documentMetaInformation()
	{
		documentMetaInformation = new ConcurrentHashMap<>();
		for( Element tag : htmlDocument.getElementsByTag( "meta" ) )
		{
			if( !documentMetaInformation.containsKey( tag.attr( "name" ) ) )
				documentMetaInformation.put( tag.attr( "name" ), Collections.synchronizedList( new ArrayList<>() ) );
			documentMetaInformation.get( tag.attr( "name" ) ).add( tag.attr( "content" ) );
		}
	}

	private ConcurrentHashMap< String, Integer > getData( String searching )
	{
		List< String > temp = documentMetaInformation.get( searching );
		ConcurrentHashMap< String, Integer > data = new ConcurrentHashMap<>();
		if( temp != null )
			for( String line : temp )
				for( String word : line.split( "\\s+" ) )
					data.put( word, 1 );
		return data;
	}
}