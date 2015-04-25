package vfs;

import java.nio.file.Path;


public class CommandMV extends CommandBehaviour{
	private String vdName;
	private String pathSource;
	private String pathTarget;

	public CommandMV(VFS vfs,String vdName,String pathSource,String pathTarget) {
		super(vfs);
		this.vdName = vdName;
		this.pathSource = pathSource;
		this.pathTarget = pathTarget;
	}

	@Override
	public void go() throws InvalidInput, DuplicatedNameException, InvalidNameException {
		
		boolean sourceIsFile= vfs.checkPath(vdName,pathSource);
		
		String omitLastElement;
		
		//We check if the given path, without the last element, is well constructed.
		
		int i=pathTarget.lastIndexOf("/");
		
			
		if(pathTarget=="/"&&i==(pathTarget.length()-1)){//It ends up with /.
			pathTarget=pathTarget.substring(0, i);//We take it away
			i=pathTarget.lastIndexOf("/");
			
		}

		if(i==-1){//no /.There's just one element.We just have to check vdName is right.
			vfs.checkPath(vdName,"/");
		}else if(i==0){//There's just one element.We just have to check vdName is right.
			vfs.checkPath(vdName,"/");
		}else{
			omitLastElement=pathTarget.substring(0, i);
			vfs.checkPath(vdName,omitLastElement);
		}
		//sourcePath and targetPath(until the element before the last element) are well constructed.
		//Once we have arrived here (no exceptions thrown) if we run checkPath through targetPath
		//we know that it's the last element that doesn't exist!
		
		Path source = vfs.toAbsolutePath(vdName,this.pathSource);
		Path target = vfs.toAbsolutePath(vdName,this.pathTarget);
		
		if (target.getParent() == null) { //the destination is root, then it can only be a "move" situation
			vfs.move(vdName, pathSource, pathTarget);
			System.out.println("your file or directory is moved to " + target.toString());
		}
		else{
			//here you said boolean targetIsFile = vfs.checkPath(this.vdName,target.getParent().toString()); 
			//but this is not the last element, it will always be false. Plus, calling checkPath with sth that went through a Paths.get gives problems in windows
			boolean lastElementExistsInVfs=true;
			boolean targetIsFile=false;
			//if we run checkPath through targetPath
			//we know that it's the last element that doesn't exist!
			try{
				targetIsFile=vfs.checkPath(vdName, pathTarget);//if element exists true or false
																//if it doesn't, false.(we have to initialize it to sth)
			}catch(InvalidInput e){
				//this means it doesn't exist
				lastElementExistsInVfs=false;
			}
			
			
			if (source.getParent() == null) throw new InvalidInput("you cannot move or rename the root");
			
			//LastElement of target doesn't exist.
			//	It has to be a rename operation (for that both paths until the last element need to be equal)
			//  If this condition is not accomplished, then invalid Input. (we don't allow move+rename at the same time).
			if(!lastElementExistsInVfs){
				if (source.getParent().equals(target.getParent())){
					String newName = target.getFileName().toString();
					if (sourceIsFile) {
						vfs.renameFile(vdName, pathSource, newName);
						System.out.println("Your file is renamed to "+ newName);
					}
					else {
						vfs.renameDirectory(vdName, pathSource, newName);
						System.out.println("Your directory is renamed to  "+newName);
					}
			
				
				}else{
					throw new InvalidInput("Forbidden operation.Not allowed to move and rename at the same time.");
				}
				//LastElement of target exists.
				//	It has to be a move operation. 
				//  For that target has to be a directory. 
				//  If this condition is not accomplished, then invalid Input.(last element exists and we can't move anything into a file)
			}else{
			
			
				if (targetIsFile) throw new InvalidInput("Invalid Input: "+pathTarget+" is a file in this virtual disk. The destination must be a directory");
				else {
					vfs.move(vdName, pathSource, pathTarget);
					System.out.println("your file/directory is moved to " + target.toString());
				}
			}
			
		}
		
		/* An altenative 
		Path source = vfs.toAbsolutePath(vdName,this.pathSource);
		Path target = vfs.toAbsolutePath(vdName,this.pathTarget);
		
		if (target.getParent() == null) { //the destination is root, then it can only be a "move" situation
			vfs.move(vdName, pathSource, pathTarget);
			System.out.println("your file or directory is moved to " + target.toString());
		}
		else{
			boolean sourceIsFile = vfs.checkPath(this.vdName,this.pathSource);
			if (source.getParent() == null) throw new InvalidInput("you cannot move or rename the root");
			else{
				if (source.getParent().equals(target.getParent())){
				//rename situation
				//if source.getparent is correct (cause we did checkpath before), target.getParent is correct too
					String newName = target.getFileName().toString();
					if (sourceIsFile) {
						vfs.renameFile(vdName, pathSource, newName);
						System.out.println("Your file is renamed");
					}
					else {
						vfs.renameDirectory(vdName, pathSource, newName);
						System.out.println("Your directory is renamed");
					}
				}
				else {//move situation
					boolean targetIsFile = vfs.checkPath(this.vdName,this.pathTarget);
					if (targetIsFile) throw new InvalidInput("the destination must be a directory");
					else {
						vfs.move(vdName, pathSource, pathTarget);
						System.out.println("your file/directory is moved to " + target.toString());
					}
				}
			}
		}*/
		
	}

}
