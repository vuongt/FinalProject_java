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
		this.vfsPath = vfsPath;
	}

	@Override

	public void go() throws InvalidInput {

		vfs.checkPath(vdName, vfsPath);
		ArrayList<Fichier> result = vfs.findFile(searchFile, vdName, vfsPath);

		
		if(result!=null){

			System.out.println(result.size() + " file(s) found" +
					".Their absolute path in " + vdName + " are : ");
			for (Fichier f : result){
				System.out.println(f.getAbsolutePath());
			}
		}else{
			System.out.println("No file named '"+searchFile+"' was found");
		}
		

	}
}
