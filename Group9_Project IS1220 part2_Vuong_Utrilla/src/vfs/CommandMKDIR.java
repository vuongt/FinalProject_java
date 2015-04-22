package vfs;

public class CommandMKDIR extends CommandBehaviour {
	
	private String vdName;
	private String dirName;
	private String vfsPath;
	public CommandMKDIR(VFS vfs, String vdName, String dirName, String vfsPath) {
		super(vfs);
		this.vdName = vdName;
		this.dirName = dirName;
		this.vfsPath = vfsPath;
	}
	
	public void go() throws InvalidInput,DuplicatedNameException, InvalidNameException{
		vfs.checkName(dirName);
		vfs.checkPath(vdName, vfsPath);
		vfs.createDirectory(vdName, dirName, vfsPath);
		System.out.println(dirName+" was created.");
		
	}

}
