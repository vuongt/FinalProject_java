

package vfs;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Interface {

	
	
	
	
	public static void main(String[] args){
		
		VFS vfs=new VFS();
		Scanner sc=new Scanner(System.in);
		
		StringTokenizer st;
		String s;
		boolean on=true;
		
		String vdName;//common elements in most commands
		String vfsPath;
		String hostPath;
		
		
		//Behaviour
		CommandBehaviour behaviour=null;
		
		while(on){
			
			
			//Demand of a command
			System.out.println("vfs:");
			s=sc.nextLine();
			st=new StringTokenizer(s," ");
			
			//If the user introduces no input we run the loop again
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
				
				int intarg;
				if(st.countTokens()==0){//No path, no arg are given.
					
					intarg=0;
					//update of behaviour
					behaviour=new CommandLS(vfs,vdName,intarg);
					
				}else if(st.countTokens()==1){//A path or an arg is given
					String argOrPath=st.nextToken();
					
					if(argOrPath.equals("-l")){
						intarg = 1;
						behaviour=new CommandLS(vfs,vdName,intarg);
					
					}else{//The second token is read as a vfspath, no argument 'arg'
						intarg=0;
						vfsPath=argOrPath;
						
						//update of behaviour
						behaviour=new CommandLS(vfs,vdName,intarg,vfsPath);
					}
				}else if(st.countTokens()==2){//An argument and a path are given
					String arg=st.nextToken();
					
					if(arg.equals("-l")){
						int argInt=1;
						vfsPath=st.nextToken();
						
						//update of behaviour
						behaviour=new CommandLS(vfs,vdName,argInt,vfsPath);
						
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
				
				//update of behaviour
				behaviour=new CommandCD(vfs,vdName,vfsPath);
				
				break;
				
			case "touch"://create file
				if(!(st.countTokens()==3)){
					System.out.println("Invalid input. Type 'help touch' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				String fileName=st.nextToken();
				vfsPath=st.nextToken();
				
				//update of behaviour
				behaviour=new CommandTOUCH(vfs,vdName,fileName,vfsPath);
				break;
				
			case "mkdir"://create directory
				
				if(!(st.countTokens()==3)){
					System.out.println("Invalid input. Type 'help mkdir' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				String dirName=st.nextToken();				
				vfsPath=st.nextToken();
				
				//update of behaviour
				behaviour=new CommandMKDIR(vfs,vdName,dirName,vfsPath);
				break;
				
			case "rm"://remove a file or directory
				
				if(!(st.countTokens()==2)){
					System.out.println("Invalid input. Type 'help rm' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				vfsPath=st.nextToken();
				
				//update of behaviour
				behaviour=new CommandRM(vfs,vdName,vfsPath);
				break;
				
			case "mv":
				//if the two paths are different and path Target is a directory,mv move the object whose path is pathSource to the DIRECTORY whose path is pathTarget
				////if the two paths are different and path Target is a file,mv throws exception
				//if the parents of two paths are similar then mv renames the file/directory whose path is pathSource
				if (st.countTokens()!= 3){
					System.out.println("Invalid input.Type 'help mv' to display instructions.");
					break;
				}
				vdName = st.nextToken();
				String pathSource = st.nextToken();
				String pathTarget = st.nextToken();
				
				behaviour = new CommandMV(vfs,vdName,pathSource,pathTarget);
				break;
			case "cp":
				//copy file/directory to a directory (copy object)
				//copy the content of a file to an other file (the content of the destination file will be replaced by the source file's)
				//throws exception when user want to copy a directory to a file
				if (st.countTokens()!= 3){
					System.out.println("Invalid input.Type 'help mv' to display instructions.");
					break;
				}
				vdName = st.nextToken();
				String stringSource = st.nextToken();
				String stringTarget = st.nextToken();
				
				behaviour = new CommandCP(vfs,vdName,stringSource,stringTarget);
				break;		
				
				
			case "exp"://export file or directory
				if(!(st.countTokens()==3)){
					System.out.println("Invalid input. Type 'help exp' to display instructions.");
					break;
				}
				hostPath=st.nextToken();
				vdName=st.nextToken();
				vfsPath=st.nextToken();//includes the item to export
				
				
				//update of behaviour
				behaviour=new CommandEXP(vfs,hostPath,vdName,vfsPath);
				break;
				
			case "impvfs"://import file or dir
				if(!(st.countTokens()==3)){
					System.out.println("Invalid input. Type 'help impvfs' to display instructions.");
					break;
				}
				
				hostPath=st.nextToken();//includes the item to import
				vdName=st.nextToken();
				vfsPath=st.nextToken();
				
				//update of behaviour
				behaviour=new CommandIMPVFS(vfs,hostPath,vdName,vfsPath);
				break;
				
			case "crvfs"://create VD
				if(!(st.countTokens()==2)){
					System.out.println("Invalid input. Type 'help crvfs' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				long sizeMax;
				try{
					sizeMax=Long.parseLong(st.nextToken());
				}catch(NumberFormatException e){
					System.out.println("Invalid input. Type 'help crvfs' to display instructions.");
					break;
					
				}
				
				//update of behaviour
				behaviour=new CommandCRVFS(vfs,vdName,sizeMax);
				break;
			case "rmvfs"://remove VD
				if(!(st.countTokens()==1)){
					System.out.println("Invalid input. Type 'help crvfs' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				
				//update of behaviour
				behaviour=new CommandRMVFS(vfs,vdName);
				break;
			case "expvfs"://export VD
				if(!(st.countTokens()==2)){
					System.out.println("Invalid input. Type 'help expvfs' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				hostPath=st.nextToken(); //path of the directory where we will export (not containing the name of the file, automatically assigned by the method)
				
				//update of behaviour
				behaviour=new CommandEXPVFS(vfs,vdName,hostPath);
				break;
			case "savevfs"://saveVD
				
				if(!(st.countTokens()==1)){
					System.out.println("Invalid input. Type 'help savevfs' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				
				//update of behaviour
				behaviour=new CommandSAVEVFS(vfs,vdName);
				break;
				
			case "free":
				
				if(!(st.countTokens()==1)){
					System.out.println("Invalid input. Type 'help free' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				//update of behaviour
				behaviour=new CommandFREE(vfs,vdName);
				break;
				
			case "pwd":// print current position
				if(!(st.countTokens()==1)){
					System.out.println("Invalid input. Type 'help pwd' to display instructions.");
					break;
				}
				
				vdName=st.nextToken();
				
				//update of behaviour
				behaviour=new CommandPWD(vfs,vdName);
				break;
	
			case "find":
				
			
				if(st.countTokens()==2){
					vdName=st.nextToken();
					String searchFile=st.nextToken();
					
					//update of behaviour
					behaviour=new CommandFIND(vfs,vdName,searchFile);
				}else if(st.countTokens()==3){
					vdName=st.nextToken();
					String searchFile=st.nextToken();
					vfsPath=st.nextToken();
					
					//update of behaviour
					behaviour=new CommandFIND(vfs,vdName,searchFile,vfsPath);
				}else{
					System.out.println("Invalid input. Type 'help find' to display instructions.");
					break;
				}
				
				
				
				break;
				
			case "help":
				
				if(!st.hasMoreTokens()){
					
					//update of behaviour
					behaviour=new CommandHELP(vfs);
					
				}else if(st.countTokens()==1){
					String command=st.nextToken();
					
					//update of behaviour
					behaviour=new CommandHELP(vfs,command);
			
				}else{
					System.out.println("Invalid input. Type 'help' to display instructions.");
					break;
				}
				
				break;
				
			case "stop":
				if(st.hasMoreTokens()){
					System.out.println("Invalid input. Type 'help stop' to display instructions.");
					break;
				}
				
				System.out.println("Would you like to close the system?(yes/no):");
				String answer=sc.nextLine();
				if(answer.equalsIgnoreCase("yes")){
					on=false;
					System.out.println("**************************************************");
				}else if(answer.equalsIgnoreCase("no")){
					
				}else{
					System.out.println("Invalid input. Type 'help stop' to display instructions.");
					break;
				}
				break;
				
			default:
				
				System.out.println("Invalid input. Type 'help' to display instructions.");
				break;
			}
			
			
			
			
			
			
			
			//Executing the command chosen by the user (by the method 'go()' of the attribute 'behaviour'.
			//This method perform different strategies depending on how 'behaviour' has been updated (with which class among the ones that extend CommandBehaviour)
			//(Application of strategy pattern)
			//Handling of the different exceptions thrown by the vfs methods.
			
			if(behaviour != null){// use != because if behaviour is really null then NullPointerException is thrown
				try{
					behaviour.go();
					
				}catch(DirectoryNotFoundException e){//To distinguish from IOException
					System.out.println(e.getMessage());
					
				}catch(FileNotFoundException e){//To distinguish from IOException
					System.out.println(e.getMessage());
					
				}catch(InvalidPathException e){
					System.out.println("Invalid path: "+ e.getMessage());
					
					
				}catch(IOException e){
					System.out.println("An I/O exception occurred while trying to connect with the host file system.");
				
				}catch(InvalidNameException e){
					System.out.println(e.getMessage());
					System.out.println("Invalid file/directory name characters: ' ','|','\','/','?','*','\"',':','<','>'");
					
				}catch(Exception e){//For the moment, no further specialization in the treatment of the rest of exception
					System.out.println(e.getMessage());
				}finally{
					
					behaviour=null;//Reseting behaviour for the next iteration. 
				}
				
			}
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		sc.close();
		
	}
}
