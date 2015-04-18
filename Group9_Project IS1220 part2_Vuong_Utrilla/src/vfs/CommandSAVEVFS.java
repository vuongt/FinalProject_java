package vfs;

import java.io.IOException;

public class CommandSAVEVFS extends CommandBehaviour{
	private String vdName;

	public CommandSAVEVFS(VFS vfs, String vdName) {
		super(vfs);
		this.vdName = vdName;
	}

	@Override
	public void go() throws InvalidInput, IOException{
		vfs.checkPath(vdName, "/");
		vfs.save(vdName);
		System.out.println(vdName + " is saved");
	}

	
}
