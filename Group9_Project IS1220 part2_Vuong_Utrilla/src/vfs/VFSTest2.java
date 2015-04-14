package vfs;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class VFSTest2 {
	
	
	VFS vfs2;
	VirtualDisk vd2;
	String s="";
	

	@Before
	public void method(){
		try{
			vfs2=new VFS();
			vd2 = new VirtualDisk("Root",1000);
			vfs2.getVirtualDisks().put("Root",vd2 );
			Directory D1 = new Directory("D1");
			Directory D2 = new Directory("D2");
			Fichier file1 = new Fichier("file1.txt");
			Fichier file2 = new Fichier("file2.png");
			Fichier file3 = new Fichier("file2.jpg");
			vd2.getDirectoryMap().put("D1",D1);
			vd2.getDirectoryMap().put("D2",D2);
			vd2.getFileMap().put("file1.txt",file1);
			D1.getFileMap().put("file2.png", file2);
			D1.getFileMap().put("file3.jpg", file3);
			s="C:\\Users\\Tuyet VUONG\\Documents\\javatest\\";
			
			
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	@Test
	public void testSave() {
		
		FileInputStream filein=null;
		ObjectInputStream in=null;
		try{
			
			vfs2.createFile("Root", "file4.pdf", "/D1/");
			
			
			
			Path p1=Paths.get(s);
			Path p2=Paths.get("Root");
			Path p3=p1.resolve(p2);
			
			
			File file=new File(p3.toString());
			if(file.exists())file.delete();
			
			vfs2.exportVfs("Root",s);
			
			vfs2.save("Root");
			
			filein= new FileInputStream(file);
			
			in=new ObjectInputStream(filein);
			
			VirtualDisk test=(VirtualDisk)in.readObject();
			
			assertEquals(vd2,test);
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
		finally{
			if(in!=null){
				try{
					in.close();
				}catch(IOException e){}
			}
			if(filein!=null){
				try{
					filein.close();
				}catch(IOException e){}	
			}
		}
	}

	@Test
	public void testExportFile() {
		File fileIn=null;
		FileInputStream in=null;
		try{

			
			vfs2.exportFile(s,"Root","/file1.txt");
			
			Path p1=Paths.get(s);
			Path p2=Paths.get("file1.txt");
			Path p3=p1.resolve(p2);
			
			
			fileIn=new File(p3.toString());
			in=new FileInputStream(fileIn);
			
			long size=Files.size(p3);
			
			byte b;
			ArrayList<Byte> data=new ArrayList<Byte>();
			while((b=(byte)in.read())!=-1){
				
				data.add(b);
					
			}
			
			Fichier file1=vd2.getFileMap().get("file1.txt");
			Fichier test=new Fichier("file1.txt",size,"",data);
			assertEquals(test,file1);

		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}finally{
			if(in!=null){
				try{
					in.close();
				}catch(IOException e){}
				
			}
		}
		
		
	
	}
	
	@Test 
	
	public void testFindFile(){
		try{
			ArrayList<Fichier> list=vfs2.findFile("file2.png","Root");
			
			Fichier test=new Fichier("file2.png");
			assertEquals(test,list.get(0));
			
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	@Test
	public void testImportFile() {
		try{
			
			
			
			Path p1=Paths.get(s);
			Path p2=Paths.get("file5.txt");
			Path p3=p1.resolve(p2);
			
			
			File file=new File(p3.toString());
			
			
			file.createNewFile();
			
			
			
			vfs2.importFile(p3.toString(),"Root","/D2/");//Importing file1 to directory d2
			
			ArrayList<Fichier> list=vfs2.findFile("file5.txt", "Root");
			Fichier test=new Fichier("file5.txt",0,"/file5.txt");
			assertEquals(list.get(0),test);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}

	@Test
	public void testExportDirectory() {
		try{
			
			vd2.getDirectoryMap().get("D2").addFile("file4.txt");
			vfs2.exportDirectory(s, "Root", "/D2/");
			
			Path p1=Paths.get(s);
			Path p2=Paths.get("D2");
			Path p3=p1.resolve(p2);
			
			File test=new File(p3.toString());
			assertTrue(test.isDirectory());
			
			Path p4=p3.resolve("file4.txt");
			File test2=new File(p4.toString());
			assertTrue(test2.isFile());
			
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testImportDirectory() {
		try{
		
		
			Path p1=Paths.get(s);
			Path p2=Paths.get("D3");
			Path p3=p1.resolve(p2);
		
			File dir=new File(p3.toString());
			dir.mkdir();
			
			Path p4=Paths.get("file6.pdf");
			Path p5=p3.resolve(p4);
			File file=new File(p5.toString());
			file.createNewFile();
			
			
			vfs2.importDirectory(p3.toString(), "Root", "/D1/");
			
			Path p=Paths.get("/D1/");
			
			Directory d1=vfs2.goPath("Root",p, 1);
			
			assertTrue(d1.getDirectoryMap().containsKey("D3"));
			
			Directory d3=d1.getDirectoryMap().get("D3");
			assertTrue(d3.getFileMap().containsKey("file6.pdf"));
			 
		}
	catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
}
