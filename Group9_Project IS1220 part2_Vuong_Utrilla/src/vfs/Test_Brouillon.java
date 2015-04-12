package vfs;


import java.io.IOException;

import java.util.ArrayList;

public class Test_Brouillon {

	/**
	 * @param args
	 * @throws InvalidInput 
	 * @throws DuplicatedNameException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InvalidInput, DuplicatedNameException {
		VFS vfs2=new VFS();
		VirtualDisk vd2 = new VirtualDisk("Root",1000);
		vfs2.getVirtualDisks().put("Root",vd2 );
		Directory D1 = new Directory("D1");
		Directory D2 = new Directory("D2");
		Fichier file1 = new Fichier("file1.txt");
		Fichier file2 = new Fichier("file2.png");
		Fichier file3 = new Fichier("file3.jpg");
		vd2.addDirectory(D1);
		vd2.addDirectory(D2);
		D1.addDirectory("D11");
		vfs2.createFile("Root", "filetest", "/D1/D11/");
		vfs2.createFile("Root", "filetest", "/D2");
		vd2.addDirectory(D1);
		vd2.addDirectory(D2);
		vd2.addFile(file1);
		D1.addFile(file2);
		D1.addFile( file3);
		
		
		ArrayList<Fichier> a = vfs2.findFile("filetest", "Root");
		for (Fichier f : a){
			System.out.println(f.getAbsolutePath());
		}
		
	}
		
		
}


