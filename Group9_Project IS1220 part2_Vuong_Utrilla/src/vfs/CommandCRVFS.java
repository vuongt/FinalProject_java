package vfs;


public class CommandCRVFS extends CommandBehaviour {
	private String vdName;
	private long sizeMax;

	public CommandCRVFS(VFS vfs,String vdName,long sizeMax) {
		super(vfs);
		this.sizeMax = sizeMax;
		this.vdName = vdName;
		
	}

	@Override
	public void go() throws InvalidInput, DuplicatedNameException {
		vfs.createVirtualDisk(vdName, sizeMax);
		System.out.println("A virtual disk named " + vdName + " is created");
		
	}

	
}
