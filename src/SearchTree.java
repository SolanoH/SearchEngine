import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.io.FileUtils;
/**
 * 
 */

/**
 * @author Hector
 *
 */
public class SearchTree {

	/**
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * 
	 */
	public static LinkedList<String> getFiles(String rootFolder) {
		LinkedList<String>  fileList = new LinkedList<String>();
		File file = new File(rootFolder);
		Collection<File> files = FileUtils.listFiles(file, null, true);
		for(File file2 : files){
			fileList.add(file2.getAbsolutePath());
		}
		return fileList;
		
	}

	
	

}
