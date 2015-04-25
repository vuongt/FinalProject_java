package vfs;

import java.io.IOException;

/**
 * CommandBehaviour:
 *	Depending of the arguments introduced by an external user into Interface, it can commit two functionalities to a 
 *VFS: the exportation of a file or the exportation of a directory to the host file system
 *
 */

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

	public void go() throws InvalidInput, IOException, DuplicatedNameException, InvalidNameException{

		if (vfs.checkPath(vdName, vfsPath)){
			vfs.exportFile(hostPath, vdName, vfsPath);// the path correspond to a file

			System.out.println("The file is exported to" + hostPath);
		}else {
			vfs.exportDirectory(hostPath, vdName, vfsPath);// the path correspond to a directory
			System.out.println("The directory is exported to" + hostPath);
		}

	}

}
