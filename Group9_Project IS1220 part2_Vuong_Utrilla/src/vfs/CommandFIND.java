package vfs;

import java.io.IOException;
import java.io.FileNotFoundException;
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
		this.vfsPath = vfsPath;
	}

	@Override
	public void go() throws InvalidInput, IOException, DuplicatedNameException,
			FileNotFoundException, DirectoryNotFoundException, SizeException {
		vfs.checkPath(vdName, vfsPath);
		ArrayList<Fichier> list=vfs.findFile(searchFile, vdName, vfsPath);
		
		if(list.equals(null)){
			System.out.println("No file named "+searchFile+" exists in "+vdName);
		}else{
			for(Fichier f:list){
				System.out.println(f.getName()+"--"+f.getAbsolutePath());
			}
		}
		
	}

}
