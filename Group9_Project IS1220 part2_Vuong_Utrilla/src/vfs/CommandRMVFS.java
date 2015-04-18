package vfs;

import java.io.IOException;


public class CommandRMVFS extends CommandBehaviour{
	private String vdName;

	public CommandRMVFS(VFS vfs,String vdName) {
		super(vfs);
		this.vdName = vdName;
		
	}

	@Override
	public void go() throws InvalidInput, IOException {
		vfs.checkPath(vdName, "/");
		vfs.deleteVirtualDisk(vdName);
		System.out.println(vdName + " is deleted");
		
	}

}
