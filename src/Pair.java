public class Pair< K, V >
{
	protected K first;
	protected V second;

	public Pair()
	{
		this( null, null );
	}

	public Pair( K f )
	{
		this( f, null );
	}

	public Pair( K f, V s )
	{
		first = f;
		second = s;
	}

	public K getFirst() {
		return first;
	}

	public void setFirst( K first )
	{
		this.first = first;
	}

	public V getSecond()
	{
		return second;
	}

	public void setSecond( V second )
	{
		this.second = second;
	}
}