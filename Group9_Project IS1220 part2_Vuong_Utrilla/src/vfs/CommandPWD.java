package vfs;


public class CommandPWD extends CommandBehaviour {
	private String vdName;

	public CommandPWD(VFS vfs, String vdName) {
		super(vfs);
		this.vdName = vdName;
	}

	@Override
	public void go() throws InvalidInput {
		vfs.checkPath(this.vdName, "/");

		System.out.println("You are in the virtual disk named: " + vdName);
		System.out.println("The current position is: " + vfs.getVirtualDisks().get(vdName).getCurrentPosition());
	}

}
