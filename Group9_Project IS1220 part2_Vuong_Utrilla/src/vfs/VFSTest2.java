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
	String s="VFSTest2";
	;
	

	@Before
	public void method(){
		try{
			
			//using commands tested in the first set of tests
			vfs2=new VFS();
			vfs2.createVirtualDisk("Root", 1000);
			vd2=vfs2.getVirtualDisks().get("Root");
			
			vfs2.createDirectory("Root", "D1", "/");
			vfs2.createDirectory("Root", "D2", "/");
			vfs2.createFile("Root", "file1.txt", "/");
			vfs2.createFile("Root", "file2.png", "/D1/");
			vfs2.createFile("Root", "file3.jpg", "/D1/");
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	/*@Test
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
	}*/
	/**
	 * TestExportFile.
	 * Test of the basic functionality of ExportFile ( by exporting /file1.txt, then importing it manually (FileInputStream) and comparing both tests.)
	 */
	/*@Test
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
			
			
			
			byte b;
			ArrayList<Byte> data=new ArrayList<Byte>();
			while((b=(byte)in.read())!=-1){
				
				data.add(b);
					
			}
			
			
			
			Fichier file1=vd2.getFileMap().get("file1.txt");
			Fichier test=new Fichier("file1.txt","/file1.txt",data);
			
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
		
		
	
	}*/
	
	/**
	 * TestExportFile2. 
	 * While we export a file to the host system's location, if there's already a file with the same name DuplicatedNameException is thrown.
	 */
	/*@Test (expected= DuplicatedNameException.class)
	public void testExportFile2() {
		
				try {
					vfs2.exportFile(s,"Root","/testExportFile2.txt");
				} catch (Exception e) {
				}

	}*/
	
	/**
	 * TestImportFile
	 * Test the basic functionality of import file ( By importing the file 'testImportFile.txt'-which existed already inside  
	 * the folder VFSTest2 of the host file system. Converting the content of the imported file to an string and comparing it
	 * to the expected content: 'testImportFile: this is a test.'-it was saved in 'testImportFile.txt' when this file was created.)
	 */
	
	/*@Test
	public void testImportFile() {
		try{
		
			Path p=Paths.get(s).resolve("testImportFile.txt");
			
			vfs2.importFile(p.toString(),"Root","/D2/");//Importing testImportFile to directory D2
			
			Fichier importedFile=vfs2.getVirtualDisks().get("Root").getDirectoryMap().get("D2").getFileMap().get("testImportFile.txt");
			
			ArrayList<Byte> data=importedFile.getData();
			byte[] data2=new byte[(int)importedFile.getSize()];
			int i=0;
			for(Byte b:data){
				data2[i]=b;
				i++;
			}
			
			String contentImportedFile=new String(data2);
			assertEquals(contentImportedFile,"testImportFile: this is a test.");
			
			
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}*/
	
	@Test (expected=FileNotFoundException.class)
	public void testImportFile2() {
		
				try {
					
					Path p=Paths.get(s).resolve("testImportFile2.txt");
					System.out.println(p.toString());//Host file system path of a non-existing file
					vfs2.importFile(p.toString(),"Root","/D2/");
				}catch(FileNotFoundException e){
					System.out.println("si");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}

	}
	

	/*@Test
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
	}*/
	
}
