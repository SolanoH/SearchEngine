import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

public class BuildIndex
{
	private int documentID;
	private int numberOfOutputFiles;

	private Post post;
	private InvertedIndex index;

	public BuildIndex()
	{
		documentID = 0;
		numberOfOutputFiles = 0;
		post = new Post();
		index = new InvertedIndex();

		//ExecutorService executorService = Executors.newFixedThreadPool( 4 );

		loadFiles();

		//writeOutputFile( false );
	}

	public Post getPost(){ return post; }

	public InvertedIndex getIndex(){ return index; }

	private void loadFiles()
	{
		try( Stream< String > lines = Files.lines( Paths.get( System.getProperty( "user.dir" ) + "/WEBPAGES_RAW/bookkeeping.tsv"),
				Charset.defaultCharset() ) ) { lines.forEachOrdered( this::processFile ); }
		catch( Exception e ) { e.printStackTrace(); }
	}

	private void processFile( String line )
	{
		String[] data = line.split( "\t" );
		Parser parser = new Parser( new File( "WEBPAGES_RAW/" + data[ 0 ] ) );
		if( !parser.badURL() )
		{
			for( Map.Entry< String, Integer > entry : parser.getWordFreq().entrySet() )
				index.add( documentID, entry.getKey() );
			post.addPost( new DocData( parser, documentID, data[ 1 ] ) );
			documentID++;
			if( lowMemory() ) writeOutputFile();
		}
	}





	//Not Working
	private void writeOutputFile() 
	{
		try {
			index.writeIndexesToDisk();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean lowMemory()
	{
		return Runtime.getRuntime().freeMemory() <= ( long ) ( Runtime.getRuntime().totalMemory() * 0.25 );
	}
}