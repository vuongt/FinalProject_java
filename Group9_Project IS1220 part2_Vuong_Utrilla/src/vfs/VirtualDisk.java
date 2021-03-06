package vfs;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Virtual disk:
 * Class that represents a virtual disk of the vfs.
 * Extends directory, implements Serializable, Item
 *
 *
 */
public class VirtualDisk extends Directory implements Serializable, Item {
	
	private static final long serialVersionUID = 444;
	private String currentPosition;
	private long sizeMax;
	private ArrayList<String> hostPath;//list of the hostPaths the virtual disk is exported to
	
	/**
	 * Create a VirtualDisk
	 * Initialization:
	 * hostPath:empty string.
	 * @param name
	 * @param size
	 * @throws InvalidInput
	 * @throws InvalidNameException
	 */
	public VirtualDisk(String name, long size) throws InvalidInput, InvalidNameException{
		super(name);

		if (size < 0 ) throw new InvalidInput("The size must be positive.");

		this.sizeMax = size;
		this.hostPath = new ArrayList<String>();
		this.absolutePath = "/";
		this.currentPosition = "/";
	}
	
	
	/**
	 * Create a VirtualDisk
	 * @param name
	 * @param currentPosition
	 * @param sizeMax
	 * @param hostPath
	 * @param absolutePath
	 * @param fileMap
	 * @param directoryMap
	 * @throws InvalidInput
	 * @throws InvalidNameException
	 */
	public VirtualDisk(String name, String currentPosition, long sizeMax,
			ArrayList<String> hostPath,String absolutePath,
			HashMap<String, Fichier> fileMap,
			HashMap<String, Directory> directoryMap) throws InvalidInput, InvalidNameException {
		super(name,absolutePath,fileMap,directoryMap);
		this.currentPosition = currentPosition;

		if (sizeMax < 0 ) throw new InvalidInput("The size must be positive.");

		this.sizeMax = sizeMax;
		this.hostPath = hostPath;
		this.absolutePath = "/";
	}

	/**
	 * Computes the occupied space (addition of the size of each element contained in the virtual disk)
	 * @return occupied space in bytes 
	 */
	public long getOccupiedSpace() {
		return this.getSize();
	}
	
	public long getSizeMax() {
		return sizeMax;
	}

	public ArrayList<String> getHostPath() {
		return hostPath;
	}
	
	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof VirtualDisk))return false;
		VirtualDisk vd = (VirtualDisk) o;
		
		return (this.currentPosition.equals(vd.currentPosition)&&this.getAbsolutePath().equals(vd.getAbsolutePath())&&this.getName().equals(vd.getName())&&this.getFileMap().equals(vd.getFileMap())&&this.getDirectoryMap().equals(vd.getDirectoryMap())&&this.sizeMax==vd.sizeMax&&this.hostPath.equals(vd.hostPath));
			
	}

}
