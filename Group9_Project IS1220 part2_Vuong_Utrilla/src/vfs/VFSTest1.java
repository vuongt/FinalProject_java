package vfs;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class VFSTest1 {
	VFS vfs1;;
	@Before
	public void intialize() throws DuplicatedNameException, InvalidInput{
		try {
			vfs1 = new VFS();
			VirtualDisk Root = new VirtualDisk("Root",1000);
			vfs1.getVirtualDisks().put("Root", Root);
			Directory D1 = new Directory("D1");
			Directory D2 = new Directory("D2");
			Directory D11 = new Directory("D11");
			Fichier file1 = new Fichier("file1");
			Fichier file11 = new Fichier("file11");
			Root.getDirectoryMap().put("D1",D1);
			Root.getDirectoryMap().put("D2",D2);
			Root.getFileMap().put("file1",file1);
			D1.getDirectoryMap().put("D11", D11);
			D1.getFileMap().put("file11", file11);
		
		}
		catch (Exception e) {assertTrue(false);}
	}
	
	@Test
	public void testIsAbsolute() {
			try {
				String path1 = new String("/this/is/an/absolute/path/vfs/");
				String path2 = new String("../this/is/a/relative/path/");
				assertTrue(vfs1.isAbsolute(path1));
				assertFalse(vfs1.isAbsolute(path2));
			}
			catch (Exception e) {assertTrue(false);}
			
	}

	@Test
	public void testToAbsolutePath() {
		try {
			vfs1.changePosition("Root", "/D1/D11");
			String path1 = new String("/D1/D11");
			String path2 = new String("../../D2");
			String path3 = new String("././file11");
			Path expected1 = Paths.get("/D1/D11");
			Path expected2 = Paths.get("/D2");
			Path expected3 = Paths.get("/D1/D11/file11");
			assertTrue(vfs1.toAbsolutePath("Root",path1).equals(expected1));
			assertTrue(vfs1.toAbsolutePath("Root",path2).equals(expected2));
			assertTrue(vfs1.toAbsolutePath("Root",path3).equals(expected3));
		}
		catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);}
	}
	
	/*@Test(expected = InvalidInput.class)
	public final void whenTryToGoUpFromTheRootThenExceptionIsThrown(){
		vfs1.toAbsolutePath("Root","..");
	}*/

	@Test
	public void testGoPath() throws InvalidInput {
		try { 
			Path path = Paths.get("/D1/D11");
			Directory result = vfs1.goPath("Root",path,1);
			Directory expected = vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D1").getDirectoryMap().get("D11");
			assertTrue(expected.equals(result));
		}
		catch (Exception e) {assertTrue(false);}
	}

	@Test
	public void testCreateVirtualDisk()  {
		try{
		vfs1.createVirtualDisk("testdisk", 10000);
		assertTrue(vfs1.getVirtualDisks().containsKey("testdisk"));}
		catch (Exception e) {assertTrue(false);}
	}

	@Test
	public void testDeleteVirtualDisk() throws DuplicatedNameException, InvalidInput, IOException {
		try{
		vfs1.deleteVirtualDisk("Root");
		assertFalse(vfs1.getVirtualDisks().containsKey("Root"));}
		catch (Exception e) {assertTrue(false);}
	}
		

	@Test
	public void testCreateFile() throws InvalidInput {
		try {
			vfs1.createFile("Root","file","/D1/D11/");
			assertTrue(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D1").getDirectoryMap().get("D11").getFileMap().containsKey("file"));
		}
		catch (Exception e) {assertTrue(false);}
	}
	
	@Test
	public void testCreateDirectory() throws InvalidInput {
		try{vfs1.createDirectory("Root","D12","/D1/");
		assertTrue(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D1").getDirectoryMap().containsKey("D12"));}
		catch (Exception e) {assertTrue(false);}
	}
	
	@Test
	public void testDeleteFile() throws InvalidInput {
		try{vfs1.deleteFile("Root","/D1/file11");
		assertFalse(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D1").getFileMap().containsKey("file11"));}
		catch (Exception e) {assertTrue(false);}
	}

	@Test
	public void testDeleteDirectory() throws InvalidInput {
		try { vfs1.deleteDirectory("Root","/D1/D11");
		assertFalse(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D1").getDirectoryMap().containsKey("file11"));}
		catch (Exception e) {assertTrue(false);}
	}
	
	@Test
	public void testRenameFile() throws InvalidInput {
		try{
			vfs1.renameFile("Root","/D1/file11","file12");
			assertTrue(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D1").getFileMap().containsKey("file12"));
		}
		catch (Exception e) {assertTrue(false);}
	}

	@Test
	public void testRenameDirectory() {
		try{
			vfs1.renameDirectory("Root","/D1/D11","D13");
			assertTrue(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D1").getDirectoryMap().containsKey("D13"));
		}
		catch (Exception e) {assertTrue(false);}
	}
	
	
	@Test
	public void testShow() throws InvalidInput {
		try{
			ArrayList<ArrayList<String>> result = vfs1.show("Root", "", "/D1/");
			ArrayList<ArrayList<String>> expected = new ArrayList<ArrayList<String>>();
			ArrayList<String> list0 = new ArrayList<String>();
			ArrayList<String> list1 = new ArrayList<String>();
			list0.add("file11");
			list0.add("f");
			list1.add("D11");
			list1.add("d");
			expected.add(list0);
			expected.add(list1);
			assertTrue(result.equals(expected));
		}
		catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);}
	}

	@Test
	public void testCopy() throws InvalidInput {
		try { 
		vfs1.copy("Root","/D1/D11/","/D2/");
		assertTrue(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D2").getDirectoryMap().containsKey("D11"));
		}
		catch (Exception e) {assertTrue(false);}
	}
	
	@Test
	public void testMove() {
		try { 
			vfs1.move("Root","/D1/D11","D2");
			assertTrue(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D2").getDirectoryMap().containsKey("D11"));
			assertFalse(vfs1.getVirtualDisks().get("Root").getDirectoryMap().get("D1").getDirectoryMap().containsKey("D11"));}
		catch (Exception e) {assertTrue(false);}
	}
	
	@Test
	public void testChangePosition() {
		try { 
			vfs1.changePosition("Root", "/D1/D11/");			
			System.out.println(vfs1.getVirtualDisks().get("Root").getCurrentPosition());
			assertEquals("/D1/D11/",vfs1.getVirtualDisks().get("Root").getCurrentPosition());
		}
		catch (Exception e) {assertTrue(false);}
	}
}
