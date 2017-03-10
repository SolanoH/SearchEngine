import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Ranker
{

    private List< String > query;
    private LinkedHashSet< docData > docsData;



    public Ranker( List< String > a, LinkedHashSet< docData > d )
    {
        query = a;
        docsData = d;
    }

    public List< String > rankURL()
    {
        HashMap< String, Integer > r = new HashMap<>();
        for( docData doc : docsData )
        {
            int score = 0;
            if( doc.hasAuthor( query ) )
                score += rankAuthors( doc );
            if( doc.hasDescription( query ) )
                score += rankDescription( doc );
            if( doc.hasKeyWords( query ) )
                score += rankKeywords( doc );
            if( doc.hasTitle( query ) )
                score += rankTitle( doc );
            if( doc.hasWordFreq( query ) )
                score += rankWordFreq( doc );
            r.put( doc.getUrl(), score );
        }

        Set<Map.Entry<String, Integer>> set = r.entrySet();
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o1.getValue()).compareTo( o2.getValue() );//Ascending order
                //return (o2.getValue()).compareTo( o1.getValue() );//Descending order
            }
        } );

        int counter = 0;
        List< String> pp = new LinkedList<>();
        for( Map.Entry< String, Integer > e : list )
        {
            if( counter == 5 )
                break;
            pp.add( e.getKey() );
            counter++;
        }

        return pp;
    }

    public int getScore( ConcurrentHashMap< String, Integer > map, int value  )
    {
        int score = 0;
        for( String author : query )
        {
            if( map.containsKey( author ) )
                score += value;
        }
        return score;
    }

    public int rankAuthors( docData doc )
    {
       return getScore( doc.getAuthors(), 20 );
    }

    public int rankKeywords( docData doc )
    {
        return getScore( doc.getKeywords(), 15 );
    }

    public int rankDescription( docData doc )
    {
        return getScore( doc.getDescription(), 12 );
    }

    public int rankTitle( docData doc )
    {
        return getScore( doc.getTitle(), 20 );
    }

    public int rankWordFreq( docData doc )
    {
        int score = 0;
        for( String word : query )
            if( doc.getWordFreq().containsKey( word ) )
                score += doc.getWordFreq().get( word );
        return score;
    }
}