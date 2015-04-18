package vfs;

import java.io.IOException;
import java.util.ArrayList;

public class CommandLS extends CommandBehaviour{
	private String vdName;
	private String arg;
	private String vfsPath;
	//pourquoi pas faire ca;(sinon int arg est jamais utilis�)
	//Oui, je regroupe dans les constructeurs suivants
	
	//ls vdName vfsPath ----->intarg = 0
	//ls vdName -l vfsPath ----->intarg = 1
	public CommandLS(VFS vfs, String vdName, int intarg,String vfsPath){
		super(vfs);
		this.vdName = vdName;
		if(intarg==0){
			this.arg = "";
		}
		else if(intarg==1){
			this.arg= "-l";
		}
		this.vfsPath = vfsPath;
	}
	
	//ls vdName -l  ----> intarg = 1
	//ls vdName -----> intarg = 0;
	public CommandLS(VFS vfs, String vdName, int intarg){
		super(vfs);
		this.vdName = vdName;
		if(intarg==0){
			this.arg = "";
		}
		else if(intarg==1){
			this.arg= "-l";
		}
		this.vfsPath = vfs.getVirtualDisks().get(vdName).getCurrentPosition();
	}
	
	
	@Override
	public void go() throws InvalidInput, IOException, DuplicatedNameException {
		vfs.checkPath(vdName, vfsPath);
		ArrayList<ArrayList<String>> list = vfs.show(vdName, arg, vfsPath);
		for (ArrayList<String> smallList : list){
			for (String s :smallList){
				System.out.println(s);}
		}
	}
}
