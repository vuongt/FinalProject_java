package vfs;


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
	public void go() throws InvalidInput, DuplicatedNameException {
		boolean sourceIsFile = vfs.checkPath(vdName, source);
		boolean targetIsFile = vfs.checkPath(vdName, target);
		if (targetIsFile){
			if (sourceIsFile) vfs.copyContentFile(vdName,source,target);
			else throw new InvalidInput("Cannot copy a directory to a file");
		}
		
		else {
			vfs.copy(vdName, source, target);
		}
		
	}

}
