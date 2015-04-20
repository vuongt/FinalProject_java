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
	

	@Before
	public void method(){
		try{
			
			//using commands tested in the first set of tests
			vfs2=new VFS();
			vfs2.createVirtualDisk("Root", 30000);
			vd2=vfs2.getVirtualDisks().get("Root");
			
			vfs2.createDirectory("Root", "D1", "/");
			vfs2.createDirectory("Root", "D2", "/D1/");
			vfs2.createFile("Root", "file2.txt", "/D1/");
			
			
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	/**
	 * testExportVfs
	 * testing the main functionality of exportVfs.
	 * (Exporting the virtual disk 'Root'using exportVfs()- which is based on serialization. Then deserializing 
	 * 'manually' the serialized object and comparing it to the first v
	 * irtual disk.
	 */
	@Test
	public void testExportVfs(){
		FileInputStream fileIn=null;
		ObjectInputStream in=null;
		Path p1=Paths.get(s);
		Path p2=p1.resolve("Root");
		
		try{
			
			vfs2.exportVfs("Root", s);
			
			
			fileIn=new FileInputStream(p2.toString());
			in= new ObjectInputStream(fileIn);
			
			VirtualDisk test=(VirtualDisk)in.readObject();
			
			assertEquals(vd2,test);
		}catch(Exception e){
			e.printStackTrace();
			assertFalse(true);
		}finally{
			
			if(fileIn!=null){
				try{
					fileIn.close();
				}catch(IOException e){}
			}
			
			if(in!=null){
				try{
					in.close();
				}catch(IOException e){}
			}
			
			
			//DELETING THE CREATED FILE CONTAINING THE VIRTUAL DISK
			File file=new File(p2.toString());
			file.delete();
			
		}
		
	}
	/**
	 * testExportVfs2
	 * When exporting a virtual disk 'vdName' to a location of the host file system, if a binary file named 'vdName'
	 * already exists in this location then DuplicatedNameException is thrown.
	 * @throws DuplicatedNameException
	 * @throws InvalidInput
	 * @throws IOException
	 */
	@Test (expected=DuplicatedNameException.class)
	public void testExportVfs2() throws DuplicatedNameException, InvalidInput, IOException{
		
		vfs2.createVirtualDisk("testExportVfs2", 1000000);
		vfs2.exportVfs("testExportVfs2", s);
	}
	
	/**
	 * testSave()
	 * Testing the main functionality of save(): updating the file where a virtual disk 'vdName' was last exported.
	 * (Creating a virtual disk and exporting it. Modifying the virtual disk and applying the method save(). Deserializing 
	 * the virtual disk and comparing it to the initial one)
	 */
	@Test
	public void testSave() {
		
		FileInputStream filein=null;
		ObjectInputStream in=null;
		File file=null;
		try{
			
			
		
			
			
			Path p1=Paths.get(s);
			Path p2=p1.resolve("RootBis");
			
			
			file=new File(p2.toString());
			if(file.exists())file.delete();
			
			vfs2.createVirtualDisk("RootBis", 1000);
			vfs2.exportVfs("RootBis", s);
			
			vfs2.createFile("RootBis", "file1.pdf", "/");
			vfs2.save("RootBis");
			
			filein= new FileInputStream(file);
			
			in=new ObjectInputStream(filein);
			
			VirtualDisk vd=vfs2.getVirtualDisks().get("RootBis");
			VirtualDisk test=(VirtualDisk)in.readObject();
			
			assertEquals(vd,test);
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
				
				file.delete();
			
			
				
			
			}
		}
	}
	
	/**
	 * testSave2()
	 * When saving a file, if it hasn't been exported before then InvalidInput is thrown.
	 * @throws DuplicatedNameException
	 * @throws InvalidInput
	 * @throws IOException
	 */
	@Test (expected=InvalidInput.class)
	public void testSave2() throws DuplicatedNameException, InvalidInput, IOException{
		
		vfs2.createVirtualDisk("RootBisBis", 1000);
		vfs2.save("RootBisBis");
		
	}
	/**
	 * TestExportFile.
	 * Test of the basic functionality of ExportFile 
	 * (Exporting /file1.txt using exportFile()-which was manually imported previously-.Then importing it 
	 * manually, creating a Fichier 'test' and comparing it to the Fichier 'file1' of the Virtual Disk 'Root'.
	 * The exported file is then deleted from the host file system.)
	 */
	@Test
	public void testExportFile() {
		File fileIn=null;
		FileInputStream in=null;
		File fileIn2=null;
		FileInputStream in2=null;
		try{

			//importing a file 'manually'
			
			Path p1=Paths.get(s);
			Path p2=p1.resolve("testImportFile.txt");
			
			fileIn=new File(p2.toString());
			in=new FileInputStream(fileIn);
			
			byte b;
			ArrayList<Byte> data=new ArrayList<Byte>();
			while((b=(byte)in.read())!=-1){
				
				data.add(b);
					
			}
			
			//storing it inside 'Root', name:file1.txt
			Fichier file1=new Fichier("file1.txt",data);
			vfs2.getVirtualDisks().get("Root").addFile(file1);
			
			//applying exportFile
			vfs2.exportFile(s,"Root","/file1.txt");
			
			
			//importing manually the file exported with exportFile
			Path p3=p1.resolve("file1.txt");
			
			
			fileIn2=new File(p3.toString());
			in2=new FileInputStream(fileIn2);
			
			byte b2;
			ArrayList<Byte> data2=new ArrayList<Byte>();
			while((b2=(byte)in2.read())!=-1){
				
				data2.add(b2);
					
			}
			
			//Creating a new Fichier with the expected values 
			Fichier test=new Fichier("file1.txt","/file1.txt",data2);
			
			
			//Comparing test and file1
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
			
			if(in2!=null){
				try{
					in2.close();
				}catch(IOException e){}
			}

			//DELETING THE EXPORTED FILE
			fileIn2.delete();
		}
		
		
	
	}
	
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
	@Test (expected= DuplicatedNameException.class)
	public void testExportFile2() throws InvalidInput, DuplicatedNameException, IOException {
					//Adding a file to export
					vfs2.createFile("Root", "testExportFile2.txt", "/");
					vfs2.exportFile(s,"Root","/testExportFile2.txt");//testExportFile2.txt exists already in this location of the host file system 
				

	}
	
	/**
	 * TestImportFile
	 * Test the basic functionality of import file (Importing the file 'testImportFile.txt'-which 
	 * existed already inside the folder VFSTest2 of the host file system. Comparing the attributes 
	 * of the Fichier 'testImportFile.txt'imported to the system to the expected ones: name, absolutePath 
	 * and data- by converting the content of the imported file to an string and comparing it to the 
	 * expected content: 'testImportFile: this is a test.'that  was saved in 'VFSTest2/testImportFile.txt'
	 *  when this file was created.)
	 */
	
	@Test
	public void testImportFile() {
		try{
		
			Path p=Paths.get(s).resolve("testImportFile.txt");
			
			vfs2.importFile(p.toString(),"Root","/");
			
			Fichier importedFile=vfs2.getVirtualDisks().get("Root").getFileMap().get("testImportFile.txt");
			
			assertEquals(importedFile.getName(),"testImportFile.txt");
			assertEquals(importedFile.getAbsolutePath(),"/testImportFile.txt");
			
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
		
	}
	
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
	@Test (expected=FileNotFoundException.class)
	public void testImportFile2() throws FileNotFoundException, IOException, InvalidInput, SizeException, DuplicatedNameException {

					
					Path p=Paths.get(s).resolve("testImportFile2.txt");//Host file system path of a non-existing file
					vfs2.importFile(p.toString(),"Root","/D2/");
				

	}
	
	/**
	 * TestImportFile3.
	 * When importing a file to a virtual disk, if its size is bigger than the amount of free space in the disk 
	 * 'SizeException' is thrown.
	 * (Importing a file of 41KB to the Virtual Disk "Root". This disk's free space is smaller or equal to 30KB (sizeMax))
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidInput
	 * @throws SizeException
	 * @throws DuplicatedNameException
	 */
	@Test (expected=SizeException.class)
	public void testImportFile3() throws FileNotFoundException, IOException, InvalidInput, SizeException, DuplicatedNameException  {
		
		
		long freeSpace=(vd2.getSizeMax()-vd2.getOccupiedSpace());
		
		Path p=Paths.get(s).resolve("testImportFile3.txt");
		long size=Files.size(p);
		
		
		assertTrue(size>freeSpace);//The file we want to import is bigger than the space available. 
		
		vfs2.importFile(p.toString(), "Root", "/");
	}
	
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
			
			
			vfs2.exportDirectory(s, "Root", "/D1/");
			
			Path p1=Paths.get(s);
			Path p2=p1.resolve("D1");
			
			test=new File(p2.toString());
			assertTrue(test.isDirectory());
		 	
			Path p3=p2.resolve("file2.txt");
			test2=new File(p3.toString());
			assertTrue(test2.isFile());
			
			Path p4=p2.resolve("D2");
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

	
	/**
	 * TestImportDirectory
	 * Test of the basic functionality of importDirectory.
	 * (Importing an existing directory using  importDirectory()(testImportDirectory, which contains the file 
	 * testImportDirectory(file)).Then, importing the file 'manually', creating a test Directory and a test Fichier and 
	 * comparing them to the imported ones).
	 */
	@Test
	public void testImportDirectory() {
		File fileIn=null;
		FileInputStream in=null;
		
		try{
		
		
			Path p1=Paths.get(s);
			Path p2=p1.resolve("testImportDirectory");
			
			
			vfs2.importDirectory(p2.toString(), "Root", "/");
			
			//importing the file manually
			
			
			Path p3=p2.resolve("testImportDirectory(file).txt");
			
			fileIn=new File(p3.toString());
			in=new FileInputStream(fileIn);
			
			byte b;
			ArrayList<Byte> data=new ArrayList<Byte>();
			while((b=(byte)in.read())!=-1){
				
				data.add(b);
					
			}
			
			//Creating test elements
			Fichier testFile=new Fichier("testImportDirectory(file).txt",data);
			Directory testDirectory=new Directory("testImportDirectory","/testImportDirectory/");
			testDirectory.addFile(testFile);
			
			Directory dir=vfs2.getVirtualDisks().get("Root").getDirectoryMap().get("testImportDirectory");
			Fichier file=dir.getFileMap().get("testImportDirectory(file).txt");
			
			//Comparing
			assertEquals(file,testFile);
			assertEquals(dir,testDirectory);
			
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
	
	/**
	 * TestImportDirectory2
	 * When importing a non-existing directory from a location of the host file system, DirectoryNotFoundException is thrown.
	 * @throws DirectoryNotFoundException
	 * @throws FileNotFoundException
	 * @throws InvalidInput
	 * @throws DuplicatedNameException
	 * @throws SizeException
	 * @throws IOException
	 */
	@Test (expected=DirectoryNotFoundException.class)
	public void testImportDirectory2() throws DirectoryNotFoundException, FileNotFoundException, InvalidInput, DuplicatedNameException, SizeException, IOException{
		Path p1=Paths.get(s);
		Path p2=p1.resolve("testImportDirectory2");
		vfs2.importDirectory(p2.toString(),"Root","/");
	}
	
	/**
	 * testFindFile
	 * Testing the main functionality of findFile(): given a 'filename', a 'vdName' disk and a 'vfsPath',
	 * the method returns an ArrayList<Fichier> of the files of name 'filename' inside the virtual disk 'vdname' starting 
	 * the research in 'vfsPath'.
	 */
	@Test 
	
	public void testFindFile(){
		try{
			
			vfs2.createFile("Root", "file3.jpg", "/");
			vfs2.createFile("Root", "file3.jpg", "/D1/");
			
			ArrayList<Fichier> list=vfs2.findFile("file3.jpg", "Root", "/");
			
			assertEquals(2,list.size());
			assertEquals("file3.jpg",list.get(0).getName());
			assertEquals("file3.jpg",list.get(1).getName());
			
			
		}catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
}
