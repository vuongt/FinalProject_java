package vfs;

import java.io.IOException;

public class CommandIMPVFS extends CommandBehaviour{
	private String hostPath;
	private String vdName;
	private String vfsPath;
	
	public CommandIMPVFS(VFS vfs,String hostPath, String vdName, String vfsPath) {
		super(vfs);
		this.hostPath = hostPath;
		this.vdName = vdName;
		this.vfsPath = vfsPath;
	
	}

	@Override
	public void go() throws InvalidInput, ElementNotFoundException, IOException, SizeException, DuplicatedNameException {
		if (vfs.checkPath(vdName, vfsPath)){
			vfs.importFile(hostPath, vdName, vfsPath);// the path correspond to a file
			System.out.println("A file is imported to the virtual disk");
		}
		else {
			vfs.importDirectory(hostPath, vdName, vfsPath);// the path correspond to a directory
			System.out.println("A directory is imported to the virtual disk");

		}
		
	}

}
