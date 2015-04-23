package vfs;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;

public class CommandIMPVFS extends CommandBehaviour{
	private String hostPath;
	private String vdName;
	private String vfsPath;
	

	public CommandIMPVFS(VFS vfs, String hostPath,String vdName, String vfsPath) {

		super(vfs);
		this.hostPath = hostPath;
		this.vdName = vdName;
		this.vfsPath = vfsPath;
	
	}

	@Override

	public void go() throws InvalidInput, IOException, DuplicatedNameException,FileNotFoundException, DirectoryNotFoundException, SizeException, InvalidNameException {
	
	File fileOrDir=new File(hostPath);
	vfs.checkPath(vdName, "/");
	
		
	if (fileOrDir.isFile()){
		vfs.importFile(hostPath, vdName, vfsPath);// the path correspond to a file
		System.out.println("The file was imported to the virtual disk");
	}else if(fileOrDir.isDirectory()){
		
		vfs.importDirectory(hostPath, vdName, vfsPath);// the path correspond to a directory
		System.out.println("The directory was imported to the virtual disk");

	}else{//Not file or directory. Exception is thrown. 
		throw new FileNotFoundException("The element "+hostPath+" doesn't exist in the host file system.");
		
	}

	}

}
