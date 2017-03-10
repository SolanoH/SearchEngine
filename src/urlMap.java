import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 */

/**
 * @author Hector
 *
 */
public class urlMap {
	/**
	 * 
	 */
	public urlMap(File filename) {
	}
	
	public static ConcurrentHashMap<String, String> buildUrlMap(String filename){
		{
			ConcurrentHashMap<String, String> urls = new ConcurrentHashMap<String, String>();
			try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
			    String line;
			    while ((line = buffer.readLine()) != null) {
			       String[] data = line.split(",");
			       
			       urls.put(data[0].trim(), data[1].trim());
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

				
			return  urls;
		}
	}

}
