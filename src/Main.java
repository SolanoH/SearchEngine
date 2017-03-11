import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
        BuildIndex buildIndex = new BuildIndex();
        SearchDocuments searchDocuments = new SearchDocuments( buildIndex.getIndex() );
        Scanner scanner = new Scanner( System.in );
        do
        {
            System.out.print( "\nSelect Option\n\t1. Query\n\t2. Exit\nEnter Option: " );
            String option = scanner.nextLine();
            while(  !option.equals( "1" ) && !option.equals( "2" ) )
            {
                System.out.print( "\nInvalid Option. Please Enter Option 1 or 2: " );
                option = scanner.nextLine();
            }
            if( option.equals( "2" ) )
                break;
            System.out.print( "\nEnter Query: " );
            System.out.println( searchDocuments.searchQuery( scanner.nextLine() ) );
        }while( true );
        scanner.close();
    }
}