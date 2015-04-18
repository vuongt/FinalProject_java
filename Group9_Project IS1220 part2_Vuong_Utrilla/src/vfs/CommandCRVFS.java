package vfs;

public class CommandCRVFS extends CommandBehaviour {

	private String vdName;
	private int size;
	
	
	public CommandCRVFS(VFS vfs, String vdName, int size) {
		super(vfs);
		this.vdName = vdName;
		this.size = size;
	}
	
	public void go() throws DuplicatedNameException,InvalidInput{
		vfs.createVirtualDisk(vdName, size);
		System.out.println("Virtual disk created.");
	}
	
	
}
