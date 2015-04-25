package vfs;
/**
 * CommandCD:
 * Commands a change of the attribute 'currentPosition' of a determined Virtual Disk on a VFS
 *
 */
public class CommandCD extends CommandBehaviour{

	
	private String vdName;
	private String vfsPath;
	
	public CommandCD(VFS vfs,String vdName,String vfsPath){
		super(vfs);
		this.vdName=vdName;
		this.vfsPath=vfsPath;
	}
	
	public void go() throws InvalidInput, InvalidNameException{
		if(vfs.checkPath(vdName, vfsPath)){throw new InvalidInput("Current position has to point to the location of a directory.");}
		vfs.changePosition(vdName, vfsPath);
		System.out.println("Updated current position");
		
	}
}
