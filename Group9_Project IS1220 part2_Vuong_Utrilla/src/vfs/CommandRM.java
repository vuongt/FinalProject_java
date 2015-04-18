package vfs;



public class CommandRM extends CommandBehaviour{
	
	public String vdName;
	public String vfsPath;
	
	public CommandRM(VFS vfs,String vdName, String vfsPath) {
		super(vfs);
		this.vdName = vdName;
		this.vfsPath = vfsPath;
	}

	@Override
	public void go() throws InvalidInput {
		if (vfs.checkPath(vdName, vfsPath)){
			vfs.deleteFile(vdName, vfsPath);// the path correspond to a file
			System.out.println("The file is deleted");
		}
		else {
			vfs.deleteDirectory(vdName, vfsPath);// the path correspond to a directory
			System.out.println("The directory is deleted");
		}
		
	}

}
