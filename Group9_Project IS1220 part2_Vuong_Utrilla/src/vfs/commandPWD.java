package vfs;


public class commandPWD extends CommandBehaviour {
	private String vdName;

	public commandPWD(VFS vfs, String vdName) {
		super(vfs);
	}

	@Override
	public void go() throws InvalidInput {
		vfs.checkPath(vdName, "/");
		System.out.println("You are in the virtual disk named" + vdName);
		System.out.println("The current position is:" + vfs.getVirtualDisks().get(vdName).getCurrentPosition());
	}

}
