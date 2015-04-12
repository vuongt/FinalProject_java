package vfs;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class VFS {
	private HashMap<String,VirtualDisk> virtualDisks;


	public VFS(){
		super();
		this.virtualDisks = new HashMap<String, VirtualDisk>();
		
	}
	
	/**
	 * This method test if a path is absolute. 
	 * Note that the an absolute path in vfs is absolute if it starts with the root notation "/"
	 * @param path 
	 * @return true if path is absolute
	 */
	public boolean isAbsolute(String path){
		if (path.equals("")) return true;
		if (path.substring(0,1).equals("/")) return true;
		else return false;
	}
	
	/**
	 * this method use the current position in the virtual disk named vfsname to 
	 * convert a path (of type String) to an absolute path (of type Path)
	 * @param vfsName ; the name of the virtual disk
	 * @param path : the path to convert
	 * @return an absolute path of type Path
	 */
	public Path toAbsolutePath(String vfsName ,String path){
		Path absolutePath = null;
		if (isAbsolute(path)) absolutePath = Paths.get(path);
		else {
			Path currentPath = Paths.get(this.getVirtualDisks().get(vfsName).getCurrentPosition());
			if (path.substring(0,2).equals("./")){
				Path relativePath = Paths.get(path.substring(1));
				absolutePath = currentPath.resolve(relativePath);
			}
			if (path.substring(0,3).equals("../")){
				Path relativePath = Paths.get(path.substring(2));
				absolutePath = currentPath.resolve(relativePath);
			}
		}
		return absolutePath;
	}
	
	/**
	 *This method take the name of a virtual disk, a path and an integer n then return the n-th directory starting from the end of the path.
	 */
	public Directory goPath(String vfsname,Path path, int n)throws InvalidInput{
		if (!virtualDisks.containsKey(vfsname)) {throw new InvalidInput("no virtual disk found");}
		else if (path == null || path.toString().equals("")||path.toString().equals("/")||path.toString().equals("\\")) return this.virtualDisks.get(vfsname);
		else {
			Integer pathLength = new Integer(path.getNameCount());
			if (n > pathLength) return this.virtualDisks.get(vfsname);
			if (n < 1) {throw new InvalidInput("n must be smaller than the size of the path");}
			else{
				Directory destination = this.virtualDisks.get(vfsname);
				for (int i=0; i <= (pathLength-n); i++){
					String key = path.getName(i).toString();
					if (destination.getDirectoryMap().containsKey(key))
						{destination = destination.getDirectoryMap().get(key);}
					else { throw new InvalidInput("invalid path");}
				}
				return destination;
			}
		}
	}
	
	
	/**
	 * Create a new virtual disk
	 */
	public void createVirtualDisk(String name, long size) throws DuplicatedNameException, InvalidInput{
		if (virtualDisks.containsKey(name))
			throw new DuplicatedNameException();
		else this.virtualDisks.put(name,new VirtualDisk(name,size));
	}
	
	
	/**
	 * Delete a virtual disk
	 */
	public void deleteVirtualDisk(String name)throws InvalidInput, IOException{
		if (virtualDisks.containsKey(name)){
			this.virtualDisks.remove(name);}
			//delete the virtual disk file in the host file system
			/*for (String pathString : hostPath){
				Path path = Paths.get(pathString);
				Files.delete(path);
			}
		}*/
		else 
			throw new InvalidInput("There isn't a virtual disk with the given name");
		}
	
	/**
	 * Exporter a virtual disk
	 */
	public void exportVfs(String vDName,String hostPath) throws InvalidInput,IOException,DuplicatedNameException{
		
		if(!virtualDisks.containsKey(vDName)) throw new InvalidInput("None existing disk.");
		VirtualDisk vd=virtualDisks.get(vDName);
				
		//We create the complete hostPath containing the name of the virtual disk
		
		Path p1=Paths.get(hostPath);
		Path p2=Paths.get(vDName);
		Path completeHostPath=p1.resolve(p2);
		
		File file=new File(completeHostPath.toString());
		if(file.exists()) throw new DuplicatedNameException("A file with name "+vDName+" already exists in this location of the host file system");
		file.createNewFile();
		FileOutputStream fileOut=new FileOutputStream(file);
		ObjectOutputStream out=new ObjectOutputStream(fileOut);
		
		//Serialization
		out.writeObject(vd);
		out.close();
		fileOut.close();
		
		//Adding the host path to the list of host paths to which the vfs has been exported
		vd.getHostPath().add(completeHostPath.toString());
		
	
	}
	
	/**
	 * We save the state of a virtualdisk in the last hostpath where it was exported, erasing the previous contents of the file.
	 *  If the file where it was exported has been deleted, it creates a new one
	 * @param vfsname, the name of the virtualDisk 
	 * @throws InvalidInput
	 * @throws IOException
	 */
	public void save(String vfsname) throws InvalidInput,IOException{
		
		//We make sure the disk exists
		if(!virtualDisks.containsKey(vfsname)) throw new InvalidInput("None existing disk.");
		VirtualDisk vd=virtualDisks.get(vfsname);
		
		if (vd.getHostPath().equals(null)) throw new InvalidInput("you have to export the virtual disk before saving it");
		
		//We get the path of the file that needs to be updated
		String hostPath=vd.getHostPath().get(vd.getHostPath().size()-1);
		
		File fileToUpdate=new File(hostPath);
		
		//We check if the file to which the vfs was exported still exists. If it doesn't, we create a new one
		if(!fileToUpdate.exists()) fileToUpdate.createNewFile();
		
		FileOutputStream fileOut=new FileOutputStream(fileToUpdate);
		
		//We erase the possible previous contents
		fileOut.write((new String()).getBytes());
		
		//Serialization
		ObjectOutputStream out=new ObjectOutputStream(fileOut);
		out.writeObject(vd);
		out.close();
		fileOut.close();
		
	}
	
		
	/**
	 * create, delete, rename files and directories.
	 */
	public void createFile(String vfsName,String fileName,String vfsPath) throws InvalidInput {
		Path path = toAbsolutePath(vfsName,vfsPath);
		goPath(vfsName,path,1).addFile(fileName);
	}
	
	public void createDirectory(String vfsName, String directoryName,String vfsPath) throws InvalidInput{
		Path path = toAbsolutePath(vfsName,vfsPath);
		goPath(vfsName,path,1).addDirectory(directoryName);
	}
	
	public void deleteFile(String vfsName,String vfsPath) throws InvalidInput{
		Path path = toAbsolutePath(vfsName,vfsPath);
		goPath(vfsName,path,2).deleteFile(path.getFileName().toString());
	}
	
	public void deleteDirectory(String vfsName, String vfsPath) throws InvalidInput {
		Path path = toAbsolutePath(vfsName,vfsPath);
		goPath(vfsName,path,2).deleteDirectory(path.getFileName().toString());
	}
	public void renameFile(String vfsName, String vfsPath, String newName) throws InvalidInput {
		Path path = toAbsolutePath(vfsName,vfsPath);
		String oldName = path.getFileName().toString();
		Fichier temp = goPath(vfsName,path,2).getFileMap().get(oldName);
		if (! temp.equals(null)) {
			temp.setName(newName);
			goPath(vfsName,path,2).getFileMap().remove(oldName);
			goPath(vfsName,path,2).getFileMap().put(newName, temp);}
	}
/**
 * This method rename a directory
 * @param vfsName name of the virtual disk
 * @param vfsPath the path of the directory, the old name of the directory is at the end of the path
 * @param newName the new name of the directory
 * @throws InvalidInput
 */
	public void renameDirectory(String vfsName, String vfsPath, String newName) throws InvalidInput{
		Path path = toAbsolutePath(vfsName,vfsPath);
		String oldName = path.getFileName().toString();
		Directory temp = goPath(vfsName,path,2).getDirectoryMap().get(oldName);
		if (! temp.equals(null)) {
			temp.setName(newName);
			goPath(vfsName,path,2).getDirectoryMap().remove(oldName);
			goPath(vfsName,path,2).getDirectoryMap().put(newName, temp);
		}
	}
	
	
	/**
	 * Exports a file to an specific location the host file system
     * @param hostPath, the path in the host file system where the file has to be exported.
     * @param vDName, the name of the VirtualDisk.
     * @param vfsPathString, the path of the VFS, including the name of the file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidInput
	 * @throws DuplicatedNameException
	 */
	public void exportFile(String hostPath,String vDName, String vfsPath) throws FileNotFoundException,IOException,InvalidInput, DuplicatedNameException{
		
		if(!virtualDisks.containsKey(vDName)) throw new InvalidInput("None existing disk.");//The vfs		
		Path pVfs=toAbsolutePath(vDName,vfsPath);//Path in the vfs


		Directory dir=goPath(vDName,pVfs,2);//this is the directory which contains the file to export

		
		if(!(dir.getFileMap().containsKey(pVfs.getFileName().toString()))) throw new InvalidInput("Wrong path");
		Fichier fileToExport = dir.getFileMap().get(pVfs.getFileName().toString());
		
		//We create the hostPath containing the name of the file
		
		Path p1=Paths.get(hostPath);
		Path p2=Paths.get(pVfs.getFileName().toString());
		Path completeHostPath=p1.resolve(p2);
		
		//We create the File object that'll be linked to the new file in the host file system
		File exportedFile=new File(completeHostPath.toString());
		if(exportedFile.isFile()) throw new DuplicatedNameException(p2.toString()+" exits already in the specified location of the host file system.");//We check a file with the same name doesn't exist in the same location
		exportedFile.createNewFile();//We create the file in the host system
		
		//Creating the byte stream
		FileOutputStream out=new FileOutputStream(exportedFile);
		
		//Copying the content
		
		int i;
		if(fileToExport.equals(null)) {
			out.close();
			throw new InvalidInput("No file");
		}
		else{
		for(i=0;i<fileToExport.getSize();i++){
			
			out.write((fileToExport.getData().get(i)));
		}
		//Closing the stream
		out.close();
		}
	}
	
	/**
	 * Import a file from the host file system to an specific location of the VFS
	 * @param hostPath, the path of the host file system,including the name of the file.
	 * @param vDName, the name of the VirtualDisk.
	 * @param vfsPath, the path inside the VFS where it has to be imported.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidInput
	 * @throws SizeException
	 * @throws DuplicatedNameException
	 */
   public void importFile(String hostPath,String vDName, String vfsPath) throws FileNotFoundException,IOException,InvalidInput,SizeException,DuplicatedNameException{
		
		if(!virtualDisks.containsKey(vDName)) throw new InvalidInput("None existing disk.");//We look for the disk
		VirtualDisk vd=virtualDisks.get(vDName);
		
		File file=new File(hostPath); //We create a file object associated to the one given by the user
		FileInputStream in=new FileInputStream(file);//We create a byte stream. It will throw FileNotFoundException if the file doesn't exist
		
		Path pVfs=toAbsolutePath(vDName,vfsPath);
		
		Directory dir=goPath(vDName,pVfs,1);//We get the directory of the Vfs where the file goes
		
		Path pHost=Paths.get(hostPath);//The user enters an ABSOLUTE host path
		
		
		//The SIZE of the imported file
		long size=Files.size(pHost);
		if((vd.getSizeMax() - vd.getOccupiedSpace())< size) {
			in.close();
			throw new SizeException("Not enough free space in the virtual disk");
		}
		
		//The NAME of the imported file
		String fileName=pHost.getFileName().toString();
		if(dir.getFileMap().containsKey(fileName)) {
			in.close();
			throw new DuplicatedNameException(fileName+" exists already in the specified location of the virtual file system.");//we make sure the file doesn't exist already in the directory of the vfs
		}
		
		//The DATA of the file
		byte b;
		ArrayList<Byte> data=new ArrayList<Byte>();
		while((b=(byte)in.read())!=-1){
			data.add(b);
		}
		dir.addFile(new Fichier(fileName,size,"",data));//We add the file into the vfs
		in.close();
	}
   
   
 /**
  *Export a directory and all its content into an specific location of the host file system.
  * @param hostPath, the path in the host file system where the directory has to be exported.
  * @param vDName, the name of the VirtualDisk.
  * @param vfsPathString, the path of the VFS, including the name of the directory.
  * @throws FileNotFoundException
  * @throws IOException
  * @throws InvalidInput
  */
   public void exportDirectory(String hostPath,String vDName, String vfsPathString) throws FileNotFoundException,IOException,InvalidInput,DuplicatedNameException{
	   
		if(!virtualDisks.containsKey(vDName)) throw new InvalidInput("None existing disk.");//We look for the disk
		
		
		//We create the vfsPath
		Path vfsPath=toAbsolutePath(vDName,vfsPathString);

		
		//We get the directory to be exported
		Directory dirToExport=goPath(vDName,vfsPath,1);
;
		
		//We create the hostpath including the directory to export
		Path p1=Paths.get(hostPath);
		Path p2=Paths.get(dirToExport.getName());
		Path completePath=p1.resolve(p2);

		//We create the new directory in the host path.
		
		File dir=new File(completePath.toString());
		if(dir.isDirectory()) throw new DuplicatedNameException();//We check the directory doesn't exist already in this location
		dir.mkdir();

		//We export the files contained in the directory
		for(Fichier f:dirToExport.getFileMap().values()){
			
			//Creating the path of the file in the vfs
			Path filePath=vfsPath.resolve(Paths.get(f.getName()));
			exportFile(completePath.toString(),vDName, filePath.toString());
			
			
		}
		
		//We export the directories contained in the directory
		
		for(Directory d:dirToExport.getDirectoryMap().values()){
			
			//Creating the path of the directory in the vfs
			Path dirPath=vfsPath.resolve(Paths.get(d.getName()));
			exportDirectory(completePath.toString(),vDName,dirPath.toString());
			
		}
	   
   }
   /**
    * Imports a directory and all its content to an specific location of the VFS, from the host file system.
	 * @param hostPath, the path of the host file system, including the name of the directory
	 * @param vDName, the name of the VirtualDisk
	 * @param vfsPath, the path inside the VFS where it has to be imported
    * @throws InvalidInput
    * @throws DuplicatedNameException
    * @throws DirectoryNotFoundException
    * @throws SizeException
    * @throws FileNotFoundException
    * @throws IOException
    */
   public void importDirectory(String hostPathString,String vDName, String vfsPathString) throws InvalidInput,DuplicatedNameException,DirectoryNotFoundException,SizeException,FileNotFoundException,IOException{
	   if(!virtualDisks.containsKey(vDName)) throw new InvalidInput("None existing disk.");//We look for the disk
	   
	   //We get directory of the vfs into which the directory will be imported
	   Path vfsPath=toAbsolutePath(vDName,vfsPathString);
	   Directory dir=goPath(vDName,vfsPath,1);
	   
	   Path hostPath=Paths.get(hostPathString);
	   if(dir.getDirectoryMap().containsKey(hostPath.getFileName().toString())) throw new DuplicatedNameException();//We make sure the directory doesn't contain another with the same name
	   
	   File dirToImport=new File(hostPathString);
	   if(!dirToImport.isDirectory()) throw new DirectoryNotFoundException("The specified directory  doesn't exist in the host file system");//We make sure the directory exists
	   
	   //We create the imported directory in the vfs
	   Directory importedDirectory=new Directory(hostPath.getFileName().toString());
	   //We add it to the vfs
	   dir.addDirectory(importedDirectory);
	
	   //We import the directories and files contained in the directory
	   int i;
	   for(i=0;i<dirToImport.list().length;i++){
		   
		   //We create the  new host path of the new item to be imported
		   
		   Path p1=Paths.get(hostPathString);
		   Path p2=Paths.get(dirToImport.list()[i]);
		   Path newHostPath=p1.resolve(p2);
		   
		 //We create the new vfs path into which the elements will be imported
		   Path p3=Paths.get(importedDirectory.getName());
		   Path newVfsPath=vfsPath.resolve(p3);
		   
		   //We check whether the item is a file or a directory
		   File item=new File(newHostPath.toString());
		   
		   
		   
		   if(item.isFile()){
			   
			   importFile(newHostPath.toString(),vDName,newVfsPath.toString());
			   
		   }else if(item.isDirectory()){
			   
			   importDirectory(newHostPath.toString(),vDName,newVfsPath.toString());
			   
		   }
	   }
   }
	
	
	/**
	 * to copy within the virtual disk named vfsName, the content of a file /directory whose absolute name is sourcePath 
	 * into a file/directory whose absolute name is targetPath
	 */
	public void copy(String vfsName, String sourcePath, String targetPath) throws InvalidInput{
		Directory source = goPath(vfsName,Paths.get(sourcePath),2);
		Directory target = goPath(vfsName, Paths.get(targetPath),1);
		String name = Paths.get(sourcePath).getFileName().toString();
		target.addDirectory(source.getDirectoryMap().get(name));
		target.addFile(source.getFileMap().get(name));
	}
	
	
	/**
	 * to move a file/directory from oldpath to newpath
	 */
	public void move(String vfsname, String oldpath, String newpath) throws InvalidInput{
		Directory source = goPath(vfsname,Paths.get(oldpath),2);
		Directory target = goPath(vfsname, Paths.get(newpath),1);
		String name = Paths.get(oldpath).getFileName().toString();
		target.addDirectory(source.getDirectoryMap().get(name));
		target.addFile(source.getFileMap().get(name));
		source.getDirectoryMap().remove(name);
		source.getFileMap().remove(name);
	}
	/**
	 * This method change the currentPosition to a new position which is defined by path
	 */
	public void changePosition(String vfsname, String path){
		Path newPosition = toAbsolutePath(vfsname,path);
		this.getVirtualDisks().get(vfsname).setCurrentPosition(newPosition.toString());
		
	}
	/**
	 * list the information concerning files and directories contained in absolute position corresponding to vfsPath
	 * @param vfsname
	 * @param arg
	 * @param vfsPath
	 * @throws InvalidInput
	 */
	public ArrayList<String> show(String vfsname, String arg, String vfsPath) throws InvalidInput{
		Path path;
		ArrayList<String> result = new ArrayList<String>();
		if (vfsPath == "") path = Paths.get(this.getVirtualDisks().get("vfsname").getCurrentPosition());
		else {
		path = Paths.get(vfsPath);
		}
		Directory currentDirectory = goPath(vfsname,path,1);
		if (arg == ""){
			for (String name : currentDirectory.getFileMap().keySet())
				result.add(name + "    f");
			for (String name : currentDirectory.getDirectoryMap().keySet())
				result.add(name + "    d");
		}
		if (arg == "-l"){
			for ( Fichier file : currentDirectory.getFileMap().values())
				result.add(file.getName() + "     " + file.getSize() + "    f");
			for (Directory d : currentDirectory.getDirectoryMap().values())
				result.add(d.getName() + "     " + d.getSize() + "    d");
		}
		return result;
	}
	
	
	/**
	 * Looks for all the files inside a VFS with a given name, starting from an specified location in the virtualDisk
	 * @param nameFile, the name of the file
	 * @param vDName, the name of the VirtualDisk
	 * @param vfsPath, the path where the search has to start.
	 * @return the list of found files (Fichier), null if none where found
	 * @throws InvalidInput
	 */
	public ArrayList<Fichier> findFile(String nameFile,String vDName,String vfsPath) throws InvalidInput{
		ArrayList<Directory> visited = new ArrayList<Directory>();
		Stack<Directory> stack=new Stack<Directory>();
		Directory current;
		ArrayList<Fichier> foundFiles = new ArrayList<Fichier>();
		boolean test;
		
		Path p=toAbsolutePath(vDName,vfsPath);
		Directory dir = goPath(vDName,p,1);//Throws exception if the path is wrong
	
	
		visited.add(dir);
		stack.push(dir);
		while(!stack.empty()){

			current=stack.peek();

			test=false;
			
			for(Directory d:current.getDirectoryMap().values()){				
				if(!(visited.contains(d))){
					test=true;
					stack.push(d);
					visited.add(d);
					break;
				}
			}
			if(!(test)){
				Directory dos=stack.pop();
				if(dos.getFileMap().containsKey(nameFile)){
					foundFiles.add(dos.getFileMap().get(nameFile));
				}
			}
		
	}
		if (foundFiles.isEmpty()) return null;
		else{return foundFiles;}
	}
	/**
	 * Looks for all the files inside a VFS with a given name, starting from the root.
	 * @param nameFile, the name of the file
	 * @param vDName, the name of the VirtualDisk
	 * @return the list of found files (Fichier), null if none where found
	 * @throws InvalidInput
	 */
	public ArrayList<Fichier> findFile(String nameFile,String vDName) throws InvalidInput{
		
		if(!virtualDisks.containsKey(vDName)) {
			throw new InvalidInput("None existing disk.");//We check whether the disk exists
		}
		return findFile(nameFile,vDName,"/");
	}


	public HashMap<String, VirtualDisk> getVirtualDisks() {
		return virtualDisks;
	}

	public void setVirtualDisks(HashMap<String, VirtualDisk> virtualDisks) {
		this.virtualDisks = virtualDisks;
	}
	
}