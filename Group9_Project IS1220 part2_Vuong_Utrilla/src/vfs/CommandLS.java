package vfs;

import java.io.IOException;
import java.util.ArrayList;

public class CommandLS extends CommandBehaviour{
	private String vdName;
	private String arg;
	private String vfsPath;
	
	//ls vdName vdsPath---->intarg=0
	//ls vdName -l vfsPath---->intarg=1
	public CommandLS(VFS vfs,String vdName,int intarg){
		super(vfs);
		this.vdName = vdName;
		if(intarg==0){
			this.arg = "";
		}else if(intarg==1){
			this.arg="-l";
		}
		this.vfsPath = "";
	}
	
	//ls vdName [vdsPath]---->intarg=0
	//ls vdName -l [vfsPath]---->intarg=1
	public CommandLS(VFS vfs, String vdName,int intarg, String vfsPath){
		super(vfs);
		this.vdName = vdName;
		if(intarg==0){
			this.arg = "";
		}else if(intarg==1){
			this.arg="-l";
		}
		this.vfsPath = vfsPath;
	}

	@Override
	public void go() throws InvalidInput, IOException, DuplicatedNameException, InvalidNameException {
		if(vfsPath==""){
			vfs.checkPath(vdName, "/");
		}else{
			vfs.checkPath(vdName, vfsPath);
		}

		ArrayList<ArrayList<String>> list = vfs.show(vdName, arg, vfsPath);
	
		for (ArrayList<String> smallList : list){
			for (String s :smallList){
				System.out.format("%-35s",s);}
			System.out.println("\n");
		}
		
		if(list.isEmpty()){
			System.out.println("Empty directory");
		}
	}
}

