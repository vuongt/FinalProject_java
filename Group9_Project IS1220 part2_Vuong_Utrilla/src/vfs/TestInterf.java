package vfs;
import java.util.ArrayList;

import java.io.*;

public class TestInterf {

	
	public static void main(String[] args) throws IOException{
		
		File file=new File("C:\\Users\\Candela\\Desktop\\d\\image.bmp");	
		FileInputStream in= new FileInputStream(file);
		
		byte b;
		ArrayList<Byte> data=new ArrayList<Byte>();
		while((b=(byte)in.read())!=-1){
			data.add(b);
		}
		
		
		
		
		File file2=new File("C:\\Users\\Candela\\Desktop\\d\\image2.bmp");
		FileOutputStream out=new FileOutputStream(file2);
		
		System.out.println(data.isEmpty());
		for(Byte c:data){
			System.out.println(c);
			out.write(c);
		
		}
		
		in.close();
		out.close();
	}
		
			
	
}
