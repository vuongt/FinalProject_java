package vfs;

import java.io.IOException;

public class CommandEXPVFS extends CommandBehaviour {
	
	public String vdName;
	public String hostPath;

	public CommandEXPVFS(VFS vfs,String vdName,String hostPath) {
		super(vfs);
		this.vdName = vdName;
		this.hostPath= hostPath;
	}

	@Override
	public void go() throws InvalidInput, IOException, DuplicatedNameException{
		vfs.checkPath(vdName,"/");
		vfs.exportVfs(vdName, hostPath);
		System.out.println("The virtual disk named" + vdName + "is exported to" + hostPath);

	}

}
