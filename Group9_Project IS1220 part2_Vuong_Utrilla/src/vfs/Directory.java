package vfs;

import java.util.*;
import java.io.Serializable;

public class Directory implements Serializable {
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
	
	public void addFile(String name){
		Fichier file = new Fichier(name);
		file.setAbsolutePath(this.absolutePath + "/" + name);
		this.fileMap.put(name, file);
	}
	public void addFile(Fichier file){
		if (file == null) return;
		this.fileMap.put(file.getName(),file);
		this.fileMap.get(file.getName()).setAbsolutePath(this.absolutePath + "/" + file.getName());
	}
	
	public void addDirectory(String name){
		Directory d = new Directory(name);
		d.setAbsolutePath(this.absolutePath + "/" + name);
		this.directoryMap.put(name, d);	
	}
	
	public void addDirectory(Directory d){
		if (d == null) return;
		this.directoryMap.put(d.getName(),d);
		this.directoryMap.get(d.getName()).setAbsolutePath(this.absolutePath + "/" + d.getName());
	}
	
	public void deleteFile(String name){
		this.fileMap.remove(name);
	}
	public void deleteDirectory(String name){
		this.directoryMap.remove(name);
	}
	
	public long getSize(){
		long size=0;
		for (Fichier file : this.fileMap.values()){
			size = size + file.getSize();
		}
		for (Directory d : this.directoryMap.values()){
			size = size + d.getSize();
		}
		return size;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public HashMap<String, Fichier> getFileMap() {
		return fileMap;
	}

	public HashMap<String, Directory> getDirectoryMap() {
		return directoryMap;
	}
	
	
	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	@Override
	public boolean equals(Object o){
		
		if(!(o instanceof Directory)) return false;
		Directory d=(Directory)o;
		return (this.name.equals(d.name) && this.fileMap.equals(d.fileMap) && this.directoryMap.equals(d.directoryMap));
	
	}

}
