package vfs;

public class CommandTOUCH extends CommandBehaviour {

	
	private String vdName;
	private String fileName;
	private String vfsPath;
	
	public CommandTOUCH(VFS vfs, String vdName, String fileName, String vfsPath) {
		super(vfs);
		this.vdName = vdName;
		this.fileName = fileName;
		this.vfsPath = vfsPath;
	}
	
	public void go() throws InvalidInput,DuplicatedNameException{
		
		vfs.checkPath(vdName, vfsPath);
		vfs.createFile(vdName, fileName, vfsPath);
		System.out.println(vdName+"was created.");
	}
	
	
}
