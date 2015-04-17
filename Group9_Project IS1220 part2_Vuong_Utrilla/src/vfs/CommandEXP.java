package vfs;

import java.io.IOException;

public class CommandEXP extends CommandBehaviour{
	private String hostPath;
	private String vdName;
	private String vfsPath;
	
	
	public CommandEXP(VFS vfs,String hostPath, String vdName, String vfsPath) {
		super(vfs);
		this.hostPath = hostPath;
		this.vdName = vdName;
		this.vfsPath = vfsPath;
	}

	@Override
	public void go() throws InvalidInput, IOException, DuplicatedNameException, FileNotFoundException {
		if (vfs.checkPath(vdName, vfsPath))
			vfs.exportFile(hostPath, vdName, vfsPath);// the path correspond to a file
		else vfs.exportDirectory(hostPath, vdName, vfsPath);// the path correspond to a directory
	}

}
