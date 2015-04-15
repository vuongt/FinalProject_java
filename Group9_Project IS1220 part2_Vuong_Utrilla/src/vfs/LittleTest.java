package vfs;

import java.nio.file.Path;
import java.util.Scanner;
import java.nio.file.Paths;
import java.io.File;

public class LittleTest {
	
	
	public static void main(String[] args){
		
		
		/*true*/
		
		Path p11=Paths.get("/");

		System.out.println("path 11: "+p11.toString());
		System.out.println(p11.getNameCount());
		System.out.println(p11.resolve("/london/"));
	
		
		Path p15=Paths.get("/london");
		Path p16=p15.resolve("london2/");
		System.out.println(p16.toString());
		
		
		public boolean checkPath(String vdName, Path path, int n){
			
		
			int length=path.getNameCount();
			
			
			//The path contains just a root. We can just check if it's contained in the list of Virtual Disks.
			if(path.getNameCount()==0){
				
				if(n!=1){
					throw new InvalidInput("Invalid position.")
				}
				if(!this.getVirtualDisks().containsKey(vdName)){
					throw new InvalidInput("None existing virtual disk.");//I'd create another one...
				}
				
				return this.getVirtualDisks().get(vdName);
				
			}else{//The path is longer than the root.
			
			
				if(n<=0||n>length+1){
					throw new InvalidInput("Invalid position");
				}
				
				
				if(n==length+1){//The directory we search is the root
					
					if(!this.getVirtualDisks().containsKey(vdName)){
						throw new InvalidInput("None existing virtual disk.");//I'd create another one...
					}
					
					return  this.getVirtualDisks().get(vdName);
					
				}else{//The directory we search is a child of the root
					if(!this.getVirtualDisks().containsKey(vdName)){
						throw new InvalidInput("None existing virtual disk.");//I'd create another one...
					}
					
					Directory destination=this.getVirtualDisks().get(vdName);
					
					int i;
					for(i=0;i<=(length-n);i++){
						String currentDirectory=path.getName(i).toString();
						if(!destination.getDirectoryMap().containsKey(currentDirectory)){
							throw new InvalidInput("Invalid vfs path.");
							
						}
						
						destination=destination.getDirectoryMap().get(currentDirectory);
						
					}
					
					return destination;
				
				}
				
			}
			
		}
		
		public Directory goPath(String vfsname,Path path, int n)throws InvalidInput{
			//do a CHECKPATH
			
			
			if(){
			//just the root, what convention?
			}
			
			
			else {
				Integer pathLength = new Integer(path.getNameCount()); // the length of the path
				
				// if n is bigger than the length of the path then we return to the virtual disk (root)
				if (n > pathLength) return this.virtualDisks.get(vfsname);
				
				//n must be positive
				if (n < 1) {throw new InvalidInput("n must be positive");}
				else{
					Directory destination = this.virtualDisks.get(vfsname);
					for (int i=0; i <= (pathLength-n); i++){
						String currentDirectory = path.getName(i).toString();
						if (destination.getDirectoryMap().containsKey(currentDirectory))
							{destination = destination.getDirectoryMap().get(currentDirectory);}
						else { throw new InvalidInput("invalid path");}
					}
					return destination;
				}
			}
		}*/
		
		
		
		
	}

}
