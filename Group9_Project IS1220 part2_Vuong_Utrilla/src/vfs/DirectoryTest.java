package vfs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DirectoryTest {
	
	Directory D1;

	@Before
	public void initialize() throws DuplicatedNameException,InvalidInput{
		try{
			D1 = new Directory("D1");
			Directory D11 = new Directory("D11");
			Fichier file11 = new Fichier("file11");
			D1.getDirectoryMap().put("D11", D11);
			D1.getFileMap().put("file11", file11);
		}
		catch(Exception e){assertTrue(false);}
	}
	
	@Test
	public void testAddFile() throws DuplicatedNameException {
		D1.addFile("file12");
		assertTrue(D1.getFileMap().containsKey("file12"));
	}
	//testAddFile
	@Test (expected = DuplicatedNameException.class)
	public void whenFileNameIsDuplicatedThenExceptionIsThrown() throws DuplicatedNameException{
		D1.addFile("file11");
	}
		
	
	@Test
	public void testAddFilebis() throws DuplicatedNameException {
		Fichier file12 = new Fichier("file12");
		D1.addFile(file12);
		assertTrue(D1.getFileMap().containsKey("file12"));
	}
	//testAddFilebis
	@Test (expected = DuplicatedNameException.class)
	public void whenFileNameIsDuplicatedThenExceptionIsThrownbis() throws DuplicatedNameException{
		Fichier file11bis = new Fichier("file11");
		D1.addFile(file11bis);
	}
	
	@Test 
	public void testAddDirectory() throws DuplicatedNameException{
		D1.addDirectory("D12");
		assertTrue(D1.getDirectoryMap().containsKey("D12"));
	}
	//testAddDirectory
	@Test (expected = DuplicatedNameException.class)
	public void whenDirectoryNameIsDuplicatedThenExceptionIsThrown() throws DuplicatedNameException{
		D1.addDirectory("D11");
	}
	
	@Test 
	public void testAddDirectorybis() throws DuplicatedNameException{
		Directory D12 = new Directory("D12");
		D1.addDirectory(D12);
		assertTrue(D1.getDirectoryMap().containsKey("D12"));
	}
	//testAddDirectory
	@Test (expected = DuplicatedNameException.class)
	public void whenDirectoryNameIsDuplicatedThenExceptionIsThrownbis() throws DuplicatedNameException{
		Directory D11bis = new Directory("D11");
		D1.addDirectory(D11bis);
	}
	
	@Test
	public void testGetAbsolutepath() throws InvalidInput, DuplicatedNameException{
		VFS vfs1 = new VFS();
		VirtualDisk Root = new VirtualDisk("Root",1000);
		vfs1.getVirtualDisks().put("Root", Root);
		Root.addDirectory(D1);
		Directory D12 = new Directory("D12");
		D1.addDirectory(D12);
		String expected = "/D1/D12/";
		assertEquals(D12.getAbsolutePath(),expected);
	}
	
	@Test
	public void testGetSize() throws InvalidInput, DuplicatedNameException{
		try { 
			VFS vfs1 = new VFS();
			VirtualDisk Root = new VirtualDisk("Root",1000000);
			vfs1.getVirtualDisks().put("Root", Root);
			Root.addDirectory(D1);
			Directory D12 = new Directory("D12");
			D1.addDirectory(D12);
			vfs1.importFile("VFSTest2/testImportFile.txt", "Root", "/D1/");
			vfs1.importFile("VFSTest2/testImportFile3.txt", "Root", "/D1/D12");
			long expected = D1.getFileMap().get("testImportFile.txt").getSize() +D12.getFileMap().get("testImportFile3.txt").getSize(); 
			assertEquals(D1.getSize(),expected);
			
		}
		catch (Exception e){
			e.printStackTrace();
			assertTrue(false);}
		
	}

}
