package vfs;

import java.util.*;
import java.io.Serializable;

/**
 * Directory:
 * Class that represents the folders stored in the vfs. 
 * Implements Serializable,Item
 *
 */
public class Directory implements Serializable, Item {
	private String name;
	protected String absolutePath;
	protected HashMap<String,Fichier> fileMap;//maps and directories contained in the vfs
	protected HashMap<String,Directory> directoryMap;
	private static final long serialVersionUID = 445;
	
	
	/**
	 * Constructor of Directory.
	 * fileMap, directoryMap: empty HashMap.
	 * absolutePath. empty String.
	 * @param name
	 * @throws InvalidNameException
	 */
	public Directory(String name) throws InvalidNameException{
		super();
		if(name==null||name.contains("|")||name.contains(" ")||name.contains("/")||name.contains("\\")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidNameException(name+ " has an invalid syntax");
		}
		this.name= name;
		this.fileMap= new HashMap<String,Fichier>();
		this.directoryMap = new HashMap<String,Directory>();
		this.absolutePath = "";
	}
	/**
	 * Constructor of Directory.
	 * fileMap, directoryMap: empty HashMap. 
	 * @param name
	 * @param absolutePath
	 * @throws InvalidNameException
	 */
	public Directory(String name, String absolutePath) throws InvalidNameException {
		super();
		if(name==null||name.contains("|")||name.contains(" ")||name.contains("/")||name.contains("\\")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidNameException(name+ " has an invalid syntax");
		}
		this.name = name;
		this.absolutePath = absolutePath;
		this.fileMap = new HashMap<String,Fichier>();
		this.directoryMap =  new HashMap<String,Directory>();
	}
	/**
	 * Constructor of Directory
	 * @param name
	 * @param absolutePath
	 * @param fileMap
	 * @param directoryMap
	 * @throws InvalidNameException
	 */
	
	public Directory(String name, String absolutePath,
			HashMap<String, Fichier> fileMap,
			HashMap<String, Directory> directoryMap) throws InvalidNameException {
		super();
		if(name==null||name.contains("|")||name.contains("/")||name.contains(" ")||name.contains("\\")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidNameException(name+ " has an invalid syntax");
		}
		this.name = name;
		this.absolutePath = absolutePath;
		this.fileMap = fileMap;
		this.directoryMap = directoryMap;
	}

	
	/**
	 * add a file (Fichier) to this directory
	 * @param name, name of the file
	 * @throws DuplicatedNameException
	 * @throws InvalidNameException
	 */

	public void addFile(String name) throws DuplicatedNameException, InvalidNameException{
		if (this.getFileMap().containsKey(name)) throw new DuplicatedNameException("There is already a file named " + name+ " in this directory");
		else {
			Fichier file = new Fichier(name);
			file.setAbsolutePath(this.absolutePath + name);
			this.fileMap.put(name, file);
		}
		
	}
	/**
	 * add a file (Fichier) to this directory
	 * @param file, Fichier object to add
	 * @throws DuplicatedNameException
	 */
	public void addFile(Fichier file) throws DuplicatedNameException{
		if (this.getFileMap().containsKey(file.getName())) throw new DuplicatedNameException("There is already a file named " + file.getName()+ "in this directory");
		if (file != null){
			this.fileMap.put(file.getName(),file);
			this.fileMap.get(file.getName()).setAbsolutePath(this.absolutePath + file.getName());
			}
	}

	/**
	 * Add a directory (Directory) to this directory
	 * @param name of the directory to add
	 * @throws DuplicatedNameException
	 * @throws InvalidNameException
	 */
	public void addDirectory(String name) throws DuplicatedNameException, InvalidNameException{
		if (this.getDirectoryMap().containsKey(name)) throw new DuplicatedNameException("There is already a directory named " + name+ " in this directory");
		else {
			Directory d = new Directory(name);
			d.setAbsolutePath(this.absolutePath + name + "/" );
			this.directoryMap.put(name, d);
			}
	}
	
	/**
	 * Add a directory (Directory) to this directory
	 * @param d, Directory object to add
	 * @throws DuplicatedNameException
	 */
	public void addDirectory(Directory d) throws DuplicatedNameException{
		if (this.getDirectoryMap().containsKey(d.getName())) throw new DuplicatedNameException("there is already a directory named " + d.getName()+ "in this directory");
		if (d != null){ 
		this.directoryMap.put(d.getName(),d);
		this.directoryMap.get(d.getName()).setAbsolutePath(this.absolutePath + d.getName() + "/");
		}
	}
	
	/**
	 * Delete a file (Fichier) from this directory
	 * @param name, name of the file to delete
	 */
	public void deleteFile(String name){
		this.fileMap.remove(name);
	}
	
	/**
	 * Delete a directory (Directory) from this directory
	 * @param name, name of the directory to delete
	 */
	public void deleteDirectory(String name){
		this.directoryMap.remove(name);
	}
	
	/**
	 * Calculate the size of the directory by recurrence
	 * @return  size in bytes 
	 */
	@Override
	public long getSize(){
		long size=0;
		for (Item i : this.getElement())
			size = size + i.getSize();
		return size;
	}


	@Override
	public String getAbsolutePath() {
		return absolutePath;
	}

	
	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof Directory)) return false;
		Directory d=(Directory)o;
		return (this.name.equals(d.name) && this.fileMap.equals(d.fileMap) && this.directoryMap.equals(d.directoryMap));
	
	}
	
	/**
	 * Listing elements in a directory
	 * @return ArrayList<Item> of the Item elements contained in directoryMap and fileMap
	 */
	public ArrayList<Item> getElement(){
		ArrayList<Item> list = new ArrayList<Item>();
		for (Directory d : this.directoryMap.values())
			list.add(d);
		for (Fichier f : this.fileMap.values())
			list.add(f);
		return list;
	}
	

	public void setName(String name) throws InvalidNameException {
		if(name==null||name.contains("|")||name.contains("/")||name.contains(" ")||name.contains("\\")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidNameException(name+ " has an invalid syntax");
		}
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	

	public HashMap<String, Fichier> getFileMap() {
		return fileMap;
	}
	
	public HashMap<String, Directory> getDirectoryMap() {
		return directoryMap;
	}
	
	
}
