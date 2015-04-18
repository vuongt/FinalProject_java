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
	public void go() throws InvalidInput, IOException, DuplicatedNameException, ElementNotFoundException{
		if (vfs.checkPath(vdName, vfsPath)){
			vfs.exportFile(hostPath, vdName, vfsPath);// the path correspond to a file
			System.out.println("A file is exported to" + hostPath);
		}
		else {
			vfs.exportDirectory(hostPath, vdName, vfsPath);// the path correspond to a directory
			System.out.println("A directory is exported to" + hostPath);
		}
		
	}

}
