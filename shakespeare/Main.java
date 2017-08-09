package shakespeare;

import java.io.IOException;

/**
 * 
 * @author oleschmitt
 *
 *	class Main to start the application
 */

public class Main {
	
	public static void main(String[] args) throws IOException {
		MyReaderWriter myReaderWriter = new MyReaderWriter();
		myReaderWriter.run();
		System.exit(0);
	}
}
