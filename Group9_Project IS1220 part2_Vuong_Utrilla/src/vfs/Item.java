package vfs;

/**
 * Item:
 * Interface that represents any object stored in the vfs.
 *
 */
public interface Item {
	/**
	 * Computes the space occupied by an element of the vfs
	 * @return size in bytes
	 */
	public long getSize();
	/**
	 * Computes the absolutePath of an element of the vfs.
	 * @return absolutePath
	 */
	public String getAbsolutePath();
}
