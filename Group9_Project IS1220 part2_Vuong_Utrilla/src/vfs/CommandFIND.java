package vfs;
import java.util.ArrayList;
/**
 * CommandFIND:
 * Commands the research of a file in a VFS, by filename. If files are found, it displays its vfspaths (if no files are
 * found, message to the user).
 * If the user introduces a vfspath, the research is commanded inside that location. If not, it's commanded
 *  in the entire VirtualDisk. (different constructors of CommandFind are used)
 *
 */
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

	public void go() throws InvalidInput, InvalidNameException {

		if(vfs.checkPath(vdName, vfsPath)){throw new InvalidInput("Research must be done in a directory. Unable to search in a file's location.");}
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
