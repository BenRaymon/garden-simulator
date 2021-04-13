
public class BackgroundDataLoader extends Thread {
	private Thread thread;
	private String threadName;
	
	public BackgroundDataLoader(String name) {
		this.threadName = name;
	}
	
	public void start() {
		System.out.println("Starting background load process");
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
	// The thread runs this function
	public void run() {
			
	}
}
