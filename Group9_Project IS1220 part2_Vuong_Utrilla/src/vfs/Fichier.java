package vfs;


import java.io.Serializable;
import java.util.ArrayList;

public class Fichier implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 446;
	private String name;
	private long size;
	private String absolutePath;
	private ArrayList<Byte> data;
	
	
	
	public Fichier(String name, long size) {
		super();
		this.name = name;
		this.size = size;
		this.absolutePath = "";
		this.data =  new ArrayList<Byte>();
	}

	public Fichier(String name) {
		super();
		this.name = name;
		this.size=0;
		this.data =  new ArrayList<Byte>();
		this.absolutePath = "";
	}
	public Fichier(String name, long size,  ArrayList<Byte> data) {
		super();
		this.name = name;
		this.size = size;
		this.absolutePath = "";
		this.data = data;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}
	

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public ArrayList<Byte> getData() {
		return data;
	}
	@Override 
	public boolean equals(Object o){
		if(!(o instanceof Fichier)) return false;
		Fichier f=(Fichier)o;
		return (this.name.equals(f.name)&&this.size==f.size&&this.absolutePath.equals(f.absolutePath)&&this.data.equals(f.data));
	}
	

}
