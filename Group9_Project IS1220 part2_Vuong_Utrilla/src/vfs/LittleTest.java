package vfs;

import java.nio.file.Path;
import java.util.Scanner;
import java.nio.file.Paths;
import java.io.File;

public class LittleTest {
	
	
	public static void main(String[] args){
		
		CommandBehaviour cd=new CommandCD(new VFS(),"hola","hola");
		
		try{
			cd.go();
		}catch(Exception e){
			
		}
		
		
		
		
		
	}

}
