package vfs;
/**
 * CommandMKDIR:
 * Commands the creation of a directory in a Virtual Disk of a VFS. 
 * 
 *
 */
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
	
		vfs.checkPath(vdName, vfsPath);
		vfs.createDirectory(vdName, dirName, vfsPath);
		System.out.println(dirName+" was created.");
		
	}

}
