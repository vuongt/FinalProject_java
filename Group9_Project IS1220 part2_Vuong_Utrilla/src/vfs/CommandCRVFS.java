package vfs;

public class CommandCRVFS extends CommandBehaviour {

	private String vdName;
	private long sizeMax;
	
	
	public CommandCRVFS(VFS vfs, String vdName, long sizeMax) {
		super(vfs);
		this.vdName = vdName;
		this.sizeMax = sizeMax;
	}
	
	public void go() throws DuplicatedNameException,InvalidInput{
		vfs.createVirtualDisk(vdName, sizeMax);
		System.out.println("Virtual disk created.");
	}
	
	
}
