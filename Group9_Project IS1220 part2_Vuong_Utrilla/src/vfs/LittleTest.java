package vfs;

import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.Path;
import java.util.Scanner;
import java.nio.file.Paths;
import java.io.*;

public class LittleTest {
	
	
	public static void main(String[] args){
		
	try{
		Scanner sc=new Scanner(System.in);
		String s=sc.nextLine();
		Path p=Paths.get(s);
		System.out.println(p.getName(0));
		System.out.println(p.getName(1));
		System.out.println(p.getName(2));
		System.out.println(p.toString());
		
		
		
		
		
	}catch(Exception e){
		e.printStackTrace();
		e.getMessage();
	}
		
		
	
		
	}
		
		
		

	
}
