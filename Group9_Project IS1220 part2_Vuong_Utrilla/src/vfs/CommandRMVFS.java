
package vfs;

import java.io.IOException;

/**
 * CommandRMVFS:
 * Commands the deletion of a VirtualDisk from a VFS. 
 *
 */
public class CommandRMVFS extends CommandBehaviour{

	private String vdName;

	public CommandRMVFS(VFS vfs, String vdName) {
		super(vfs);
		this.vdName = vdName;
	}
	
	public void go() throws InvalidInput, IOException, InvalidNameException{
		vfs.checkPath(vdName,"/");
		vfs.deleteVirtualDisk(vdName);
		System.out.println(vdName + " was deleted");
		
	}
}

