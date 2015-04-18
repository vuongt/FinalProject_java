package vfs;


public class CommandFREE extends CommandBehaviour{
	private String vdName;

	public CommandFREE(VFS vfs,String vdName) {
		super(vfs);
		this.vdName = vdName;
		
	}

	@Override
	public void go() throws InvalidInput{
		vfs.checkPath(vdName, "/");
		System.out.println("Occupied space of the virtual disk " + vdName + " :"
		 + vfs.getVirtualDisks().get(vdName).getSize());
		System.out.println("Free space of the virtual disk " + vdName+ " :" 
		 + (vfs.getVirtualDisks().get(vdName).getSizeMax() - vfs.getVirtualDisks().get(vdName).getSize()));
	}

}
