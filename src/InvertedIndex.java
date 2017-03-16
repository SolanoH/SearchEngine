import java.io.*;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;



/**
 * InvertedIndex class implements an inverted index. The class does not handle exceptions,
 * that is all exception are thrown for the calling program to handle exceptions.
 * The format for the inverted index to be loaded from file must be the following format:
 * Term1, post1, post2, post3, ....., postn
 * Term2, post1, post2, post3, ....., postn
 * Inverted Index uses the class Pair as its value
 * The invertedIndexed is split into five sub inverted indexes for a smaller memory footprint
 * only one sub inverted index will be active in memory
 */
public class InvertedIndex {
    private ConcurrentHashMap<String,TermData> activeIndex;
    private ConcurrentHashMap<String, ConcurrentHashMap<String,TermData> > indexes;
    private String activeIndexKey = "";
    private String INT = "INT";
    private String AE = "AE";
    private String FJ = "FJ";
    private String KO = "KO";
    private String PT = "PT";
    private String UZ = "UZ";

    /***
     * Empty Constructor
     */
    public InvertedIndex() {
        indexes = new ConcurrentHashMap<>(6);
        indexes.put(INT, new ConcurrentHashMap<>());
        indexes.put(AE, new ConcurrentHashMap<>());
        indexes.put(FJ, new ConcurrentHashMap<>());
        indexes.put(KO, new ConcurrentHashMap<>());
        indexes.put(PT, new ConcurrentHashMap<>());
        indexes.put(UZ, new ConcurrentHashMap<>());
    }


    /***
     * Constructor with initial capacity;
     * @param initialCapacity
     */
    public InvertedIndex(int initialCapacity) {
        indexes = new ConcurrentHashMap<>(6);
        indexes.put(INT, new ConcurrentHashMap<>(initialCapacity));
        indexes.put(AE, new ConcurrentHashMap<>(initialCapacity));
        indexes.put(FJ, new ConcurrentHashMap<>(initialCapacity));
        indexes.put(KO, new ConcurrentHashMap<>(initialCapacity));
        indexes.put(PT, new ConcurrentHashMap<>(initialCapacity));
        indexes.put(UZ, new ConcurrentHashMap<>(initialCapacity));
    }

    /***
     * This Constructor loads an  active inverted index from file
     * @param indexKey is the inverted index key for the index to be loaded i.e indexAE, indexFJ ...
     * @throws FileNotFoundException
     * @throws IOException
     */
    public InvertedIndex(String indexKey) throws FileNotFoundException, IOException {
        indexes = new ConcurrentHashMap<>(6);
        indexes.put(INT, new ConcurrentHashMap<>());
        indexes.put(AE, new ConcurrentHashMap<>());
        indexes.put(FJ, new ConcurrentHashMap<>());
        indexes.put(KO, new ConcurrentHashMap<>());
        indexes.put(PT, new ConcurrentHashMap<>());
        indexes.put(UZ, new ConcurrentHashMap<>());
        loadIndexFromDisk(indexKey);
    }

    /***
     * add adds term to the inverted index, if term is not in the index
     * a new element is added to the index, if the term already exist, the document
     * frequency is incremented for that term.
     * @param docID is the document id that contains the String term
     * @param term
     */
    public void add(int docID, String term){
        String indexKey = mapKey(term);
        if( term.isEmpty() )
            return;
        if (indexes.get(indexKey).getOrDefault(term, null) == null){
            TermData element = new TermData(docID);
            indexes.get(indexKey).put(term,element);
        }
        else{
            indexes.get(indexKey).get(term).addPosting(docID);
            indexes.get(indexKey).get(term).incrementDocFrequency();
        }
    }

    /***
     * getDocufreq returns the number of documents that contain the String term
     * if term in the inverted index, returns null otherwise.
     * @param term
     * @return The number of documents that contain the @param term
     * @throws IOException
     * @throws FileNotFoundException
     */
    public int getDocFreq(String term) throws FileNotFoundException, IOException{
        String indexKey = mapKey(term);
        if ( !indexKey.equals( activeIndexKey ) )
        {
            activeIndexKey = indexKey;
            loadIndexFromDisk();
        }
        return activeIndex.getOrDefault(term, null).getFirst();
    }

    /***
     * getPostings returns a sorted LinkedHashset with all the documents
     * that contain the String term in increasing order. 0,1,2,..,n.
     * @param term
     * @return Returns an empty LinkedHashSet if the String term is not in the inverted index
     * @throws IOException
     * @throws FileNotFoundException
     */
    public LinkedHashSet <Integer> getPostings(String term) throws FileNotFoundException, IOException{
        String indexKey = mapKey(term);
        if( !indexKey.equals( activeIndexKey) )
        {
            activeIndexKey = indexKey;
            loadIndexFromDisk();
        }
        return activeIndex.getOrDefault(term, null) == null ? new LinkedHashSet <Integer>() : activeIndex.get(term).getSecond();
  
    }


    /***
     * writeIndextoDisk(String indexKey, String filename) writes index to disk.
     * @param filename is the name of the inverted index to be written to disk
     * @param indexKey is the key of the sub index to be written to disk
     */
    private void writeIndexToDisk(String indexKey, String filename) throws IOException{
        PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
        for (Entry<String, TermData> t : indexes.get(indexKey).entrySet()){
            writer.print(t.getKey());
            writer.print("," + indexes.get(indexKey).get(t.getKey()).getFirst());
            for (Integer integer : indexes.get(indexKey).get(t.getKey()).getSecond()) {
                writer.print("," + integer);
            }
            writer.println();
        }
        writer.close();
    }


    /***
     * writeIndexesToDisk() writes all the inverted disk to disk and frees memory.
     * @throws IOException
     */
    public void writeIndexesToDisk() throws IOException{
        String indexKey;
        for (Entry<String, ConcurrentHashMap<String, TermData>> t : indexes.entrySet()){
            indexKey = t.getKey();
            String filename = "index" + indexKey;
            writeIndexToDisk(indexKey,filename);
            indexes.get(indexKey).clear();
        }
        System.gc();
    }



    /***
     * loadIndexFromDisk(String indexKey) loads an inverted index from disk
     * @param indexKey
     * @throws IOException
     * @throws FileNotFoundException
     */
    private void loadIndexFromDisk(String indexKey) throws FileNotFoundException, IOException{
        activeIndex = new ConcurrentHashMap<>(100);
        activeIndexKey = indexKey;
        try (BufferedReader buffer = new BufferedReader(new FileReader("index" + activeIndexKey))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                activeIndex.putIfAbsent(data[0], new TermData());
                for (int i = 1; i < data.length; i++){
                    activeIndex.get(data[0]).addPosting(Integer.parseInt(data[i]));
                }
            }
        }
    }

    /***
     * loadIndexFromDisk()
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void loadIndexFromDisk() throws FileNotFoundException, IOException{
        String filename = "index" + activeIndexKey;
        activeIndex = new ConcurrentHashMap<>(100);
        try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                activeIndex.putIfAbsent(data[0], new TermData());
                for (int i = 1; i < data.length; i++){
                    activeIndex.get(data[0]).addPosting(Integer.parseInt(data[i]));
                }
            }
        }
    }


    /**
     * String mapKey(String term) maps term to a sub index
     * @param term
     * @return the key for the sub inverted index that may contain term
     */
    private String mapKey(String term){
        String indexKey;
        if (term.compareToIgnoreCase(AE) < 0)
            indexKey = INT;
        else if (term.compareToIgnoreCase(FJ) < 0)
            indexKey = AE;
        else if (term.compareToIgnoreCase(KO) < 0)
            indexKey = FJ;
        else if (term.compareToIgnoreCase(PT) < 0)
            indexKey = KO;
        else if (term.compareToIgnoreCase(UZ) < 0)
            indexKey = PT;
        else
            indexKey = UZ;
        return indexKey;
    }
}