import java.io.File;
import java.io.FileFilter;

public class Searcher implements Runnable {
	private SynchronizedQueue<java.io.File> directoryQ;
	private SynchronizedQueue<java.io.File> resultQ;
	private Boolean poll;
	private FileFilter filter;
	private String extension;
	
	@Override
	public void run() {
//		System.out.println("Searcher thread " + Thread.currentThread().getId() + " started.");
		
		resultQ.registerProducer();
		File poppedItem;
		while (poll){
			poppedItem = directoryQ.dequeue();
			if (poppedItem == null){
				poll = false;
			} else {
				pushInQueue(resultQ, poppedItem);
			}
		}
		resultQ.unregisterProducer();
		
//		System.out.println("Searcher thread " + Thread.currentThread().getId() + " finished.");
	}
	
	public void pushInQueue(SynchronizedQueue <File> queue, java.io.File dir){
		File[] files = dir.listFiles(filter);
		for (int i = 0; i < files.length ; i++){
			if (files[i].getName().endsWith(extension)){
				//System.out.println(files[i]);
				queue.enqueue(files[i]);
			}
		}
	}

	public Searcher(java.lang.String extension,
			SynchronizedQueue<java.io.File> directoryQueue,
			SynchronizedQueue<java.io.File> resultsQueue) {
		directoryQ = directoryQueue;
		resultQ = resultsQueue;
		poll = true;
		this.extension = extension;
		
		filter = new FileFilter() {
			@Override
			public boolean accept(File someFile) {
				return someFile.isFile();
			}
		};

	}
}
