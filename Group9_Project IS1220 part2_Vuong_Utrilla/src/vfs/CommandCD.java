package vfs;

public class CommandCD extends CommandBehaviour{

	
	private String vdName;
	private String vfsPath;
	
	public CommandCD(VFS vfs,String vdName,String vfsPath){
		super(vfs);
		this.vdName=vdName;
		this.vfsPath=vfsPath;
	}
	
	public void go() throws InvalidInput{
		vfs.checkPath(vdName, vfsPath);
		vfs.changePosition(vdName, vfsPath);
		System.out.println("Updated current position");
		
	}
}
