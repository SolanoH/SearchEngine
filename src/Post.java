
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;


public class Post
{
	public ConcurrentHashMap< Integer, DocData > posts;

	public Post()
	{
		posts = new ConcurrentHashMap<>();
	}

	public void addPost( DocData docData )
	{
		posts.put( docData.getDocumentID(), docData );
	}
	
	
	

	// Takes a list of document IDs and returns a list of docData
	public LinkedHashSet< DocData > getPostData( LinkedHashSet< Integer > postList )
	{
		LinkedHashSet< DocData > postings = new LinkedHashSet<>();
		for( Integer aPostList : postList )
			postings.add( posts.get( aPostList ) );
		return postings;
	}
}