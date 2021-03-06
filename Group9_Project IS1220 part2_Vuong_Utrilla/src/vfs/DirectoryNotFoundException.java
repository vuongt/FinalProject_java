package vfs;
import java.io.IOException;
/**
 * DirectoryNotFoundException:
 * Signals that a directory being imported doesn't exist in the host file system.
 *
 */
public class DirectoryNotFoundException extends IOException {

	
	private static final long serialVersionUID = 1L;

	public DirectoryNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DirectoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DirectoryNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DirectoryNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	

}
