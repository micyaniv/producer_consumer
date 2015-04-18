import java.io.File;
import java.io.FileFilter;

public class Scouter implements Runnable {
	private SynchronizedQueue<java.io.File> directoryQ;
	private java.io.File root;
	private FileFilter filter;
	
	@Override
	public void run() {
		directoryQ.registerProducer();
//		System.out.println("Scouter thread " + Thread.currentThread().getId() + " Started.");
		pushInQueue(directoryQ, root);
		directoryQ.unregisterProducer();
		
//		System.out.println("Scouter thread " + Thread.currentThread().getId() + " finished.");
	}
	
	public void pushInQueue(SynchronizedQueue <File> queue, java.io.File dir){
		queue.enqueue(dir);
		File[] directories = dir.listFiles(filter);
		for (int i = 0; i < directories.length; i++){
			pushInQueue(queue, directories[i]);
		}
	}

	public Scouter(SynchronizedQueue<java.io.File> directoryQueue,
			java.io.File root) {
		directoryQ = directoryQueue;
		this.root = root;
		
		
		filter = new FileFilter() {
			@Override
			public boolean accept(File someFile) {
				return someFile.isDirectory();
			}
		};

	}
}
