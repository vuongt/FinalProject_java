package vfs;

import static org.junit.Assert.*;

import org.junit.Test;

public class FichierTest {

	@Test
	public void testGetAbsolutePath() throws DuplicatedNameException, InvalidInput {
		Fichier file11 = new Fichier("file11");
		VFS vfs1 = new VFS();
		VirtualDisk Root = new VirtualDisk("Root",1000);
		vfs1.getVirtualDisks().put("Root", Root);
		Directory D1 = new Directory("D1");
		Root.addDirectory(D1);
		D1.addFile(file11);
		assertEquals(file11.getAbsolutePath(),"/D1/file11");
		}

}
