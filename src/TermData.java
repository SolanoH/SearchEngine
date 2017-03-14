import java.util.LinkedHashSet;

public class TermData extends Pair< Integer, LinkedHashSet< Integer > >
{
    public TermData()
    {
        super( 0 );
        second = new LinkedHashSet<>();
    }

    public TermData( Integer posting )
    {
        super( 1 );
        second = new LinkedHashSet<>();
        second.add( posting );
    }

    public void addPosting( Integer posting )
    {
        second.add( posting );
        first++;
    }

    public void incrementDocFrequency()
    {
        first++;
    }
}
