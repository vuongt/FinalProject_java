package vfs;

import java.nio.file.Path;

import java.util.Scanner;
import java.nio.file.Paths;
import java.io.*;

public class LittleTest {
	
	
	public static void main(String[] args){

		try{Path p=Paths.get("VFSTest2").resolve("testImportFile2.txt");
		System.out.println(p.toString());//Host file system path of a non-existing file
	
		File file=new File(p.toString());
		FileInputStream in=new FileInputStream(file);
		}catch(FileNotFoundException e){
			System.out.println("yes");
		}
		
		
		
		
		
		
	}

	
}
