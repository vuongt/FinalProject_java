package vfs;


import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * Fichier:
 * Class to represent files stored in the vfs.
 * Implements Serializable,item.
 */
public class Fichier implements Serializable, Item{
	
	private static final long serialVersionUID = 446;
	private String name;
	private String absolutePath;
	private ArrayList<Byte> data;
	
/**
 * Constructor of Fichier.
 * Initializations:
 * data:empty array.
 * absolutePath: empty string.
 * @param name
 * @throws InvalidNameException
 */
	public Fichier(String name) throws InvalidNameException {
		super();
		if(name==null||name.contains("|")||name.contains("/")||name.contains(" ")||name.contains("\\")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidNameException(name+ " has an invalid syntax");
		}
		this.name = name;
		this.data =  new ArrayList<Byte>();
		this.absolutePath = "";
	}
	
	/**
	 * Constructor of Fichier.
	 * Initializations:
	 * absolutePath: empty array.
	 * @param name
	 * @param data
	 * @throws InvalidNameException
	 */
	public Fichier(String name, ArrayList<Byte> data) throws InvalidNameException {
		super();
		if(name==null||name.contains("|")||name.contains("/")||name.contains(" ")||name.contains("\\")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidNameException(name+ " has an invalid syntax");
		}
		this.name = name;
		this.absolutePath = "";
		this.data = data;
	}
	
	/**
	 * Constructor of Fichier.
	 * @param name
	 * @param absolutePath
	 * @param data
	 * @throws InvalidNameException
	 */
	public Fichier(String name,String absolutePath,  ArrayList<Byte> data) throws InvalidNameException {
		super();
		if(name==null||name.contains("|")||name.contains("/")||name.contains(" ")||name.contains("\\")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidNameException(name+ " has an invalid syntax");
		}
		this.name = name;
		this.absolutePath =absolutePath;
		this.data = data;
	}
	
	public void setName(String name) throws InvalidNameException {
		if(name==null||name.contains("|")||name.contains("/")||name.contains("\\")||name.contains(" ")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidNameException(name+ " has an invalid syntax");
		}
		this.name = name;
	}


	public String getName() {
		return name;
	}
	
	
	@Override
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
		return (this.name.equals(f.name)&&this.absolutePath.equals(f.absolutePath)&&this.data.equals(f.data));
	}
	
	/**
	 * computes the size of the file
	 * @return size in bytes
	 */
	@Override
	public long getSize() {
		return data.size();
	}
	
	

}
