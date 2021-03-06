package vfs;
/**
 * CommandCP:
 * Depending on the inputs introduced by the external user, it can command two functionalities to a VFS:
 * the copy of a file/directory to a location of a VirtualDisk or the copy of the content of a file into 
 * another file of the VirtualDisk
 * 
 */

public class CommandCP extends CommandBehaviour {

	private String vdName;
	private String source;
	private String target;
	
	public CommandCP(VFS vfs,String vdName,String stringSource, String stringTarget) {
		super(vfs);
		this.vdName = vdName;
		this.source = stringSource;
		this.target = stringTarget;
	}
	
	
	
	@Override
	public void go() throws InvalidInput, DuplicatedNameException, InvalidNameException {
		boolean sourceIsFile = vfs.checkPath(vdName, source);
		boolean targetIsFile = vfs.checkPath(vdName, target);
		
		if (targetIsFile){
			
			if (sourceIsFile) {
				vfs.copyContentFile(vdName,source,target);
				System.out.println("The content of "+ source +" is copied to " + target);
			}
			else throw new InvalidInput("Cannot copy a directory to a file");
		}
		
		else {
			vfs.copy(vdName, source, target);
			System.out.println(source + " is copied to " + target);
		}
		
	}

}
