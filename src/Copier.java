import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Copier implements Runnable {
	private SynchronizedQueue<java.io.File> resultQ;
	File destination;
	FileWriter output;

	@Override
	public void run() {
//		System.out.println("Copier thread " + Thread.currentThread().getId()	+ " started.");
		
		File poppedItem = null;
		Boolean poll = true;
		while (poll) {
			poppedItem = resultQ.dequeue();

			if (poppedItem == null) {
				poll = false;
			} else {
				//System.out.println("Dequeued file: " + poppedItem.getName());
				//System.out.println("Dequeued file: "+ poppedItem.getAbsolutePath());

				try {
					// Currently appends to an existing file the new results.
					output = new FileWriter(this.destination, true);
					output.write(poppedItem.getAbsolutePath() + "\n");
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
//		System.out.println("Copier thread " + Thread.currentThread().getId()	+ " finished.");
	}

	public Copier(java.io.File destination,
			SynchronizedQueue<java.io.File> resultsQueue) {
		resultQ = resultsQueue;
		this.destination = destination;
	}

}
