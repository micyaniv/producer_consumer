import java.io.File;

public class DiskSearcher {
	
	/**
	 * @param args
	 */
	static int DIRECTORY_QUEUE_CAPACITY = 512;
	static int RESULTS_QUEUE_CAPACITY = 512;
	static int MAX_THREADS = 10;
	static String WANTED_SUFFIX = "pdf";
	static String USER_HOME = System.getProperty("user.home");
	
	public static void main(String[] args) {

		// Create two queues.
		// One for the directories the scouter finds.
		// Another for the files with the extension we are searching for.
		SynchronizedQueue <File> directoryQ = new SynchronizedQueue<File>(DIRECTORY_QUEUE_CAPACITY);
		SynchronizedQueue <File> resultQ = new SynchronizedQueue<File>(RESULTS_QUEUE_CAPACITY);
		int i;
		String rootDirectory = USER_HOME;
		String outputPath = USER_HOME + "/workspace/output.txt";
		
		Scouter scouterRunnable = new Scouter(directoryQ, new File(rootDirectory));
		Searcher searcherRunnable = new Searcher(WANTED_SUFFIX, directoryQ, resultQ);
		Copier copierRunnable = new Copier(new File(outputPath), resultQ);
		
		Thread scouterThread = new Thread(scouterRunnable);
		Thread [] searcherThreads = new Thread[MAX_THREADS];
		Thread [] copierThreads = new Thread[MAX_THREADS];
		
		for (i = 0; i< MAX_THREADS; i++){
			searcherThreads[i]= new Thread(searcherRunnable);
			copierThreads[i] = new Thread(copierRunnable);
		}
		
		scouterThread.start();
		for(i = 0; i < MAX_THREADS; i++){
			searcherThreads[i].start();
			copierThreads[i].start();
		}
	}
}
