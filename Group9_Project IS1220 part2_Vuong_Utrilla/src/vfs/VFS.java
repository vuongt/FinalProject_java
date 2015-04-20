package vfs;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

		if (path.substring(0,1).equals("/")) {
			return true;
		}
		
		else return false;
	}
	
	/**
	 * Use the current position in the virtual disk named vfsname to 
	 * convert a path (of type String) to an absolute path (of type Path)
	 * @param vfsName ; the name of the virtual disk
	 * @param path : the path to convert
	 * @return an absolute path of type Path
	 * @throws InvalidInput
	 */
	public Path toAbsolutePath(String vfsName ,String path) throws InvalidInput{
		Path absolutePath = null;
		
		if (isAbsolute(path)) absolutePath = Paths.get(path);//If it's already absolute,we simply return it
		else {
			/* handle relative paths such as : 
			 * .
			 * ..
			 * ./london
			 * ../london
			 * ./././london
			 * ../../london 
			 * and so on...
			 * it can't resolve path such as london/../picture or london/./picture
			 */
			/*
			 * ATTENTION: About the method getParent: 
			 * it will return null if the path don't have a parent, (it means that if the path is root "/")
			 * if the path is "/Dir1", it will return "/"
			 * instead of verifying if the path is root, i will check if its parent is null
			 * I do this to fix the NullPointerException
			 */
			Path currentPath = Paths.get(this.getVirtualDisks().get(vfsName).getCurrentPosition());
				
				if (path.equals(".")){
					absolutePath = currentPath;
	
				}
				else if(path.equals("..")){
					if (currentPath.getParent() == null){
						//The current path is root
						throw new InvalidInput("Can not go up further. Already in the root.");
					}
					else
						absolutePath = currentPath.getParent();
				
				}
				else {
					String pathNext = path;
					//I use startsWith in condition of if to avoid IndexOutOfRangeException, and the expression is also shorter
					//if pathNext is still relative, we restart the process with pathNext
					while (pathNext.startsWith(".")){ 
						if(pathNext.startsWith("./")){
							//currentPath doesn't change
							pathNext = pathNext.substring(2);
							
						}
						else if(pathNext.startsWith("../")){
							if (currentPath.getParent() == null){
								//The current path is root
								throw new InvalidInput("Can not go up further. Already in the root.");
							}
							else{
								currentPath = currentPath.getParent();//we go up on currentPath
								pathNext = pathNext.substring(3);
							}
						}
					}
					//at this point, pathNext is such as : London/picture/pic1 and we have done all the necessary change to currentPath
					absolutePath = currentPath.resolve(pathNext);
				}
				
			}
		return absolutePath;
	}
	
	/**
	 * This method check if a path is correct ( if all the directories and files exist in the virtual disk named vdName)
	 * @param vdName : name of the virtual disk we are looking at
	 * @param vfsPath : path to check
	 * @return : true if vfsPath is the path of a file
	 * false if vfsPath is the path of a directory
	 * if there are a file and a directory with the same name, it returns false ( we will take the path of the directory)
	 * @throws InvalidInput 
	 */
	public boolean checkPath(String vdName, String vfsPath) throws InvalidInput{
		boolean isFile;
		
		// We check if the virtual disk named vdName exists
		if(!this.getVirtualDisks().containsKey(vdName))
			throw new InvalidInput("None existing virtual disk.");
		
		else{
			Path path = this.toAbsolutePath(vdName,vfsPath);
			int length=path.getNameCount();
			
			Directory current= this.getVirtualDisks().get(vdName);
			
			if(length == 0) // vfspath is the path of the root
				isFile= false;
			
			else {

					if(length == 1){;}
					// the directory current is still the virtual disk 
					else {//The path length is bigger than 2			
						for(int i=0;i<=(length-2);i++){
							String next = path.getName(i).toString();
							if(!(current.getDirectoryMap().containsKey(next))){
								throw new InvalidInput("Invalid vfs path.");
							}
							current=current.getDirectoryMap().get(next);
	 					}

	 				}
					String finalName = path.getFileName().toString();
					if (current.getDirectoryMap().containsKey(finalName)) isFile= false;
					else if (current.getFileMap().containsKey(finalName)) isFile= true;
					else throw new InvalidInput("Invalid vfs path.");

				}

			}
		return isFile;
	}

	/**
	 * This method is used to navigate in a virtual disk using a path.
	 * @param vfsname : name of the virtual disk
	 * @param path : a given path of type Path
	 * @param n : an integer positive
	 * @return the directory at the n-th position counting from the end of the path. If n is  bigger than the size of the path, then this method return root
	 * @throws InvalidInput
	 */
	public Directory goPath(String vdName,Path path, int n)throws InvalidInput{
		int length=path.getNameCount();
		
		
		
		//The path contains just a root.
		if(length==0){
			if(n!=1){
				throw new InvalidInput("Invalid position.");
			}
			return this.getVirtualDisks().get(vdName);
	
		}
		else{//The path is longer than the root.
			if(n<=0||n>length+1){
				throw new InvalidInput("Invalid position");
			}
			if(n==length+1){//The directory we search is the root
				return  this.getVirtualDisks().get(vdName);
			}
			else{//The directory we search is a child of the root				
				Directory current=this.getVirtualDisks().get(vdName);
				for(int i=0;i<=(length-n);i++){
					String next =path.getName(i).toString();				
					current=current.getDirectoryMap().get(next);
				}
				return current;
			}
		}
	}
	
	/**
	 * Create a new virtual disk
	 * @param name
	 * @param size : the fixed size of the virtual disk
	 * @throws DuplicatedNameException : when there is already a virtual disk named "name"
	 * @throws InvalidInput
	 */
	public void createVirtualDisk(String name, long size) throws DuplicatedNameException, InvalidInput{
		if (virtualDisks.containsKey(name))
			throw new DuplicatedNameException("A virtual disk of name '"+name+"' already exists.");
		else this.virtualDisks.put(name,new VirtualDisk(name,size));
	}
	
	
	/**
	 * Delete a virtual disk named "name" from the vfs core
	 * @param name
	 
	 */
	//@throws IOException 
	public void deleteVirtualDisk(String name){//IOException
			this.virtualDisks.remove(name);
			//delete the virtual disk file in the host file system
			/*for (String pathString : hostPath){
				Path path = Paths.get(pathString);
				Files.delete(path);
			}
		}*/
		}
	
	
	/**
	 * Export a virtual disk to the host file system
	 * @param vDName : name of the virtual disk to export
	 * @param hostPath : path to the directory in the host file system where we will export the virtual disk
	 * @throws IOException
	 * @throws DuplicatedNameException
	 */
	public void exportVfs(String vDName,String hostPath) throws IOException,DuplicatedNameException{
		FileOutputStream fileOut=null;
		ObjectOutputStream out=null;
		
		VirtualDisk vd=virtualDisks.get(vDName);
		
		//We create the complete hostPath containing the name of the virtual disk
		Path p1=Paths.get(hostPath);
		Path completeHostPath=p1.resolve(vDName);
		
		try{
		
			File file=new File(completeHostPath.toString());//The file where we'll export the VD
			boolean created=file.createNewFile();
			//We check a file with the same name doesn't exist already in the host file system
			System.out.println(created);
			if(!created) throw new DuplicatedNameException("A file with name "+vDName+" already exists in this location of the host file system");
			
			
			
			//Adding the host path to the list of host paths to which the vfs has been exported
			vd.getHostPath().add(completeHostPath.toString());
			
			fileOut=new FileOutputStream(file);
			out=new ObjectOutputStream(fileOut);
			
			//Serialization
			out.writeObject(vd);
			
			
			
			
		}catch(IOException e){
			//Removing the host path from the list of host paths to which the vfs has been exported
			vd.getHostPath().remove(vd.getHostPath().size()-1);
			throw new IOException();
		}finally{
			if(out!=null){
				try{
					out.close();
				}catch(IOException e){
					
				}
			}
			if(fileOut!=null){
				try{
					fileOut.close();
				}catch(IOException e){
					
				}
			}
			
		}
	}
	
	
	/**
	 * We save the state of a virtualdisk in the last hostpath where it was exported, erasing the previous contents of the file.
	 *  If the file where it was exported has been deleted, it creates a new one
	 * @param vfsname, the name of the virtualDisk 
	 * @throws InvalidInput
	 * @throws IOException
	 */
	public void save(String vfsname) throws InvalidInput,IOException{
		FileOutputStream fileOut=null;
		ObjectOutputStream out=null;
		
		try{
			//We make sure the disk exists
			VirtualDisk vd=virtualDisks.get(vfsname);
			
			if (vd.getHostPath().isEmpty()) throw new InvalidInput("You have to export the virtual disk before saving it");
			
			//We get the path of the file that needs to be updated
			String hostPath=vd.getHostPath().get(vd.getHostPath().size()-1);
			
			File fileToUpdate=new File(hostPath);
			
			//We check if the file to which the vfs was exported still exists. If it doesn't, we create a new one.
			if(!fileToUpdate.exists()) fileToUpdate.createNewFile();
			
			fileOut=new FileOutputStream(fileToUpdate);
			
			//We erase the possible previous contents
			fileOut.write((new String()).getBytes());
			
			//Serialization
			out=new ObjectOutputStream(fileOut);
			out.writeObject(vd);
			
		}catch(IOException e){
			throw new IOException();
		}finally{
			
			if(out!=null){
				try{
					out.close();
				}catch(IOException e){}
			}
			
			if(fileOut!=null){
				try{
					fileOut.close();
				}catch(IOException e){}
			}
				
		}
		
		
	}
	
		
	/**
	 * Create a file 
	 * @param vfsName : name of the virtual disk where the file is stored
	 * @param fileName : name  of the file
	 * @param vfsPath : path(absolute or not) of the file
	 * @throws InvalidInput
	 */
	public void createFile(String vfsName,String fileName,String vfsPath) throws InvalidInput,DuplicatedNameException {
		Path path = toAbsolutePath(vfsName,vfsPath);
		goPath(vfsName,path,1).addFile(fileName);
	}
	
	/**
	 * Create a directory
	 * @param vfsName : name of the virtual disk where the directory is created
	 * @param directoryName : name of the directory
	 * @param vfsPath : path ( absolute or not) of the directory
	 * @throws InvalidInput
	 * @throws DuplicatedNameException
	 */
	public void createDirectory(String vfsName, String directoryName,String vfsPath) throws InvalidInput,DuplicatedNameException{
		Path path = toAbsolutePath(vfsName,vfsPath);
		goPath(vfsName,path,1).addDirectory(directoryName);
	}
	
	/**
	 * delete a file 
	 * @param vfsName: name of the virtual disk
	 * @param vfsPath: path of the file to delete ( which contains the name of the file at the end)
	 * @throws InvalidInput
	 */
	public void deleteFile(String vfsName,String vfsPath) throws InvalidInput{
		Path path = toAbsolutePath(vfsName,vfsPath);
		goPath(vfsName,path,2).deleteFile(path.getFileName().toString());
	}
	
	/**
	 * Delete a directory
	 * @param vfsName : name of the virtual disk
	 * @param vfsPath : path of the directory to delete ( which contains the name of the directory at the end)
	 * @throws InvalidInput
	 */
	public void deleteDirectory(String vfsName, String vfsPath) throws InvalidInput {
		Path path = toAbsolutePath(vfsName,vfsPath);
		goPath(vfsName,path,2).deleteDirectory(path.getFileName().toString());
	}
	
	/**
	 * Rename a file
	 * @param vfsName: name of the virtual disk
	 * @param vfsPath: path of the file to rename (which contains the old name at the end)
	 * @param newName: new name of the file
	 * @throws InvalidInput
	 */
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
     * @param vfsPath, the path of the VFS, including the name of the file.
	 * @throws IOException
	 * @throws InvalidInput
	 * @throws DuplicatedNameException
	 */

	public void exportFile(String hostPath,String vDName, String vfsPath) throws IOException,InvalidInput, DuplicatedNameException{

		FileOutputStream out=null;
		try{			
			Path pVfs=toAbsolutePath(vDName,vfsPath);//Path in the vfs
			Directory dir=goPath(vDName,pVfs,2);//this is the directory which contains the file to export
	
			Fichier fileToExport = dir.getFileMap().get(pVfs.getFileName().toString());
			
			//We create the hostPath containing the name of the file
			
			Path p1=Paths.get(hostPath);
			Path completeHostPath=p1.resolve(pVfs.getFileName());
			
			//We create the File object that'll be linked to the new file in the host file system
			File exportedFile=new File(completeHostPath.toString());
			
			
			boolean created=exportedFile.createNewFile();//We create the file in the host system
			//We check a file with the same name doesn't exist already in the host file system (createNewFile returns false in this case)
			if(!created) throw new DuplicatedNameException(pVfs.getFileName().toString()+" exits already in the specified location of the host file system.");//We check a file with the same name doesn't exist in the same location
			//Creating the byte stream
			out=new FileOutputStream(exportedFile);
			
			//Copying the content
			
			int i;//NOW IT'S IMPOSSIBLE FOR IT TO BE NULL SINCE WE DO A CHECKPATH BEFORE...IF WE ERASE IT THE CODE GIVES NO PROBLEM
			/*if(fileToExport.equals(null)) {
				out.close();
				throw new InvalidInput("No file");*/
			//}else{
				for(i=0;i<fileToExport.getSize();i++){
					out.write((fileToExport.getData().get(i)));
				//}
			}
		}catch(IOException e){
			throw new IOException();
		}finally{
			if(out!=null){
				try{
					out.close();
				}catch(IOException e){}
			}
			
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
	   FileInputStream in=null;
	   try{
		   
			VirtualDisk vd=virtualDisks.get(vDName);
			
			File file=new File(hostPath); //We create a file object associated to the one given by the user
			in=new FileInputStream(file);//We create a byte stream. It will throw FileNotFoundException if the file doesn't exist
			
			Path pVfs=toAbsolutePath(vDName,vfsPath);
			
			Directory dir=goPath(vDName,pVfs,1);//We get the directory of the virtual disk where the file goes
			
			Path pHost=Paths.get(hostPath);//The user enters an ABSOLUTE host path
			
			
			//The SIZE of the imported file
			long size=Files.size(pHost);
			
			if((vd.getSizeMax() - vd.getOccupiedSpace())< size) {
				in.close();
				throw new SizeException("Not enough free space in the virtual disk.");
			}
			
			//The NAME of the imported file
			String fileName=pHost.getFileName().toString();
			
			//The DATA of the file
			byte b;
			ArrayList<Byte> data=new ArrayList<Byte>();
			while((b=(byte)in.read())!=-1){
				data.add(b);
			}
			
			dir.addFile(new Fichier(fileName,data));//We add the file into the vfs
			
	   }catch(FileNotFoundException e){//to separate FileNotFound from other IOExceptions
		   
		   throw new FileNotFoundException("The file "+Paths.get(hostPath).getFileName().toString()+" doesn't exist in the host file system. Unable to import.");
	   }catch(IOException e){
		   throw new IOException();
	   }finally{
		   if(in!=null){
			  try{
				  in.close();
			  }catch(IOException e){}
		   }
		
	   }
	}
   
   
	 /**
	  *Export a directory and all its content into an specific location of the host file system.
	  * @param hostPath, the path in the host file system where the directory has to be exported.
	  * @param vDName, the name of the VirtualDisk.
	  * @param vfsPathString, the path of the VFS, including the name of the directory.
	  * @throws IOException
	  * @throws InvalidInput
	  */

   public void exportDirectory(String hostPath,String vDName, String vfsPathString) throws IOException,InvalidInput,DuplicatedNameException{

	   
		//if(!virtualDisks.containsKey(vDName)) throw new InvalidInput("None existing disk.");//We look for the disk
		
		
		//We create the vfsPath
		Path vfsPath=toAbsolutePath(vDName,vfsPathString);
		
		//We get the directory to be exported
		Directory dirToExport=goPath(vDName,vfsPath,1);
		
		
		//We create the hostpath including the directory to export
		Path p1=Paths.get(hostPath);
		Path completePath=p1.resolve(dirToExport.getName());

		//We create the new directory in the host path.
		
		File dir=new File(completePath.toString());
		if(dir.isDirectory()) throw new DuplicatedNameException("A directory with name "+dirToExport.getName()+" already exists in this location of the host file system");//We check the directory doesn't exist already in this location
		dir.mkdir();

		//We export the files contained in the directory
		for(Fichier f:dirToExport.getFileMap().values()){
			
			exportFile(completePath.toString(),vDName, vfsPathString+"/"+f.getName());
			
			
		}
		
		//We export the directories contained in the directory
		
		for(Directory d:dirToExport.getDirectoryMap().values()){
			
			exportDirectory(completePath.toString(),vDName,vfsPathString+"/"+d.getName());
			
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

	   //if(!virtualDisks.containsKey(vDName)) throw new InvalidInput("None existing disk.");//We look for the disk
	   
	   //We get directory of the vfs into which the directory will be imported
	   Path vfsPath=toAbsolutePath(vDName,vfsPathString);
	   Directory dir=goPath(vDName,vfsPath,1);
	   
	   Path hostPath=Paths.get(hostPathString);
	   
	   File dirToImport=new File(hostPathString);

	   if(!dirToImport.isDirectory()) throw new DirectoryNotFoundException("The directory "+hostPath.getFileName().toString() +" doesn't exist in the host file system");//We make sure the directory exists

	   
	   //We create the imported directory in the virtual disk
	   Directory importedDirectory=new Directory(hostPath.getFileName().toString());
	   //We add it to the VD
	   dir.addDirectory(importedDirectory);
	
	   //We import the directories and files contained in the directory
	   int i;
	   for(i=0;i<dirToImport.list().length;i++){
		   
		   //We create the  new host path of the new item to be imported
		   
		   Path p1=Paths.get(hostPathString);
		   Path newHostPath=p1.resolve(dirToImport.list()[i]);
		   
		 //We create the new vfs path into which the elements will be imported
		   String newVfsPath=vfsPathString + importedDirectory.getName();
		   
		   //We check whether the item is a file or a directory
		   File item=new File(newHostPath.toString());
		   
		   if(item.isFile())			   
			   importFile(newHostPath.toString(),vDName,newVfsPath);
			   
		   else if(item.isDirectory())			   
			   importDirectory(newHostPath.toString(),vDName,newVfsPath);
			   
	   }
   }
	/**
	 * to copy within the virtual disk named vfsName, the content of a file /directory whose absolute name is sourcePath 
	 * into a file/directory whose absolute name is targetPath
	 * @param vfsName : name of the virtual disk
	 * @param sourcePath : the path of the file/directory to copy, which contains the name of the file/directory at the end
	 * @param targetPath : the path of the DIRECTORY where the file/directory to copy is put.
	 * @throws InvalidInput
	 * @throws DuplicatedNameException 
	 */
	public void copy(String vfsName, String sourcePath, String targetPath) throws InvalidInput, DuplicatedNameException{
		Directory source = goPath(vfsName,Paths.get(sourcePath),2);
		Directory target = goPath(vfsName, Paths.get(targetPath),1);
		String name = Paths.get(sourcePath).getFileName().toString();
		if (source.getDirectoryMap().containsKey(name))
			target.addDirectory(source.getDirectoryMap().get(name));
		if (source.getFileMap().containsKey(name))
			target.addFile(source.getFileMap().get(name));
		//else throw new InvalidInput("The file/directory you want to copy doesn't exist");
	}
	
	
	/**
	 * To move a file/directory from oldpath to newpath
	 * @param vfsname : name of the virtual disk
	 * @param oldpath : the old address of the file/directory
	 * @param newpath : the new address of the file/directory
	 * @throws InvalidInput
	 * @throws DuplicatedNameException 
	 */
	public void move(String vfsname, String oldpath, String newpath) throws InvalidInput, DuplicatedNameException{
		Directory source = goPath(vfsname,Paths.get(oldpath),2);
		Directory target = goPath(vfsname, Paths.get(newpath),1);
		String name = Paths.get(oldpath).getFileName().toString();
		// can we exploit checkPath here ? 
		if (source.getDirectoryMap().containsKey(name)){
			target.addDirectory(source.getDirectoryMap().get(name));
			source.getDirectoryMap().remove(name);
		}
		if (source.getFileMap().containsKey(name)) {
			target.addFile(source.getFileMap().get(name));
			source.getFileMap().remove(name);
		}
	}
	
	/**
	 * This method change the currentPosition of a virtual disk to a new position
	 * @param vfsname : name of the virtual disk whose currentPosition is changed
	 * @param path : the new currentPosition
	 */
	//* @throws InvalidInput 
	public void changePosition(String vdName, String path){//throws InvalidInput
		this.getVirtualDisks().get(vdName).setCurrentPosition(path);
		/*Path newPosition = toAbsolutePath(vdName,path);
		int length=newPosition.getNameCount();
		
		//We check if the newPosition path is correct
		if(length==0){//new position is the root
			if(!this.getVirtualDisks().containsKey(vdName)){
				throw new InvalidInput("None existing virtual disk.");
			}
		}else{//new position is a bigger path
			/*if(!this.getVirtualDisks().containsKey(vdName)){
				throw new InvalidInput("None existing virtual disk.");
			}
			Directory destination=this.getVirtualDisks().get(vdName);
			int i;
			for(i=0;i<length;i++){
				String currentDirectory=newPosition.getName(i).toString();
				if(!destination.getDirectoryMap().containsKey(currentDirectory)){
					throw new InvalidInput("Invalid vfs path.")
				}
				destination=destination.getDirectoryMap().get(currentDirectory);
			}			
		}*/
			
		//The path is correct, then we change current position
	}
	
	
	/**
	 * list the information concerning files and directories contained in a directory
	 * @param vfsname : name of the virtual disk
	 * @param arg : argument
	 * arg = "" : we list names and types of elements
	 * arg = "-l" : we list names and type and size of elements
	 * @param vfsPath : the path of the directory we are looking at
	 * @return : an array of information (String), depends on arg
	 * @throws InvalidInput
	 */
	public ArrayList<ArrayList<String>>show(String vfsname, String arg, String vfsPath) throws InvalidInput{
		Path path;
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		if (vfsPath == "") path = Paths.get(this.getVirtualDisks().get("vfsname").getCurrentPosition());
		else {
		path = Paths.get(vfsPath);
		}
		
		Directory currentDirectory = goPath(vfsname,path,1);
		
			
		if (arg == "-l"|| arg == ""){
			for ( Fichier file : currentDirectory.getFileMap().values()){
				ArrayList<String> list=new ArrayList<String>();
				list.add(file.getName());
				if(arg=="-l"){
					list.add(Long.toString(file.getSize()));
				}
				list.add("f");
				
				result.add(list);
			}
			for (Directory d : currentDirectory.getDirectoryMap().values()){
				ArrayList<String> listd=new ArrayList<String>();
				listd.add(d.getName());
				if(arg=="-l"){
					listd.add(Long.toString(d.getSize()));
				}
				listd.add("d");
				
				result.add(listd);
				
			}	
		}
		return result;
	}
	
	
	/**
	 * Search for a file inside a VFS with a given name, starting from an specified location in the virtualDisk
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
		Directory dir = goPath(vDName,p,1);
	
	
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


	public HashMap<String, VirtualDisk> getVirtualDisks() {
		return virtualDisks;
	}

	/**
	 * Checks if a name is correct (characters |/\:*?"<> not allowed).
	 * @param name, the tested name
	 * @throws InvalidSyntaxException, unfollowed rules.
	 */
	public void checkSyntax(String name) throws InvalidSyntaxException{
		
		if(name==null||name.contains("|")||name.contains("/")||name.contains("\\")||name.contains(":")||name.contains("*")||name.contains("?")||name.contains("\"")||name.contains("<")||name.contains(">")){
			throw new InvalidSyntaxException(name+ " has an invalid syntax for this VFS");
		}
		
	}
}
