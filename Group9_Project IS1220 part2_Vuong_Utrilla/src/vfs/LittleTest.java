package vfs;

import java.nio.file.Path;

import java.util.Scanner;
import java.nio.file.Paths;
import java.io.File;

public class LittleTest {
	
	
	public static void main(String[] args){
		while(true)
			{Scanner sc=new Scanner(System.in);
		System.out.println("introduce:");
		
		String s=sc.nextLine();
		
		System.out.println(Paths.get(s).getName(0));
		System.out.println(Paths.get(s).getName(1));
		System.out.println(Paths.get(s).toString());}
		
		
		
		
		
		
	}

	
}
