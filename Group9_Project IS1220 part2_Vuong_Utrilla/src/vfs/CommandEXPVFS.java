package vfs;

import java.io.IOException;
/**
 * CommandEXPVFS:
 * Commands the exportation of a determined VirtualDisk of a VFS.
 *
 */

public class CommandEXPVFS extends CommandBehaviour {
	
	public String vdName;
	public String hostPath;

	public CommandEXPVFS(VFS vfs,String vdName,String hostPath) {
		super(vfs);
		this.vdName = vdName;
		this.hostPath= hostPath;
	}

	@Override
	public void go() throws InvalidInput, IOException, DuplicatedNameException, InvalidNameException{
		vfs.checkPath(vdName,"/");
		vfs.exportVfs(vdName, hostPath);
		System.out.println("The virtual disk named " + vdName + " was exported to " + hostPath);

	}

}
