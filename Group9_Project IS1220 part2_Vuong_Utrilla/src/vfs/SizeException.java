package vfs;
/**
 * SizeException:
 * Signals the a file or directory to add to a virtual disk occupies a bigger space than the free space of the virtual disk. 
 */
public class SizeException extends Exception{

	
	private static final long serialVersionUID = 1L;

	public SizeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SizeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public SizeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SizeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SizeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	

}
