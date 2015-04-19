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
			vfs2.createFile("Root", "file4.txt", "/D2/");
			vfs2.createDirectory("Root", "D3", "/D2/");
			
			
			
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
	 * Test of the basic functionality of ExportFile 
	 * (Exporting /file1.txt, then importing it manually (FileInputStream) and comparing it to the initial one.
	 * The exported file is then deleted from the host file system.)
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
				//DELETING EXPORTED FILE, IN THE HOST FILE SYSTEM
				fileIn.delete();
				
			}
		}
		
		
	
	}*/
	
	/**
	 * TestExportFile2. 
	 * When we export a file to a host system's location, if there's already a file with the same name, 
	 * then DuplicatedNameException is thrown.
	 * (Creating 'testExportFile2.txt' and exporting it to a location of the host system that contains 
	 * a file with the same name already.)
	 * @throws DuplicatedNameException 
	 * @throws InvalidInput 
	 * @throws IOException 
	 */
	/*@Test (expected= DuplicatedNameException.class)
	public void testExportFile2() throws InvalidInput, DuplicatedNameException, IOException {
					//Adding a file to export
					vfs2.createFile("Root", "testExportFile2.txt", "/");
					vfs2.exportFile(s,"Root","/testExportFile2.txt");//testExportFile2.txt exists already in this location of the host file system 
				

	}
	
	/**
	 * TestImportFile
	 * Test the basic functionality of import file 
	 * (Importing the file 'testImportFile.txt'-which existed already inside the folder 
	 * VFSTest2 of the host file system. Converting the content of the imported file to an 
	 * string and comparing it to the expected content: 'testImportFile: this is a test.'-
	 * it was saved in 'VFSTest2/testImportFile.txt' when this file was created.)
	 */
	
	/*@Test
	public void testImportFile() {
		try{
		
			Path p=Paths.get(s).resolve("testImportFile.txt");
			
			vfs2.importFile(p.toString(),"Root","/");//Importing testImportFile to directory D2
			
			Fichier importedFile=vfs2.getVirtualDisks().get("Root").getFileMap().get("testImportFile.txt");
			
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
	
	/**
	 * TestImportFile2
	 * When we import a non-existing file to the vfs, FileNotFoundException is thrown.
	 * (Importing 'testImportFile2.txt' from a location of the host file system where it doesn't exist.)
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidInput
	 * @throws SizeException
	 * @throws DuplicatedNameException
	 */
	/*@Test (expected=FileNotFoundException.class)
	public void testImportFile2() throws FileNotFoundException, IOException, InvalidInput, SizeException, DuplicatedNameException {

					
					Path p=Paths.get(s).resolve("testImportFile2.txt");//Host file system path of a non-existing file
					vfs2.importFile(p.toString(),"Root","/D2/");
				

	}*/
	
	/**
	 * TestImportFile3.
	 * When importing a file to a virtual disk, if its size is bigger than the amount of free space in the disk 
	 * 'SizeException' is thrown.
	 * (Importing a file of 41KB to the Virtual Disk "Root". This disk's free space is smaller than 1KB.)
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidInput
	 * @throws SizeException
	 * @throws DuplicatedNameException
	 */
	/*@Test (expected=SizeException.class)
	public void testImportFile3() throws FileNotFoundException, IOException, InvalidInput, SizeException, DuplicatedNameException  {
		
		
		Path p=Paths.get(s).resolve("testImportFile3.txt");//testImportFile3 size is 41KB. The virtual disk free space is smaller than 1KB (size)
		vfs2.importFile(p.toString(), "Root", "/");
	}
	*/
	/**
	 * testExportDirectory.
	 * Tests the main functionality of exportDirectory().
	 * (Exporting directory 'D2' that contains a file-'file4.txt'- and a directory-'D3'-.Checking the creation in the host file system.
	 * Deletion of the created items in the host file system when the method is finished)
	 
	 */
	@Test
	public void testExportDirectory() {
		File test=null;
		File test2=null;
		File test3=null;
		
		try{
			
			
			vfs2.exportDirectory(s, "Root", "/D2/");
			
			Path p1=Paths.get(s);
			Path p2=p1.resolve("D2");
			
			test=new File(p2.toString());
			assertTrue(test.isDirectory());
		 	
			Path p3=p2.resolve("file4.txt");
			test2=new File(p3.toString());
			assertTrue(test2.isFile());
			
			Path p4=p2.resolve("D3");
			test3=new File(p4.toString());
			assertTrue(test3.isDirectory());
		
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}finally{
			if(test3!=null){
				test3.delete();
			}
			if(test2!=null){
				test2.delete();
			}
			if(test!=null){
				test.delete();
			}
			
			
		}
	}
	/**
	 * testExportDirectory2.
	 * When we export a directory to a host system's location, if there's already a directory with the same name, 
	 * then DuplicatedNameException is thrown.
	 * @throws InvalidInput
	 * @throws DuplicatedNameException
	 * @throws IOException
	 */
	@Test (expected=DuplicatedNameException.class)
	public void testExportDirectory2() throws InvalidInput, DuplicatedNameException, IOException{
		
		vfs2.createDirectory("Root", "testExportDirectory2", "/");
		vfs2.exportDirectory(s, "Root", "/testExportDirectory2");
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
	
	
	/*@Test 
	
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
