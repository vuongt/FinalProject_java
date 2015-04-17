package vfs;

import java.io.IOException;

public class CommandIMPVFS extends CommandBehaviour{
	private String hostPath;
	private String vdName;
	private String vfsPath;
	
	public CommandIMPVFS(VFS vfs,String vdName, String vfsPath, String hostPath) {
		super(vfs);
		this.hostPath = hostPath;
		this.vdName = vdName;
		this.vfsPath = vfsPath;
	
	}

	@Override
	public void go() throws InvalidInput, IOException, DuplicatedNameException,FileNotFoundException, DirectoryNotFoundException, SizeException {
		if (vfs.checkPath(vdName, vfsPath))
			vfs.importFile(hostPath, vdName, vfsPath);// the path correspond to a file
		else vfs.importDirectory(hostPath, vdName, vfsPath);// the path correspond to a directory
		
	}

}
