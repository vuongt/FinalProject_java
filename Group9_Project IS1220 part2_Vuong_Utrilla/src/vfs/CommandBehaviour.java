package vfs;
import java.io.*;
public abstract class CommandBehaviour {
	VFS vfs;
	
	public CommandBehaviour(VFS vfs){
		this.vfs=vfs;
	}
	
	public abstract void go() throws InvalidInput,IOException,DuplicatedNameException, FileNotFoundException, ElementNotFoundException, SizeException;
	

}