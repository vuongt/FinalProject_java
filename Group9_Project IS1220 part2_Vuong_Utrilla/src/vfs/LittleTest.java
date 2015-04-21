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
		VFS vfs=new VFS();
		
		vfs.createVirtualDisk("root", 1000000);
		vfs.importFile("C:\\Users\\Candela\\Desktop\\import\\piñote.jpg", "root",".");
		
		Directory dir=vfs.getVirtualDisks().get("root");
		Fichier file=dir.getFileMap().get("piñote.jpg");
		
		System.out.println(file.getName());
		int i;
		
		for(i=0;i<(int)file.getSize();i++){
			System.out.println(file.getData().get(i));
			
		}
		
		vfs.renameFile("root","./piñote.jpg", "piñote5.jpg");

		
		vfs.exportFile("C:\\Users\\Candela\\Desktop\\import", "root", "/piñote5.jpg");
		
		
		
		/*File file=new File("C:\\Users\\Candela\\Desktop\\import\\pdf.pdf");
		FileInputStream in= new FileInputStream(file);
		
		byte[] preData=new byte[(int)file.length()];
		
		in.read(preData);
		ArrayList<Byte> data=new ArrayList<Byte>();
		int i;
		for(i=0;i<(int)file.length();i++){
			data.add(preData[i]);
		}
		byte[] postData=new byte[data.size()];
		for(i=0;i<data.size();i++){
			postData[i]=data.get(i);
			
		}
		
		FileOutputStream out=new FileOutputStream("C:\\Users\\Candela\\Desktop\\import\\cpoy.pdf");
		out.write(postData);
		
		in.close();
		out.close();*/
		
		
		
		
		
	}catch(Exception e){
		e.printStackTrace();
		e.getMessage();
	}
		
		
	
		
	}
		
		
		

	
}
