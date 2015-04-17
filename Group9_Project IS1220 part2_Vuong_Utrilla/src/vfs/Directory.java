package vfs;

import java.util.*;//hello
import java.io.Serializable;

public class Directory implements Serializable, Item {
	private String name;
	protected String absolutePath;
	protected HashMap<String,Fichier> fileMap;
	protected HashMap<String,Directory> directoryMap;
	private static final long serialVersionUID = 445;
	
	public Directory(String name){
		super();
		this.name= name;
		this.fileMap= new HashMap<String,Fichier>();
		this.directoryMap = new HashMap<String,Directory>();
		this.absolutePath = "";
	}
	
	public Directory(String name, String absolutePath) {
		super();
		this.name = name;
		this.absolutePath = absolutePath;
		this.fileMap = new HashMap<String,Fichier>();
		this.directoryMap =  new HashMap<String,Directory>();
	}
	
	public Directory(String name, String absolutePath,
			HashMap<String, Fichier> fileMap,
			HashMap<String, Directory> directoryMap) {
		super();
		this.name = name;
		this.absolutePath = absolutePath;
		this.fileMap = fileMap;
		this.directoryMap = directoryMap;
	}

	


	public void addFile(String name) throws DuplicatedNameException{
		if (this.getFileMap().containsKey(name)) throw new DuplicatedNameException("there is already a file named " + name+ "in this directory");
		else {
			Fichier file = new Fichier(name);
			file.setAbsolutePath(this.absolutePath + "/" + name);
			this.fileMap.put(name, file);
		}
		
	}
	public void addFile(Fichier file) throws DuplicatedNameException{
		if (this.getFileMap().containsKey(file.getName())) throw new DuplicatedNameException("there is already a file named " + file.getName()+ "in this directory");
		if (file != null){
			this.fileMap.put(file.getName(),file);
			this.fileMap.get(file.getName()).setAbsolutePath(this.absolutePath + "/" + file.getName());
			}
	}
	
	public void addDirectory(String name) throws DuplicatedNameException{
		if (this.getDirectoryMap().containsKey(name)) throw new DuplicatedNameException("there is already a directory named " + name+ "in this directory");
		else {
			Directory d = new Directory(name);
			d.setAbsolutePath(this.absolutePath + "/" + name);
			this.directoryMap.put(name, d);
			}
	}
	
	public void addDirectory(Directory d) throws DuplicatedNameException{
		if (this.getDirectoryMap().containsKey(d.getName())) throw new DuplicatedNameException("there is already a directory named " + d.getName()+ "in this directory");
		if (d != null){ 
		this.directoryMap.put(d.getName(),d);
		this.directoryMap.get(d.getName()).setAbsolutePath(this.absolutePath + "/" + d.getName());
		}
	}
	
	public void deleteFile(String name){
		this.fileMap.remove(name);
	}
	public void deleteDirectory(String name){
		this.directoryMap.remove(name);
	}
	
	/**
	 * Calculate the size of a directory by recurrence
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
	
	public ArrayList<Item> getElement(){
		ArrayList<Item> list = new ArrayList<Item>();
		for (Directory d : this.directoryMap.values())
			list.add(d);
		for (Fichier f : this.fileMap.values())
			list.add(f);
		return list;
	}
	

	public void setName(String name) {
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
