import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.text.Document;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class readerTester {


	public static void main(String[] args) throws IOException{
		File htmlDocument = new File("/Users/Hector/Downloads/WEBPAGES_RAW/0/2");
		org.jsoup.nodes.Document doc = Jsoup.parse(htmlDocument, "UTF-8");
		System.out.println(doc);
		Element body = doc.body();
		//System.out.println(body);
		Elements title = doc.getElementsByTag("title");
		System.out.println(title);
	}

}
