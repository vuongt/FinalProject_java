package vfs;

import java.io.IOException;

public class CommandFIND extends CommandBehaviour{
	private String vdName;
	private String searchFile;
	private String vfsPath;

	public CommandFIND(VFS vfs,String vdName, String searchFile) {
		super(vfs);
		this.vdName = vdName;
		this.searchFile = searchFile;
		this.vfsPath = "/";
	}
	
	public CommandFIND(VFS vfs,String vdName, String searchFile,String vfsPath) {
		super(vfs);
		this.vdName = vdName;
		this.searchFile = searchFile;
		this.vfsPath = "/";
	}

	@Override
	public void go() throws InvalidInput, IOException, DuplicatedNameException,
			FileNotFoundException, DirectoryNotFoundException, SizeException {
		vfs.checkPath(vdName, vfsPath);
		vfs.findFile(searchFile, vdName, vfsPath);
		
	}

}
