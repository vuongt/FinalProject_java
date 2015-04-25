package vfs;
import java.io.*;
/**
 * 
 * CommandBehaviour:
 * (Application of strategy pattern) Abstract class that represent the different behaviours the main method of the class Interface has to perform, 
 * depending on the command an external user chooses . There's an extension of this class per command accepted by Interface: each extension overrides 
 * the method go() with the behaviour corresponding to its command (It classifies the arguments introduced and calls the appropiate methods of VFS ) 
 *
 */
public abstract class CommandBehaviour {
	VFS vfs;
	
	public CommandBehaviour(VFS vfs){
		this.vfs=vfs;
	}
	
	public abstract void go() throws InvalidInput,IOException,InvalidNameException,DuplicatedNameException, FileNotFoundException, DirectoryNotFoundException, SizeException;
	

}