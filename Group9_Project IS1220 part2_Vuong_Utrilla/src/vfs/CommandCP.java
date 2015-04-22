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
	public void go() throws InvalidInput, DuplicatedNameException, InvalidNameException {
		boolean sourceIsFile = vfs.checkPath(vdName, source);
		System.out.println("aqui");
		boolean targetIsFile = vfs.checkPath(vdName, target);
		System.out.println("a1ui");
		if (targetIsFile){
			
			if (sourceIsFile) {
				vfs.copyContentFile(vdName,source,target);
				System.out.println("the content of "+ source +" is copied to " + target);
			}
			else throw new InvalidInput("Cannot copy a directory to a file");
		}
		
		else {
			System.out.println("aqui");
			vfs.copy(vdName, source, target);
			System.out.println(source + " is copied to " + target);
		}
		
	}

}
