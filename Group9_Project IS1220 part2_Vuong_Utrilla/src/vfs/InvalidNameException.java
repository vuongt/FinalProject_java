package vfs;
/**
 * 
 * InvalidNameException:
 * Signals a name of an element uses an invalid character (|/\?*: "<>)
 *
 */
public class InvalidNameException extends Exception{

	private static final long serialVersionUID = 500L;

	public InvalidNameException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidNameException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public InvalidNameException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public InvalidNameException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public InvalidNameException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
