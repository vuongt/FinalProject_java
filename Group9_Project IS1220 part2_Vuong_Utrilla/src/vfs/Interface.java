package vfs;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Interface {

	
	public static void main(String[] args){
		
		Scanner sc=new Scanner(System.in);
		StringTokenizer st;
		String s;
		boolean on=true;
		int n;
		String vdName;//common elements in most commands
		String vfsPath;
		String hostPath;
		
		while(on){
			System.out.println("vfs:");//????
			
			
			s=sc.nextLine();
			st=new StringTokenizer(s," ");//falta probar
			
			//if the user introduces no input we run the loop again
			if(!st.hasMoreTokens()){
				continue;
			}
			
			switch(st.nextToken()){
			
			case "ls":
				
				if(!st.hasMoreTokens()){//No vdName
					System.out.println("Invalid input. Type 'help ls' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				
				if(st.countTokens()==0){//No path, no arg are given. 
					
					//TO DO:Display list (names,type) in current directory
					
				}else if(st.countTokens()==1){//A path or an arg is given
					String argOrPath=st.nextToken();
					
					if(argOrPath.equals("-l")){
						
						//TO DO:Display list (names,size,type) in current directory
					
					}else{//The second token is read as a vfspath
						
						vfsPath=st.nextToken();
						//TO DO:Display list (names,type) in specified path
					}
				}else if(st.countTokens()==2){//An argument an a path are given
					String arg=st.nextToken();
					
					if(arg.equals("-l")){
						vfsPath=st.nextToken();
						
						//TO DO: Display list(names,size,type) in specified path
						
					}else{
						System.out.println("Invalid input. Type 'help ls' to display instructions.");
						break;
					}
				}else{
					System.out.println("Invalid input. Type 'help ls' to display instructions.");
					break;
				}
				break;
				
				
			case "cd"://Change current position
				
				if(!(st.countTokens()==2)){
					System.out.println("Invalid input. Type 'help cd' to display instructions.");
					break;
				}
			
				vdName=st.nextToken();
				vfsPath=st.nextToken();
				
				//TO DO: change current position of vdname to vfspath
				
				break;
				
			case "touch"://create file
				if(!(st.countTokens()==3)){
					System.out.println("Invalid input. Type 'help touch' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				String fileName=st.nextToken();
				vfsPath=st.nextToken();
				
				//TO DO: create a file vdName,fileName,vfsPath
				break;
			case "mkdir"://create directory
				
				if(!(st.countTokens()==3)){
					System.out.println("Invalid input. Type 'help mkdir' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				String dirName=st.nextToken();				
				vfsPath=st.nextToken();
				
				//TO DO: create a dir vdName, dirName, vfsPath
				break;
			case "rm"://remove a file or directory
				
				if(!(st.countTokens()==2)){
					System.out.println("Invalid input. Type 'help rm' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				vfsPath=st.nextToken();
				
				//TO DO: remove file or directory vdName, vfsPath
				break;
			//FALTA	
			case "rename"://rename file or directory
				break;
			case "mv"://move file/dir
				break;
			case "cp"://copy file/dir
				break;	
				
				
			case "exp"://export file or directory
				if(!(st.countTokens()==3)){
					System.out.println("Invalid input. Type 'help exp' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				vfsPath=st.nextToken();//includes the item to export
				hostPath=st.nextToken();
				
				//TO DO
				break;
				
			case "impvfs"://import file or dir
				if(!(st.countTokens()==3)){
					System.out.println("Invalid input. Type 'help impvfs' to display instructions.");
					break;
				}
				
				hostPath=st.nextToken();//includes the item to import
				vdName=st.nextToken();
				vfsPath=st.nextToken();
				
				//TO DO hostPath, vdName, vfsPath
				break;
				
			case "crvfs"://create VD
				if(!(st.countTokens()==2)){
					System.out.println("Invalid input. Type 'help crvfs' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				try{
					int size=Integer.parseInt(st.nextToken());
				}catch(NumberFormatException e){
					System.out.println("Invalid input. Type 'help crvfs' to display instructions.");
					break;
					
				}
				
				//TO  DO vdName size
				break;
			case "rmvfs"://remove VD
				if(!(st.countTokens()==1)){
					System.out.println("Invalid input. Type 'help crvfs' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				
				//TO DO vdName 
				break;
			case "expvfs"://export VD
				if(!(st.countTokens()==2)){
					System.out.println("Invalid input. Type 'help expvfs' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				hostPath=st.nextToken(); //path of the directory where we will export (not containing the name of the file, automatically assigned by the method)
				
				//TO DO vdName hostPath
				
				break;
			case "savevfs"://saveVD
				
				if(!(st.countTokens()==1)){
					System.out.println("Invalid input. Type 'help savevfs' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				
				//TO DO vdName 
				
				break;
			case "free":
				
				if(!(st.countTokens()==1)){
					System.out.println("Invalid input. Type 'help free' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				
				//TO DO vdName
				break;
			case "pwd"://current position
				if(!(st.countTokens()==1)){
					System.out.println("Invalid input. Type 'help pwd' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				
				//TO DO vdName
				break;
	
			case "find":
				if(!(st.countTokens()==2)){
					System.out.println("Invalid input. Type 'help find' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				String searchFile=st.nextToken();
				
				//TO DO vdName searchFile
				break;
			case "help":
				
				if(!st.hasMoreTokens()){
					System.out.println("******************VFS INTERFACE******************\nType 'help <commandName>' to get the instructions of each command.");
					System.out.println("ls-List the elements in a position of a virtual disk");
					System.out.println("cd-Change current position of a virtual disk");
					System.out.println("touch-Create a file");
					System.out.println("mkdir-Create a directory");
					System.out.println("rm-Remove a file/directory");
					System.out.println("cp");
					System.out.println("mv");
					System.out.println("exp-Export a file/directory into the host file system");
					System.out.println("impvfs-Import a file/directory into the host file system");
					System.out.println("crvfs-Create a new virtual disk");
					System.out.println("rmvfs-Remove a virtual disk");
					System.out.println("ls-List the elements is a position of a virtual disk");
					
				}else{
					String command=st.nextToken();
					
					switch(command):
						case "ls":
						case "cd":
						case 
					
					
					
					
					
				}
				
				break;
			default:
				break;
			
			
			}
			
			
			
		}
		
		sc.close();
		
	}
}
