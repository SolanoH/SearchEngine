import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;

public class SearchDocuments
{
	InvertedIndex invertedIndex;

	public SearchDocuments( InvertedIndex index )
	{
		invertedIndex = index;
	}

	public LinkedHashSet< Integer > searchQuery( String query ) throws FileNotFoundException, IOException
	{
		LinkedHashSet< Integer > postings = new LinkedHashSet<>();
		for( String word : query.toLowerCase().split( "\\s+" ) )
			if( postings.isEmpty() )
				postings.addAll( invertedIndex.getPostings( word ) );
			else
				postings.retainAll( invertedIndex.getPostings( word ) );
		return postings;
	}







//	System.out.println(postings.getSearchResults());
//	Ranker ranker = new Ranker(d, post.getPostData( postings.getSearchResults() ) );
//	System.out.println(ranker.rankURL());

}