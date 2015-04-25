package vfs;
/**
 * CommandFREE:
 * Commands the display of the occupied/free space of a determined VirtualDisk in a VFS
 *
 */

public class CommandFREE extends CommandBehaviour{
	private String vdName;

	public CommandFREE(VFS vfs,String vdName) {
		super(vfs);
		this.vdName = vdName;
		
	}

	@Override
	public void go() throws InvalidInput, InvalidNameException{
		vfs.checkPath(vdName, "/");
		System.out.println("Occupied space of the virtual disk " + vdName + " :"

				+ vfs.getVirtualDisks().get(vdName).getSize());
		System.out.println("Free space of the virtual disk " + vdName+ " :" 
				 + (vfs.getVirtualDisks().get(vdName).getSizeMax() - vfs.getVirtualDisks().get(vdName).getSize()));

	}

}
