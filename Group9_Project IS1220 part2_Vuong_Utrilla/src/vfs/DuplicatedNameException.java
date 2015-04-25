package vfs;
/**
 * DuplicatedNameException:
 * Signals a file or directory name is repeated in the location of the vfs where it's being created or in the location
 * of the host file system where it's being exported
 *
 */
public class DuplicatedNameException extends Exception {

	
	private static final long serialVersionUID = 1L;

	public DuplicatedNameException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DuplicatedNameException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DuplicatedNameException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DuplicatedNameException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DuplicatedNameException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
