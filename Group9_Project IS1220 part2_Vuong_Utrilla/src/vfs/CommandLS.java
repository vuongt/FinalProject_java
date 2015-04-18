package vfs;


import java.util.ArrayList;

public class CommandLS extends CommandBehaviour{
	private String vdName;
	private String arg;
	private String vfsPath;
	
	public CommandLS(VFS vfs,String vdName){
		super(vfs);
		this.vdName = vdName;
		this.arg = "";
		this.vfsPath = vfs.getVirtualDisks().get(vdName).getCurrentPosition();
	}
	
	public CommandLS(VFS vfs, String vdName, int arg){
		super(vfs);
		this.vdName = vdName;
		this.arg = "-l";
		this.vfsPath = vfs.getVirtualDisks().get(vdName).getCurrentPosition();
	}
	
	public CommandLS(VFS vfs, String vdName, String vfsPath){
		super(vfs);
		this.vdName = vdName;
		this.arg = "";
		this.vfsPath = vfsPath;
		
	}
	public CommandLS(VFS vfs, String vdName,String arg, String vfsPath){
		super(vfs);
		this.vdName = vdName;
		this.arg = arg;
		this.vfsPath = vfsPath;
	}

	@Override
	public void go() throws InvalidInput{
		vfs.checkPath(vdName, vfsPath);
		ArrayList<ArrayList<String>> list = vfs.show(vdName, arg, vfsPath);
		for (ArrayList<String> smallList : list){
			for (String s :smallList){
				System.out.println(s);}
			System.out.println("\n");
		}
	}
}
