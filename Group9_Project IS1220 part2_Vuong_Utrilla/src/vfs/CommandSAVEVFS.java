package vfs;
/**
 * CommandSAVE:
 * For a Virtual Disk that has been exported, it commands the update of the last file of the host file system where it was
 * exported. 
 */
import java.io.IOException;

public class CommandSAVEVFS extends CommandBehaviour{
	private String vdName;

	public CommandSAVEVFS(VFS vfs, String vdName) {
		super(vfs);
		this.vdName = vdName;
	}

	@Override

	public void go() throws InvalidInput, IOException, DuplicatedNameException,
			  SizeException, InvalidNameException {

		vfs.checkPath(vdName, "/");
		vfs.save(vdName);
		System.out.println(vdName + " is saved");

	}

	
}
