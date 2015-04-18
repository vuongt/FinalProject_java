package vfs;

import java.util.ArrayList;

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
	public void go() throws InvalidInput{
		vfs.checkPath(vdName, vfsPath);
		ArrayList<Fichier> result = vfs.findFile(searchFile, vdName, vfsPath);
		System.out.println("Search result in " + vdName + " : ");
		for (Fichier f : result){
			System.out.println(f.getAbsolutePath());
		}
	}
}
