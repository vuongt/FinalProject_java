package vfs;

public class UserInterface {

	/**
	 * @param args
	 * @throws InvalidInput 
	 * @throws DuplicatedNameException 
	 */
	public static void main(String[] args) throws DuplicatedNameException, InvalidInput {
		VFS vfs = new VFS();
		vfs.createVirtualDisk("virtualDisk1", 1000000);
		
	}
	
	


}
	